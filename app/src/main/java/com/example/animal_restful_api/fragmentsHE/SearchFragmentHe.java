package com.example.animal_restful_api.fragmentsHE;

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
import com.example.animal_restful_api.adapters.AnimalAdapterHe;
import com.example.animal_restful_api.models.AnimalHe;
import com.example.animal_restful_api.viewmodel.SearchViewModelHe;

import java.util.ArrayList;
import java.util.List;

public class SearchFragmentHe extends Fragment {
    private RecyclerView recyclerView;
    private AnimalAdapterHe animalAdapter;
    private EditText searchEditText;
    private Button searchButton;
    private ImageView placeholderImage;
    private SearchViewModelHe searchViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Initialize the ViewModel
        searchViewModel = new ViewModelProvider(this).get(SearchViewModelHe.class);

        View rootView = inflater.inflate(R.layout.fragment_search_he, container, false);
        searchEditText = rootView.findViewById(R.id.searchEditTextHe);
        searchButton = rootView.findViewById(R.id.searchButtonHe);
        recyclerView = rootView.findViewById(R.id.recyclerViewHe);
        placeholderImage = rootView.findViewById(R.id.placeholderImageHe);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initially hide RecyclerView and show placeholder image
        recyclerView.setVisibility(View.GONE);
        placeholderImage.setVisibility(View.VISIBLE);

        searchButton.setOnClickListener(v -> {
            String animalName = searchEditText.getText().toString().trim();
            if (!animalName.isEmpty()) {
                searchWikipedia(animalName);
            } else {
                Toast.makeText(getContext(), "נא להזין שם של חיה", Toast.LENGTH_SHORT).show();
            }
        });

        // Observe changes in the animal list
        searchViewModel.getAnimalList().observe(getViewLifecycleOwner(), animals -> {
            if (animals != null && !animals.isEmpty()) {
                animalAdapter = new AnimalAdapterHe(animals, clickedAnimal -> {
                    // Create a bundle to pass data to the AnimalDetailsFragment
                    Bundle bundle = new Bundle();
                    bundle.putString("animal_name", clickedAnimal.getTitle());
                    bundle.putString("animal_image_url", clickedAnimal.getImageUrl());
                    bundle.putString("animal_description", clickedAnimal.getDescription());
                    bundle.putString("animal_summary", clickedAnimal.getSummary());
                    bundle.putString("animal_url", clickedAnimal.getPageUrl());

                    // Navigate to AnimalDetailsFragmentHe and pass the data
                    NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                    navController.navigate(R.id.action_searchFragmentHe_to_animalDetailsFragmentHe, bundle);
                });

                recyclerView.setAdapter(animalAdapter);

                // Hide placeholder image and show RecyclerView
                recyclerView.setVisibility(View.VISIBLE);
                placeholderImage.setVisibility(View.GONE);
            } else {
                // If no animals found, show the placeholder image
                recyclerView.setVisibility(View.GONE);
                placeholderImage.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "לא נמצאו תוצאות לחיה", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void searchWikipedia(String animalName) {
        // Create a new AnimalHe object based on the search input
        AnimalHe animal = new AnimalHe(animalName);

        // Simulate API response
        List<AnimalHe> animalList = new ArrayList<>();
        animalList.add(animal);

        // Update the ViewModel with the search results
        searchViewModel.setAnimalList(animalList);
    }
}