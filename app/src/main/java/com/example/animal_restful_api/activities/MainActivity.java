package com.example.animal_restful_api.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animal_restful_api.R;
import com.example.animal_restful_api.adapters.AnimalAdapter;
import com.example.animal_restful_api.api.ApiService;
import com.example.animal_restful_api.api.RetrofitClient;
import com.example.animal_restful_api.models.Animal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AnimalAdapter animalAdapter;
    private EditText searchEditText;
    private Button searchButton, shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        shareButton = findViewById(R.id.shareButton);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchButton.setOnClickListener(v -> {
            String animalName = searchEditText.getText().toString();
            if (!animalName.isEmpty()) {
                fetchAnimalData(animalName);
            } else {
                Toast.makeText(this, "Please enter an animal name", Toast.LENGTH_SHORT).show();
            }
        });

        shareButton.setOnClickListener(v -> {
            if (animalAdapter != null && !animalAdapter.getLastAnimalInfo().isEmpty()) {
                shareAnimalInfo(animalAdapter.getLastAnimalInfo());
            } else {
                Toast.makeText(this, "No animal data to share", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchAnimalData(String animalName) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        String query = animalName.trim().toLowerCase();

        Call<List<Animal>> call = apiService.getAnimal(query);

        call.enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Log.d("API Response", response.body().toString());
                    // Set new adapter with fetched data
                    animalAdapter = new AnimalAdapter(response.body());
                    recyclerView.setAdapter(animalAdapter);
                } else {
                    Log.d("API Response", "Response body is null or unsuccessful");
                    Toast.makeText(MainActivity.this, "No data found for the animal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                Log.e("API Error", t.getMessage());
                Toast.makeText(MainActivity.this, "Failed to load animal data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void shareAnimalInfo(String animalInfo) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, animalInfo);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share Animal Info"));
    }
}
