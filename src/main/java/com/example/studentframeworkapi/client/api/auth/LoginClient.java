package com.example.studentframeworkapi.client.api.auth;

import com.example.studentframeworkapi.client.api.resource.GetClient;
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

    public static String signInUser(String path, int statusCode, Map<String, Object> body, String token) {
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
        logger.info("Response Body: {}", response.asString());

        if (response.getStatusCode() != statusCode) {
            logger.error("Expected status code {} but got {}. Response: {}", statusCode, response.getStatusCode(), response.asString());
            throw new RuntimeException("Expected status code " + statusCode + " but got " + response.getStatusCode() + ". Response: " + response.asString());
        }

        String responseBody = response.asString();
        if (!responseBody.contains("token")) {
            logger.error("Response does not contain a 'token'. Response: {}", responseBody);
            throw new RuntimeException("Response does not contain a 'token'. Response: " + responseBody);
        }

        return response.asString();
    }

    public static String signInUserFailed(String path, int expectedStatusCode, Map<String, Object> body, String token) {
        String email = (String) body.get("email");

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
        logger.info("Response Body: {}", response.asString());

        Assert.assertEquals(response.getStatusCode(), expectedStatusCode,
                "Expected status code " + expectedStatusCode + ", but got " + response.getStatusCode());

        if (expectedStatusCode == 400) {
            String error = response.jsonPath().getString("error");
            Assert.assertNotNull(error, "Expected error message but none was returned.");
            logger.info("Error message: {}", error);
        }

        return response.asString();
    }
}
