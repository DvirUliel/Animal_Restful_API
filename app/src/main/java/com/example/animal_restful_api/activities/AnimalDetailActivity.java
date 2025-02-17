package com.example.animal_restful_api.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.animal_restful_api.R;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.graphics.Typeface;


public class AnimalDetailActivity extends AppCompatActivity {

    private ImageView animalDetailImageView;
    private TextView animalDetailNameTextView;
    private TextView animalDetailLocationsTextView;
    private TextView animalDetailWeightTextView;
    private TextView animalDetailHeightTextView;
    private TextView animalDetailLengthTextView;
    private TextView animalDetailDietTextView;
    private TextView animalDetailSloganTextView;
    private TextView animalDetailDescriptionTextView;

    private void setBoldText(TextView textView, String label, String value) {
        SpannableString spannableString = new SpannableString(label + value);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, label.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_detail);

        animalDetailImageView = findViewById(R.id.animalDetailImageView);
        animalDetailNameTextView = findViewById(R.id.animalDetailNameTextView);
        animalDetailLocationsTextView= findViewById(R.id.animalDetailLocationsTextView);
        animalDetailWeightTextView = findViewById(R.id.animalDetailWeightTextView);
        animalDetailHeightTextView = findViewById(R.id.animalDetailHeightTextView);
        animalDetailLengthTextView = findViewById(R.id.animalDetailLengthTextView);
        animalDetailDietTextView = findViewById(R.id.animalDetailDietTextView);
        animalDetailSloganTextView = findViewById(R.id.animalDetailSloganTextView);
        animalDetailDescriptionTextView = findViewById(R.id.animalDetailDescriptionTextView);




        // קבלת המידע שנשלח מ-animaladapter
        String animalName = getIntent().getStringExtra("animal_name");
        String imageUrl = getIntent().getStringExtra("animal_image_url");
        String locations = getIntent().getStringExtra("animal_locations");
        String weight = getIntent().getStringExtra("animal_weight");
        String height = getIntent().getStringExtra("animal_height");
        String length = getIntent().getStringExtra("animal_length");
        String diet = getIntent().getStringExtra("animal_diet");
        String slogan = getIntent().getStringExtra("animal_slogan");
        String description = getIntent().getStringExtra("animal_description");

        if(slogan == null){
            slogan = "No data";
        }
        if(height == null){
            height = "No data";
        }
        if(weight == null){
            weight = "No data";
        }
        if(length == null){
            length = "No data";
        }


        // הצגת שם החיה
        animalDetailNameTextView.setText(animalName);
        setBoldText(animalDetailLocationsTextView, "Locations: ", locations);
        setBoldText(animalDetailWeightTextView, "Weight: ", weight);
        setBoldText(animalDetailHeightTextView, "Height: ", height);
        setBoldText(animalDetailLengthTextView, "Length: ", length);
        setBoldText(animalDetailDietTextView, "Diet: ", diet);
        setBoldText(animalDetailSloganTextView, "Slogan: ", slogan);
        setBoldText(animalDetailDescriptionTextView, "Description: ", description);



        // טעינת התמונה באמצעות Glide
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.noimageavailable) // Placeholder
                .error(R.drawable.noimageavailable) // אם יש טעות בטעינה
                .into(animalDetailImageView);
    }
}
