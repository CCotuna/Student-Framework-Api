package com.example.studentframeworkapi.client.api.auth;

import com.example.studentframeworkapi.model.auth.RegisterErrorResponse;
import com.example.studentframeworkapi.model.auth.RegisterResponse;
import com.example.studentframeworkapi.util.JsonConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.Map;

import static com.example.studentframeworkapi.client.api.ApiClient.isValidEmail;

public class RegisterClient {
    private static final Logger logger = LogManager.getLogger(RegisterClient.class);

    public static RegisterResponse signUpUser(String path, int statusCode, Map<String, Object> body, String token) {
        String email = (String) body.get("email");
        String password = (String) body.get("password");

        logger.info("Sending POST request to path: {}", path);
        logger.info("Request Body: email={}, password={}", email, password);

        if (!isValidEmail(email)) {
            logger.error("Invalid email format: {}", email);
            throw new RuntimeException("Invalid email format: " + email);
        }

        if (password == null || password.length() < 4) {
            logger.error("Password must not be null, and have at least 4 characters.");
            throw new RuntimeException("Password must not be null, and have at least 4 characters.");
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

        RegisterResponse registerResponse = response.as(RegisterResponse.class);
        logger.info("Deserialized Register Response: {}", registerResponse);

        if (registerResponse.getId() == null) {
            logger.error("Response doesn't contain an 'id'. Full Response: {}", response.asString());
            throw new RuntimeException("Response doesn't contain an 'id'. Full Response: " + response.asString());
        }
        if (registerResponse.getToken() == null || registerResponse.getToken().isEmpty()) {
            logger.error("Response doesn't contain a token or token is empty. Full Response: {}", response.asString());
            throw new RuntimeException("Response doesn't contain a token or token is empty. Full Response: " + response.asString());
        }

        return registerResponse;
    }

    public static RegisterErrorResponse signUpUserFailed(String path, int expectedStatusCode, Map<String, Object> body, String token) {
        logger.info("Sending negative REGISTER POST request to path: {}", path);
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

        RegisterErrorResponse errorResponse = response.as(RegisterErrorResponse.class);
        logger.info("Deserialized Register Error Response: {}", errorResponse);

        if (expectedStatusCode == 400) {
            Assert.assertNotNull(errorResponse.getError(), "Expected error message.");
            Assert.assertFalse(errorResponse.getError().isEmpty(), "Error message from deserialized object is empty.");
            logger.info("Error message from deserialized object: {}", errorResponse.getError());
        }

        return errorResponse;
    }
}