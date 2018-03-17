package com.currencycloud.fakebook.service;

import com.currencycloud.fakebook.entity.Recipient;
import com.currencycloud.fakebook.repository.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lekanomotayo on 14/03/2018.
 */

@Service
public class RecipientService {

    @Autowired
    RecipientRepository recipientRepository;


    public Recipient save(Recipient recipient) {
        return recipientRepository.save(recipient);
    }

    public Recipient findById(Long id) {
        return recipientRepository.findOne(id);
    }

    public Recipient findByFullname(String fullname) {
        return recipientRepository.findByFullname(fullname);
    }

    public Recipient findByRecipientId(Long recipientId) {
        return recipientRepository.findByRecipientId(recipientId);
    }

    public Recipient findByExtRecipientId(String extRecipientId) {
        return recipientRepository.findByExtRecipientId(extRecipientId);
    }

    public List<Recipient> findAll() {
        return recipientRepository.findAll();
    }


}
