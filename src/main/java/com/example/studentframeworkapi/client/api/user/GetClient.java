package com.example.studentframeworkapi.client.api.user;

import com.example.studentframeworkapi.model.user.ListUserResponse;
import com.example.studentframeworkapi.model.user.SingleUserResponse;
import com.example.studentframeworkapi.util.JsonConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.example.studentframeworkapi.client.api.ApiClient.validateResponse;

public class GetClient {

    private static final Logger logger = LogManager.getLogger(GetClient.class);

    public static SingleUserResponse getSingleUserParsed(String endpoint, int expectedStatusCode, String token) {
        logger.info("Sending GET request to endpoint: {} with token: {}", endpoint, token);

        Response response = RestAssured.given()
                .baseUri(JsonConfigReader.getConfig().baseUrl)
                .header("x-api-key", token)
                .when()
                .log().all()
                .get(endpoint);

        logger.info("Received response with status code: {}", response.getStatusCode());
        validateResponse(response, expectedStatusCode);

        SingleUserResponse singleUserResponse = null;
        if (response.getStatusCode() == 200) {
            singleUserResponse = response.as(SingleUserResponse.class);
            logger.info("Deserialized SingleUserResponse: {}", singleUserResponse);
        }
        return singleUserResponse;
    }

    public static ListUserResponse getAllUsersParsed(String endpoint, int expectedStatusCode, String token) {
        logger.info("Sending GET request to endpoint: {} with token: {}", endpoint, token);

        Response response = RestAssured.given()
                .baseUri(JsonConfigReader.getConfig().baseUrl)
                .header("x-api-key", token)
                .when()
                .log().all()
                .get(endpoint);

        logger.info("Received response with status code: {}", response.getStatusCode());
        validateResponse(response, expectedStatusCode);

        ListUserResponse listUserResponse = null;
        if (response.getStatusCode() == 200) {
            listUserResponse = response.as(ListUserResponse.class);
            logger.info("Deserialized ListUserResponse: {}", listUserResponse);
        }
        return listUserResponse;
    }

    public static Response getUserResponse(String endpoint, String token) {
        logger.info("Sending GET request to endpoint: {} with token: {}", endpoint, token);

        Response response = RestAssured.given()
                .baseUri(JsonConfigReader.getConfig().baseUrl)
                .header("x-api-key", token)
                .when()
                .get(endpoint);

        logger.info("Received raw response with status code: {}", response.getStatusCode());
        return response;
    }

    public static ListUserResponse getUserWithDelayParsed(String endpoint, int expectedStatusCode, String token) {
        logger.info("Sending GET request to endpoint: {} with delay and token: {}", endpoint, token);

        Response response = RestAssured.given()
                .baseUri(JsonConfigReader.getConfig().baseUrl)
                .header("x-api-key", token)
                .when()
                .log().all()
                .get(endpoint);

        logger.info("Received response with status code: {}", response.getStatusCode());
        validateResponse(response, expectedStatusCode);

        ListUserResponse listUserResponse = null;
        if (response.getStatusCode() == 200) {
            listUserResponse = response.as(ListUserResponse.class);
            logger.info("Deserialized ListUserResponse (with delay): {}", listUserResponse);
        }
        return listUserResponse;
    }
}