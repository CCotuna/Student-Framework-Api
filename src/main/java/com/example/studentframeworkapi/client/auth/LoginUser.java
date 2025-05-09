package com.example.studentframeworkapi.client.auth;

import com.example.studentframeworkapi.client.api.auth.LoginClient;

import java.util.Map;

public class LoginUser {
    public static String loginUser(String path, int statusCode, Map<String, Object> requestBody, String token) {
        return LoginClient.signInUser(path, statusCode, requestBody, token);
    }

    public static String loginUserUnsuccessful(String path, int statusCode, Map<String, Object> requestBody, String token) {
        return LoginClient.signInUserFailed(path, statusCode, requestBody, token);
    }
}
