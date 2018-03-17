package com.currencycloud.fakebook.repository;

import com.currencycloud.fakebook.entity.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RecipientRepository extends JpaRepository<Recipient, Long> {
    Recipient findByFullname(String fullname);
    Recipient findByRecipientId(Long recipientId);
    Recipient findByExtRecipientId(String extRecipientId);
}
