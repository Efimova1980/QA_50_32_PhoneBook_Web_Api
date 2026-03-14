package utils;

import dto.Contact;
import dto.Token;
import dto.User;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import java.io.IOException;

import static utils.PropertiesReader.getProperty;

public interface ILogin extends BaseApi {

    default Token loginGetToken() {
        Token token;
        SoftAssert softAssert = new SoftAssert();

        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));

        RequestBody requestBody = RequestBody
                .create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URL)
                .post(requestBody)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            if (response.code() == 200 && response.body() != null) {
                token = GSON.fromJson(response.body().string(), Token.class);
                return token;
            } else System.out.println("no token in response");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new Token();
    }

    default String addContactGetId(Contact contact, Token token){
        RequestBody requestBody = RequestBody
                .create(GSON.toJson(contact), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CONTACT_URL)
                .addHeader(AUTH, token.getToken())
                .post(requestBody)
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            if(response.code() == 200  && response.body() != null){
                String body = response.body().string();
                return body.replaceAll("[\"}{]", "").split("ID: ")[1];
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "";
    }
}
