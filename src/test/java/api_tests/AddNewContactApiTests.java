package api_tests;

import dto.Contact;
import dto.ResponseMessage;
import dto.Token;
import dto.User;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.BaseApi;
import utils.ContactFactory;
import utils.TestNGListener;

import java.io.IOException;

import static utils.PropertiesReader.getProperty;

@Listeners(TestNGListener.class)

public class AddNewContactApiTests implements BaseApi {
    Token token;

    @BeforeClass
    public void login(){
        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));

        RequestBody requestBody = RequestBody
                .create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            token = GSON.fromJson(response.body().string(), Token.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addNewContactPositive_ApiTest(){
        Contact contact = ContactFactory.positiveContact();
        RequestBody requestBody = RequestBody
                .create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CONTACT_URL)
                .addHeader("Authorization","Bearer " + token.getToken())
                .post(requestBody)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            Assert.assertEquals(response.code(),200);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
