package com.example.studentframeworkapi.client.user;

import com.example.studentframeworkapi.client.api.PostClient;

import java.util.Map;

public class CreateUser {
    public static String createUser(String path, int statusCode, Map<String, Object> requestBody, String token){
        return PostClient.createUserWithToken(path, statusCode, requestBody, token);
    }
}
