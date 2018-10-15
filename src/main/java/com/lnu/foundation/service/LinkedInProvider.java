package com.lnu.foundation.service;

import com.lnu.foundation.model.UserBean;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.linkedin.api.LinkedInProfileFull;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.net.URL;

@Service
public class LinkedInProvider {

    private static final String LINKED_IN = "linkedIn";

    private static final String REDIRECT_LOGIN = "redirect:/login";

    @Autowired
    BaseProvider socialLoginBean;

    @Autowired
    UserService service;

    public String getLinkedInUserData(Model model, UserBean userForm) {

        ConnectionRepository connectionRepository = socialLoginBean.getConnectionRepository();
        if (connectionRepository.findPrimaryConnection(LinkedIn.class) == null) {
            return REDIRECT_LOGIN;
        }
        populateUserDetailsFromLinkedIn(userForm);
        model.addAttribute("loggedInUser", userForm);
        model.addAttribute("therapies", service.getResearcherTherapies());

        model.addAttribute("feed", getRSSFeed());
        return "researcher";
    }

    private SyndFeed getRSSFeed() {
        SyndFeed feed = null;
        try {
            String url = "https://www.news-medical.net/tag/feed/parkinsons-disease.aspx";
            try (XmlReader reader = new XmlReader(new URL(url))) {
                feed = new SyndFeedInput().build(reader);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feed;
    }

    private void populateUserDetailsFromLinkedIn(UserBean userForm) {
        LinkedIn linkedIn = socialLoginBean.getLinkedIn();
        LinkedInProfileFull linkedInUser = linkedIn.profileOperations().getUserProfileFull();
        userForm.setEmail(linkedInUser.getEmailAddress());
        userForm.setFirstName(linkedInUser.getFirstName());
        userForm.setLastName(linkedInUser.getLastName());
        userForm.setImage(linkedInUser.getProfilePictureUrl());
        userForm.setProvider(LINKED_IN);
    }

}
