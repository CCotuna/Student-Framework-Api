package com.example.studentframeworkapi.client.user;

import com.example.studentframeworkapi.client.api.user.PostClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class CreateUser {
    public static String createUser(String path, int statusCode, Map<String, Object> requestBody, String token){
        return PostClient.createUserWithToken(path, statusCode, requestBody, token);
    }


}
