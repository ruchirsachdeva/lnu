package com.lnu.foundation.service;

import com.lnu.foundation.model.SignupForm;
import com.lnu.foundation.model.TestSession;
import com.lnu.foundation.model.Therapy;
import com.lnu.foundation.model.User;
import com.lnu.foundation.repository.RoleRepository;
import com.lnu.foundation.repository.TestSessionRepository;
import com.lnu.foundation.repository.TherapyRepository;
import com.lnu.foundation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.google.api.Google;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by rucsac on 15/10/2018.
 */
@Service
public class UserService implements UserDetailsService, SocialUserDetailsService {

    private static final String RESEARCHER = "researcher";
    private static final String PHYSICIAN = "physician";
    @Autowired
    private UserRepository repository;

    @Autowired
    BaseProvider socialLoginBean;

    @Autowired
    TestSessionRepository testRepository;

    @Autowired
    TherapyRepository therapyRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getResearcher() {
        return repository.findByRole(roleRepository.findByName(RESEARCHER));
    }

    public List<User> getPhysician() {
        return repository.findByRole(roleRepository.findByName(PHYSICIAN));
    }

    public Collection<Therapy> getMedTherapies(User med) {
        return therapyRepository.findByMed(med);
    }

    public boolean isMedLoggedIn() {
        return isPhysicianLoggedIn() || isResearcherLoggedIn();
    }

    private boolean isPhysicianLoggedIn() {
        return socialLoginBean.getConnectionRepository().findPrimaryConnection(Google.class) != null;
    }

    private boolean isResearcherLoggedIn() {
        return socialLoginBean.getConnectionRepository().findPrimaryConnection(LinkedIn.class) != null;
    }

    public Collection<TestSession> getSessions(String username) {
        return testRepository.findByTest_Therapy_Med_Username(username);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public User signup(SignupForm signupForm) {
        final User user = new User();
        user.setEmail(signupForm.getEmail());
        user.setUsername(signupForm.getEmail());
        user.setFirstName(signupForm.getName());
        if(signupForm.getPassword()!=null) {
            user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
        }
        user.setProvider(signupForm.getProvider());
        repository.save(user);
        return user;
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public User save(User user) {
        repository.save(user);
        return user;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        com.lnu.foundation.model.User user = repository.findByUsername(email).orElseThrow(() -> new UsernameNotFoundException("No user found with email: " + email));
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("LOGGED_USER"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);


    }


    @Override
    public SocialUserDetails loadUserByUserId(String userId)
            throws UsernameNotFoundException, DataAccessException {
        final Optional<User> user = repository.findByUsername(userId);
        final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
        user.ifPresent(detailsChecker::check);
        return user.orElseThrow(() -> new UsernameNotFoundException("user not found."));
    }
}
