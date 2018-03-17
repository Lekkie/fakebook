package com.currencycloud.fakebook.entity;

import com.currencycloud.fakebook.entity.BaseModel;

import javax.persistence.*;

/**
 * Created by lekanomotayo on 14/03/2018.
 */

@Entity
@Table(name = "tbl_recipients")
public class Recipient  extends BaseModel {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long recipientId;
    @Column
    private String fullname;
    @Column
    private String extRecipientId;

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

    public String getExtRecipientId() {
        return extRecipientId;
    }

    public void setExtRecipientId(String extRecipientId) {
        this.extRecipientId = extRecipientId;
    }
}
