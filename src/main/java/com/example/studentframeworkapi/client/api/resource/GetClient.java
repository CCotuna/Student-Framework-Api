package com.example.studentframeworkapi.client.api.resource;

import com.example.studentframeworkapi.model.resource.ListResourceResponse;
import com.example.studentframeworkapi.model.resource.SingleResourceResponse;
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

    public static SingleResourceResponse getSingleResourceParsed(String endpoint, int expectedStatusCode, String token) {
        logger.info("Sending GET request to endpoint: {} with token: {}", endpoint, token);

        Response response = RestAssured.given()
                .baseUri(JsonConfigReader.getConfig().baseUrl)
                .header("x-api-key", token)
                .when()
                .log().all()
                .get(endpoint);

        logger.info("Received response with status code: {}", response.getStatusCode());

        validateResponse(response, expectedStatusCode);

        if (response.getStatusCode() == 200) {
            SingleResourceResponse singleResourceResponse = response.as(SingleResourceResponse.class);
            logger.info("Deserialized SingleResourceResponse: {}", singleResourceResponse);
            return singleResourceResponse;
        }

        if(expectedStatusCode != 200) {
            throw new IllegalStateException("getSingleResourceParsed should be used for successful responses (200 OK). Received: " + response.getStatusCode());
        }
        return response.as(SingleResourceResponse.class);
    }

    public static ListResourceResponse getAllResourcesParsed(String endpoint, int expectedStatusCode, String token) {
        logger.info("Sending GET request to endpoint: {} with token: {}", endpoint, token);

        Response response = RestAssured.given()
                .baseUri(JsonConfigReader.getConfig().baseUrl)
                .header("x-api-key", token)
                .when()
                .log().all()
                .get(endpoint);

        logger.info("Received response with status code: {}", response.getStatusCode());

        validateResponse(response, expectedStatusCode);

        if (response.getStatusCode() == 200) {
            ListResourceResponse listResourceResponse = response.as(ListResourceResponse.class);
            logger.info("Deserialized ListResourceResponse: {}", listResourceResponse);
            return listResourceResponse;
        }

        if(expectedStatusCode != 200) {
            throw new IllegalStateException("getAllResourcesParsed should be used for successful responses (200 OK). Received: " + response.getStatusCode());
        }
        return response.as(ListResourceResponse.class);
    }

    public static Response getResourceResponse(String endpoint, String token) {
        logger.info("Sending GET request to endpoint: {} with token: {}", endpoint, token);

        Response response = RestAssured.given()
                .baseUri(JsonConfigReader.getConfig().baseUrl)
                .header("x-api-key", token)
                .when()
                .get(endpoint);

        logger.info("Received raw response with status code: {}", response.getStatusCode());
        return response;
    }
}
