package com.currencycloud.fakebook.model;

import com.currencycloud.fakebook.service.RestClientService;
import org.springframework.stereotype.Component;

/**
 * Created by lekanomotayo on 16/03/2018.
 */
@Component
public class Auth {

    private static Auth auth;
    private static String token;

    private Auth(){
    }

    public static Auth getInstance(){
        if(auth == null)
            auth = new Auth();
        return auth;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Auth.token = token;
    }
}
