package com.example.studentframeworkapi.client.auth;

import com.example.studentframeworkapi.client.api.auth.RegisterClient;
import com.example.studentframeworkapi.model.auth.RegisterErrorResponse;
import com.example.studentframeworkapi.model.auth.RegisterResponse;

import java.util.Map;

public class RegisterUser {
    public static RegisterResponse registerUser (String path, int statusCode, Map<String, Object> requestBody, String token){
        return RegisterClient.signUpUser(path, statusCode, requestBody, token);
    }

    public static RegisterErrorResponse registerUserUnsuccessful (String path, int statusCode, Map<String, Object> requestBody, String token){
        return RegisterClient.signUpUserFailed(path, statusCode, requestBody, token);
    }
}
