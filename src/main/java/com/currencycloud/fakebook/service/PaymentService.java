package com.currencycloud.fakebook.service;

import com.currencycloud.fakebook.entity.Payment;
import com.currencycloud.fakebook.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lekanomotayo on 14/03/2018.
 */

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;


    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment update(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<Payment> findByExtPaymentId(String extPaymentId) {
        return paymentRepository.findByExtPaymentId(extPaymentId);
    }

    public Payment findBy(Long id) {
        return paymentRepository.findOne(id);
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

}
