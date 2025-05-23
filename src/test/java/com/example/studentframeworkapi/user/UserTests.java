package com.example.studentframeworkapi.user;

import com.example.studentframeworkapi.BaseTest;
import com.example.studentframeworkapi.client.api.user.GetClient;
import com.example.studentframeworkapi.model.user.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

import static com.example.studentframeworkapi.client.api.user.PostClient.loadUsersFromJson;
import static com.example.studentframeworkapi.client.user.CreateUser.createUser;
import static com.example.studentframeworkapi.client.user.DeleteUser.deleteUser;
import static com.example.studentframeworkapi.client.user.GetUser.*;
import static com.example.studentframeworkapi.client.user.UpdateUser.updateUser;

public class UserTests extends BaseTest {

    public static final String AUTH_TOKEN = "reqres-free-v1";

    @Test(description = "Verify that a new user can be successfully created with valid name and job, status: 201")
    void testCreateUserWithToken() {
        UserRequest requestBody = UserRequest.builder()
                .name("Chen")
                .job("Software Developer")
                .build();

        String path = "/api/users";
        int expectedStatusCode = 201;

        UserResponse userResponse = createUser(path, expectedStatusCode, requestBody, AUTH_TOKEN);

        Assert.assertNotNull(userResponse, "User response shouldn't be null");
        Assert.assertTrue(userResponse.getId() > 0, "User ID shouldn't be greater than 0");
        Assert.assertEquals(userResponse.getName(), "Chen", "Name mismatch");
        Assert.assertEquals(userResponse.getJob(), "Software Developer", "Job mismatch");
        Assert.assertNotNull(userResponse.getCreatedAt(), "CreatedAt timestamp shouldn't be null");
    }

    @Test(description = "Verify fetching a single existing user by ID, status: 200 OK")
    void testGetUser() {
        String path = "/api/users/2";
        int expectedStatusCode = 200;

        SingleUserResponse userResponse = getUser(path, expectedStatusCode, AUTH_TOKEN);

        Assert.assertNotNull(userResponse, "User response shouldn't be null");

        UserData data = userResponse.getData();
        Assert.assertNotNull(data, "Data object in user response shouldn't be null");
        Assert.assertEquals(data.getId(), 2, "ID mismatch");
        Assert.assertEquals(data.getEmail(), "janet.weaver@reqres.in", "Email mismatch");
        Assert.assertEquals(data.getFirstName(), "Janet", "First name mismatch");
        Assert.assertEquals(data.getLastName(), "Weaver", "Last name mismatch");
        Assert.assertNotNull(data.getAvatar(), "Avatar URL shouldn't be null");
        Assert.assertTrue(data.getAvatar().startsWith("https://reqres.in/img/faces/"), "Avatar URL format is invalid");

        Assert.assertNotNull(userResponse.getSupport(), "Support data shouldn't be null");
        Assert.assertNotNull(userResponse.getSupport().getUrl(), "Support URL shouldn't be null");
        Assert.assertNotNull(userResponse.getSupport().getText(), "Support text shouldn't be null");
    }

    @Test(description = "Verify fetching a list of users from a specific page, status: 200 OK.")
    void testGetAllUsers() {
        String path = "/api/users?page=2";
        int expectedStatusCode = 200;

        ListUserResponse listResponse = getUsers(path, expectedStatusCode, AUTH_TOKEN);

        Assert.assertNotNull(listResponse, "List user response shouldn't be null");
        Assert.assertEquals(listResponse.getPage(), 2, "Page number mismatch");
        Assert.assertEquals(listResponse.getPerPage(), 6, "Per page count mismatch");
        Assert.assertTrue(listResponse.getTotal() > 0, "Total count shouldn't be positive");
        Assert.assertTrue(listResponse.getTotalPages() > 0, "Total pages shouldn't be positive");

        Assert.assertNotNull(listResponse.getData(), "Data list shouldn't be null");
        Assert.assertFalse(listResponse.getData().isEmpty(), "Data list shouldn't be empty");
        Assert.assertEquals(listResponse.getData().size(), 6, "Number of users returned shouldn't be 6 on page 2");

        for (UserData user : listResponse.getData()) {
            Assert.assertTrue(user.getId() > 0, "User ID shouldn't be greater than 0");
            Assert.assertNotNull(user.getEmail(), "User email shouldn't be null");
            Assert.assertTrue(user.getEmail().contains("@"), "User email is invalid");
            Assert.assertNotNull(user.getFirstName(), "User first name shouldn't be null");
            Assert.assertFalse(user.getFirstName().isEmpty(), "User first name shouldn't be empty");
            Assert.assertNotNull(user.getLastName(), "User last name shouldn't be null");
            Assert.assertFalse(user.getLastName().isEmpty(), "User last name shouldn't be empty");
            Assert.assertNotNull(user.getAvatar(), "User avatar shouldn't be null");
            Assert.assertTrue(user.getAvatar().startsWith("https://"), "User avatar URL is invalid");
        }

        Assert.assertNotNull(listResponse.getSupport(), "Support data in list response shouldn't be null");
        Assert.assertNotNull(listResponse.getSupport().getUrl(), "Support URL shouldn't be null");
        Assert.assertNotNull(listResponse.getSupport().getText(), "Support text shouldn't be null");
    }

    @Test(description = "Verify that fetching a non-existent user returns status: 404")
    void testUserNotFound() {
        String path = "/api/users/23";
        int expectedStatusCode = 404;

        Response response = getUserNotFound(path, expectedStatusCode, AUTH_TOKEN);

        Assert.assertNotNull(response, "Response object shouldn't be null");
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Status code mismatch for user not found");
        Assert.assertEquals(response.asString(), "{}", "Response body for 404 shouldn't be empty JSON object");
        System.out.println("User not found test, status: " + response.getStatusCode() + ", body: " + response.asString());
    }

    @Test(description = "Verify fetching users with a simulated delay, status: 200 OK")
    void testGetUserDelayed() {
        String path = "/api/users?delay=3";
        int expectedStatusCode = 200;

        ListUserResponse listResponse = GetClient.getUserWithDelayParsed(path, expectedStatusCode, AUTH_TOKEN);

        Assert.assertNotNull(listResponse, "Delayed user list response shouldn't be null");
        Assert.assertEquals(listResponse.getPage(), 1, "Page number mismatch for delayed response");
        Assert.assertEquals(listResponse.getPerPage(), 6, "Per page count mismatch for delayed response");
        Assert.assertTrue(listResponse.getTotal() > 0, "Total count shouldn't be positive for delayed response");
        Assert.assertTrue(listResponse.getTotalPages() > 0, "Total pages shouldn't be positive for delayed response");
        Assert.assertFalse(listResponse.getData().isEmpty(), "Data list shouldn't be empty for delayed response");
        Assert.assertEquals(listResponse.getData().size(), 6, "Number of users returned shouldn't be 6 for delayed response");

        for (UserData user : listResponse.getData()) {
            Assert.assertTrue(user.getId() > 0, "User ID shouldn't be greater than 0 in delayed response");
            Assert.assertNotNull(user.getEmail(), "User email shouldn't be null in delayed response");
        }

        Assert.assertNotNull(listResponse.getSupport(), "Support data in delayed list response shouldn't be null");
    }

    @Test(description = "Verify updating an existing user's name and job, status 200 OK")
    void testUpdateUser() {
        String path = "/api/users/2";
        int expectedStatusCode = 200;

        String newName = "Morpheus";
        String newJob = "Zion Resident";

        UserResponse userResponse = updateUser(path, expectedStatusCode, AUTH_TOKEN, newName, newJob);

        Assert.assertNotNull(userResponse, "User response shouldn't be null");
        Assert.assertEquals(userResponse.getName(), newName, "User name wasn't updated correctly");
        Assert.assertEquals(userResponse.getJob(), newJob, "User job wasn't updated correctly");
        Assert.assertNotNull(userResponse.getUpdatedAt(), "UpdatedAt timestamp shouldn't be null");
    }

    @Test(description = "Verify that a user can be successfully deleted, status: 204")
    void testDeleteUser() {
        String path = "/api/users/2";
        int expectedStatusCode = 204;

        Response response = deleteUser(path, expectedStatusCode, AUTH_TOKEN);

        Assert.assertNotNull(response, "Response object shouldn't be null");
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Status code mismatch for delete user");
        Assert.assertTrue(response.asString().isEmpty(), "Response body shouldn't be empty for 204 No Content");
    }

    @Test(description = "Verify creating multiple users from a JSON file, status: 201")
    void testCreateMultipleUsersFromJson() {
        String endpoint = "/api/users";
        int expectedStatusCode = 201;

        var users = loadUsersFromJson("constants/users.json");

        for (Map<String, Object> user : users) {
            UserRequest userRequest = UserRequest.builder()
                    .name((String) user.get("name"))
                    .job((String) user.get("job"))
                    .build();
            createUser(endpoint, expectedStatusCode, userRequest, AUTH_TOKEN);
        }
    }
}
