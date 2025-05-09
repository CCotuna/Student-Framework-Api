package com.example.studentframeworkapi.resource;

import com.example.studentframeworkapi.BaseTest;
import org.testng.annotations.Test;

import static com.example.studentframeworkapi.client.resource.GetResource.*;

public class ResourceTests extends BaseTest {

    public static final String AUTH_TOKEN = "reqres-free-v1";

    @Test
    void testGetResource() {
        getResource("/api/unkown/5", 200, AUTH_TOKEN);
    }

    @Test
    void testGetAllResources() {
        getResources("/api/unkown", 200, AUTH_TOKEN);
    }

    @Test
    void testResourceNotFound() {
        getResourceNotFound("/api/unknown/23", 404, AUTH_TOKEN);
    }
}
