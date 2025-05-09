package com.example.studentframeworkapi.client.api;

import com.example.studentframeworkapi.util.JsonConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import static com.example.studentframeworkapi.client.api.ApiClient.validateResponse;

public class GetClient {

    private static final Logger logger = LogManager.getLogger(GetClient.class);

    public static String getUserWithToken(String endpoint, int statusCode, String token) {
        logger.info("Sending GET request to endpoint: {} with token: {}", endpoint, token);
        logger.info("Request Body: None (GET request does not have body)");

        Response response = RestAssured.given()
                .baseUri(JsonConfigReader.getConfig().baseUrl)
                .header("x-api-key", token)
                .when()
                .log().all()
                .get(endpoint);

        logger.info("Received response with status code: {}", response.getStatusCode());
        logger.info("Response Body: {}", response.asString());
        validateResponse(response, statusCode);

        if (response.getStatusCode() == 200) {
            String userId = response.jsonPath().getString("data.id");
            Assert.assertNotNull(userId, "User ID is missing");
            Assert.assertTrue(Integer.parseInt(userId) > 0, "User ID should be greater than 0");

            String email = response.jsonPath().getString("data.email");
            Assert.assertNotNull(email, "User email is missing");
            Assert.assertTrue(email.contains("@"), "User email is invalid: " + email);
            Assert.assertTrue(email.matches("^[A-Za-z0-9+_.-]+@(.+)$"), "User email format is invalid: " + email);

            String firstName = response.jsonPath().getString("data.first_name");
            Assert.assertNotNull(firstName, "User first name is missing");
            Assert.assertFalse(firstName.isEmpty(), "User first name is empty");

            String lastName = response.jsonPath().getString("data.last_name");
            Assert.assertNotNull(lastName, "User last name is missing");
            Assert.assertFalse(lastName.isEmpty(), "User last name is empty");

            String avatar = response.jsonPath().getString("data.avatar");
            Assert.assertNotNull(avatar, "User avatar is missing");
            Assert.assertTrue(avatar.startsWith("https://"), "User avatar URL is invalid: " + avatar);
        }

        return response.asString();
    }

}
