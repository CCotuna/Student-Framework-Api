package com.example.studentframeworkapi.client.auth;

import com.example.studentframeworkapi.client.api.auth.RegisterClient;

import java.util.Map;

public class RegisterUser {
    public static String registerUser (String path, int statusCode, Map<String, Object> requestBody, String token){
        return RegisterClient.signUpUser(path, statusCode, requestBody, token);
    }

    public static String registerUserUnsuccessful (String path, int statusCode, Map<String, Object> requestBody, String token){
        return RegisterClient.signUpUserFailed(path, statusCode, requestBody, token);
    }
}
