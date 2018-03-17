package com.currencycloud.fakebook.service;

import com.currencycloud.fakebook.comms.RestClient;
import com.currencycloud.fakebook.config.RestConfig;
import com.currencycloud.fakebook.model.Auth;
import com.currencycloud.fakebook.entity.Currency;
import com.currencycloud.fakebook.entity.Payment;
import com.currencycloud.fakebook.entity.Recipient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by lekanomotayo on 15/03/2018.
 */

@Service
public class RestClientService {

    private static final Logger logger = LoggerFactory.getLogger(RestClientService.class);

    @Autowired
    RestConfig restConfig;
    @Autowired
    RestClient restClient;
    @Autowired
    CurrencyService currencyService;
    @Autowired
    RecipientService recipientService;


    public String getAuthentication(){
        try{
            String authUrl = restConfig.getPaymentApiUrl() + "/login";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", restConfig.getPaymentApiUsername());
            jsonObject.put("apikey", restConfig.getPaymentApiKey());
            JSONObject jsonObjectResp = restClient.sendPostRequest(authUrl, jsonObject);
            if(jsonObjectResp == null)
                throw new Exception("Auth failed");

            String token = jsonObjectResp.get("token") == null ? null : String.valueOf( jsonObjectResp.get("token"));
            if(token == null || token.isEmpty())
                throw new Exception("Auth failed");

            return token;
        }
        catch(Exception ex){
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public Recipient createRecipient(Recipient recipient){

        try{
            String token = getToken();
            String recipientUrl = restConfig.getPaymentApiUrl() + "/recipients";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", recipient.getFullname());
            JSONObject jsonObjectRecip = new JSONObject();
            jsonObjectRecip.put("recipient", jsonObject);
            JSONObject jsonObjectResp = restClient.sendPostRequest(recipientUrl, token, jsonObjectRecip);
            if(jsonObjectResp == null)
                throw new Exception("Recipient  creation failed");
            JSONObject jsonObjRecipResp = jsonObjectResp.get("recipient") == null ? null : ((JSONObject) jsonObjectResp.get("recipient"));
            if(jsonObjRecipResp == null)
                throw new Exception("Recipient  creation failed");
            String id = jsonObjRecipResp == null ? null : (jsonObjRecipResp.get("id") == null ? null : String.valueOf(jsonObjRecipResp.get("id")));

            if(id == null || id.isEmpty())
                throw new Exception("Recipient  creation failed");

            recipient.setExtRecipientId(id);
            return recipient;
        }
        catch(Exception ex){
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public Payment makePayment(Payment payment){

        try{
            String token = getToken();
            Recipient recipient = recipientService.findByRecipientId(payment.getRecipientId());
            Currency currency = currencyService.findByCurrencyId(payment.getCurrencyId());
            String paymentUrl = restConfig.getPaymentApiUrl() + "/payments";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount", payment.getAmount());
            jsonObject.put("currency", currency.getCode());
            jsonObject.put("recipient_id", recipient.getExtRecipientId());
            JSONObject jsonObjectPay = new JSONObject();
            jsonObjectPay.put("payment", jsonObject);
            JSONObject jsonObjectResp = restClient.sendPostRequest(paymentUrl, token, jsonObjectPay);
            if (jsonObjectResp == null)
                throw new Exception("Payment  creation failed");

            JSONObject jsonObjPayResp = jsonObjectResp.get("payment") == null ? null : ((JSONObject) jsonObjectResp.get("payment"));
            if (jsonObjPayResp == null)
                throw new Exception("Payment  creation failed");

            String id = jsonObjPayResp == null ? null : (jsonObjPayResp.get("id") == null ? null : String.valueOf(jsonObjPayResp.get("id")));
            if (id == null || id.isEmpty())
                throw new Exception("Payment  creation failed");

            Payment paymentResp = json2Payment(jsonObjPayResp);
            paymentResp.setPaymentId(payment.getPaymentId());
            paymentResp.setCurrencyId(payment.getCurrencyId());
            paymentResp.setRecipientId(payment.getRecipientId());
            return paymentResp;
        }
        catch(Exception ex){
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    public List<Payment> getAllPaymentStatus(){

        try{
            String token = getToken();
            String paymentUrl = restConfig.getPaymentApiUrl() + "/payments";
            JSONObject jsonObjectGetPayResp = restClient.sendGetRequest(paymentUrl, token);
            JSONArray jsonObjPayResp = jsonObjectGetPayResp.get("payments") == null ? null : ((JSONArray) jsonObjectGetPayResp.get("payments"));

            if(jsonObjPayResp != null){
                List<Payment> paymentList = StreamSupport.stream(jsonObjPayResp.spliterator(), false)
                        .filter(Objects::nonNull)
                        .map(jsonObject ->{
                            try {
                                return json2Payment((JSONObject) jsonObject);
                            }
                            catch(Exception ex){
                                ex.printStackTrace();
                                return null;
                            }
                        })
                        .filter(Objects::nonNull) // filter returned null from map result
                        .collect(Collectors.toList());
                return paymentList;
            }
        }
            catch(Exception ex){
            logger.error(ex.getMessage(), ex);
        }
        return null;
    }

    private Payment json2Payment(JSONObject jsonObj) throws Exception{
        if(jsonObj == null)
            return null;

        Payment payment = new Payment();
        String id = jsonObj.get("id") == null ? null : String.valueOf(jsonObj.get("id"));
        String status = jsonObj.get("status") == null ? null : String.valueOf(jsonObj.get("status"));
        String amt = jsonObj.get("amount") == null ? null : String.valueOf(jsonObj.get("amount"));
        if(id == null || id.isEmpty())
            throw new Exception("Payment creation failed");

        payment.setStatus(status);
        payment.setExtPaymentId(id);
        payment.setAmount(new BigDecimal(amt));

        return payment;
    }


    private String getToken(){
        String token = Auth.getInstance().getToken();
        if(token == null || token.isEmpty()) {
            token = getAuthentication();
            Auth.getInstance().setToken(token);
        }
        return token;
    }

}
