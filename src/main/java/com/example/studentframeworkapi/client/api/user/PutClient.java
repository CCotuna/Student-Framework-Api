package com.example.studentframeworkapi.client.api.user;

import com.example.studentframeworkapi.model.user.UserRequest;
import com.example.studentframeworkapi.model.user.UserResponse;
import com.example.studentframeworkapi.util.JsonConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.example.studentframeworkapi.client.api.ApiClient.validateResponse;

public class PutClient {
    private static final Logger logger = LogManager.getLogger(PutClient.class);

    public static UserResponse updateCurrentUser(String endpoint, int expectedStatusCode, String token, UserRequest requestBody) {
        logger.info("Sending PUT request to update user at path: {}. Request Body: {}", endpoint, requestBody);

        Response response = RestAssured.given()
                .baseUri(JsonConfigReader.getConfig().baseUrl)
                .header("x-api-key", token)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .log().all()
                .put(endpoint);

        logger.info("Received response with status code: {}", response.getStatusCode());
        logger.info("Response Body: {}", response.asString());

        validateResponse(response, expectedStatusCode);

        UserResponse userResponse = null;
        if (response.getStatusCode() == 200) {
            userResponse = response.as(UserResponse.class);
            logger.info("Deserialized UserResponse for update: {}", userResponse);
        }

        if (response.getTime() > 2000) {
            logger.warn("Response time for PUT request exceeded 2000 ms: {} ms", response.getTime());
        }

        return userResponse;
    }
}
