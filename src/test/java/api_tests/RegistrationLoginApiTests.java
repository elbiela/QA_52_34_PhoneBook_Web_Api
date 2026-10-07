package api_tests;

import dto.User;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseApi;

import java.io.IOException;

import static utils.UserFactory.*;
import static utils.PropertiesReader.*;

public class RegistrationLoginApiTests implements BaseApi {
    User user;
    @Test(groups = {"regression", "smoke", "registration", "positive"})
    public void registrationApiPositiveTest(){
        user = positiveRegistrationUser();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 200);
    }

    @Test(groups = {"regression", "registration", "login", "positive"})
    public void loginApiWithNewlyRegisteredUserPositiveTest(){
        user = positiveRegistrationUser();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 200, "validate code 200");
        request = new Request.Builder()
                .url(BASE_URL+LOGIN_URL)
                .post(requestBody)
                .build();
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 200, "validate code 200");
    }

    @Test(groups = {"regression", "registration", "negative"})
    public void registrationApiInvalidPasswordNegativeTest(){
        user = positiveRegistrationUser();
        user.setPassword("fghfg123!");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 400);
    }

    @Test(groups = {"regression", "registration", "negative"})
    public void registrationApiInvalidEmailNegativeTest(){
        user = positiveRegistrationUser();
        user.setUsername("dsjfhdfhj.rjh");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 400);
    }

    @Test(groups = {"regression", "registration", "negative"})
    public void registrationApiEmptyEmailNegativeTest(){
        user = positiveRegistrationUser();
        user.setUsername("");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 400);
    }

    @Test(groups = {"regression", "registration", "negative"})
    public void registrationApiEmptyPasswordNegativeTest(){
        user = positiveRegistrationUser();
        user.setPassword("");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 400);
    }

    @Test(groups = {"regression", "registration", "negative"})
    public void registrationApiEmptyAllFieldsNegativeTest(){
        user = positiveRegistrationUser();
        user.setUsername("");
        user.setPassword("");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 400);
    }

    @Test(groups = {"regression", "registration", "negative"})
    public void registrationApiDuplicateUserNegativeTest() {
        user = positiveRegistrationUser();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            OK_HTTP_CLIENT.newCall(request).execute();
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 409);
    }

    @Test(groups = {"regression", "registration", "negative"})
    public void registrationApiWrongFormatNegativeTest() {
        user = positiveRegistrationUser();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), TEXT);
        Request request = new Request.Builder()
                .url(BASE_URL + REGISTRATION_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 500);
    }

    @Test(groups = {"regression", "smoke", "login", "positive"})
    public void loginApiPositiveTest(){
        user = User.builder()
                .username(getProperty("base.properties", "email"))
                .password(getProperty("base.properties", "password"))
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 200);
    }

    @Test(groups = {"regression", "login", "negative"})
    public void loginApiUnregisteredEmailNegativeTest(){
        user = positiveRegistrationUser();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 401);
    }

    @Test(groups = {"regression", "smoke", "login", "negative"})
    public void loginApiWrongPasswordNegativeTest(){
        User user = User.builder()
                .username(getProperty("base.properties", "email"))
                .password("Tvhj123$$")
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 401);
    }

    @Test(groups = {"regression", "login", "negative"})
    public void loginApiInvalidEmailFormatNegativeTest(){
        user = positiveRegistrationUser();
        user.setUsername("fjbjfdj.dj");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 401);
    }

    @Test(groups = {"regression", "login", "negative"})
    public void loginApiInvalidPasswordFormatNegativeTest(){
        user = positiveLoginUser();
        user.setPassword("GFFG567!");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 401);
    }

    @Test(groups = {"regression", "login", "negative"})
    public void loginApiEmptyEmailNegativeTest(){
        user = positiveLoginUser();
        user.setUsername("");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 401);
    }

    @Test(groups = {"regression", "login", "negative"})
    public void loginApiEmptyPasswordNegativeTest(){
        user = positiveLoginUser();
        user.setPassword("");
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 401);
    }

    @Test(groups = {"regression", "login", "negative"})
    public void loginApiEmptyAllFieldsTest(){
        user = User.builder()
                .username("")
                .password("")
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(user), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_URL)
                .post(requestBody)
                .build();
        Response response;
        try {
            response = OK_HTTP_CLIENT.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(response);
        Assert.assertEquals(response.code(), 401);
    }


}
