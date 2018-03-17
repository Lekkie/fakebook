package com.currencycloud.fakebook.validator;

import com.currencycloud.fakebook.entity.Friend;
import com.currencycloud.fakebook.model.FriendForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class FriendFormValidator implements Validator {

    public boolean supports(Class<?> aClass) {
        return FriendForm.class.equals(aClass);
    }


    public void validate(Object o, Errors errors) {
        FriendForm friend = (FriendForm) o;

        if (friend.getFriendId() == null || friend.getFriendId() < 1) {
            errors.rejectValue("friendId", "Friend.not.selected");
        }

    }
}
