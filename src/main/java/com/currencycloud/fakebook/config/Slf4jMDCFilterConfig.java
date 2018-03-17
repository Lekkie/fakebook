package com.currencycloud.fakebook.config;

import com.currencycloud.fakebook.instrumentation.Slf4jMDCFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lekanomotayo
 */

@Configuration
public class Slf4jMDCFilterConfig {


    public static final String DEFAULT_RESPONSE_TOKEN_HEADER = "Response_Token";
    public static final String DEFAULT_MDC_UUID_TOKEN_KEY = "Slf4jMDCFilter.UUID";
    public static final String DEFAULT_REQUEST_TOKEN_HEADER = "X-Header-Token";


    private String responseHeader = DEFAULT_RESPONSE_TOKEN_HEADER;
    private String mdcTokenKey = DEFAULT_MDC_UUID_TOKEN_KEY;
    private String requestHeader = DEFAULT_REQUEST_TOKEN_HEADER;

    @Bean
    public FilterRegistrationBean servletRegistrationBean(Slf4jMDCFilter log4jMDCFilter) {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("log4jMDCFilter");
        //final Slf4jMDCFilter log4jMDCFilterFilter = new Slf4jMDCFilter(responseHeader, mdcTokenKey, requestHeader);
        registrationBean.setFilter(log4jMDCFilter);
        registrationBean.setOrder(2);
        return registrationBean;
    }

}
