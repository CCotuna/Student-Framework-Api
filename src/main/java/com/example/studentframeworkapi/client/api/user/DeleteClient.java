package com.example.studentframeworkapi.client.api.user;

import com.example.studentframeworkapi.util.JsonConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import static com.example.studentframeworkapi.client.api.ApiClient.validateResponse;

public class DeleteClient {
    private static final Logger logger = LogManager.getLogger(DeleteClient.class);

    public static Response deleteCurrentUser(String endpoint, int expectedStatusCode, String token) {
        logger.info("Sending DELETE request to endpoint: {} with token: {}", endpoint, token);

        Response response = RestAssured.given()
                .baseUri(JsonConfigReader.getConfig().baseUrl)
                .header("x-api-key", token)
                .when()
                .log().all()
                .delete(endpoint);

        logger.info("Received response with status code: {}", response.getStatusCode());
        logger.info("Response Body: {}", response.asString());

        validateResponse(response, expectedStatusCode);

        if (response.getTime() > 2000) {
            logger.warn("Response time for DELETE request exceeded 2000 ms: {} ms", response.getTime());
        }

        return response;
    }
}
