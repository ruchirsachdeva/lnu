package com.lnu.foundation.controller;

import com.lnu.foundation.model.TestSession;
import com.lnu.foundation.model.User;
import com.lnu.foundation.provider.BaseProvider;
import com.lnu.foundation.repository.RoleRepository;
import com.lnu.foundation.repository.TestSessionRepository;
import com.lnu.foundation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.google.api.Google;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rucsac on 10/10/2018.
 */
@RestController
public class UserController {
    @Autowired
    private UserRepository repository;

    @Autowired
    BaseProvider socialLoginBean;

    @Autowired
    TestSessionRepository testRepository;

    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/physician/tests")
    public Collection<TestSession> getPhysicianTests() {
        ConnectionRepository connectionRepository = socialLoginBean.getConnectionRepository();
        if (connectionRepository.findPrimaryConnection(Google.class) == null) {
            return Collections.emptyList();
        }
        List<User> physician = repository.findByRole(roleRepository.findByName("physician"));
        return testRepository.findByTest_Therapy_Med(physician.get(0)).stream()
                .collect(Collectors.toList());
    }

    @GetMapping("/researcher/tests")
    public Collection<TestSession> getResearcherTests() {
        ConnectionRepository connectionRepository = socialLoginBean.getConnectionRepository();
        if (connectionRepository.findPrimaryConnection(LinkedIn.class) == null) {
            return Collections.emptyList();
        }
        List<User> physician = repository.findByRole(roleRepository.findByName("researcher"));
        return testRepository.findByTest_Therapy_Med(physician.get(0)).stream()
                .collect(Collectors.toList());
    }
}
