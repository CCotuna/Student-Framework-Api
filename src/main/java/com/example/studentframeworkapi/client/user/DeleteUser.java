package com.example.studentframeworkapi.client.user;

import com.example.studentframeworkapi.client.api.user.DeleteClient;
import io.restassured.response.Response;

public class DeleteUser {
    public static Response deleteUser(String path, int statusCode, String token) {
        return DeleteClient.deleteCurrentUser(path, statusCode, token);
    }
}
