package com.loconav.translation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Countofexpiredvehicle{

    public static void main(String[] args) {
        try {
            // Define the URL and headers
            String apiUrl = "https://linehaul.stg12.loconav.dev/api/v5/vehicle_service/expired_vehicles_count";
            String acceptHeader = "application/json";
            String authorizationHeader = "8PD6xGB_7ozSxs1Fd_K_";
            String secretHeader = "_ed183673b9709a69e51ed86e6b53b";

            // Create a URL object
            URL url = new URL(apiUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Set headers
            connection.setRequestProperty("Accept", acceptHeader);
            connection.setRequestProperty("Authorization", authorizationHeader);
            connection.setRequestProperty("X-Linehaul-V2-Secret", secretHeader);

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response data
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                // Print the response data
                System.out.println("Response Data:");
                System.out.println(response.toString());
            } else {
                System.out.println("Request failed with response code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}