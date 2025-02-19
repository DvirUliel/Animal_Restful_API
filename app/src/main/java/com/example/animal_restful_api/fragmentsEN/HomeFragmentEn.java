package com.example.animal_restful_api.fragmentsEN;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.animal_restful_api.R;

public class HomeFragmentEn extends Fragment {

    private String selectedLanguage = "English"; // Default selection

    public HomeFragmentEn() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_en, container, false);

        // Language Spinner Setup
        Spinner languageSpinner = view.findViewById(R.id.languageSpinner);
        String[] languages = {"English", "עברית"}; // English and Hebrew options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, languages);
        languageSpinner.setAdapter(adapter);

        // Handle language selection (but do nothing when Hebrew is selected)
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguage = languages[position]; // Save selected language
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Get Started button click listener
        view.findViewById(R.id.getStartedButton).setOnClickListener(v -> {
            if (selectedLanguage.equals("English")) {
                // Navigate to the English search fragment
                Navigation.findNavController(view).navigate(R.id.action_homeFragmentEn_to_searchFragmentEn);
            }
            else {
                // Navigate to the Hebrew search fragment
//                Navigation.findNavController(view).navigate(R.id.action_homeFragmentEn_to_searchFragmentHe);
            }
        });

        return view;
    }
}
