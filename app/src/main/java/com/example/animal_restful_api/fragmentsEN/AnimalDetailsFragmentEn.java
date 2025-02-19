package com.example.animal_restful_api.fragmentsEN;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.animal_restful_api.R;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.graphics.Typeface;

public class AnimalDetailsFragmentEn extends Fragment {

    private ImageView animalDetailImageView;
    private TextView animalDetailNameTextView;
    private TextView animalDetailLocationsTextView;
    private TextView animalDetailWeightTextView;
    private TextView animalDetailHeightTextView;
    private TextView animalDetailLengthTextView;
    private TextView animalDetailDietTextView;
    private TextView animalDetailSloganTextView;
    private TextView animalDetailDescriptionTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_animal_details_en, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        animalDetailImageView = view.findViewById(R.id.animalDetailImageView);
        animalDetailNameTextView = view.findViewById(R.id.animalDetailNameTextView);
        animalDetailLocationsTextView = view.findViewById(R.id.animalDetailLocationsTextView);
        animalDetailWeightTextView = view.findViewById(R.id.animalDetailWeightTextView);
        animalDetailHeightTextView = view.findViewById(R.id.animalDetailHeightTextView);
        animalDetailLengthTextView = view.findViewById(R.id.animalDetailLengthTextView);
        animalDetailDietTextView = view.findViewById(R.id.animalDetailDietTextView);
        animalDetailSloganTextView = view.findViewById(R.id.animalDetailSloganTextView);
        animalDetailDescriptionTextView = view.findViewById(R.id.animalDetailDescriptionTextView);

        if (getArguments() != null) {
            String animalName = getArguments().getString("animal_name", "No data");
            String imageUrl = getArguments().getString("animal_image_url", "");
            String locations = getArguments().getString("animal_locations", "No data");
            String weight = getArguments().getString("animal_weight", "No data");
            String height = getArguments().getString("animal_height", "No data");
            String length = getArguments().getString("animal_length", "No data");
            String diet = getArguments().getString("animal_diet", "No data");
            String slogan = getArguments().getString("animal_slogan", "No data");
            String description = getArguments().getString("animal_description", "No data");

            animalDetailNameTextView.setText(animalName);
            setBoldText(animalDetailLocationsTextView, "Locations: ", locations);
            setBoldText(animalDetailWeightTextView, "Weight: ", weight);
            setBoldText(animalDetailHeightTextView, "Height: ", height);
            setBoldText(animalDetailLengthTextView, "Length: ", length);
            setBoldText(animalDetailDietTextView, "Diet: ", diet);
            setBoldText(animalDetailSloganTextView, "Slogan: ", slogan);
            setBoldText(animalDetailDescriptionTextView, "Description: ", description);

            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.noimageavailable)
                    .error(R.drawable.noimageavailable)
                    .into(animalDetailImageView);
        }
    }

    private void setBoldText(TextView textView, String label, String value) {
        SpannableString spannableString = new SpannableString(label + value);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, label.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }
}

