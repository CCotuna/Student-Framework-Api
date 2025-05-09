package com.example.studentframeworkapi.client.user;

import com.example.studentframeworkapi.client.api.GetClient;

public class GetUser {
    public static String getUser(String path, int statusCode, String token) {
        return GetClient.getUserWithToken(path, statusCode, token);
    }

    public static String getUsers(String path, int expectedStatusCode, String token) {
        return GetClient.getUsers(path, expectedStatusCode, token);
    }

    public static String getUserNotFound(String path, int statusCode, String token) {
        return GetClient.getUserWithToken(path, statusCode, token);
    }
}
