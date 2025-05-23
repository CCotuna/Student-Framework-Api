package com.example.studentframeworkapi.client.user;

import com.example.studentframeworkapi.client.api.user.GetClient;
import com.example.studentframeworkapi.model.user.ListUserResponse;
import com.example.studentframeworkapi.model.user.SingleUserResponse;
import io.restassured.response.Response;

public class GetUser {

    public static SingleUserResponse getUser(String path, int statusCode, String token) {
        return GetClient.getSingleUserParsed(path, statusCode, token);
    }

    public static ListUserResponse getUsers(String path, int expectedStatusCode, String token) {
        return GetClient.getAllUsersParsed(path, expectedStatusCode, token);
    }

    public static Response getUserNotFound(String path, int statusCode, String token) {
        return GetClient.getUserResponse(path, token);
    }

    public static ListUserResponse getUserDelayed(String path, int statusCode, String token) {
        return GetClient.getUserWithDelayParsed(path, statusCode, token);
    }
}
