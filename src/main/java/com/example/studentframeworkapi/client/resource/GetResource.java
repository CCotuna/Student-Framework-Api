package com.example.studentframeworkapi.client.resource;

import com.example.studentframeworkapi.client.api.resource.GetClient;
import com.example.studentframeworkapi.model.resource.ListResourceResponse;
import com.example.studentframeworkapi.model.resource.SingleResourceResponse;

import io.restassured.response.Response;

public class GetResource {

    public static SingleResourceResponse getResource(String path, int expectedStatusCode, String token) {
        return GetClient.getSingleResourceParsed(path, expectedStatusCode, token);
    }

    public static ListResourceResponse getResources(String path, int expectedStatusCode, String token) {
        return GetClient.getAllResourcesParsed(path, expectedStatusCode, token);
    }

    public static Response getResourceResponseForStatusCheck(String path, String token) {
        return GetClient.getResourceResponse(path, token);
    }
}