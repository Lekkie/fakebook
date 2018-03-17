package com.currencycloud.fakebook.service;

import com.currencycloud.fakebook.entity.Currency;
import com.currencycloud.fakebook.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lekanomotayo on 14/03/2018.
 */

@Service
public class CurrencyService{

    @Autowired
    CurrencyRepository currencyRepository;


    public Currency save(Currency currency) {
        return currencyRepository.save(currency);
    }

    public Currency findByCurrencyId(Long id) {
        return currencyRepository.findOne(id);
    }

    public Currency findByCode(String code) {
        return currencyRepository.findByCode(code);
    }

    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

}
