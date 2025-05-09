package com.example.studentframeworkapi.client.api.user;

import com.example.studentframeworkapi.util.JsonConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

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

    public static String getAllUsers(String endpoint, int statusCode, String token) {
        logger.info("Sending GET request to endpoint: {}", endpoint);

        Response response = RestAssured.given()
                .baseUri(JsonConfigReader.getConfig().baseUrl)
                .header("x-api-key", token)
                .when()
                .log().all()
                .get(endpoint);

        logger.info("Received response with status code: {}", response.getStatusCode());
        logger.info("Response Body (Pretty Printed): \n{}", response.prettyPrint());

        validateResponse(response, statusCode);

        List<Map<String, Object>> users = response.jsonPath().getList("data");
        Assert.assertFalse(users.isEmpty(), "Expected at least one user");
        for (Map<String, Object> user : users) {
            Assert.assertNotNull(user.get("id"), "User id is missing");
            Assert.assertNotNull(user.get("email"), "User email is missing");
            Assert.assertNotNull(user.get("first_name"), "User first name is missing");
            Assert.assertNotNull(user.get("last_name"), "User last name is missing");
            Assert.assertNotNull(user.get("avatar"), "User avatar is missing");

        }

        return response.asString();
    }

    public static String getUserWithDelay(String path, int expectedStatusCode, String token) {
        logger.info("Sending GET request to path: {} with delay", path);

        Response response = RestAssured.given()
                .baseUri(JsonConfigReader.getConfig().baseUrl)
                .header("x-api-key", token)
                .header("Content-Type", "application/json")
                .when()
                .get(path);

        logger.info("Received Response with Status Code: {}", response.getStatusCode());
        logger.info("Response Body: {}", response.asString());

        Assert.assertEquals(response.getStatusCode(), expectedStatusCode,
                "Expected status code " + expectedStatusCode + ", but got " + response.getStatusCode());

        int perPage = response.jsonPath().getInt("per_page");
        int total = response.jsonPath().getInt("total");
        List<Map<String, Object>> users = response.jsonPath().getList("data");

        Assert.assertTrue(perPage > 0, "Expected per_page > 0");
        Assert.assertTrue(total >= perPage, "Total users should be >= per_page");
        Assert.assertEquals(users.size(), perPage, "Number of users returned should match per_page");

        Map<String, Object> firstUser = users.get(0);
        Assert.assertNotNull(firstUser.get("id"), "First user id should not be null");
        Assert.assertNotNull(firstUser.get("email"), "First user email should not be null");
        Assert.assertNotNull(firstUser.get("first_name"), "First user first name should not be null");
        Assert.assertNotNull(firstUser.get("avatar"), "First user avatar should not be null");

        return response.asString();
    }

}
