package com.currencycloud.fakebook.entity;

import com.currencycloud.fakebook.entity.BaseModel;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by lekanomotayo on 14/03/2018.
 */

@Entity
@Table(name = "tbl_payments")
public class Payment  extends BaseModel {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long paymentId;
    @Column
    private BigDecimal amount; // Always save amount/money/pries in bigdecimal,  doubles/floats loses precision
    @Column
    private Long currencyId;
    @Column
    private Long recipientId;
    @Column
    private String status;
    @Column
    private String extPaymentId;

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExtPaymentId() {
        return extPaymentId;
    }

    public void setExtPaymentId(String extPaymentId) {
        this.extPaymentId = extPaymentId;
    }
}
