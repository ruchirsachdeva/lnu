package com.lnu.foundation.controller;

import com.lnu.foundation.model.TestSession;
import com.lnu.foundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by rucsac on 10/10/2018.
 */
@RestController
public class UserController {


    @Autowired
    UserService service;

    @GetMapping("/tests")
    public Collection<TestSession> getTestSessions() {
        Collection<TestSession> sessions = new ArrayList<>();
        if (service.isPhysician()) {
            sessions = service.getPhysicianSessions();
        } else if (service.isResearcher()) {
            sessions = service.getResearcherSessions();
        }
        return sessions.stream()
                .collect(Collectors.toList());
    }
}
