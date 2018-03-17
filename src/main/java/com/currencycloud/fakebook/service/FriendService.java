package com.currencycloud.fakebook.service;

import com.currencycloud.fakebook.entity.Friend;
import com.currencycloud.fakebook.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lekanomotayo on 14/03/2018.
 */

@Service
public class FriendService {

    @Autowired
    FriendRepository friendRepository;

    public Friend save(Friend friend) {
        return friendRepository.save(friend);
    }

    public Friend findById(Long id) {
        return friendRepository.findOne(id);
    }

    public Friend findByFullname(String fullname) {
        return friendRepository.findByFullname(fullname);
    }

    public List<Friend> findAll() {
        return friendRepository.findAll();
    }


}
