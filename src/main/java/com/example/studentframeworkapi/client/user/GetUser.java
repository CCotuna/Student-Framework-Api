package com.example.studentframeworkapi.client.user;

import com.example.studentframeworkapi.client.api.user.GetClient;

public class GetUser {
    public static String getUser(String path, int statusCode, String token) {
        return GetClient.getUserWithToken(path, statusCode, token);
    }

    public static String getUsers(String path, int expectedStatusCode, String token) {
        return GetClient.getAllUsers(path, expectedStatusCode, token);
    }

    public static String getUserNotFound(String path, int statusCode, String token) {
        return GetClient.getUserWithToken(path, statusCode, token);
    }

    public static String getUserDelayed(String path, int statusCode, String token) {
        return GetClient.getUserWithDelay(path, statusCode, token);
    }
}
