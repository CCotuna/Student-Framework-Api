package com.example.studentframeworkapi.resource;

import com.example.studentframeworkapi.BaseTest;
import com.example.studentframeworkapi.model.resource.ListResourceResponse;
import com.example.studentframeworkapi.model.resource.ResourceData;
import com.example.studentframeworkapi.model.resource.SingleResourceResponse;
import com.example.studentframeworkapi.model.resource.SupportData;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.example.studentframeworkapi.client.resource.GetResource.*;

public class ResourceTests extends BaseTest {
    public static final String AUTH_TOKEN = "reqres-free-v1";

    @Test(description = "Verify fetching a single existing resource by ID, status: 200")
    void testGetResource() {
        String path = "/api/unknown/2";
        int expectedStatusCode = 200;

        SingleResourceResponse resourceResponse = getResource(path, expectedStatusCode, AUTH_TOKEN);
        Assert.assertNotNull(resourceResponse, "Resource response shouldn't be null");

        ResourceData data = resourceResponse.getData();
        Assert.assertNotNull(data, "Data in resource response shouldn't be null");
        Assert.assertEquals(data.getId(), 2, "ID mismatch");
        Assert.assertNotNull(data.getName(), "Name shouldn't be null");
        Assert.assertFalse(data.getName().isEmpty(), "Name shouldn't be empty");
        Assert.assertTrue(data.getYear() > 1900, "Year seems invalid");
        Assert.assertNotNull(data.getColor(), "Color shouldn't be null");
        Assert.assertTrue(data.getColor().matches("^#([A-Fa-f0-9]{6})$"), "Color format is invalid: " + data.getColor());
        Assert.assertNotNull(data.getPantoneValue(), "Pantone value shouldn't be null");

        SupportData support = resourceResponse.getSupport();
        Assert.assertNotNull(support, "Support data in resource response shouldn't be null");
        Assert.assertNotNull(support.getUrl(), "Support URL shouldn't be null");
        Assert.assertNotNull(support.getText(), "Support text shouldn't be null");
        Assert.assertFalse(support.getText().isEmpty(), "Support text shouldn't be empty");
    }

    @Test(description = "Verify fetching a list of all resources, status: 200")
    void testGetAllResources() {
        String path = "/api/unknown";
        int expectedStatusCode = 200;

        ListResourceResponse listResponse = getResources(path, expectedStatusCode, AUTH_TOKEN);

        Assert.assertNotNull(listResponse, "List resource response shouldn't be null");
        Assert.assertTrue(listResponse.getPage() > 0, "Page number shouldn't be positive");
        Assert.assertTrue(listResponse.getPerPage() > 0, "Per page count shouldn't be positive");
        Assert.assertTrue(listResponse.getTotal() >= 0, "Total count shouldn't be non-negative");
        Assert.assertTrue(listResponse.getTotalPages() > 0, "Total pages shouldn't be positive");

        Assert.assertNotNull(listResponse.getData(), "Data list shouldn't be null");
        Assert.assertFalse(listResponse.getData().isEmpty(), "Data list shouldn't be empty");

        for (ResourceData resource : listResponse.getData()) {
            Assert.assertTrue(resource.getId() > 0, "Resource ID shouldn't be greater than 0");
            Assert.assertNotNull(resource.getName(), "Resource name shouldn't be null");
            Assert.assertFalse(resource.getName().isEmpty(), "Resource name shouldn't be empty");
            Assert.assertTrue(resource.getYear() > 1900, "Resource year seems invalid");
            Assert.assertNotNull(resource.getColor(), "Resource color shouldn't be null");
            Assert.assertTrue(resource.getColor().matches("^#([A-Fa-f0-9]{6})$"), "Resource color format is invalid: " + resource.getColor());
            Assert.assertNotNull(resource.getPantoneValue(), "Resource pantone value shouldn't be null");
        }

        SupportData support = listResponse.getSupport();
        Assert.assertNotNull(support, "Support data in list response shouldn't be null");
        Assert.assertNotNull(support.getUrl(), "Support URL shouldn't be null");
        Assert.assertNotNull(support.getText(), "Support text shouldn't be null");
        Assert.assertFalse(support.getText().isEmpty(), "Support text shouldn't be empty");
    }

    @Test(description = "Verify that fetching a non-existent resource returns status: 204")
    void testResourceNotFound() {
        String path = "/api/unknown/23";
        int expectedStatusCode = 404;

        Response response = getResourceResponseForStatusCheck(path, AUTH_TOKEN);

        Assert.assertNotNull(response, "Response object shouldn't be null");
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Status code mismatch for resource not found");
        System.out.println("Resource not found test, status: " + response.getStatusCode() + ", body: " + response.asString());
    }
}
