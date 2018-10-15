package com.lnu.foundation.service;

import com.lnu.foundation.model.TestSession;
import com.lnu.foundation.model.Therapy;
import com.lnu.foundation.model.User;
import com.lnu.foundation.provider.BaseProvider;
import com.lnu.foundation.repository.RoleRepository;
import com.lnu.foundation.repository.TestSessionRepository;
import com.lnu.foundation.repository.TherapyRepository;
import com.lnu.foundation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.google.api.Google;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true)
    public Collection<TestSession> getPhysicianSessions() {
        List<User> physician = repository.findByRole(roleRepository.findByName(PHYSICIAN));
        return testRepository.findByTest_Therapy_Med(physician.get(0));
    }

    @Transactional(readOnly = true)
    public Collection<TestSession> getResearcherSessions() {
        List<User> researcher = repository.findByRole(roleRepository.findByName(RESEARCHER));
        return testRepository.findByTest_Therapy_Med(researcher.get(0));
    }

    public Collection<Therapy> getPhysicianTherapies() {
        List<User> physician = repository.findByRole(roleRepository.findByName(PHYSICIAN));
        return therapyRepository.findByMed(physician.get(0));
    }


    public Collection<Therapy> getResearcherTherapies() {
        List<User> researcher = repository.findByRole(roleRepository.findByName(RESEARCHER));
        return therapyRepository.findByMed(researcher.get(0));
    }


    public boolean isPhysician() {
        return socialLoginBean.getConnectionRepository().findPrimaryConnection(Google.class) != null;
    }

    public boolean isResearcher() {
        return socialLoginBean.getConnectionRepository().findPrimaryConnection(LinkedIn.class) != null;
    }
}
