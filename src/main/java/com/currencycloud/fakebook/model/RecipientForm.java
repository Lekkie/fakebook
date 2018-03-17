package com.currencycloud.fakebook.model;

/**
 * Created by lekanomotayo on 16/03/2018.
 */
public class RecipientForm {

    private Long recipientId;
    private String fullname;

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
