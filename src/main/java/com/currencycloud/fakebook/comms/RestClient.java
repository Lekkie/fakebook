package com.currencycloud.fakebook.comms;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lekanomotayo on 15/03/2018.
 */

@Component
public class RestClient {

    private static final Logger logger = LoggerFactory.getLogger(RestClient.class);

    public JSONObject sendGetRequest(String url){
        return sendGetRequest(url, null);
    }


    public JSONObject sendGetRequest(String url, String token){

        logger.debug("URL: " + url);

        HttpHeaders headers = new HttpHeaders();
        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.APPLICATION_JSON);
        headers.setAccept(list);
        RestTemplate restTemplate = new RestTemplate();
        if(token == null || token.isEmpty()){
            headers.set("Authorization", "Bearer " + token);
        }
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        String respStr = response.getBody();
        logger.debug("Resp Status: " + response.getStatusCode().value());
        logger.debug("Resp Msg: " + respStr);
        return respStr == null ? null : (new JSONObject(respStr));
    }


    public JSONObject sendPostRequest(String url, JSONObject jsonObject){
        return sendPostRequest(url, null, jsonObject);
    }

    public JSONObject sendPostRequest(String url, String token, JSONObject jsonObject){

        logger.debug("URL: " + url);
        logger.debug("Request: " + jsonObject);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        if(token == null || token.isEmpty()){
            headers.set("Authorization", "Bearer " + token);
        }
        HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        String respStr = response.getBody();
        logger.debug("Resp Status: " + response.getStatusCode().value());
        logger.debug("Resp Msg: " + respStr);
        return respStr == null ? null : (new JSONObject(respStr));
    }


}
