package com.currencycloud.fakebook.tasks;

import com.currencycloud.fakebook.entity.Payment;
import com.currencycloud.fakebook.service.PaymentService;
import com.currencycloud.fakebook.service.RestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.List;

/**
 * Created by lekanomotayo on 16/03/2018.
 */

@Configuration
public class FakebookTaskScheduler {

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
                    .map(payment -> {
                        // Coolpay returns same payment ID for new payments, so multiple payment in database share same payment id
                        List<Payment> dbPaymentList = paymentService.findByExtPaymentId(payment.getExtPaymentId());
                        if(dbPaymentList != null){
                            dbPaymentList.stream()
                                    .map(dbPayment -> {
                                        dbPayment.setStatus(payment.getStatus());
                                        paymentService.save(dbPayment);
                                        return 0;
                                    });
                        }
                        return 0;
                    });
        }
    }


    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler(); //single threaded by default
    }

}
