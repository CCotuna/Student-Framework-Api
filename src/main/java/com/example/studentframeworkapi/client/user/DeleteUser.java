package com.example.studentframeworkapi.client.user;

import com.example.studentframeworkapi.client.api.user.DeleteClient;
import com.example.studentframeworkapi.client.api.user.PutClient;

public class DeleteUser {
    public static String deleteUser(String path, int statusCode, String token) {
        return DeleteClient.deleteCurrentUser(path, statusCode, token);
    }
}
