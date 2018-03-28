package com.currencycloud.fakebook.tasks;

import com.currencycloud.fakebook.entity.Payment;
import com.currencycloud.fakebook.service.PaymentService;
import com.currencycloud.fakebook.service.RestClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by lekanomotayo on 16/03/2018.
 */

@Configuration
public class FakebookTaskScheduler {

    private static final Logger logger = LoggerFactory.getLogger(FakebookTaskScheduler.class);

    @Autowired
    RestClientService restClientService;
    @Autowired
    PaymentService paymentService;

    /***
     * Cool Payment API has no get payment by payment id, therefore payments have to be
     * retrieved in bulk.
     * SChedule payments download periodically and each client/user get payment status
     * from backend.
     */
    @Scheduled(fixedDelayString = "${fakebook.scheduler.fetch-payment-status-delay}")
    public void scheduleTaskGetAllPaymentStatus() {
        List<Payment> paymentList = restClientService.getAllPaymentStatus();
        if(paymentList != null){
            paymentList.stream()
                    .filter(Objects::nonNull)
                    .map(payment -> getDBPayments(payment))
                    .collect(Collectors.toList());

        }
        System.out.println("Finished");
    }

    private List<Payment> getDBPayments(Payment payment){
        List<Payment> savedPaymentList = new ArrayList<>();
        // Coolplay API returns same payment id for all payments, hack to fix locally.
        // Do NOT do this in real life...NEVER!
        List<Payment> dbPaymentList = paymentService.findByExtPaymentId(payment.getExtPaymentId());
         if(dbPaymentList != null){
            logger.debug("Found payment(s): " + dbPaymentList.size());
            savedPaymentList = dbPaymentList.stream()
                    .filter(Objects::nonNull)
                    .map(dbPayment -> savePayments(dbPayment, payment.getStatus()))
                    .collect(Collectors.toList());
        }
        else{
            logger.debug("Found no payment in Db");
        }
        return savedPaymentList;
    }

    private Payment savePayments(Payment payment, String status){
        System.out.println("Streaming payment from db: " + payment.getPaymentId());
        payment.setStatus(status);
        payment = paymentService.save(payment);
        logger.debug("Saved payment");
        return payment;
    }


    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler(); //single threaded by default
    }

}
