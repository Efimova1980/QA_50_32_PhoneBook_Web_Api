package api_tests;

import dto.*;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;
import utils.ContactFactory;
import utils.ILogin;
import utils.TestNGListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.PropertiesReader.getProperty;

//@Listeners(TestNGListener.class)

public class AddNewContactApiTests implements BaseApi, ILogin {
    Token token;
    SoftAssert softAssert = new SoftAssert();

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
            if (response.code()==200 && response.body() != null) {
                token = GSON.fromJson(response.body().string(), Token.class);
            }else System.out.println("no token in response");
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
                .addHeader(AUTH,"Bearer " + token.getToken())
                .post(requestBody)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            Assert.assertEquals(response.code(),200);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addNewContactPositive_ApiTest2(){
        Contact contact = ContactFactory.positiveContact();
        RequestBody requestBody = RequestBody
                .create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CONTACT_URL)
                .addHeader(AUTH,"Bearer " + token.getToken())
                .post(requestBody)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            softAssert.assertEquals(response.code(),200, "Validate status code");
            ResponseMessage responseMessage = GSON.fromJson(response.body().string(), ResponseMessage.class);
            softAssert.assertTrue(responseMessage.getMessage().contains("Contact was added!"), "validate message");
            softAssert.assertAll();
        } catch (IOException e) {
            //e.printStackTrace();
            Assert.fail("created exception");
        }
    }

    @Test
    public void addNewContactNegative_WO_Token_ApiTest(){
        Contact contact = ContactFactory.positiveContact();
        RequestBody requestBody = RequestBody
                .create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CONTACT_URL)
                .post(requestBody)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            Assert.assertEquals(response.code(),403);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addNewContactNegative_Wrong_Token_ApiTest(){
        Contact contact = ContactFactory.positiveContact();
        RequestBody requestBody = RequestBody
                .create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CONTACT_URL)
                .addHeader(AUTH,"Bearer " )
                .post(requestBody)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            softAssert.assertEquals(response.code(),401, "validate status code");
            ErrorMessageDto errorMessageDto = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            softAssert.assertEquals(errorMessageDto.getError(), "Unauthorized", "validate error");
            softAssert.assertTrue(errorMessageDto.getMessage().contains("strings must contain exactly 2 period characters"), "validate message");
            softAssert.assertAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //---------------------------------- homework -17 ------------------------------------------

    @Test
    public void addNewContactNegative_WrongBodyFormat_Text_ApiTest(){
        Contact contact = ContactFactory.positiveContact();
        RequestBody requestBody = RequestBody
                .create(GSON.toJson(contact), TEXT); //text
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CONTACT_URL)
                .addHeader(AUTH, token.getToken() )
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            softAssert.assertEquals(response.code(),500, "validate status code");
            ErrorMessageDto errorMessageDto = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            softAssert.assertTrue(errorMessageDto.getMessage().contains("Content type 'text/plain;charset=utf-8' not supported"), "validate message");
            softAssert.assertTrue(errorMessageDto.getError().contains("Internal Server Error"), "validate error");
            softAssert.assertAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addNewContactNegative_WrongJSON_ApiTest(){
        Contact contact = ContactFactory.positiveContact();
        Map<String, String> wrongJson = new HashMap<>();
        wrongJson.put("id", contact.getId());
        wrongJson.put("name", contact.getName());
        wrongJson.put("lastName", contact.getLastName());
        wrongJson.put("email", contact.getEmail());
        wrongJson.put("phoneNumber", contact.getPhone()); //wrong field name
        wrongJson.put("address", contact.getAddress());
        wrongJson.put("description", contact.getDescription());

        RequestBody requestBody = RequestBody
                .create(GSON.toJson(wrongJson), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CONTACT_URL)
                .addHeader(AUTH,"Bearer "+ token.getToken() )
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            //got response.code 200 = OK, contact was added WO field 'phone' (claimed field)
            Assert.assertEquals(response.code(),500);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addNewContactNegative_WrongURL_HTTP_ApiTest(){
        Contact contact = ContactFactory.positiveContact();
        RequestBody requestBody = RequestBody
                .create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL_HTTP + ADD_NEW_CONTACT_URL)
                .addHeader(AUTH, token.getToken() )
                .post(requestBody)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            //got response code 200
            Assert.assertEquals(response.code(),400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
