package com.currencycloud.fakebook.validator;

import com.currencycloud.fakebook.entity.Payment;
import com.currencycloud.fakebook.model.PaymentForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PaymentFormValidator implements Validator {

    private static final String AMOUNT_PATTERN = "((\\d{1,10})(((\\.)(\\d{0,2})){0,1}))";
    static Pattern pattern;
    static {
        pattern = Pattern.compile(AMOUNT_PATTERN);
    }


    public boolean supports(Class<?> aClass) {
        return PaymentForm.class.equals(aClass);
    }


    public void validate(Object o, Errors errors) {

        PaymentForm payFriend = (PaymentForm) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "amount", "NotEmpty");

        if (payFriend.getAmount() != null) {
            Matcher matcher = pattern.matcher(payFriend.getAmount());
            if (!matcher.matches()) {
                errors.rejectValue("amount", "Amount.invalid");
            }
        }


        if (payFriend.getRecipientId() == null || payFriend.getRecipientId() < 1) {
            errors.rejectValue("recipientId", "Recipient.not.selected");
        }

        if (payFriend.getCurrencyId() == null || payFriend.getCurrencyId() < 1) {
            errors.rejectValue("currencyId", "Currency.not.selected");
        }

    }
}
