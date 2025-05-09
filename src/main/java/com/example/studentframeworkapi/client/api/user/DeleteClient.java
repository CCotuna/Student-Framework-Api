package com.example.studentframeworkapi.client.api.user;

import com.example.studentframeworkapi.util.JsonConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class DeleteClient {
    private static final Logger logger = LogManager.getLogger(DeleteClient.class);

    public static String deleteCurrentUser(String endpoint, int statusCode, String token) {
        logger.info("Sending DELETE request to endpoint: {} with token: {}", endpoint, token);

        Response response = RestAssured.given()
                .baseUri(JsonConfigReader.getConfig().baseUrl)
                .header("x-api-key", token)
                .when()
                .log().all()
                .delete(endpoint);

        logger.info("Received response with status code: {}", response.getStatusCode());
        logger.info("Response Body: {}", response.asString());

        Assert.assertEquals(statusCode, response.getStatusCode(), "Expected status code " + statusCode + ", but got: " + response.getStatusCode());

        if (response.getStatusCode() == 204) {
            logger.info("User successfully deleted, no content returned.");
            Assert.assertTrue(response.asString().isEmpty(), "Response body should be empty");
        } else {
            logger.error("Unexpected status code received: {}", response.getStatusCode());
            Assert.fail("Unexpected status code: " + response.getStatusCode());
        }

        return response.asString();
    }

}
