package com.example.studentframeworkapi.user;

import com.example.studentframeworkapi.BaseTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.example.studentframeworkapi.client.api.PostClient.createUserWithToken;
import static com.example.studentframeworkapi.client.user.GetUser.getUser;

public class UserTests extends BaseTest {

    public static final String AUTH_TOKEN = "reqres-free-v1";

    @Test
    void testCreateUserWithToken() {
        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("name", "Chen");
        requestBody.put("job", "Software Developer");

        createUserWithToken("/api/users", 201, requestBody, AUTH_TOKEN);
    }

    @Test
    void testGetUser() {
        getUser("/api/users/2", 200, AUTH_TOKEN);
    }
}
