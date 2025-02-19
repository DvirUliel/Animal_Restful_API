package com.example.animal_restful_api.api;

import com.example.animal_restful_api.models.AnimalEn;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import java.util.List;

public interface ApiService {
    @Headers("X-Api-Key: +mVVhCZyWH6ofNzHBxDoOQ==tGZ7NpxbYw4PYpEB")  // API key fixed
    @GET("animals")
    Call<List<AnimalEn>> getAnimal(@Query("name") String name);
}
