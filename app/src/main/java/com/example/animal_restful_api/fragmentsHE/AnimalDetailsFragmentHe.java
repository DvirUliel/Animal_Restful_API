package com.example.animal_restful_api.fragmentsHE;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class AnimalDetailsFragmentHe extends Fragment {

    // UI components
    private ImageView animalDetailImageViewHe;
    private TextView animalDetailNameTextViewHe;
    private TextView animalDetailDescriptionTextViewHe;
    private TextView animalDetailSummaryTextViewHe;
    private TextView animalDetailUrlTextViewHe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the fragment's layout
        return inflater.inflate(R.layout.fragment_animal_details_he, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the UI components
        animalDetailImageViewHe = view.findViewById(R.id.animalDetailImageViewHe);
        animalDetailNameTextViewHe = view.findViewById(R.id.animalDetailNameTextViewHe);
        animalDetailDescriptionTextViewHe = view.findViewById(R.id.animalDetailDescriptionTextViewHe);
        animalDetailSummaryTextViewHe = view.findViewById(R.id.animalDetailSummaryTextViewHe);
        animalDetailUrlTextViewHe = view.findViewById(R.id.animalDetailUrlTextViewHe);

        if (getArguments() != null) {
            // Retrieve animal data from arguments
            String animalName = getArguments().getString("animal_name", "No data");
            String imageUrl = getArguments().getString("animal_image_url", "");
            String summary = getArguments().getString("animal_summary", "No data");
            String description = getArguments().getString("animal_description", "No data");
            String url = getArguments().getString("animal_url", "No data");

            // Set the UI elements with the retrieved data
            animalDetailNameTextViewHe.setText(animalName);
            setBoldText(animalDetailDescriptionTextViewHe, "תיאור: ", description);
            setBoldText(animalDetailSummaryTextViewHe, "אודות: ", summary);
            setBoldText(animalDetailUrlTextViewHe, "למידע נוסף: ", url);

            // Load the image using Glide
            Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.noimageavailable)
                    .error(R.drawable.noimageavailable)
                    .into(animalDetailImageViewHe);

            // Set the action for the copy URL button
            Button copyUrlButton = view.findViewById(R.id.copyUrlButtonHe);
            copyUrlButton.setOnClickListener(v -> {
                // Copy the URL to the clipboard
                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Animal URL", url);
                clipboard.setPrimaryClip(clip);
            });
        }
    }

    // Helper method to set bold text in the TextView
    private void setBoldText(TextView textView, String label, String value) {
        SpannableString spannableString = new SpannableString(label + value);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0, label.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }
}
