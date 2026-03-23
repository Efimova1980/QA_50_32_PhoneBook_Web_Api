package api_tests;

import dto.Contact;
import dto.ResponseMessage;
import dto.Token;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;
import static utils.ContactFactory.*;
import utils.ILogin;

import java.io.IOException;

public class UpdateContactApiTests implements BaseApi, ILogin {
    Token token;
    String id;
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void login(){
        token = loginGetToken();
    }

    @BeforeMethod
    public void createContact(){
        Contact contact = positiveContact();
        id = addContactGetId(contact, token);
    }

    @Test
    public void updateContactPositiveApiTest() {
        Contact newContact = positiveContact();
        newContact.setId(id);

        RequestBody requestBody = RequestBody
                .create(GSON.toJson(newContact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + EDIT_CONTACT_URL)
                .addHeader(AUTH,"Bearer " + token.getToken())
                .put(requestBody)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            softAssert.assertEquals(response.code(),200);
            ResponseMessage responseMessage = GSON.fromJson(response.body().string(), ResponseMessage.class);
            softAssert.assertTrue(responseMessage.getMessage().contains("Contact was updated"), "validate message");
            softAssert.assertAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
