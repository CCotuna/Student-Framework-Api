package com.example.studentframeworkapi.client.api;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiClient {

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
