package com.example.studentframeworkapi.client.api.user;

import com.example.studentframeworkapi.util.JsonConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class PutClient {
    private static final Logger logger = LogManager.getLogger(PutClient.class);

    public static String updateCurrentUser(String endpoint, int statusCode, String token, String name, String job) {
        logger.info("Sending PUT request to update user at path: {}. New Name: {}, New Job: {}", endpoint, name, job);

        String requestBody = String.format("{\"name\": \"%s\", \"job\": \"%s\"}", name, job);

        Response response = RestAssured.given()
                .baseUri(JsonConfigReader.getConfig().baseUrl) // sau baseUri din configurare
                .header("x-api-key", token)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .log().all()
                .put(endpoint);

        logger.info("Received response with status code: {}", response.getStatusCode());
        logger.info("Response Body: {}", response.asString());

        Assert.assertEquals(statusCode, response.getStatusCode());

        if (response.getStatusCode() == 200) {
            String updatedName = response.jsonPath().getString("name");
            String updatedJob = response.jsonPath().getString("job");
            String updatedAt = response.jsonPath().getString("updatedAt");

            Assert.assertEquals(name, updatedName, "User name was not updated correctly");
            Assert.assertEquals(job, updatedJob, "User job was not updated correctly");
            Assert.assertNotNull(updatedAt, "UpdatedAt timestamp is missing");
        }

        return response.asString();
    }

}
