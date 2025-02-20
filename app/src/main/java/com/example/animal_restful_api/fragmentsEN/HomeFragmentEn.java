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

/**
 * HomeFragmentEn is the starting screen for the app, allowing users to select a language
 * and navigate to the appropriate search screen.
 */
public class HomeFragmentEn extends Fragment {

    private String selectedLanguage = "English"; // Default language selection

    /**
     * Default constructor for HomeFragmentEn.
     * Required for fragment instantiation.
     */
    public HomeFragmentEn() {
        // Required empty public constructor
    }

    /**
     * Inflates the layout and initializes UI components.
     * @param inflater LayoutInflater to inflate the layout.
     * @param container Parent view that the fragment UI should attach to.
     * @param savedInstanceState Previous saved state (if any).
     * @return The View for the fragment UI.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_en, container, false);

        // Initialize and set up the language selection Spinner
        Spinner languageSpinner = view.findViewById(R.id.languageSpinner);
        String[] languages = {"English", "עברית"}; // Options: English and Hebrew
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, languages);
        languageSpinner.setAdapter(adapter);

        // Handle language selection changes
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguage = languages[position]; // Update selected language
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No action needed
            }
        });

        // Set click listener for the "Get Started" button
        view.findViewById(R.id.getStartedButton).setOnClickListener(v -> {
            if (selectedLanguage.equals("English")) {
                // Navigate to the English search fragment
                Navigation.findNavController(view).navigate(R.id.action_homeFragmentEn_to_searchFragmentEn);
            } else {
                // Navigate to the Hebrew search fragment
                Navigation.findNavController(view).navigate(R.id.action_homeFragmentEn_to_searchFragmentHe);
            }
        });

        return view;
    }
}
