package com.example.studentframeworkapi.client.api;

import com.example.studentframeworkapi.util.JsonConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import static com.example.studentframeworkapi.client.api.ApiClient.validateResponse;

public class GetClient {

    public static String getUserWithToken(String endpoint, int statusCode, String token) {
        Response response = RestAssured.given()
                .baseUri(JsonConfigReader.getConfig().baseUrl)
                .header("x-api-key", token)
                .when()
                .log().all()
                .get(endpoint);

        validateResponse(response, statusCode);

        return response.asString();
    }

}
