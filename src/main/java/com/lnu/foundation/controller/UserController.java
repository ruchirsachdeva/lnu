package com.lnu.foundation.controller;

import com.lnu.foundation.model.TestSession;
import com.lnu.foundation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Created by rucsac on 10/10/2018.
 */
@RestController
public class UserController {


    @Autowired
    UserService service;

    @GetMapping("/user/{username}/tests")
    public Collection<TestSession> getTestSessions(@PathVariable String username) {
        Collection<TestSession> sessions = service.isMedLoggedIn() ? service.getSessions(username) : Collections.emptyList();
        return sessions.stream()
                .collect(Collectors.toList());
    }
}
