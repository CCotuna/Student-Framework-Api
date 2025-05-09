package com.example.studentframeworkapi.client.api;

import com.example.studentframeworkapi.util.JsonConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.Map;

public class PostClient {

    public static String createUserWithToken(String endpoint, int statusCode, Map<String, Object> body, String token) {
        Response response = RestAssured.given()
                .baseUri(JsonConfigReader.getConfig().baseUrl)
                .header("x-api-key", token)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .log().all()
                .post(endpoint);

        if (response.getStatusCode() != statusCode) {
            throw new RuntimeException("API Error: Expected status code " + statusCode + " - received " + response.getStatusCode() + ". Response: " + response.asString());
        }

        if (!response.getHeader("Content-Type").contains("application/json")) {
            throw new RuntimeException("Unexpected content type: " + response.getHeader("Content-Type"));
        }

        if (response.getStatusCode() == HttpStatus.SC_CREATED) {
            int userId = response.jsonPath().getInt("id");
            if (userId <= 0) {
                throw new RuntimeException("Invalid user ID in the response.");
            }

            String userName = response.jsonPath().getString("name");
            if (userName == null || userName.isEmpty()) {
                throw new RuntimeException("User name is missing in the response.");
            }
        }

        if (response.getTime() > 2000) {
            throw new RuntimeException("Response time exceeded acceptable limit.");
        }

        return response.asString();
    }
}
