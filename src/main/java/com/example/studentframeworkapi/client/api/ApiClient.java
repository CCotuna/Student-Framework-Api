package com.example.studentframeworkapi.client.api;

import com.example.studentframeworkapi.client.api.user.PutClient;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApiClient {

    private static final Logger logger = LogManager.getLogger(ApiClient.class);

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void validateResponse(Response response, int expectedStatusCode) {
        logger.info("Validating response status code. Expected: {}, Actual: {}", expectedStatusCode, response.getStatusCode());

        if (response.getStatusCode() != expectedStatusCode) {
            throw new RuntimeException("API Error: Expected status code " + expectedStatusCode + " - received " + response.getStatusCode() + ". Response: " + response.asString());
        }

        if (response.getStatusCode() == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
            throw new RuntimeException("Server Error: " + response.asString());
        }

        if (response.getStatusCode() == HttpStatus.SC_BAD_REQUEST) {
            throw new RuntimeException("Bad Request: " + response.asString());
        }

        if (expectedStatusCode != HttpStatus.SC_NO_CONTENT) {
            String contentType = response.getHeader("Content-Type");
            if (contentType == null) {
                throw new RuntimeException("Unexpected or missing Content-Type header for status code " + response.getStatusCode() + ". Expected application/json.");
            } else if (!contentType.contains("application/json")) {
                throw new RuntimeException("Unexpected content type: " + contentType + ". Expected application/json.");
            }
        } else {
            logger.info("Content-Type header check skipped for 204 No Content response.");
        }

        if (response.getTime() > 2000) {
            logger.warn("Response time exceeded acceptable limit: {} ms", response.getTime());
        }
    }
}
