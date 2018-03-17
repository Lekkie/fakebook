package com.currencycloud.fakebook.repository;

import com.currencycloud.fakebook.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findByCode(String code);
}
