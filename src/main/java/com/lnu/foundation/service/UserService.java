package com.lnu.foundation.service;

import com.lnu.foundation.model.TestSession;
import com.lnu.foundation.model.Therapy;
import com.lnu.foundation.model.User;
import com.lnu.foundation.repository.RoleRepository;
import com.lnu.foundation.repository.TestSessionRepository;
import com.lnu.foundation.repository.TherapyRepository;
import com.lnu.foundation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.google.api.Google;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * Created by rucsac on 15/10/2018.
 */
@Service
public class UserService {

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
}
