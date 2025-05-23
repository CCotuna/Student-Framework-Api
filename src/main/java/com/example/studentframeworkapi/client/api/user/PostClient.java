package com.example.studentframeworkapi.client.api.user;

import com.example.studentframeworkapi.model.user.UserRequest;
import com.example.studentframeworkapi.model.user.UserResponse;
import com.example.studentframeworkapi.util.JsonConfigReader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class PostClient {
    private static final Logger logger = LogManager.getLogger(PostClient.class);

    public static UserResponse createUserWithToken(String endpoint, int statusCode, UserRequest requestBody, String token) {
        logger.info("Sending POST request to endpoint: {} with body: {}", endpoint, requestBody);
        logger.info("Using token: {}", token);

        Response response = RestAssured.given()
                .baseUri(JsonConfigReader.getConfig().baseUrl)
                .header("x-api-key", token)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .log().all()
                .post(endpoint);

        logger.info("Received response with status code: {}", response.getStatusCode());
        logger.info("Response Body: {}", response.asString());

        if (response.getStatusCode() != statusCode) {
            throw new RuntimeException("API Error: Expected status code " + statusCode + " - received " + response.getStatusCode() + ". Response: " + response.asString());
        }

        String contentType = response.getHeader("Content-Type");
        if (contentType == null || !contentType.contains("application/json")) {
            if (response.getStatusCode() != HttpStatus.SC_NO_CONTENT) {
                throw new RuntimeException("Unexpected or missing content type: " + contentType + ". Expected JSON for status code: " + response.getStatusCode());
            } else {
                logger.info("Content-Type header is missing, but status code is 204 No Content, which is expected.");
            }
        }

        UserResponse userResponse = null;
        if (response.getStatusCode() == HttpStatus.SC_CREATED || response.getStatusCode() == HttpStatus.SC_OK) {
            userResponse = response.as(UserResponse.class);
            logger.info("Deserialized UserResponse: {}", userResponse);

            if (userResponse.getId() <= 0) {
                throw new RuntimeException("Invalid user ID in the response.");
            }
            if (userResponse.getName() == null || userResponse.getName().isEmpty()) {
                throw new RuntimeException("User name is missing in the response.");
            }
            if (userResponse.getJob() == null || userResponse.getJob().isEmpty()) {
                throw new RuntimeException("User job is missing in the response.");
            }
            if (userResponse.getCreatedAt() == null || userResponse.getCreatedAt().isEmpty()) {
                throw new RuntimeException("Created at timestamp is missing in the response.");
            }
        }

        if (response.getTime() > 2000) {
            throw new RuntimeException("Response time exceeded acceptable limit.");
        }

        return userResponse;
    }

    public static List<Map<String, Object>> loadUsersFromJson(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = PostClient.class.getClassLoader().getResourceAsStream(filePath)) {
            if (is == null) {
                throw new FileNotFoundException("File not found: " + filePath);
            }
            return mapper.readValue(is, new TypeReference<>() {});
        } catch (IOException e) {
            logger.error("Failed to read users from JSON file", e);
            throw new RuntimeException(e);
        }
    }
}