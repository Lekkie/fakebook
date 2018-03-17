package com.currencycloud.fakebook.utils;

import com.currencycloud.fakebook.entity.*;
import com.currencycloud.fakebook.model.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


/**
 * Created by lekanomotayo on 16/03/2018.
 */
@Component
public class ModelEntityHelper {

    private ModelMapper modelMapper = new ModelMapper();

    public ModelEntityHelper(){
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setSourceNameTokenizer(NameTokenizers.CAMEL_CASE)
                .setDestinationNameTokenizer(NameTokenizers.CAMEL_CASE)
                .setMatchingStrategy(MatchingStrategies.STANDARD);
    }

    public PaymentForm convertToModel(Payment payment) {
        //PaymentForm paymentForm = modelMapper.map(payment, PaymentForm.class);
        PaymentForm paymentForm = new PaymentForm();
        paymentForm.setCurrencyId(payment.getCurrencyId());
        paymentForm.setRecipientId(payment.getRecipientId());
        paymentForm.setAmount(payment.getAmount().toPlainString());
        return paymentForm;
    }

    public Payment convertToEntity(PaymentForm paymentForm ){
        //Payment payment = modelMapper.map(paymentForm, Payment.class);
        Payment payment = new Payment();
        payment.setAmount(new BigDecimal(paymentForm.getAmount()));
        payment.setRecipientId(paymentForm.getRecipientId());
        payment.setCurrencyId(paymentForm.getCurrencyId());
        return payment;
    }

    public RecipientForm convertToModel(Recipient recipient) {
        RecipientForm recipientForm = modelMapper.map(recipient, RecipientForm.class);
        return recipientForm;
    }

    public Recipient convertToEntity(RecipientForm recipientForm){
        Recipient recipient = modelMapper.map(recipientForm, Recipient.class);
        return recipient;
    }


    public FriendForm convertToModel(Friend friend) {
        FriendForm friendForm = modelMapper.map(friend, FriendForm.class);
        return friendForm;
    }

    public Friend convertToEntity(FriendForm friendForm){
        Friend friend = modelMapper.map(friendForm, Friend.class);
        return friend;
    }


    public UserForm convertToModel(User user) {
        UserForm userForm = modelMapper.map(user, UserForm.class);
        return userForm;
    }

    public User convertToEntity(UserForm userForm){
        User user = modelMapper.map(userForm, User.class);
        return user;
    }

    public CurrencyForm convertToModel(Currency currency) {
        CurrencyForm currencyForm = modelMapper.map(currency, CurrencyForm.class);
        return currencyForm;
    }

    public Currency convertToEntity(CurrencyForm currencyForm){
        Currency recipient = modelMapper.map(currencyForm, Currency.class);
        return recipient;
    }

}
