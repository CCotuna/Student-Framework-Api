package com.example.studentframeworkapi.auth;

import com.example.studentframeworkapi.BaseTest;
import com.example.studentframeworkapi.model.auth.LoginErrorResponse;
import com.example.studentframeworkapi.model.auth.LoginResponse;
import com.example.studentframeworkapi.model.auth.RegisterErrorResponse;
import com.example.studentframeworkapi.model.auth.RegisterResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.example.studentframeworkapi.client.auth.LoginUser.loginUser;
import static com.example.studentframeworkapi.client.auth.LoginUser.loginUserUnsuccessful;
import static com.example.studentframeworkapi.client.auth.RegisterUser.registerUser;
import static com.example.studentframeworkapi.client.auth.RegisterUser.registerUserUnsuccessful;

public class AuthTests extends BaseTest {
    public static final String AUTH_TOKEN = "reqres-free-v1";

    @Test(groups = "Smoke", description = "Verify successful user login with valid credentials, status: 200.")
    void testLogin() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", "eve.holt@reqres.in");
        requestBody.put("password", "cityslicka");

        LoginResponse response = loginUser("/api/login", 200, requestBody, AUTH_TOKEN);
        Assert.assertNotNull(response.getToken(), "Token shouldn't be null for login");
        Assert.assertFalse(response.getToken().isEmpty(), "Token shouldn't be empty for login");
        System.out.println("Login successful, token: " + response.getToken());
    }

    @Test(groups = "Smoke", description = "Verify unsuccessful user login with missing password, status: 400.")
    void testLoginFailed() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", "eve.holt@reqres.in");

        LoginErrorResponse errorResponse = loginUserUnsuccessful("/api/login", 400, requestBody, AUTH_TOKEN);
        Assert.assertNotNull(errorResponse.getError(), "Error message shouldn't be null for failed login");
        Assert.assertFalse(errorResponse.getError().isEmpty(), "Error message shouldn't be empty for failed login");
        Assert.assertEquals(errorResponse.getError(), "Missing password", "Error message mismatch for failed login");
        System.out.println("Login failed as expected, error: " + errorResponse.getError());
    }

    @Test(groups = "Smoke", description = "Verify successful user registration with valid credentials, status: 200.")
    void testRegister() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", "eve.holt@reqres.in");
        requestBody.put("password", "pistol");

        RegisterResponse response = registerUser("/api/register", 200, requestBody, AUTH_TOKEN);
        Assert.assertNotNull(response.getId(), "ID shouldn't be null for register");
        Assert.assertTrue(response.getId() > 0, "ID shouldn't be a positive integer for register");
        Assert.assertNotNull(response.getToken(), "Token shouldn't be null for register");
        Assert.assertFalse(response.getToken().isEmpty(), "Token shouldn't be empty for register");
        System.out.println("Register successful, ID: " + response.getId() + ", Token: " + response.getToken());
    }

    @Test(groups = "Smoke", description = "Verify unsuccessful user registration with missing password, status: 400.")
    void testRegisterFailed() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("email", "sydney@fife");

        RegisterErrorResponse errorResponse = registerUserUnsuccessful("/api/register", 400, requestBody, AUTH_TOKEN);
        Assert.assertNotNull(errorResponse.getError(), "Error message shouldn't be null for failed register");
        Assert.assertFalse(errorResponse.getError().isEmpty(), "Error message shouldn't be empty for failed register");
        Assert.assertEquals(errorResponse.getError(), "Missing password", "Error message mismatch for failed register");
        System.out.println("Register failed as expected, error: " + errorResponse.getError());
    }
}