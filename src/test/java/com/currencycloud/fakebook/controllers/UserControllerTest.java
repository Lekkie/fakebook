package com.currencycloud.fakebook.controllers;

import com.currencycloud.fakebook.entity.User;
import com.currencycloud.fakebook.model.UserForm;
import com.currencycloud.fakebook.service.UserService;
import com.currencycloud.fakebook.utils.ModelEntityHelper;
import com.currencycloud.fakebook.utils.TestUtil;
import com.currencycloud.fakebook.validator.UserFormValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lekanomotayo on 16/03/2018.
 */
public class UserControllerTest {


    UserController controller = new UserController();
    final ModelEntityHelper modelEntityHelper = new ModelEntityHelper();
    UserFormValidator validator = new UserFormValidator();
    final UserService userService = Mockito.mock(UserService.class);

    UserForm newUser = new UserForm();
    UserForm newMissingFieldUser = new UserForm();
    final Date createOn = new Date();
    final String createBy = "User";

    @Before
    public void start() throws Exception{

        newUser.setUsername("Administrator");
        newUser.setPassword("Password");
        newUser.setFirstname("Firstname");
        newUser.setLastname("Lastname");
        newUser.setPasswordConfirm("Password");

        User savedUser = new User();
        savedUser.setUserId(1L);
        savedUser.setUsername(newUser.getUsername());
        savedUser.setPassword(newUser.getPassword());
        savedUser.setCreatedBy("User");
        savedUser.setCreatedOn(new Date());

        Mockito.when(userService.save(Matchers.any(User.class))).thenReturn(savedUser);
    }


    // test getting payment page
    @Test
    public void getRegistrationPage() throws Exception{

        Model model= new ExtendedModelMap();

        String view = controller.registration(model);

        Object userForm = model.asMap().get("userForm");
        assertTrue("Missing User form", model.containsAttribute("userForm"));
        assertTrue("Form not instance of User", userForm instanceof UserForm);
        assertEquals("View page did not match expected value", "registration", view);
    }

    //Test recipient with all fields set and not approved
    @Test
    public void SuccessFullRegisterPage() throws Exception{
        Model model= new ExtendedModelMap();
        BindingResult bindingResult = new BeanPropertyBindingResult(newUser, "userForm");

        TestUtil.setField(controller, "userService", userService);
        TestUtil.setField(validator, "userService", userService);
        TestUtil.setField(controller, "userValidator", validator);
        TestUtil.setField(controller, "modelEntityHelper", modelEntityHelper);

        String view = controller.registration(newUser, bindingResult, model);

        assertTrue("There shuld be no error", !bindingResult.hasErrors());
        assertEquals("View page did not match expected value", "redirect:/home", view);
    }


    //Test recipient with missing fields
    @Test
    public void missingUserFieldPage() throws Exception{
        Model model= new ExtendedModelMap();
        BindingResult bindingResult = new BeanPropertyBindingResult(newMissingFieldUser, "userForm");

        TestUtil.setField(controller, "userService", userService);
        TestUtil.setField(validator, "userService", userService);
        TestUtil.setField(controller, "userValidator", validator);
        TestUtil.setField(controller, "modelEntityHelper", modelEntityHelper);

        String view = controller.registration(newUser, bindingResult, model);

        Errors errors = (Errors) bindingResult;
        assertTrue("There shuld be form error", bindingResult.hasErrors());
        assertTrue("There shuld be username error", errors.hasFieldErrors("username"));
        assertTrue("There shuld be password error", errors.hasFieldErrors("password"));
        assertTrue("There shuld be passwordConfirm error", errors.hasFieldErrors("passwordConfirm"));
        String usernameCode = errors.getFieldError("username").getCode();
        String passwordCode = errors.getFieldError("password").getCode();
        String passwordConfirmCode = errors.getFieldError("passwordConfirm").getCode();
        assertEquals("Error code did not match expected value", "NotEmpty", usernameCode);
        assertEquals("Error code did not match expected value", "NotEmpty", passwordCode);
        assertEquals("Error code did not match expected value", "NotEmpty", passwordConfirmCode);
        assertEquals("View page did not match expected value", "registration", view);
    }

}
