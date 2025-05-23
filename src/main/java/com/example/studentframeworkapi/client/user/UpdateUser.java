package com.example.studentframeworkapi.client.user;

import com.example.studentframeworkapi.client.api.user.PutClient;
import com.example.studentframeworkapi.model.user.UserRequest;
import com.example.studentframeworkapi.model.user.UserResponse;

public class UpdateUser {
    public static UserResponse updateUser(String path, int statusCode, String token, String name, String job) {
        UserRequest requestBody = UserRequest.builder()
                .name(name)
                .job(job)
                .build();
        return PutClient.updateCurrentUser(path, statusCode, token, requestBody);
    }
}
