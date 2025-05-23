package com.example.studentframeworkapi.client.api.auth;

import com.example.studentframeworkapi.client.api.resource.GetClient;
import com.example.studentframeworkapi.model.auth.LoginErrorResponse;
import com.example.studentframeworkapi.model.auth.LoginResponse;
import com.example.studentframeworkapi.util.JsonConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;



import java.util.Map;

import static com.example.studentframeworkapi.client.api.ApiClient.isValidEmail;

public class LoginClient {
    private static final Logger logger = LogManager.getLogger(LoginClient.class);

    public static LoginResponse signInUser(String path, int statusCode, Map<String, Object> body, String token) {
        String email = (String) body.get("email");
        String password = (String) body.get("password");

        logger.info("Sending POST request to path: {}", path);
        logger.info("Request Body: email={}, password={}", email, password);

        if (!isValidEmail(email)) {
            logger.error("Invalid email format: {}", email);
            throw new RuntimeException("Invalid email format: " + email);
        }

        if (password == null || password.isEmpty()) {
            logger.error("Password cannot be empty or null.");
            throw new RuntimeException("Password cannot be empty or null.");
        }

        Response response = RestAssured.given()
                .baseUri(JsonConfigReader.getConfig().baseUrl)
                .header("x-api-key", token)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(path);

        logger.info("Received Response with Status Code: {}", response.getStatusCode());
        if (response.getStatusCode() != statusCode) {
            logger.error("Expected status code {} but got {}. Response: {}", statusCode, response.getStatusCode(), response.asString());
            throw new RuntimeException("Expected status code " + statusCode + " but got " + response.getStatusCode() + ". Response: " + response.asString());
        }

        LoginResponse loginResponse = response.as(LoginResponse.class);
        logger.info("Deserialized response: {}", loginResponse);

        if (loginResponse.getToken() == null || loginResponse.getToken().isEmpty()) {
            logger.error("Response doesn't contain a token or token is empty. Response: {}", response.asString());
            throw new RuntimeException("Response doesn't contain a token or token is empty. Response: " + response.asString());
        }
        return loginResponse;
    }

    public static LoginErrorResponse signInUserFailed(String path, int expectedStatusCode, Map<String, Object> body, String token) {
        logger.info("Sending negative POST request to path: {}", path);
        logger.info("Request Body: {}", body);

        Response response = RestAssured.given()
                .baseUri(JsonConfigReader.getConfig().baseUrl)
                .header("x-api-key", token)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(path);

        logger.info("Received Response with Status Code: {}", response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), expectedStatusCode,
                "Expected status code " + expectedStatusCode + ", but got " + response.getStatusCode() + ". Response: " + response.asString());

        LoginErrorResponse errorResponse = response.as(LoginErrorResponse.class);
        logger.info("Deserialized Error Response: {}", errorResponse);

        if (expectedStatusCode == 400) {
            Assert.assertNotNull(errorResponse.getError(), "Expected error message.");
            Assert.assertFalse(errorResponse.getError().isEmpty(), "Error message is empty.");
            logger.info("Error message from deserialized object: {}", errorResponse.getError());
        }

        return errorResponse;
    }
}