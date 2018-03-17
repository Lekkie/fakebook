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
import com.currencycloud.fakebook.utils.TestUtil;
import com.currencycloud.fakebook.validator.PaymentFormValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lekanomotayo on 16/03/2018.
 */
public class PaymentControllerTest {

    final RecipientService recipientService = Mockito.mock(RecipientService.class);
    final CurrencyService currencyService = Mockito.mock(CurrencyService.class);
    final RestClientService successRestClientService = Mockito.mock(RestClientService.class);
    final RestClientService failRestClientService = Mockito.mock(RestClientService.class);
    final PaymentService successPaymentService = Mockito.mock(PaymentService.class);
    final PaymentService failPaymentService = Mockito.mock(PaymentService.class);

    final ModelEntityHelper modelEntityHelper = new ModelEntityHelper();
    final PaymentFormValidator paymentValidator = new PaymentFormValidator();
    final PaymentController paymentController = new PaymentController();
    final List<Recipient> recipientList = new ArrayList<>();
    final List<Currency> currencyList = new ArrayList<>();

    PaymentForm newPayment = new PaymentForm();
    PaymentForm newMissingFieldPayment = new PaymentForm();
    final Date createOn = new Date();
    final String createBy = "User";



    @Before
    public void start() throws Exception{

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
        recipient2.setCreatedOn(createOn);
        recipientList.add(recipient1);
        recipientList.add(recipient2);

        Currency currency = new Currency();
        currency.setCurrencyId(1L);
        currency.setCode("GBP");
        currencyList.add(currency);

        newPayment.setRecipientId(1L);
        newPayment.setCurrencyId(1L);
        newPayment.setAmount("500.00");

        Payment savedPayment = new Payment();
        savedPayment.setPaymentId(1L);
        savedPayment.setRecipientId(newPayment.getRecipientId());
        savedPayment.setCurrencyId(newPayment.getCurrencyId());
        savedPayment.setAmount(new BigDecimal(newPayment.getAmount()));
        savedPayment.setCreatedOn(new Date());
        savedPayment.setCreatedBy("User");

        Payment successfulPayment = new Payment();
        successfulPayment.setPaymentId(savedPayment.getPaymentId());
        successfulPayment.setRecipientId(savedPayment.getRecipientId());
        successfulPayment.setCurrencyId(savedPayment.getCurrencyId());
        successfulPayment.setAmount(savedPayment.getAmount());
        successfulPayment.setCreatedOn(savedPayment.getCreatedOn());
        successfulPayment.setCreatedBy(savedPayment.getCreatedBy());
        successfulPayment.setStatus("Processing");
        successfulPayment.setExtPaymentId("e9a0336b-d81d-4009-9ad1-8fa1eb43418c");

        Mockito.when(recipientService.findAll()).thenReturn(recipientList);
        Mockito.when(currencyService.findAll()).thenReturn(currencyList);
        Mockito.when(successPaymentService.save(Matchers.any(Payment.class))).thenReturn(savedPayment);
        Mockito.when(successRestClientService.makePayment(savedPayment)).thenReturn(successfulPayment);
        Mockito.when(successPaymentService.update(successfulPayment)).thenReturn(successfulPayment);

        Payment failedPayment = new Payment();
        failedPayment.setRecipientId(savedPayment.getPaymentId());
        failedPayment.setRecipientId(savedPayment.getRecipientId());
        failedPayment.setCurrencyId(savedPayment.getCurrencyId());
        failedPayment.setAmount(savedPayment.getAmount());
        failedPayment.setCreatedOn(savedPayment.getCreatedOn());
        failedPayment.setCreatedBy(savedPayment.getCreatedBy());
        failedPayment.setStatus("Failed");
        Mockito.when(failRestClientService.makePayment(savedPayment)).thenReturn(failedPayment);

    }


    // test getting payment page
    @Test
    public void getPaymentPage() throws Exception{
        Model model= new ExtendedModelMap();
        PaymentController paymentController = new PaymentController();

        TestUtil.setField(paymentController, "recipientService", recipientService);
        TestUtil.setField(paymentController, "currencyService", currencyService);
        TestUtil.setField(paymentController, "modelEntityHelper", modelEntityHelper);

        String view = paymentController.payment(model);

        Object paymentForm = model.asMap().get("paymentForm");
        assertTrue("Missing Recipient form", model.containsAttribute("recipientsForm"));
        assertTrue("Missing Currency form", model.containsAttribute("currenciesForm"));
        Object recipientsFormObj = model.asMap().get("recipientsForm");
        Object currenciesFormObj = model.asMap().get("currenciesForm");
        assertTrue("Form not instance of List", recipientsFormObj instanceof List);
        assertTrue("Form not instance of List", currenciesFormObj instanceof List);
        List<Object> recipientList = (List<Object>) recipientsFormObj;
        for(Object object: recipientList){
            assertTrue("Not instance of Recipient", object instanceof RecipientForm);
        }
        List<Object> currencyList = (List<Object>) currenciesFormObj;
        for(Object object: currencyList){
            assertTrue("Not instance of Currency", object instanceof CurrencyForm);
        }
        assertTrue("Missing Payment form", model.containsAttribute("paymentForm"));
        assertTrue("Form not instance of Payment", paymentForm instanceof PaymentForm);
        assertEquals("View page did not match expected value", "payment", view);
    }

    //Test payment with all fields set and not approved
    @Test
    public void makeSuccessfulPaymentPage() throws Exception{
        Model model= new ExtendedModelMap();
        BindingResult bindingResult = new BeanPropertyBindingResult(newPayment, "paymentForm");
        PaymentFormValidator paymentValidator = new PaymentFormValidator();
        TestUtil.setField(paymentController, "recipientService", recipientService);
        TestUtil.setField(paymentController, "currencyService", currencyService);
        TestUtil.setField(paymentController, "paymentService", successPaymentService);
        TestUtil.setField(paymentController, "restClientService", successRestClientService);
        TestUtil.setField(paymentController, "paymentValidator", paymentValidator);
        TestUtil.setField(paymentController, "modelEntityHelper", modelEntityHelper);

        String view = paymentController.payment(newPayment, bindingResult, model);

        assertTrue("Expected paymentSent value to be present", model.containsAttribute("paymentSent"));
        assertTrue("There shuld be no error", !bindingResult.hasErrors());
        assertEquals("View page did not match expected value", "payment", view);
    }


    //Test payment with all fields set and not approved
    @Test
    public void makeFailedPaymentPage() throws Exception{
        Model model= new ExtendedModelMap();
        BindingResult bindingResult = new BeanPropertyBindingResult(newPayment, "paymentForm");
        TestUtil.setField(paymentController, "recipientService", recipientService);
        TestUtil.setField(paymentController, "currencyService", currencyService);
        TestUtil.setField(paymentController, "paymentService", successPaymentService);
        TestUtil.setField(paymentController, "restClientService", failRestClientService);
        TestUtil.setField(paymentController, "paymentValidator", paymentValidator);
        TestUtil.setField(paymentController, "modelEntityHelper", modelEntityHelper);

        String view = paymentController.payment(newPayment, bindingResult, model);

        assertTrue("Missing Recipient form", model.containsAttribute("recipientsForm"));
        assertTrue("Missing Currency form", model.containsAttribute("currenciesForm"));
        Object recipientsFormObj = model.asMap().get("recipientsForm");
        Object currenciesFormObj = model.asMap().get("currenciesForm");
        assertTrue("Form not instance of List", recipientsFormObj instanceof List);
        assertTrue("Form not instance of List", currenciesFormObj instanceof List);
        List<Object> recipientList = (List<Object>) recipientsFormObj;
        for(Object object: recipientList){
            assertTrue("Not instance of Recipient", object instanceof RecipientForm);
        }
        List<Object> currencyList = (List<Object>) currenciesFormObj;
        for(Object object: currencyList){
            assertTrue("Not instance of Currency", object instanceof CurrencyForm);
        }

        Errors errors = (Errors) bindingResult;
        assertTrue("There shuld be error", bindingResult.hasErrors());
        assertTrue("There shuld be amount error", errors.hasFieldErrors("amount"));
        String code = errors.getFieldError("amount").getCode();
        assertEquals("Eror code did not match expected value", "Payment.error", code);
        assertTrue("PaymentSent value should be absent", !model.containsAttribute("paymentSent"));
        assertEquals("View page did not match expected value", "payment", view);
    }

    //Test payment with missing fields
    @Test
    public void missingPaymentFieldPage() throws Exception{
        Model model= new ExtendedModelMap();
        BindingResult bindingResult = new BeanPropertyBindingResult(newMissingFieldPayment, "paymentForm");

        TestUtil.setField(paymentController, "recipientService", recipientService);
        TestUtil.setField(paymentController, "currencyService", currencyService);
        TestUtil.setField(paymentController, "paymentService", successPaymentService);
        TestUtil.setField(paymentController, "restClientService", failRestClientService);
        TestUtil.setField(paymentController, "paymentValidator", paymentValidator);
        TestUtil.setField(paymentController, "modelEntityHelper", modelEntityHelper);

        String view = paymentController.payment(newMissingFieldPayment, bindingResult, model);

        assertTrue("Missing Recipient form", model.containsAttribute("recipientsForm"));
        assertTrue("Missing Currency form", model.containsAttribute("currenciesForm"));
        Object recipientsFormObj = model.asMap().get("recipientsForm");
        Object currenciesFormObj = model.asMap().get("currenciesForm");
        assertTrue("Form not instance of List", recipientsFormObj instanceof List);
        assertTrue("Form not instance of List", currenciesFormObj instanceof List);
        List<Object> recipientList = (List<Object>) recipientsFormObj;
        for(Object object: recipientList){
            assertTrue("Not instance of Recipient", object instanceof RecipientForm);
        }
        List<Object> currencyList = (List<Object>) currenciesFormObj;
        for(Object object: currencyList){
            assertTrue("Not instance of Currency", object instanceof CurrencyForm);
        }

        Errors errors = (Errors) bindingResult;
        assertTrue("There shuld be form error", bindingResult.hasErrors());
        assertTrue("There shuld be recipient error", errors.hasFieldErrors("recipientId"));
        assertTrue("There shuld be currency error", errors.hasFieldErrors("currencyId"));
        assertTrue("There shuld be amount error", errors.hasFieldErrors("amount"));
        String recipientCode = errors.getFieldError("recipientId").getCode();
        String currCode = errors.getFieldError("currencyId").getCode();
        String amtCode = errors.getFieldError("amount").getCode();
        assertEquals("Error code did not match expected value", "Recipient.not.selected", recipientCode);
        assertEquals("Error code did not match expected value", "Currency.not.selected", currCode);
        assertEquals("Error code did not match expected value", "NotEmpty", amtCode);
        assertTrue("Expected paymentSent value to be absent", !model.containsAttribute("paymentSent"));
        assertEquals("View page did not match expected value", "payment", view);
    }

}
