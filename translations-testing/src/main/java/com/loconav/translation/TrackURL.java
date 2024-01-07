package com.loconav.translation;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TrackURL {

    public static void main(String[] args) {
        try {
            // Define the URL and headers
            String apiUrl = "https://linehaul.stg12.loconav.dev/api/v5/trucks/100/temp_track_urls";
            String acceptHeader = "application/json";
            String authorizationHeader = "8PD6xGB_7ozSxs1Fd_K_";

            // Create a URL object
            URL url = new URL(apiUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            // Set headers
            connection.setRequestProperty("Accept", acceptHeader);
            connection.setRequestProperty("Authorization", authorizationHeader);
            connection.setRequestProperty("Content-Type", "application/json");

            // Enable input and output streams for POST data
            connection.setDoOutput(true);

            // Define the JSON payload
            String jsonPayload = "{\"expiry_time\": 1704033252}";

            // Convert the payload to bytes
            byte[] payloadBytes = jsonPayload.getBytes(StandardCharsets.UTF_8);

            // Set the content length
            connection.setRequestProperty("Content-Length", String.valueOf(payloadBytes.length));

            // Write the payload to the request body
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(payloadBytes);
            }

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Handle the successful response as needed
                System.out.println("Request was successful.");
            } else {
                // Handle the error response as needed
                System.out.println("Request failed with response code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

