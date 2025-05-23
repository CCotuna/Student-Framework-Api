package com.example.studentframeworkapi.client.auth;

import com.example.studentframeworkapi.client.api.auth.LoginClient;
import com.example.studentframeworkapi.model.auth.LoginErrorResponse;
import com.example.studentframeworkapi.model.auth.LoginResponse;

import java.util.Map;

public class LoginUser {
    public static LoginResponse loginUser(String path, int statusCode, Map<String, Object> requestBody, String token) {
        return LoginClient.signInUser(path, statusCode, requestBody, token);
    }

    public static LoginErrorResponse loginUserUnsuccessful(String path, int statusCode, Map<String, Object> requestBody, String token) {
        return LoginClient.signInUserFailed(path, statusCode, requestBody, token);
    }
}
