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

    public static void validateResponse(Response response, int expectedStatusCode) {
        if (response.getStatusCode() != expectedStatusCode) {
            throw new RuntimeException("API Error: Expected status code " + expectedStatusCode + " - received " + response.getStatusCode() + ". Response: " + response.asString());
        }

        if (response.getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
            throw new RuntimeException("Server Error: " + response.asString());
        }

        if (response.getStatusCode() == HttpStatus.SC_BAD_REQUEST) {
            throw new RuntimeException("Bad Request: " + response.asString());
        }

        if (!response.getHeader("Content-Type").contains("application/json")) {
            throw new RuntimeException("Unexpected content type: " + response.getHeader("Content-Type"));
        }
    }
}
