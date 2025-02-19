package com.example.animal_restful_api.fragmentsEN;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.animal_restful_api.R;

public class HomeFragmentEn extends Fragment {

    public HomeFragmentEn() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_en, container, false);

        // Get Started button click listener
        view.findViewById(R.id.getStartedButton).setOnClickListener(v -> {
            // Navigate to the next fragment (SearchFragmentEn)
            Navigation.findNavController(view).navigate(R.id.action_homeFragmentEn_to_searchFragmentEn);
        });

        return view;
    }
}
