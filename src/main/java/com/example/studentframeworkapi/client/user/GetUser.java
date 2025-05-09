package com.example.studentframeworkapi.client.user;

import com.example.studentframeworkapi.client.api.GetClient;

public class GetUser {
    public static String getUser(String path, int statusCode, String token) {
        return GetClient.getUserWithToken(path, statusCode, token);
    }
}
