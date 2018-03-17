package com.currencycloud.fakebook.controllers;

import com.currencycloud.fakebook.entity.Currency;
import com.currencycloud.fakebook.entity.Payment;
import com.currencycloud.fakebook.entity.Recipient;
import com.currencycloud.fakebook.model.*;
import com.currencycloud.fakebook.service.CurrencyService;
import com.currencycloud.fakebook.service.PaymentService;
import com.currencycloud.fakebook.service.RecipientService;
import com.currencycloud.fakebook.utils.TestUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lekanomotayo on 16/03/2018.
 */
public class PaymentStatusFormControllerTest {


    PaymentStatusController controller = new PaymentStatusController();

    final RecipientService recipientService = Mockito.mock(RecipientService.class);
    final CurrencyService currencyService = Mockito.mock(CurrencyService.class);
    final PaymentService paymentService = Mockito.mock(PaymentService.class);

    @Before
    public void start() throws Exception{

        final List<Payment> paymentList = new ArrayList<>();
        Payment payment1 = new Payment();
        payment1.setAmount(new BigDecimal("200"));
        payment1.setPaymentId(1L);
        payment1.setExtPaymentId("31db334f-9ac0-42cb-804b-09b2f899d4d2");
        payment1.setCurrencyId(1L);
        payment1.setStatus("Processing");
        payment1.setRecipientId(1L);
        payment1.setCreatedBy("User");
        payment1.setCreatedOn(new Date());
        Payment payment2 = new Payment();
        payment2.setAmount(new BigDecimal("100"));
        payment2.setPaymentId(1L);
        payment2.setExtPaymentId("98db5a24f-9ac0-42cb-804b-09b23e99d466");
        payment2.setCurrencyId(2L);
        payment2.setStatus("Processing");
        payment2.setRecipientId(2L);
        payment2.setCreatedBy("User");
        payment2.setCreatedOn(new Date());
        paymentList.add(payment1);
        paymentList.add(payment2);

        Recipient recipient1 = new Recipient();
        recipient1.setRecipientId(1L);
        recipient1.setExtRecipientId("e9a0336b-d81d-4009-9ad1-8fa1eb43418c");
        recipient1.setFullname("Jake McFriend");
        recipient1.setCreatedBy("User");
        recipient1.setCreatedOn(new Date());
        Recipient recipient2 = new Recipient();
        recipient2.setRecipientId(2L);
        recipient2.setExtRecipientId("e9a0346b-d81d-4009-9ad1-8fa1eb223418c");
        recipient2.setFullname("Ade Olawale");
        recipient2.setCreatedBy("User");
        recipient2.setCreatedOn(new Date());

        Currency currency1 = new Currency();
        currency1.setCurrencyId(1L);
        currency1.setCode("GBP");

        Currency currency2 = new Currency();
        currency2.setCurrencyId(1L);
        currency2.setCode("USD");

        Mockito.when(paymentService.findAll()).thenReturn(paymentList);
        Mockito.when(recipientService.findByRecipientId(payment1.getRecipientId())).thenReturn(recipient1);
        Mockito.when(currencyService.findByCurrencyId(payment1.getCurrencyId())).thenReturn(currency1);
        Mockito.when(recipientService.findByRecipientId(payment2.getRecipientId())).thenReturn(recipient2);
        Mockito.when(currencyService.findByCurrencyId(payment2.getCurrencyId())).thenReturn(currency2);
    }


    // test getting payment page
    @Test
    public void getPaymentStatusPage() throws Exception{

        Model model= new ExtendedModelMap();
        TestUtil.setField(controller, "recipientService", recipientService);
        TestUtil.setField(controller, "currencyService", currencyService);
        TestUtil.setField(controller, "paymentService", paymentService);

        String view = controller.checkPaymentStatus(model);

        assertTrue("Missing Recipient form", model.containsAttribute("paymentStatusForm"));
        Object paymentStatusesFormObj = model.asMap().get("paymentStatusForm");
        assertTrue("Form not instance of List", paymentStatusesFormObj instanceof List);
        List<Object> paymentStatusList = (List<Object>) paymentStatusesFormObj;
        for(Object object: paymentStatusList){
            assertTrue("Not instance of PaymentStatus", object instanceof PaymentStatusForm);
        }

        assertEquals("View page did not match expected value", "paymentStatus", view);
    }


}
