package com.example.animal_restful_api.fragmentsEN;

import android.os.Bundle;
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
import com.example.animal_restful_api.adapters.AnimalAdapterEn;
import com.example.animal_restful_api.api.ApiService;
import com.example.animal_restful_api.api.RetrofitClient;
import com.example.animal_restful_api.models.AnimalEn;
import com.example.animal_restful_api.viewmodel.SearchViewModelEn;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class SearchFragmentEn extends Fragment {
    // Declare UI components and ViewModel
    private RecyclerView recyclerView;
    private AnimalAdapterEn animalAdapter;
    private EditText searchEditText;
    private Button searchButton;
    private SearchViewModelEn searchViewModel;
    private ImageView placeholderImage; // Placeholder image when no data is available

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Initialize the ViewModel for managing UI-related data
        searchViewModel = new ViewModelProvider(this).get(SearchViewModelEn.class);

        // Inflate the fragment layout and initialize UI components
        View rootView = inflater.inflate(R.layout.fragment_search_en, container, false);
        searchEditText = rootView.findViewById(R.id.searchEditText);
        searchButton = rootView.findViewById(R.id.searchButton);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        placeholderImage = rootView.findViewById(R.id.placeholderImage); // Initialize placeholder image

        // Set up the RecyclerView with a LinearLayoutManager for vertical scrolling
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initially, hide the RecyclerView and show the placeholder image
        recyclerView.setVisibility(View.GONE);
        placeholderImage.setVisibility(View.VISIBLE);

        // Set up a click listener for the search button
        searchButton.setOnClickListener(v -> {
            // Get the animal name from the search field
            String animalName = searchEditText.getText().toString();
            if (!animalName.isEmpty()) {
                // Fetch animal data from API if the search field is not empty
                fetchAnimalData(animalName);
            } else {
                // Show a toast if the search field is empty
                Toast.makeText(getContext(), "Please enter an animal name", Toast.LENGTH_SHORT).show();
            }
        });

        // Observe changes in the animal list from the ViewModel
        searchViewModel.getAnimalList().observe(getViewLifecycleOwner(), animals -> {
            if (animals != null && !animals.isEmpty()) {
                // Set up the adapter with the list of animals
                animalAdapter = new AnimalAdapterEn(animals, animal -> {
                    // Handle item click, navigate to AnimalDetailsFragment
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
                    bundle.putString("animal_url", animal.getPageUrl());
                    // Navigate to AnimalDetailsFragment and pass the data
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.action_searchFragmentEn_to_animalDetailsFragmentEn, bundle);
                });
                recyclerView.setAdapter(animalAdapter);

                // Hide placeholder image and show RecyclerView when data is available
                recyclerView.setVisibility(View.VISIBLE);
                placeholderImage.setVisibility(View.GONE);
            } else {
                // No data found, show the placeholder image and hide the RecyclerView
                recyclerView.setVisibility(View.GONE);
                placeholderImage.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "No data found for the animal", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    // Method to fetch animal data from the API
    private void fetchAnimalData(String animalName) {
        // Get an instance of the ApiService to make the network request
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        // Format the animal name for the query
        String query = animalName.trim().toLowerCase();
        // Make the API call to fetch the animal data
        Call<List<AnimalEn>> call = apiService.getAnimal(query);

        // Enqueue the API call and handle the response
        call.enqueue(new Callback<List<AnimalEn>>() {
            @Override
            public void onResponse(Call<List<AnimalEn>> call, Response<List<AnimalEn>> response) {
                // If the response is successful and contains data, update the ViewModel
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    searchViewModel.setAnimalList(response.body());
                } else {
                    // Show a toast if no data is found
                    Toast.makeText(getContext(), "No data found for the animal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AnimalEn>> call, Throwable t) {
                // Show a toast if the API request fails
                Toast.makeText(getContext(), "Failed to load animal data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
