package com.lnu.foundation.controller;


import com.lnu.foundation.auth.TokenHandler;
import com.lnu.foundation.model.User;
import com.lnu.foundation.repository.UserRepository;
import com.lnu.foundation.service.*;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.google.api.Google;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    private Autologin autologin;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenHandler tokenHandler;
    @Autowired
    private SecurityContextService securityContextService;
    @Autowired
    private ProviderSignInUtils providerSignInUtils;
    @Autowired
    private UserParamsValidator signupFormValidator;

    @Autowired
    private UserService userService;
    @Autowired
    private SocialUserService socialUserService;


    @RequestMapping(value = "/facebook", method = RequestMethod.GET)
    public String loginToFacebook(Model model) {
        return facebookProvider.getFacebookUserData(model, new User());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = {"/api/auth/google"}, method = RequestMethod.POST)
    public AuthResponse loginToGoogle(@RequestBody(required = false) AuthParams params) throws AuthenticationException {

        Connection<Google> googleConnection = socialUserService.getGoogleConnection(params.getToken());
        if (googleConnection != null) {
            User user = socialUserService.authenticateSocialUser(googleConnection)
                    .orElseGet(
                            () -> {
                                socialUserService.connectSocial(googleConnection);
                                return socialUserService.authenticateSocialUser(googleConnection).get();
                            }

                    );
            if (user == null) {
                userService.save(user);
            }
        }

        // providerSignInUtils.doPostSignUp(user.getUsername(), request);
        //todo MOVE LOGIN USER LOGIC TO THE RETURN METHOD CALLED FROM AUNGULAR CLIENT. THE CURRENT METHOD IS THE CALLBACK METHOD AFTER SIGNING UP
        //   Optional<String> token = MyUtil.logInUser(user);
        //      MyUtil.flash(redirectAttributes, "success", "signupSuccess");

        return securityContextService.currentUser().map(u -> {
            final String token = tokenHandler.createTokenForUser(u);
            return new AuthResponse(token);
        }).orElseThrow(RuntimeException::new); // it does not happen.
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
        private final String token;


        UsernamePasswordAuthenticationToken toAuthenticationToken() {
            return new UsernamePasswordAuthenticationToken(username, password);
        }
    }

    @Value
    private static final class AuthResponse {
        private final String token;
    }

}
