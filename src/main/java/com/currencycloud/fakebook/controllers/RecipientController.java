package com.currencycloud.fakebook.controllers;

import com.currencycloud.fakebook.entity.Recipient;
import com.currencycloud.fakebook.model.RecipientForm;
import com.currencycloud.fakebook.service.RecipientService;
import com.currencycloud.fakebook.service.RestClientService;
import com.currencycloud.fakebook.utils.ModelEntityHelper;
import com.currencycloud.fakebook.validator.RecipientFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lekanomotayo on 15/03/2018.
 */

@Controller
public class RecipientController {

    private static final Logger logger = LoggerFactory.getLogger(RecipientController.class);

    @Autowired
    ModelEntityHelper modelEntityHelper;
    @Autowired
    RecipientService recipientService;
    @Autowired
    RestClientService restClientService;
    @Autowired
    RecipientFormValidator recipientValidator;

    @RequestMapping(value = "/recipient", method = RequestMethod.GET)
    public String addRecipient(Model model) {
        model.addAttribute("recipientForm", new RecipientForm());
        return "recipient";
    }


    @RequestMapping(value = "/recipient", method = RequestMethod.POST)
    public String addRecipient(@ModelAttribute("recipientForm") RecipientForm recipientForm, BindingResult bindingResult, Model model) {

        // Validate form
        recipientValidator.validate(recipientForm, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("recipientForm", new RecipientForm());
            return "recipient";
        }

        try{
            // Save remotely
            Recipient recipient = modelEntityHelper.convertToEntity(recipientForm);
            recipient = restClientService.createRecipient(recipient);
            if(recipient == null || recipient.getExtRecipientId() == null || recipient.getExtRecipientId().isEmpty())
                throw new Exception("Unable to create recipient");
            // Save locally
            recipient = recipientService.save(recipient);
            if(recipient == null || recipient.getRecipientId() < 1)
                throw new Exception("Unable to create recipient");

            model.addAttribute("recipientAdded", true);
        }
        catch(Exception ex){
            logger.error(ex.getMessage(), ex);
            bindingResult.rejectValue("fullname", "Recipient.save.error");
            model.addAttribute("recipientForm", new RecipientForm());
            return "recipient";
        }

        return "recipient";
    }



}
