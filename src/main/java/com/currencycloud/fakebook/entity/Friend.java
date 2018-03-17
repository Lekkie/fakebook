package com.currencycloud.fakebook.entity;

import com.currencycloud.fakebook.entity.BaseModel;

import javax.persistence.*;

/**
 * Created by lekanomotayo on 16/03/2018.
 */

@Entity
@Table(name = "tbl_friends")
public class Friend extends BaseModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long friendId;
    @Column
    private String fullname;


    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
