package api_tests;

import data_providers.ContactDataProvider;
import data_providers.UserDataProvider;
import dto.User;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseApi;
import utils.UserFactory;

import java.io.IOException;
import java.util.Random;

import static utils.PropertiesReader.getProperty;
import static utils.UserFactory.positiveUser;

public class RegistrationApiTests implements BaseApi {
    @Test
    public  void registrationPositive_ApiTest(){
        User user = positiveUser();
        //System.out.println(user);
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder().url(BASE_URL + REGISTRATION_URL).post(requestBody).build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(response.code());
        Assert.assertEquals(response.code(), 200);
    }


    @Test
    public  void registrationNegative_WrongPassword_ApiTest(){
        User user = positiveUser();
        user.setPassword("wrong password");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder().url(BASE_URL + REGISTRATION_URL).post(requestBody).build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 400);
    }

    //homework 16 --------------------------------------------------------------------------------

    @Test
    public  void registrationNegative_AlreadyExist_ApiTest(){
        //already exists
        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder().url(BASE_URL + REGISTRATION_URL).post(requestBody).build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //System.out.println(response.code());
        Assert.assertEquals(response.code(), 409);
    }

    @Test
    public  void registrationNegative_EmptyPassword_ApiTest(){
        User user = UserFactory.positiveUser();
        user.setPassword("");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder().url(BASE_URL + REGISTRATION_URL).post(requestBody).build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 400);
    }

    @Test
    public  void registrationNegative_EmptyMail_ApiTest(){
        User user = UserFactory.positiveUser();
        user.setUsername("");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder().url(BASE_URL + REGISTRATION_URL).post(requestBody).build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 400);
    }

    @Test
    public  void registrationNegative_NullPassword_ApiTest(){
        User user = UserFactory.positiveUser();
        user.setPassword(null);
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder().url(BASE_URL + REGISTRATION_URL).post(requestBody).build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 400);
    }

    @Test
    public  void registrationNegative_NullMail_ApiTest(){
        User user = UserFactory.positiveUser();
        user.setUsername(null);
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder().url(BASE_URL + REGISTRATION_URL).post(requestBody).build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 400);
    }

    @Test(dataProvider = "dataProviderFromFile_WrongPassword", dataProviderClass = UserDataProvider.class)
    public  void registrationNegative_WrongPassword_ApiTest(User user){
        System.out.println(user.toString());
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder().url(BASE_URL + REGISTRATION_URL).post(requestBody).build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 400);
    }

    @Test(dataProvider = "dataProviderFromFile_WrongEmail", dataProviderClass = UserDataProvider.class)
    public  void registrationNegative_WrongMail_ApiTest(User user){
        //System.out.println(user.toString());
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder().url(BASE_URL + REGISTRATION_URL).post(requestBody).build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 400);
    }

    //like @gmail.com
    @Test
    public  void registrationNegative_WrongMailNoLocalPart_ApiTest(){
        User user = UserFactory.positiveUser();
        Random rn = new Random();
        user.setUsername("@gmail" + rn.nextInt(1000) + ".com");
        //System.out.println(user.toString());
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder().url(BASE_URL + REGISTRATION_URL).post(requestBody).build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assert.assertEquals(response.code(), 400);
    }

}