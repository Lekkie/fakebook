package com.currencycloud.fakebook.model;

/**
 * Created by lekanomotayo on 16/03/2018.
 */
public class FriendForm {

    private Long friendId;
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
