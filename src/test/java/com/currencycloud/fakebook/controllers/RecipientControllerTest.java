package com.currencycloud.fakebook.controllers;

import com.currencycloud.fakebook.entity.Recipient;
import com.currencycloud.fakebook.model.RecipientForm;
import com.currencycloud.fakebook.service.RecipientService;
import com.currencycloud.fakebook.service.RestClientService;
import com.currencycloud.fakebook.utils.ModelEntityHelper;
import com.currencycloud.fakebook.utils.TestUtil;
import com.currencycloud.fakebook.validator.RecipientFormValidator;
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
public class RecipientControllerTest {

    RecipientController controller = new RecipientController();


    final RecipientFormValidator validator = new RecipientFormValidator();
    final ModelEntityHelper modelEntityHelper = new ModelEntityHelper();
    final RestClientService successRestClientService = Mockito.mock(RestClientService.class);
    final RestClientService failRestClientService = Mockito.mock(RestClientService.class);
    final RecipientService successRecipientService = Mockito.mock(RecipientService.class);
    final RecipientService failRecipientService = Mockito.mock(RecipientService.class);

    RecipientForm newRecipient = new RecipientForm();
    RecipientForm newMissingFieldRecipient = new RecipientForm();
    final Date createOn = new Date();
    final String createBy = "User";

    @Before
    public void start() throws Exception{

        newRecipient.setFullname("Jake McFriend");

        Recipient createRecip = new Recipient();
        createRecip.setFullname(newRecipient.getFullname());
        createRecip.setExtRecipientId("e9a0336b-d81d-4009-9ad1-8fa1eb43418c");

        Recipient savedRecip = new Recipient();
        savedRecip.setRecipientId(1L);
        savedRecip.setFullname(createRecip.getFullname());
        savedRecip.setExtRecipientId(createRecip.getExtRecipientId());
        savedRecip.setCreatedBy("User");
        savedRecip.setCreatedOn(new Date());

        Mockito.when(successRestClientService.createRecipient(Matchers.any(Recipient.class))).thenReturn(createRecip);
        Mockito.when(successRecipientService.save(createRecip)).thenReturn(savedRecip);

        Recipient failedRecip = new Recipient();
        Mockito.when(failRestClientService.createRecipient(Matchers.any(Recipient.class))).thenReturn(failedRecip);

    }


    // test getting payment page
    @Test
    public void getRecipientPage() throws Exception{
        Model model= new ExtendedModelMap();
        RecipientFormValidator validator = new RecipientFormValidator();

        String view = controller.addRecipient(model);

        Object recipientForm = model.asMap().get("recipientForm");
        assertTrue("Missing Recipient form", model.containsAttribute("recipientForm"));
        assertTrue("Form not instance of Recipient", recipientForm instanceof RecipientForm);
        assertEquals("View page did not match expected value", "recipient", view);
    }

    //Test recipient with all fields set and not approved
    @Test
    public void AddSuccessfulRecipientPage() throws Exception{
        Model model= new ExtendedModelMap();
        BindingResult bindingResult = new BeanPropertyBindingResult(newRecipient, "recipientForm");

        TestUtil.setField(controller, "recipientService", successRecipientService);
        TestUtil.setField(controller, "restClientService", successRestClientService);
        TestUtil.setField(controller, "recipientValidator", validator);
        TestUtil.setField(controller, "modelEntityHelper", modelEntityHelper);

        String view = controller.addRecipient(newRecipient, bindingResult, model);

        assertTrue("Expected recipientAdded value to be present", model.containsAttribute("recipientAdded"));
        assertTrue("There shuld be no error", !bindingResult.hasErrors());
        assertEquals("View page did not match expected value", "recipient", view);
    }

    //Test recipient with all fields set and not approved
    @Test
    public void doFailedRecipientPage() throws Exception{
        Model model= new ExtendedModelMap();
        BindingResult bindingResult = new BeanPropertyBindingResult(newRecipient, "recipientForm");

        TestUtil.setField(controller, "recipientService", successRecipientService);
        TestUtil.setField(controller, "restClientService", failRestClientService);
        TestUtil.setField(controller, "recipientValidator", validator);
        TestUtil.setField(controller, "modelEntityHelper", modelEntityHelper);

        String view = controller.addRecipient(newRecipient, bindingResult, model);

        Errors errors = (Errors) bindingResult;
        assertTrue("There shuld be error", bindingResult.hasErrors());
        assertTrue("There shuld be amount error", errors.hasFieldErrors("fullname"));
        String code = errors.getFieldError("fullname").getCode();
        assertEquals("Eror code did not match expected value", "Recipient.save.error", code);
        assertTrue("recipientAdded value should be absent", !model.containsAttribute("recipientAdded"));
        assertEquals("View page did not match expected value", "recipient", view);
    }


    //Test recipient with missing fields
    @Test
    public void missingRecipientFieldPage() throws Exception{
        Model model= new ExtendedModelMap();
        BindingResult bindingResult = new BeanPropertyBindingResult(newMissingFieldRecipient, "recipientForm");

        TestUtil.setField(controller, "recipientService", successRecipientService);
        TestUtil.setField(controller, "restClientService", failRestClientService);
        TestUtil.setField(controller, "recipientValidator", validator);
        TestUtil.setField(controller, "modelEntityHelper", modelEntityHelper);

        String view = controller.addRecipient(newMissingFieldRecipient, bindingResult, model);

        Errors errors = (Errors) bindingResult;
        assertTrue("There shuld be form error", bindingResult.hasErrors());
        assertTrue("There shuld be fullname error", errors.hasFieldErrors("fullname"));
        String code = errors.getFieldError("fullname").getCode();
        assertEquals("Error code did not match expected value", "NotEmpty", code);
        assertTrue("Expected recipientAdded value to be absent", !model.containsAttribute("recipientAdded"));
        assertEquals("View page did not match expected value", "recipient", view);
    }


}
