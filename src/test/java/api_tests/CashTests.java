package api_tests;

import dto.ContactsDto;
import dto.Token;
import manager.AppManager;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;
import utils.ILogin;

import java.io.IOException;

public class CashTests implements BaseApi, ILogin {
    Token token;

    @BeforeClass
    public void login(){
        token = loginGetToken();
    }

    @Test
    public void cashNoCashPositiveTest_ApiTest(){ //if cash is off
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_URL)
                .addHeader(AUTH,token.getToken())
                .get()
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()) {
            String cashControl = response.header("Cache-Control");
            Assert.assertNotNull(cashControl);
            Assert.assertTrue(cashControl.contains("no-store"));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Test
    public void cashNotModifiedPositive_ApiTest(){ //if cash is on
        Request request = new Request.Builder()
                .url(BASE_URL + GET_ALL_CONTACTS_URL)
                .addHeader(AUTH,token.getToken())
                .get()
                .build();

        try (Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            System.out.println(response.headers());
            String etag = response.header("ETag");
            if(etag == null) {
                System.out.println("ETag is missing, caching not supported");
                Assert.fail();
            }
            Request secondRequest = new Request.Builder()
                    .url(BASE_URL + GET_ALL_CONTACTS_URL)
                    .addHeader(AUTH,token.getToken())
                    .addHeader("If-None-Match", etag)
                    .get()
                    .build();
            try (Response secondResponse = OK_HTTP_CLIENT.newCall(secondRequest).execute()){
                Assert.assertEquals(secondResponse.code(), 304);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
