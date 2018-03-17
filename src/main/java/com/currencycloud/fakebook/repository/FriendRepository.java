package com.currencycloud.fakebook.repository;

import com.currencycloud.fakebook.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FriendRepository extends JpaRepository<Friend, Long> {
    Friend findByFullname(String fullname);
}
