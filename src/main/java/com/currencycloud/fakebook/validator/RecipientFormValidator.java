package com.currencycloud.fakebook.validator;

import com.currencycloud.fakebook.entity.Recipient;
import com.currencycloud.fakebook.model.RecipientForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RecipientFormValidator implements Validator {

    private static final String RECIPIENT_PATTERN = "^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$";
    static Pattern pattern;
    static {
        pattern = Pattern.compile(RECIPIENT_PATTERN);
    }

    public boolean supports(Class<?> aClass) {
        return RecipientForm.class.equals(aClass);
    }


    public void validate(Object o, Errors errors) {
        RecipientForm paymentRecipient = (RecipientForm) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullname", "NotEmpty");

        if (paymentRecipient.getFullname() != null) {
            Matcher matcher = pattern.matcher(paymentRecipient.getFullname());
            if (!matcher.matches()) {
                errors.rejectValue("fullname", "Recipient.invalid");
            }
        }

    }
}
