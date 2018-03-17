package com.currencycloud.fakebook.controllers;

import com.currencycloud.fakebook.entity.Currency;
import com.currencycloud.fakebook.entity.Payment;
import com.currencycloud.fakebook.model.PaymentStatusForm;
import com.currencycloud.fakebook.entity.Recipient;
import com.currencycloud.fakebook.service.CurrencyService;
import com.currencycloud.fakebook.service.PaymentService;
import com.currencycloud.fakebook.service.RecipientService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by lekanomotayo on 14/03/2018.
 */

@Controller
public class PaymentStatusController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentStatusController.class);


    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    PaymentService paymentService;
    @Autowired
    CurrencyService currencyService;
    @Autowired
    RecipientService recipientService;


    @RequestMapping(value = "/paymentStatus", method = RequestMethod.GET)
    public String checkPaymentStatus(Model model) {

        List<Payment> paymentList = paymentService.findAll();
        // Fast Collector streaming.
        List<PaymentStatusForm> paymentStatusList = paymentList.stream()
                .filter(Objects::nonNull)
                .map(payment -> convertToModel(payment))
                .collect(Collectors.toList());

        model.addAttribute("paymentStatusForm", paymentStatusList);
        return "paymentStatus";
    }


    private PaymentStatusForm convertToModel(Payment payment) {
        PaymentStatusForm paymentStatusForm = modelMapper.map(payment, PaymentStatusForm.class);
        paymentStatusForm.setPaymentId(payment.getExtPaymentId());
        Currency currency = currencyService.findByCurrencyId(payment.getCurrencyId());
        paymentStatusForm.setCurrency(currency.getCode());
        Recipient recipient = recipientService.findByRecipientId(payment.getRecipientId());
        paymentStatusForm.setRecipient(recipient.getFullname());
        Date date = payment.getCreatedOn();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String dateStr = sdf.format(date);
        paymentStatusForm.setPaymentDate(dateStr);
        return paymentStatusForm;
    }

}
