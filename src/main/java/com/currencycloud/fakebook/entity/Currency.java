package com.currencycloud.fakebook.entity;

import com.currencycloud.fakebook.entity.BaseModel;

import javax.persistence.*;

/**
 * Created by lekanomotayo on 14/03/2018.
 */

@Entity
@Table(name = "tbl_currencies")
public class Currency extends BaseModel {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long currencyId;
    @Column
    private String code;
    @Column
    private String name;

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
