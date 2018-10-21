package com.lnu.foundation.controller;


import com.lnu.foundation.auth.TokenHandler;
import com.lnu.foundation.model.User;
import com.lnu.foundation.repository.UserRepository;
import com.lnu.foundation.service.*;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class LoginController {

    @Autowired
    FacebookProvider facebookProvider;

    @Autowired
    GoogleProvider googleProvider;

    @Autowired
    LinkedInProvider linkedInProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private Autologin autologin;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenHandler tokenHandler;
    @Autowired
    private SecurityContextService securityContextService;


    @RequestMapping(value = "/facebook", method = RequestMethod.GET)
    public String loginToFacebook(Model model) {
        return facebookProvider.getFacebookUserData(model, new User());
    }

    @RequestMapping(value = "/google", method = RequestMethod.GET)
    public String loginToGoogle(Model model) {
        return googleProvider.getGoogleUserData(model, new User());
    }

    @RequestMapping(value = "/linkedin", method = RequestMethod.GET)
    public String helloFacebook(Model model) {
        return linkedInProvider.getLinkedInUserData(model, new User());
    }

    @RequestMapping(value = {"/", "/login"})
    public AuthResponse login() {
        return securityContextService.currentUser().map(u -> {
            final String token = tokenHandler.createTokenForUser(u);
            return new AuthResponse(token);
        }).orElseThrow(RuntimeException::new); // it does not happen.
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.OPTIONS)
    public ResponseEntity loginOption() {
        return ResponseEntity.ok().build();
    }


    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = {"/loginaa"}, method = RequestMethod.POST)
    public AuthResponse loginPost(@RequestBody AuthParams params) {
        final UsernamePasswordAuthenticationToken loginToken = params.toAuthenticationToken();
        final Authentication authentication = authenticationManager.authenticate(loginToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return securityContextService.currentUser().map(u -> {
            final String token = tokenHandler.createTokenForUser(u);
            return new AuthResponse(token);
        }).orElseThrow(RuntimeException::new); // it does not happen.
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = {"/api/auth"}, method = RequestMethod.POST)
    public AuthResponse auth(@RequestBody(required = false) AuthParams params) throws AuthenticationException {
        if (params != null) {
            final UsernamePasswordAuthenticationToken loginToken = params.toAuthenticationToken();
            final Authentication authentication = authenticationManager.authenticate(loginToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        return securityContextService.currentUser().map(u -> {
            final String token = tokenHandler.createTokenForUser(u);
            return new AuthResponse(token);
        }).orElseThrow(RuntimeException::new); // it does not happen.
    }


    @GetMapping("/registration")
    public String showRegistration(User user) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(HttpServletResponse httpServletResponse, Model model, @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        user.setProvider("REGISTRATION");
        // Save the details in DB
        if (StringUtils.isNotEmpty(user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }

        user.setUsername(user.getEmail());

        userRepository.save(user);

        autologin.setSecuritycontext(user);

        model.addAttribute("loggedInUser", user);
        return "secure/user";
    }

    /**
     * If we can't find a user/email combination
     */
    @RequestMapping("/login-error")
    public ResponseEntity loginError(Model model) {
        model.addAttribute("loginError", true);
        return ResponseEntity.badRequest().build();
    }


    @Value
    private static final class AuthParams {
        private final String username;
        private final String password;


        UsernamePasswordAuthenticationToken toAuthenticationToken() {
            return new UsernamePasswordAuthenticationToken(username, password);
        }
    }

    @Value
    private static final class AuthResponse {
        private final String token;
    }

}
