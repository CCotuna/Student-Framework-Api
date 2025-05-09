package com.example.studentframeworkapi.client.user;

import com.example.studentframeworkapi.client.api.user.PutClient;

public class UpdateUser {
    public static String updateUser(String path, int statusCode, String token, String name, String job) {
        return PutClient.updateCurrentUser(path, statusCode, token, name, job);
    }
}
