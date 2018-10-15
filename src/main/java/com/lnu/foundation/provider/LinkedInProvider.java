package com.lnu.foundation.provider;

import com.lnu.foundation.model.Therapy;
import com.lnu.foundation.model.User;
import com.lnu.foundation.model.UserBean;
import com.lnu.foundation.repository.RoleRepository;
import com.lnu.foundation.repository.TherapyRepository;
import com.lnu.foundation.repository.UserRepository;
import com.rometools.rome.feed.synd.SyndEntry;
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
import java.util.List;

@Service
public class LinkedInProvider {

    private static final String LINKED_IN = "linkedIn";

    private static final String REDIRECT_LOGIN = "redirect:/login";

    @Autowired
    BaseProvider socialLoginBean;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TherapyRepository therapyRepository;

    @Autowired
    RoleRepository roleRepository;

    public String getLinkedInUserData(Model model, UserBean userForm) {

        ConnectionRepository connectionRepository = socialLoginBean.getConnectionRepository();
        if (connectionRepository.findPrimaryConnection(LinkedIn.class) == null) {
            return REDIRECT_LOGIN;
        }
        populateUserDetailsFromLinkedIn(userForm);
        model.addAttribute("loggedInUser", userForm);
        List<User> physician = userRepository.findByRole(roleRepository.findByName("researcher"));
        List<Therapy> therapies = therapyRepository.findByMed(physician.get(0));
        model.addAttribute("therapies", therapies);
        SyndFeed feed = null;
        try {
            String url = "https://www.news-medical.net/tag/feed/parkinsons-disease.aspx";

            try (XmlReader reader = new XmlReader(new URL(url))) {
                feed = new SyndFeedInput().build(reader);
                System.out.println(feed.getTitle());
                System.out.println("***********************************");
                for (SyndEntry entry : feed.getEntries()) {
                    System.out.println(entry);
                    System.out.println("***********************************");
                }
                System.out.println("Done");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**link = "https://www.news-medical.net/"
         comments = "https://www.news-medical.net/news/20181015/Vast-majority-of-dementia-cases-may-arise-from-spontaneous-genetic-errors.aspx#comment"
         uri = "https://www.news-medical.net/post.aspx?id=9ebcac5f-3e15-49fc-94d4-1701dae2a73c"
         title = {SyndContentImpl@10705} "SyndContentImpl.mode=null\nSyndContentImpl.type=null\nSyndContentImpl.interface=interface com.rometools.rome.feed.synd.SyndContent\nSyndContentImpl.value=Vast majority of dementia cases may arise from spontaneous genetic errors\n"
         entries = {ArrayList@10615}  size = 10
         description = {SyndContentImpl@10611} "SyndContentImpl.mode=null\nSyndContentImpl.type=null\nSyndContentImpl.interface=interface com.rometools.rome.feed.synd.SyndContent\nSyndContentImpl.value=Latest parkinsons disease News and Research\n"
         link = "https://www.news-medical.net/news/20181015/Vast-majority-of-dementia-cases-may-arise-from-spontaneous-genetic-errors.aspx"
         title = {SyndContentImpl@10610} "SyndContentImpl.mode=null\nSyndContentImpl.type=null\nSyndContentImpl.interface=interface com.rometools.rome.feed.synd.SyndContent\nSyndContentImpl.value=News-Medical.Net parkinsons disease News Feed\n"
         feedType = "rss_2.0"
         0 = {SyndEntryImpl@10681} "SyndEntryImpl.comments=https://www.news-medical.net/news/20181015/Vast-majority-of-dementia-cases-may-arise-from-spontaneous-genetic-errors.aspx#comment\nSyndEntryImpl.author=\nSyndEntryImpl.wireEntry=null\nSyndEntryImpl.link=https://www.news-medical.net/news/20181015/Vast-majority-of-dementia-cases-may-arise-from-spontaneous-genetic-errors.aspx\nSyndEntryImpl.description.mode=null\nSyndEntryImpl.description.type=text/html\nSyndEntryImpl.description.interface=interface com.rometools.rome.feed.synd.SyndContent\nSyndEntryImpl.description.value=Only a small proportion of cases of dementia are thought to be inherited - the cause of the vast majority is unknown.\nSyndEntryImpl.foreignMarkup=[]\nSyndEntryImpl.source=null\nSyndEntryImpl.updatedDate=null\nSyndEntryImpl.title=Vast majority of dementia cases may arise from spontaneous genetic errors\nSyndEntryImpl.interface=interface com.rometools.rome.feed.synd.SyndEntry\nSyndEntryImpl.enclosures=[]\nSyndEntryImpl.uri=https://www.news-medical.net/post.aspx?i"
         description = {SyndContentImpl@10706} "SyndContentImpl.mode=null\nSyndContentImpl.type=text/html\nSyndContentImpl.interface=interface com.rometools.rome.feed.synd.SyndContent\nSyndContentImpl.value=Only a small proportion of cases of dementia are thought to be inherited - the cause of the vast majority is unknown.\n"
         modules = {ArrayList@10709}  size = 1
         date = {ArrayList@10728}  size = 1
         modules = {ArrayList@10616}  size = 1
         0 = {DCModuleImpl@10654} "DCModuleImpl.date=null\nDCModuleImpl.formats=[]\nDCModuleImpl.sources=[]\nDCModuleImpl.rightsList[0]=null\nDCModuleImpl.creators=[]\nDCModuleImpl.subject=null\nDCModuleImpl.description=null\nDCModuleImpl.language=en-GB\nDCModuleImpl.source=null\nDCModuleImpl.type=null\nDCModuleImpl.title=null\nDCModuleImpl.interface=interface com.rometools.rome.feed.module.DCModule\nDCModuleImpl.descriptions=[]\nDCModuleImpl.coverages=[]\nDCModuleImpl.relation=null\nDCModuleImpl.contributor=null\nDCModuleImpl.rights=null\nDCModuleImpl.publishers=[]\nDCModuleImpl.coverage=null\nDCModuleImpl.identifier=null\nDCModuleImpl.types=[]\nDCModuleImpl.creator=null\nDCModuleImpl.languages[0]=en-GB\nDCModuleImpl.identifiers=[]\nDCModuleImpl.subjects=[]\nDCModuleImpl.format=null\nDCModuleImpl.dates=[]\nDCModuleImpl.titles=[]\nDCModuleImpl.uri=http://purl.org/dc/elements/1.1/\nDCModuleImpl.publisher=null\nDCModuleImpl.contributors=[]\nDCModuleImpl.relations=[]\n"
         language = {ArrayList@10668}  size = 1
         **/

        model.addAttribute("feed", feed);
        return "researcher";
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
