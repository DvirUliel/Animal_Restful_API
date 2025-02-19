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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animal_restful_api.R;
import com.example.animal_restful_api.adapters.AnimalAdapterHe;
import com.example.animal_restful_api.models.AnimalHe;

import java.util.ArrayList;
import java.util.List;

public class SearchFragmentHe extends Fragment {
    private RecyclerView recyclerView;
    private AnimalAdapterHe animalAdapter;
    private EditText searchEditText;
    private Button searchButton;
    private ImageView placeholderImage;
    private List<AnimalHe> animalList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_he, container, false);
        searchEditText = rootView.findViewById(R.id.searchEditTextHe);
        searchButton = rootView.findViewById(R.id.searchButtonHe);
        recyclerView = rootView.findViewById(R.id.recyclerViewHe);
        placeholderImage = rootView.findViewById(R.id.placeholderImageHe);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Hide RecyclerView initially
        recyclerView.setVisibility(View.GONE);
        placeholderImage.setVisibility(View.VISIBLE);

//        searchButton.setOnClickListener(v -> {
//            String animalName = searchEditText.getText().toString().trim();
//            if (!animalName.isEmpty()) {
//                fetchWikipediaData(animalName);
//            } else {
//                Toast.makeText(getContext(), "נא להזין שם של חיה", Toast.LENGTH_SHORT).show();
//            }
//        });

        return rootView;
    }

//    private void fetchWikipediaData(String animalName) {
//        AnimalHe animal = new AnimalHe(animalName);
//        animalList.clear();
//        animalList.add(animal);
//        animalAdapter = new AnimalAdapterHe(animalList, clickedAnimal -> {
//            Toast.makeText(getContext(), "נפתח דף ויקיפדיה", Toast.LENGTH_SHORT).show();
//        });
//        recyclerView.setAdapter(animalAdapter);
//        recyclerView.setVisibility(View.VISIBLE);
//        placeholderImage.setVisibility(View.GONE);
//    }
}
