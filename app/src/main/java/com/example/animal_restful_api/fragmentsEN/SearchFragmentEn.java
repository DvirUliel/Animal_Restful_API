package com.example.animal_restful_api.fragmentsEN;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animal_restful_api.R;
import com.example.animal_restful_api.adapters.AnimalAdapter;
import com.example.animal_restful_api.api.ApiService;
import com.example.animal_restful_api.api.RetrofitClient;
import com.example.animal_restful_api.models.Animal;
import com.example.animal_restful_api.viewmodel.SearchViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class SearchFragmentEn extends Fragment {
    private RecyclerView recyclerView;
    private AnimalAdapter animalAdapter;
    private EditText searchEditText;
    private Button searchButton;
    private SearchViewModel searchViewModel;
    private ImageView placeholderImage; // Add reference to placeholder image

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Initialize the ViewModel
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        View rootView = inflater.inflate(R.layout.fragment_search_en, container, false);
        searchEditText = rootView.findViewById(R.id.searchEditText);
        searchButton = rootView.findViewById(R.id.searchButton);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        placeholderImage = rootView.findViewById(R.id.placeholderImage); // Initialize placeholder image
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initially set the RecyclerView and Image visibility
        recyclerView.setVisibility(View.GONE);  // Hide RecyclerView by default
        placeholderImage.setVisibility(View.VISIBLE); // Show placeholder by default

        searchButton.setOnClickListener(v -> {
            String animalName = searchEditText.getText().toString();
            if (!animalName.isEmpty()) {
                fetchAnimalData(animalName);
            } else {
                Toast.makeText(getContext(), "Please enter an animal name", Toast.LENGTH_SHORT).show();
            }
        });

        // Observe changes in the animal list
        searchViewModel.getAnimalList().observe(getViewLifecycleOwner(), animals -> {
            if (animals != null && !animals.isEmpty()) {
                animalAdapter = new AnimalAdapter(animals, animal -> {
                    // Navigate to AnimalDetailsFragment
                    Bundle bundle = new Bundle();
                    bundle.putString("animal_name", animal.getName());
                    bundle.putString("animal_image_url", animal.getImageUrl());
                    bundle.putString("animal_locations", String.join(", ", animal.getLocations()));
                    bundle.putString("animal_weight", animal.getCharacteristics().get("weight"));
                    bundle.putString("animal_height", animal.getCharacteristics().get("height"));
                    bundle.putString("animal_length", animal.getCharacteristics().get("length"));
                    bundle.putString("animal_diet", animal.getCharacteristics().get("diet"));
                    bundle.putString("animal_slogan", animal.getCharacteristics().get("slogan"));
                    bundle.putString("animal_description", animal.getDescription());
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.action_searchFragmentEn_to_animalDetailsFragmentEn, bundle);
                });
                recyclerView.setAdapter(animalAdapter);

                // Hide the placeholder image and show RecyclerView if data is found
                recyclerView.setVisibility(View.VISIBLE);
                placeholderImage.setVisibility(View.GONE);
            } else {
                // No data found, show the placeholder image
                recyclerView.setVisibility(View.GONE);
                placeholderImage.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "No data found for the animal", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void fetchAnimalData(String animalName) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        String query = animalName.trim().toLowerCase();
        Call<List<Animal>> call = apiService.getAnimal(query);

        call.enqueue(new Callback<List<Animal>>() {
            @Override
            public void onResponse(Call<List<Animal>> call, Response<List<Animal>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    searchViewModel.setAnimalList(response.body());
                } else {
                    Toast.makeText(getContext(), "No data found for the animal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Animal>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load animal data", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
