package com.currencycloud.fakebook.controllers;

import com.currencycloud.fakebook.entity.Friend;
import com.currencycloud.fakebook.entity.Recipient;
import com.currencycloud.fakebook.model.FriendForm;
import com.currencycloud.fakebook.service.FriendService;
import com.currencycloud.fakebook.service.RecipientService;
import com.currencycloud.fakebook.service.RestClientService;
import com.currencycloud.fakebook.utils.ModelEntityHelper;
import com.currencycloud.fakebook.validator.FriendFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by lekanomotayo on 15/03/2018.
 */


/***
 * Enables user to add existing social media friends as payment recipient.
 * This is one of the advantage of putting payment on social media.
 */
@Controller
public class FriendController {

    private static final Logger logger = LoggerFactory.getLogger(FriendController.class);

    @Autowired
    ModelEntityHelper modelEntityHelper;
    @Autowired
    FriendService friendService;
    @Autowired
    RecipientService recipientService;
    @Autowired
    RestClientService restClientService;
    @Autowired
    FriendFormValidator friendValidator;

    @RequestMapping(value = "/friend", method = RequestMethod.GET)
    public String addRecipient(Model model) {
        model.addAttribute("friendForm", new FriendForm());
        setFriendPageData(model);
        return "friend";
    }


    @RequestMapping(value = "/friend", method = RequestMethod.POST)
    public String addRecipient(@ModelAttribute("friendForm") FriendForm friendForm, BindingResult bindingResult, Model model) {

        // Validate form
        friendValidator.validate(friendForm, bindingResult);

        if (bindingResult.hasErrors()) {
            setFriendPageData(model);
            return "friend";
        }

        try{
            Friend friend = modelEntityHelper.convertToEntity(friendForm);
            friend = friendService.findById(friend.getFriendId());
            Recipient recipient = new Recipient();
            recipient.setFullname(friend.getFullname());
            friendForm = modelEntityHelper.convertToModel(friend);

            // Save remotely
            recipient = restClientService.createRecipient(recipient);
            if(recipient == null || recipient.getExtRecipientId() == null || recipient.getExtRecipientId().isEmpty())
                throw new Exception("Unable to create recipient");
            // Save locally
            recipient = recipientService.save(recipient);
            if(recipient == null || recipient.getRecipientId() < 1)
                throw new Exception("Unable to create recipient");

            model.addAttribute("friendForm", friendForm);
            model.addAttribute("friendAdded", true);
        }
        catch(Exception ex){
            logger.error(ex.getMessage(), ex);
            bindingResult.rejectValue("fullname", "Friend.save.error");
            setFriendPageData(model);
            return "friend";
        }

        return "friend";
    }


    private void setFriendPageData(Model model){
        List<Friend> friendList = friendService.findAll();
        model.addAttribute("friendsForm", friendList);
    }

}
