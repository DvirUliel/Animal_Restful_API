package com.example.animal_restful_api.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    // The base URL for the API
    private static final String BASE_URL = "https://api.api-ninjas.com/v1/";

    // Singleton instance of Retrofit
    private static Retrofit retrofit;

    /**
     * Method to get the singleton instance of Retrofit.
     * Initializes the Retrofit instance if it's null.
     * @return Retrofit instance
     */
    public static Retrofit getClient() {
        // Check if the Retrofit instance is already created
        if (retrofit == null) {
            // If not, create a new Retrofit instance
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)  // Set the base URL for API requests
                    .addConverterFactory(GsonConverterFactory.create())  // Use Gson to convert the JSON response to Java objects
                    .build();
        }
        // Return the existing or newly created Retrofit instance
        return retrofit;
    }
}
