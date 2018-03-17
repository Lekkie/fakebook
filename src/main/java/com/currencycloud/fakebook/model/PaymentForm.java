package com.currencycloud.fakebook.model;

/**
 * Created by lekanomotayo on 16/03/2018.
 */
public class PaymentForm {

    private String amount;
    private Long currencyId;
    private Long recipientId;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public Long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(Long recipientId) {
        this.recipientId = recipientId;
    }
}
