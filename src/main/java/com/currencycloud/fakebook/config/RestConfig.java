package com.currencycloud.fakebook.config;

import com.currencycloud.fakebook.utils.KeyUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lekanomotayo on 15/03/2018.
 */

@Configuration
public class RestConfig {


    @Value("${fakebook.payment.url}")
    private String paymentApiUrl;

    @Value("${fakebook.payment.username}")
    private String paymentApiUsername;
    @Value("${fakebook.payment.apikey}")
    private String paymentApiKey;


    public String getPaymentApiUrl() {
        return paymentApiUrl;
    }

    public void setPaymentApiUrl(String paymentApiUrl) {
        this.paymentApiUrl = paymentApiUrl;
    }

    public String getPaymentApiUsername() {
        return paymentApiUsername;
    }

    public void setPaymentApiUsername(String paymentApiUsername) {
        this.paymentApiUsername = paymentApiUsername;
    }


    /**
     *  API Key in properties file is RSA encrypted. Decrypt API Key with private key in keystore.
     * @return
     */
    public String getPaymentApiKey() {
        return KeyUtils.decrypt(paymentApiKey);
    }

    public void setPaymentApiKey(String paymentApiKey) {
        this.paymentApiKey = paymentApiKey;
    }

}
