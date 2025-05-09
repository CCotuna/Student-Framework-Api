package com.example.studentframeworkapi.user;

import com.example.studentframeworkapi.BaseTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.example.studentframeworkapi.client.api.user.PostClient.loadUsersFromJson;
import static com.example.studentframeworkapi.client.user.CreateUser.createUser;
import static com.example.studentframeworkapi.client.user.DeleteUser.deleteUser;
import static com.example.studentframeworkapi.client.user.GetUser.*;
import static com.example.studentframeworkapi.client.user.UpdateUser.updateUser;

public class UserTests extends BaseTest {

    public static final String AUTH_TOKEN = "reqres-free-v1";

    @Test
    void testCreateUserWithToken() {
        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("name", "Chen");
        requestBody.put("job", "Software Developer");

        createUser("/api/users", 201, requestBody, AUTH_TOKEN);
    }

    @Test
    void testGetUser() {
        getUser("/api/users/2", 200, AUTH_TOKEN);
    }

    @Test
    void testGetAllUsers() {
        getUsers("/api/users?page=2", 200, AUTH_TOKEN);
    }

    @Test
    void testUserNotFound() {
        getUserNotFound("/api/users/23", 404, AUTH_TOKEN);
    }

    @Test
    void testUpdateUser() {
        updateUser("/api/users/2", 200, AUTH_TOKEN, "Chen", "Software Developer");
    }

    @Test
    void testDeleteUser() {
        deleteUser("/api/users/2", 204, AUTH_TOKEN);
    }

    @Test
    void testCreateMultipleUsersFromJson() {
        String endpoint = "/api/users";
        int expectedStatusCode = 201;

        var users = loadUsersFromJson("constants/users.json");

        for (Map<String, Object> user : users) {
            createUser(endpoint, expectedStatusCode, user, AUTH_TOKEN);
        }
    }
}
