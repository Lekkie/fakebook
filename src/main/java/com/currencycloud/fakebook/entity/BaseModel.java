package com.currencycloud.fakebook.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by lekanomotayo on 15/03/2018.
 */

@MappedSuperclass
public class BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "created_on")
    protected Date createdOn = new Date();
    @Column(name = "created_by")
    protected String createdBy = "System";

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
