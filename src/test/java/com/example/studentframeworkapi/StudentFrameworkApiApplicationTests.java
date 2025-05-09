package com.example.studentframeworkapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static com.example.studentframeworkapi.client.api.PostClient.createUserWithToken;

@SpringBootTest
class StudentFrameworkApiApplicationTests {

    public static final String AUTH_TOKEN = "reqres-free-v1";

    @Test
    void testCreateUserWithToken() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("name", "Chen");
        requestBody.put("job", "Software Developer");
        createUserWithToken("/api/users", 201, requestBody, AUTH_TOKEN);
    }

}
