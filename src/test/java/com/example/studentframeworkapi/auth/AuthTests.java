package com.example.studentframeworkapi.auth;

import com.example.studentframeworkapi.BaseTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.example.studentframeworkapi.client.api.auth.RegisterClient.signUpUserFailed;
import static com.example.studentframeworkapi.client.auth.LoginUser.loginUser;
import static com.example.studentframeworkapi.client.auth.LoginUser.loginUserUnsuccessful;
import static com.example.studentframeworkapi.client.auth.RegisterUser.registerUser;
import static com.example.studentframeworkapi.client.user.GetUser.getUserDelayed;

public class AuthTests extends BaseTest {
    public static final String AUTH_TOKEN = "reqres-free-v1";

    @Test(groups = "Smoke")
    void testLogin() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", "eve.holt@reqres.in");
        requestBody.put("password", "cityslicka");

        loginUser("/api/login", 200, requestBody, AUTH_TOKEN);
    }

    @Test(groups = "Smoke")
    void testLoginFailed() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", "eve.holt@reqres.in");

        loginUserUnsuccessful("/api/login", 400, requestBody, AUTH_TOKEN);
    }

    @Test(groups = "Smoke")
    void testRegister() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", "eve.holt@reqres.in");
        requestBody.put("password", "pistol");

        registerUser("/api/register", 200, requestBody, AUTH_TOKEN);
    }

    @Test(groups = "Smoke")
    void testRegisterFailed() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", "eve.holt@reqres.in");

        signUpUserFailed("/api/register", 400, requestBody, AUTH_TOKEN);
    }

    @Test(groups = "Smoke")
    void testGetUserWithDelay() {
        String path = "/api/users?delay=3";
        int expectedStatus = 200;

        getUserDelayed(path, expectedStatus, AUTH_TOKEN);
    }
}
