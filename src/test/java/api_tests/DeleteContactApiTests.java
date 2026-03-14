package api_tests;

import dto.Contact;
import dto.ErrorMessageDto;
import dto.ResponseMessage;
import dto.Token;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;
import utils.ContactFactory;
import utils.ILogin;

import java.io.IOException;

public class DeleteContactApiTests implements BaseApi, ILogin {
    Token token;
    String id;
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void login(){
        token = loginGetToken();
    }

    @Test
    public  void deleteContactPositive_ApiTest(){
        Contact contact = ContactFactory.positiveContact();
        id = addContactGetId(contact, token);

        Request request = new Request.Builder()
                .url(BASE_URL + DELETE_CONTACT_URL  + id )
                .addHeader(AUTH, token.getToken())
                .delete()
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            softAssert.assertEquals(response.code(), 200, "validate status code");
            ResponseMessage responseMessage = GSON.fromJson(response.body().string(), ResponseMessage.class);
            softAssert.assertTrue(responseMessage.getMessage().contains("Contact was deleted"), "validate message");
            softAssert.assertAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public  void deleteContactNegative_Wrong_ID_ApiTest(){
        Request request = new Request.Builder()
                .url(BASE_URL + DELETE_CONTACT_URL + "wrong_id" )
                .addHeader(AUTH, token.getToken())
                .delete()
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            softAssert.assertEquals(response.code(), 400, "validate status code");
            ErrorMessageDto errorMessageDto = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            softAssert.assertTrue(errorMessageDto.getMessage().contains("not found in your contacts"), "validate error message");
            softAssert.assertAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
