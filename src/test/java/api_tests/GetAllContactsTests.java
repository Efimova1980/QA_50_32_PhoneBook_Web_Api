package api_tests;

import dto.ContactsDto;
import dto.ErrorMessageDto;
import dto.ResponseMessage;
import dto.Token;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;
import utils.ILogin;

import java.io.IOException;

public class GetAllContactsTests implements BaseApi, ILogin {
    Token token;
    SoftAssert softAssert = new SoftAssert();

    @BeforeClass
    public void login(){
        token = loginGetToken();
    }

    @Test
    public void getAllUserContactsPositive_ApiTest(){
        System.out.println(token);
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_URL)
                .addHeader(AUTH,token.getToken())
                .get()
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            ContactsDto contactsDto = GSON.fromJson(response.body().string(), ContactsDto.class);
            System.out.println(contactsDto);
            Assert.assertEquals(response.code(),200);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void getAllUserContactsNegative_WrongToken_ApiTest(){
        System.out.println(token);
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_URL)
                .addHeader(AUTH,"wrong token")
                .get()
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            softAssert.assertEquals(response.code(),401, "validate status code");
            ErrorMessageDto errorMessageDto = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            softAssert.assertEquals(errorMessageDto.getError(), "Unauthorized", "validate error name");
            softAssert.assertTrue(errorMessageDto.getMessage().contains("strings must contain exactly 2 period characters"), "validate message");
            softAssert.assertAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
