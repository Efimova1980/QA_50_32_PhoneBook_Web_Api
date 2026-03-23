package api_tests;

import dto.ErrorMessageDto;
import dto.Token;
import dto.User;
import lombok.val;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;
import static utils.UserFactory.positiveUser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static utils.PropertiesReader.getProperty;

public class LoginApiTests implements BaseApi {
    SoftAssert softAssert = new SoftAssert();
    User user;

    @BeforeMethod
    public void createUser(){
        user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
    }

    @Test
    public void LoginPositiveApiTest() {
        RequestBody requestBody = RequestBody
                .create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            Assert.assertEquals(response.code(), 200);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void LoginNegativeApiTest_LoginIncorrect() {
        user.setUsername("hkhjhj");
        RequestBody requestBody = RequestBody
                .create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            softAssert.assertEquals(response.code(), 401, "validate status");
            ErrorMessageDto errorMessageDto = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            softAssert.assertTrue(errorMessageDto.getError()
                    .contains("Unauthorized"), "validate error ");
            System.out.println(errorMessageDto.getMessage());
            softAssert.assertEquals(errorMessageDto.getMessage(),"Login or Password incorrect"
                    , "validate error message");
            softAssert.assertAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void LoginNegativeApiTest_PasswordIncorrect() {
        user.setPassword("hkhjhj");
        RequestBody requestBody = RequestBody
                .create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            softAssert.assertEquals(response.code(), 401, "validate status");
            ErrorMessageDto errorMessageDto = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            softAssert.assertTrue(errorMessageDto.getError()
                    .contains("Unauthorized"), "validate error ");
            softAssert.assertEquals(errorMessageDto.getMessage(),"Login or Password incorrect"
                    , "validate error message");
            softAssert.assertAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void LoginNegativeApiTest_WrongRequest() {
        RequestBody requestBody = RequestBody
                .create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .put(requestBody) //post -> put
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            Assert.assertEquals(response.code(), 403);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void LoginNegativeApiTest_WrongMediaType() {
        RequestBody requestBody = RequestBody
                .create(user.toString(), TEXT);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            softAssert.assertEquals(response.code(), 400, "validate status");
            ErrorMessageDto errorMessageDto = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
            softAssert.assertEquals(errorMessageDto.getMessage(), "Wrong format Credential Object"
                    ,"validate error message" );
            softAssert.assertEquals(errorMessageDto.getError(), "Bad Request", "Validate error");
            softAssert.assertAll();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void LoginNegativeApiTest_WrongBaseUrl() {
        RequestBody requestBody = RequestBody
                .create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL_HTTP+ LOGIN_URL )
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            Assert.assertEquals(response.code(), 400); //bug, actually 200
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void LoginNegativeApiTest_WrongRequestBody() {
        Map<String, String> wrongUser = new HashMap<>();
        wrongUser.put("password" , user.getPassword());
        wrongUser.put("user" , user.getUsername()); //wrong field key

        RequestBody requestBody = RequestBody
                .create(GSON.toJson(wrongUser), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL_HTTP+ LOGIN_URL )
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            Assert.assertEquals(response.code(), 400);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void LoginNegativeApiTest_SpacesInUserName() {
        user.setUsername(" " + user.getUsername() + " "); //server does not cut spaces before and after
        RequestBody requestBody = RequestBody
                .create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL_HTTP+ LOGIN_URL )
                .post(requestBody)
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            Assert.assertEquals(response.code(), 200); //actual 401
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
