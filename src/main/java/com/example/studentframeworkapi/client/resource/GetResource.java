package com.example.studentframeworkapi.client.resource;

import com.example.studentframeworkapi.client.api.resource.GetClient;

public class GetResource {
    public static String getResource(String path, int statusCode, String token) {
        return GetClient.getResourceWithToken(path, statusCode, token);
    }

    public static String getResources(String path, int statusCode, String token) {
        return GetClient.getAllResources(path, statusCode, token);
    }

    public static String getResourceNotFound(String path, int statusCode, String token) {
        return GetClient.getResourceWithToken(path, statusCode, token);
    }
}
