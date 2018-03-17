package com.currencycloud.fakebook.controllers;

import com.currencycloud.fakebook.entity.Currency;
import com.currencycloud.fakebook.entity.Payment;
import com.currencycloud.fakebook.entity.Recipient;
import com.currencycloud.fakebook.model.CurrencyForm;
import com.currencycloud.fakebook.model.PaymentForm;
import com.currencycloud.fakebook.model.RecipientForm;
import com.currencycloud.fakebook.service.CurrencyService;
import com.currencycloud.fakebook.service.PaymentService;
import com.currencycloud.fakebook.service.RecipientService;
import com.currencycloud.fakebook.service.RestClientService;
import com.currencycloud.fakebook.utils.ModelEntityHelper;
import com.currencycloud.fakebook.validator.PaymentFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by lekanomotayo on 14/03/2018.
 *
 */

@Controller
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    ModelEntityHelper modelEntityHelper;
    @Autowired
    private PaymentFormValidator paymentValidator;
    @Autowired
    RecipientService recipientService;
    @Autowired
    CurrencyService currencyService;
    @Autowired
    RestClientService restClientService;
    @Autowired
    PaymentService paymentService;


    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public String payment(Model model) {

        model.addAttribute("paymentForm", new PaymentForm());
        setPaymentPageData(model);

        return "payment";
    }


    @RequestMapping(value = "/payment", method = RequestMethod.POST)
    public String payment(@ModelAttribute("paymentForm") PaymentForm paymentForm, BindingResult bindingResult, Model model) {

        // Validate form fields are acceptable
        paymentValidator.validate(paymentForm, bindingResult);

        if (bindingResult.hasErrors()) {
            setPaymentPageData(model);
            return "payment";
        }

        try{
            // Save payment before sending
            Payment payment = modelEntityHelper.convertToEntity(paymentForm);
            payment = paymentService.save(payment);

            // send payment
            payment = restClientService.makePayment( payment);
            if(payment == null)
                throw new Exception("Payment Failed");

            if(payment.getStatus() == null
                    || (!payment.getStatus().equalsIgnoreCase("Processing")
                    && !payment.getStatus().equalsIgnoreCase("Paid")))
                throw new Exception("Payment Failed");

            // Update response
            paymentService.update(payment);
            model.addAttribute("paymentSent", true);
        }
        catch(Exception ex){
            logger.error(ex.getMessage(), ex);
            bindingResult.rejectValue("amount", "Payment.error");
            setPaymentPageData(model);
            return "payment";
        }

        return "payment";
    }


    private void setPaymentPageData(Model model){
        List<Recipient> recipientList = recipientService.findAll();

        // Fast Collector streaming.
        List<RecipientForm> recipientFormList = recipientList.stream()
                .filter(Objects::nonNull)
                .map(recipient -> modelEntityHelper.convertToModel(recipient))
                .collect(Collectors.toList());
        model.addAttribute("recipientsForm", recipientFormList);


        List<Currency> currencyList = currencyService.findAll();
        // Fast Collector streaming.
        List<CurrencyForm> currencyFormList = currencyList.stream()
                .filter(Objects::nonNull)
                .map(currency -> modelEntityHelper.convertToModel(currency))
                .collect(Collectors.toList());
        model.addAttribute("currenciesForm", currencyFormList);
    }


}
