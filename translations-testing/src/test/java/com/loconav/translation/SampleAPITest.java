package com.loconav.translation;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SampleAPITest {

    @Test
    public void testGetRequest() {
        // Define the API endpoint URL
        String apiEndpoint = "https://linehaul.stg12.loconav.dev/api/v5/vehicle_service/expired_vehicles_count";

        // Send a GET request to the API
        Response response = RestAssured.get(apiEndpoint);

        // Get the response status code
        int statusCode = response.getStatusCode();

        // Validate the response status code (expecting 200 - OK)
        Assert.assertEquals(statusCode, 200);

        // Parse the JSON response
        String responseBody = response.getBody().asString();

        // Validate the response data (check for specific fields)
        Assert.assertTrue(responseBody.contains("userId"));
        Assert.assertTrue(responseBody.contains("id"));
        Assert.assertTrue(responseBody.contains("title"));
        Assert.assertTrue(responseBody.contains("body"));

        System.out.println("API Test Passed!");
    }
}
