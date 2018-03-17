package com.currencycloud.fakebook.service;

import com.currencycloud.fakebook.comms.RestClient;
import com.currencycloud.fakebook.config.RestConfig;
import com.currencycloud.fakebook.model.Auth;
import com.currencycloud.fakebook.entity.Currency;
import com.currencycloud.fakebook.entity.Payment;
import com.currencycloud.fakebook.entity.Recipient;
import com.currencycloud.fakebook.utils.TestUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lekanomotayo on 16/03/2018.
 */
public class RestClientServiceTest {

    RestClientService restClientService = new RestClientService();

    final RestConfig restConfig = Mockito.mock(RestConfig.class);
    final RestClient restClient = Mockito.mock(RestClient.class);
    final CurrencyService currencyService = Mockito.mock(CurrencyService.class);
    final RecipientService recipientService = Mockito.mock(RecipientService.class);

    final String BASE_API_URL = "Http://localhost:8080/api/v1";

    JSONObject successAuthReqJson;
    JSONObject failAuthReqJson;

    @Before
    public void start() throws Exception {


        successAuthReqJson = new JSONObject();
        successAuthReqJson.put("username",  "LekanO");
        successAuthReqJson.put("apikey",  "DE439C178F925C03");
        failAuthReqJson = new JSONObject();
        successAuthReqJson.put("username",  "WrongUser");
        successAuthReqJson.put("apikey",  "WrongPass");

        JSONObject successAuthJson = new JSONObject();
        JSONObject failAuthJson = new JSONObject();

        Mockito.when(restConfig.getPaymentApiUsername()).thenReturn("LekanO");
        Mockito.when(restConfig.getPaymentApiKey()).thenReturn("DE439C178F925C03");
        Mockito.when(restConfig.getPaymentApiUrl()).thenReturn(BASE_API_URL);
        Mockito.when(restClient.sendPostRequest(BASE_API_URL + "/login", successAuthReqJson)).thenReturn(successAuthJson);
        //Mockito.when(currencyService.findAll()).thenReturn(recipientList);
        //Mockito.when(currencyService.findAll()).thenReturn(recipientList);


    }


    @Test
    public void getSuccessfulAuthentication() throws Exception {

        JSONObject successAuthJson = new JSONObject();
        successAuthJson.put("token", "aff06fec-e041-4994-849e-223f0569e0bc");
        Mockito.when(restConfig.getPaymentApiUsername()).thenReturn("LekanO");
        Mockito.when(restConfig.getPaymentApiKey()).thenReturn("DE439C178F925C03");
        Mockito.when(restConfig.getPaymentApiUrl()).thenReturn(BASE_API_URL);
        Mockito.when(restClient.sendPostRequest(Matchers.eq(BASE_API_URL + "/login"),
                Matchers.any(JSONObject.class))).thenReturn(successAuthJson);
        TestUtil.setField(restClientService, "restClient", restClient);
        TestUtil.setField(restClientService, "restConfig", restConfig);

        String token = restClientService.getAuthentication();

        assertEquals("Token did not match expected value", "aff06fec-e041-4994-849e-223f0569e0bc", token);
    }

    @Test
    public void getFailAuthentication() throws Exception {

        JSONObject failAuthJson = new JSONObject();
        Mockito.when(restConfig.getPaymentApiUsername()).thenReturn("LekanO");
        Mockito.when(restConfig.getPaymentApiKey()).thenReturn("DE439C178F925C03");
        Mockito.when(restConfig.getPaymentApiUrl()).thenReturn(BASE_API_URL);
        Mockito.when(restClient.sendPostRequest(Matchers.eq(BASE_API_URL + "/login"),
                Matchers.any(JSONObject.class))).thenReturn(failAuthJson);
        TestUtil.setField(restClientService, "restClient", restClient);
        TestUtil.setField(restClientService, "restConfig", restConfig);

        String token = restClientService.getAuthentication();

        assertNull("Token is expected to ne null", token);
    }


    @Test
    public void createSuccessfulRecipient() throws Exception {

        Recipient recipient = new Recipient();
        recipient.setFullname("Fullname");
        JSONObject successJson = new JSONObject();
        JSONObject recipJson = new JSONObject();
        recipJson.put("id", "e9a0336b-d81d-4009-9ad1-8fa1eb43418c");
        recipJson.put("name", "Jake McFriend");
        successJson.put("recipient", recipJson);

        Auth.getInstance().setToken("aff06fec-e041-4994-849e-223f0569e0bc");
        Mockito.when(restConfig.getPaymentApiUrl()).thenReturn(BASE_API_URL);
        Mockito.when(restClient.sendPostRequest(Matchers.eq(BASE_API_URL + "/recipients"),
                Matchers.eq("aff06fec-e041-4994-849e-223f0569e0bc"),
                Matchers.any(JSONObject.class))).thenReturn(successJson);
        TestUtil.setField(restClientService, "restClient", restClient);
        TestUtil.setField(restClientService, "restConfig", restConfig);

        Recipient recipientResp = restClientService.createRecipient(recipient);

        assertEquals("Recipient ID did not match expected value", "e9a0336b-d81d-4009-9ad1-8fa1eb43418c", recipientResp.getExtRecipientId());
    }

    @Test
    public void createFailRecipient() throws Exception {

        Recipient recipient = new Recipient();
        recipient.setFullname("Fullname");
        JSONObject failAuthJson = new JSONObject();

        Auth.getInstance().setToken("aff06fec-e041-4994-849e-223f0569e0bc");
        Mockito.when(restConfig.getPaymentApiUrl()).thenReturn(BASE_API_URL);
        Mockito.when(restClient.sendPostRequest(Matchers.eq(BASE_API_URL + "/recipients"),
                Matchers.eq("aff06fec-e041-4994-849e-223f0569e0bc"),
                Matchers.any(JSONObject.class))).thenReturn(failAuthJson);
        TestUtil.setField(restClientService, "restClient", restClient);
        TestUtil.setField(restClientService, "restConfig", restConfig);
        //TestUtil.setField(restClientService, "currencyService", currencyService);
        Recipient recipientResp = restClientService.createRecipient(recipient);

        assertNull("Recipient is expected to ne null", recipientResp);
    }


    @Test
    public void makeSuccessfulPayment() throws Exception {

        Payment payment = new Payment();
        payment.setRecipientId(1L);
        payment.setCurrencyId(1L);
        payment.setAmount(new BigDecimal("500"));
        JSONObject successJson = new JSONObject();
        JSONObject paymentJson = new JSONObject();
        paymentJson.put("id", "31db334f-9ac0-42cb-804b-09b2f899d4d2");
        paymentJson.put("amount", "10.5");
        paymentJson.put("currency", "GBP");
        paymentJson.put("recipient_id", "6e7b146e-5957-11e6-8b77-86f30ca");
        paymentJson.put("status", "processing");
        successJson.put("payment", paymentJson);

        Auth.getInstance().setToken("aff06fec-e041-4994-849e-223f0569e0bc");
        Mockito.when(restConfig.getPaymentApiUrl()).thenReturn(BASE_API_URL);

        Recipient recipient = new Recipient();
        recipient.setExtRecipientId("6e7b146e-5957-11e6-8b77-86f30ca");
        Mockito.when(recipientService.findByRecipientId(1L)).thenReturn(recipient);
        Currency currency = new Currency();
        currency.setCode("GBP");
        Mockito.when(currencyService.findByCurrencyId(1L)).thenReturn(currency);

        Mockito.when(restClient.sendPostRequest(Matchers.eq(BASE_API_URL + "/payments"),
                Matchers.eq("aff06fec-e041-4994-849e-223f0569e0bc"),
                Matchers.any(JSONObject.class))).thenReturn(successJson);
        TestUtil.setField(restClientService, "restClient", restClient);
        TestUtil.setField(restClientService, "restConfig", restConfig);
        TestUtil.setField(restClientService, "recipientService", recipientService);
        TestUtil.setField(restClientService, "currencyService", currencyService);

        Payment paymentResp = restClientService.makePayment(payment);

        assertEquals("Payment ID did not match expected value", "31db334f-9ac0-42cb-804b-09b2f899d4d2", paymentResp.getExtPaymentId());
        assertNotNull("Payment status is not expected to be null", paymentResp.getStatus());
    }

    @Test
    public void makeFailPayment() throws Exception {

        Payment payment = new Payment();
        payment.setRecipientId(1L);
        payment.setCurrencyId(1L);
        payment.setAmount(new BigDecimal("500"));
        JSONObject failJson = new JSONObject();

        Auth.getInstance().setToken("aff06fec-e041-4994-849e-223f0569e0bc");
        Mockito.when(restConfig.getPaymentApiUrl()).thenReturn(BASE_API_URL);

        Recipient recipient = new Recipient();
        recipient.setExtRecipientId("6e7b146e-5957-11e6-8b77-86f30ca");
        Mockito.when(recipientService.findByRecipientId(1L)).thenReturn(recipient);
        Currency currency = new Currency();
        currency.setCode("GBP");
        Mockito.when(currencyService.findByCurrencyId(1L)).thenReturn(currency);

        Mockito.when(restClient.sendPostRequest(Matchers.eq(BASE_API_URL + "/payments"),
                Matchers.eq("aff06fec-e041-4994-849e-223f0569e0bc"),
                Matchers.any(JSONObject.class))).thenReturn(failJson);
        TestUtil.setField(restClientService, "restClient", restClient);
        TestUtil.setField(restClientService, "restConfig", restConfig);
        TestUtil.setField(restClientService, "recipientService", recipientService);
        TestUtil.setField(restClientService, "currencyService", currencyService);

        Payment paymentResp = restClientService.makePayment(payment);

        assertNull("Payment is expected to ne null", paymentResp);

    }



    @Test
    public void getSuccessfulPayment() throws Exception {

        JSONObject successJson = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        JSONObject paymentJson1 = new JSONObject();
        paymentJson1.put("id", "31db334f-9ac0-42cb-804b-09b2f899d4d2");
        paymentJson1.put("amount", "10.5");
        paymentJson1.put("currency", "GBP");
        paymentJson1.put("recipient_id", "6e7b146e-5957-11e6-8b77-86f30ca");
        paymentJson1.put("status", "processing");
        JSONObject paymentJson2 = new JSONObject();
        paymentJson2.put("id", "31db334f-9ac0-42cb-804b-09b2f899d4d2");
        paymentJson2.put("amount", "10.5");
        paymentJson2.put("currency", "GBP");
        paymentJson2.put("recipient_id", "6e7b146e-5957-11e6-8b77-86f30ca");
        paymentJson2.put("status", "processing");
        List<JSONObject> jsonObjectList = new ArrayList<>();
        jsonArray.put(0, paymentJson1);
        jsonArray.put(1, paymentJson2);
        successJson.put("payments", jsonArray);

        Auth.getInstance().setToken("aff06fec-e041-4994-849e-223f0569e0bc");
        Mockito.when(restConfig.getPaymentApiUrl()).thenReturn(BASE_API_URL);

        Mockito.when(restClient.sendGetRequest(Matchers.eq(BASE_API_URL + "/payments"),
                Matchers.eq("aff06fec-e041-4994-849e-223f0569e0bc"))).thenReturn(successJson);

        TestUtil.setField(restClientService, "restClient", restClient);
        TestUtil.setField(restClientService, "restConfig", restConfig);

        List<Payment> paymentList = restClientService.getAllPaymentStatus();

        assertNotNull("Payments are not expected to be null", paymentList);
        for(Object object: paymentList){
            assertTrue("Not instance of Payment", object instanceof Payment);
            assertNotNull("Payment status is not expected to be null", ((Payment) object).getStatus());
            assertNotNull("Payment ID is not expected to be null", ((Payment) object).getExtPaymentId());
        }
    }

    @Test
    public void getFailPayment() throws Exception {

        JSONObject failJson = new JSONObject();

        Auth.getInstance().setToken("aff06fec-e041-4994-849e-223f0569e0bc");
        Mockito.when(restConfig.getPaymentApiUrl()).thenReturn(BASE_API_URL);

        Recipient recipient = new Recipient();
        recipient.setExtRecipientId("6e7b146e-5957-11e6-8b77-86f30ca");
        Mockito.when(recipientService.findByRecipientId(1L)).thenReturn(recipient);
        Currency currency = new Currency();
        currency.setCode("GBP");
        Mockito.when(currencyService.findByCurrencyId(1L)).thenReturn(currency);

        Mockito.when(restClient.sendGetRequest(Matchers.eq(BASE_API_URL + "/payments"),
                Matchers.eq("aff06fec-e041-4994-849e-223f0569e0bc"))).thenReturn(failJson);

        TestUtil.setField(restClientService, "restClient", restClient);
        TestUtil.setField(restClientService, "restConfig", restConfig);
        TestUtil.setField(restClientService, "recipientService", recipientService);
        TestUtil.setField(restClientService, "currencyService", currencyService);

        List<Payment> paymentList = restClientService.getAllPaymentStatus();

        assertNull("Payments are expected to ne null", paymentList);

    }


}
