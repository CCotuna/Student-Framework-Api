package com.example.studentframeworkapi.client.user;

import com.example.studentframeworkapi.client.api.user.PostClient;
import com.example.studentframeworkapi.model.user.UserRequest;
import com.example.studentframeworkapi.model.user.UserResponse;

public class CreateUser {

    public static UserResponse createUser(String path, int statusCode, UserRequest requestBody, String token) {
        return PostClient.createUserWithToken(path, statusCode, requestBody, token);
    }
}
