package com.example.studentframeworkapi.client.api.resource;

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

    public static String getResourceWithToken(String endpoint, int statusCode, String token) {
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
            Map<String, Object> data = response.jsonPath().getMap("data");
            Assert.assertNotNull(data, "Data is missing");

            if (data.containsKey("id")) {
                String id = data.get("id").toString();
                Assert.assertNotNull(id, "ID is missing");
                Assert.assertTrue(Integer.parseInt(id) > 0, "ID should be greater than 0");
            }

            if (data.containsKey("name")) {
                String name = data.get("name").toString();
                Assert.assertNotNull(name, "Name is missing");
                Assert.assertFalse(name.isEmpty(), "Name should not be empty");
            }

            if (data.containsKey("color")) {
                String color = data.get("color").toString();
                Assert.assertNotNull(color, "Color is missing");
                Assert.assertTrue(color.matches("^#([A-Fa-f0-9]{6})$"), "Color format is invalid: " + color);
            }

            if (data.containsKey("pantone_value")) {
                String pantoneValue = data.get("pantone_value").toString();
                Assert.assertNotNull(pantoneValue, "Pantone value is missing");
            }

            Map<String, String> support = response.jsonPath().getMap("support");
            if (support != null) {
                String supportUrl = support.get("url");
                Assert.assertNotNull(supportUrl, "Support URL is missing");
                Assert.assertTrue(supportUrl.startsWith("https://"), "Support URL is invalid: " + supportUrl);

                String supportText = support.get("text");
                Assert.assertNotNull(supportText, "Support text is missing");
                Assert.assertFalse(supportText.isEmpty(), "Support text should not be empty");
            }
        }

        return response.asString();
    }


    public static String getAllResources(String endpoint, int statusCode, String token) {
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
            List<Map<String, Object>> resources = response.jsonPath().getList("data");
            Assert.assertNotNull(resources, "Data is missing");

            for (Map<String, Object> resource : resources) {
                String id = resource.get("id").toString();
                Assert.assertNotNull(id, "ID is missing");
                Assert.assertTrue(Integer.parseInt(id) > 0, "ID should be greater than 0");

                String name = resource.get("name").toString();
                Assert.assertNotNull(name, "Name is missing");
                Assert.assertFalse(name.isEmpty(), "Name should not be empty");

                String color = resource.get("color").toString();
                Assert.assertNotNull(color, "Color is missing");
                Assert.assertTrue(color.matches("^#([A-Fa-f0-9]{6})$"), "Color format is invalid: " + color);

                String pantoneValue = resource.get("pantone_value").toString();
                Assert.assertNotNull(pantoneValue, "Pantone value is missing");
            }

            Map<String, String> support = response.jsonPath().getMap("support");
            if (support != null) {
                String supportUrl = support.get("url");
                Assert.assertNotNull(supportUrl, "Support URL is missing");
                Assert.assertTrue(supportUrl.startsWith("https://"), "Support URL is invalid: " + supportUrl);

                String supportText = support.get("text");
                Assert.assertNotNull(supportText, "Support text is missing");
                Assert.assertFalse(supportText.isEmpty(), "Support text should not be empty");
            }
        }

        return response.asString();
    }



}
