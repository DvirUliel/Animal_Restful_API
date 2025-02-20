package com.example.animal_restful_api.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.animal_restful_api.R;
import com.example.animal_restful_api.models.AnimalHe;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AnimalAdapterHe extends RecyclerView.Adapter<AnimalAdapterHe.AnimalViewHolder> {
    private List<AnimalHe> animalList;  // List of animals to display
    private OnItemClickListener clickListener;  // Listener for item clicks
    private String lastAnimalInfo = "";  // Holds information about the last animal clicked

    // Interface to handle item click events
    public interface OnItemClickListener {
        void onItemClick(AnimalHe animal);
    }

    public AnimalAdapterHe(List<AnimalHe> animalList, OnItemClickListener clickListener) {
        this.animalList = animalList;
        this.clickListener = clickListener;
    }

    public String getLastAnimalInfo() {
        return lastAnimalInfo;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the view for an individual animal item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_item, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        AnimalHe animal = animalList.get(position);

        // Display the animal's name
        String info = "שם: " + animal.getTitle();
        holder.animalTextView.setText(info);
        lastAnimalInfo = info;

        // Fetch additional data (summary, description, image) from Wikipedia
        fetchWikipediaData(animal, holder.animalImageView, holder);

        // Set the click listener for the item view
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(animal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animalList.size();  // Return the number of items in the list
    }

    // ViewHolder class for individual animal items
    static class AnimalViewHolder extends RecyclerView.ViewHolder {
        TextView animalTextView;
        ImageView animalImageView;

        public AnimalViewHolder(View itemView) {
            super(itemView);
            animalTextView = itemView.findViewById(R.id.animalTextView);
            animalImageView = itemView.findViewById(R.id.animalImageView);
        }
    }

    /**
     * Fetches data from Wikipedia API based on the animal's title and updates the UI.
     */
    private void fetchWikipediaData(AnimalHe animal, ImageView imageView, AnimalViewHolder holder) {
        OkHttpClient client = new OkHttpClient();
        // Wikipedia API URL for the animal's page summary
        String url = "https://he.wikipedia.org/api/rest_v1/page/summary/" + animal.getTitle().replace(" ", "_");

        // Build the request
        Request request = new Request.Builder().url(url).get().build();

        // Make an asynchronous call to fetch the data
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();  // Handle error
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        // Parse the JSON response from Wikipedia
                        String responseData = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseData);

                        String summary = jsonObject.optString("extract", "");
                        String description = jsonObject.optString("description", "");

                        // Check if the entry is a valid animal
                        if (!isAnimal(summary) && !isAnimal(description)) {
                            // If not a valid animal, remove it from the list and refresh the UI
                            removeInvalidAnimal(animal, holder);
                            return;
                        }

                        animal.setSummary(summary);
                        animal.setDescription(description);

                        // Extract the page URL from the response
                        if (jsonObject.has("content_urls")) {
                            String pageUrl = jsonObject.getJSONObject("content_urls").getJSONObject("mobile").getString("page");
                            animal.setPageUrl(pageUrl);
                        }

                        // Extract and display the image URL
                        if (jsonObject.has("thumbnail")) {
                            String imageUrl = jsonObject.getJSONObject("thumbnail").getString("source");
                            animal.setImageUrl(imageUrl);

                            // Use Glide to load the image
                            imageView.post(() -> Glide.with(imageView.getContext())
                                    .load(imageUrl)
                                    .placeholder(R.drawable.noimageavailable)
                                    .error(R.drawable.noimageavailable)
                                    .into(imageView));
                        }

                        // Update the text information for the animal
                        holder.animalTextView.post(() -> {
                            String info = "שם: " + animal.getTitle() + "\nתיאור: " + animal.getDescription();
                            holder.animalTextView.setText(info);
                            lastAnimalInfo = info;
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();  // Handle JSON parsing error
                    }
                }
            }
        });
    }

    /**
     * Removes an invalid animal from the list and refreshes the UI.
     */
    private void removeInvalidAnimal(AnimalHe invalidAnimal, AnimalViewHolder holder) {
        animalList.remove(invalidAnimal);

        // Run UI updates on the main thread
        holder.itemView.post(() -> {
            notifyDataSetChanged();  // Notify the adapter that the data has changed
        });
    }

    /**
     * Function to check if the text contains keywords related to animals.
     */
    private boolean isAnimal(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        // List of keywords that indicate the entry is related to animals
        String[] animalKeywords = {
                "בעל חיים", "סוג של", "ממשפחת", "סוג במשפחת", "יונק", "זוחל", "דג", "עוף", "חרק",
                "דו-חיים", "טורף", "תת-מין", "צמחוני", "חי", "חיית מחמד", "חיית טרף", "מינים",
                "מערכת בעלי חיים", "תת-מינים", "סדרה", "מיני בעלי חיים", "הסוג", "המשפחות",
                "מינים שונים", "חיות", "המשפחתיים", "עולמם של בעלי חיים", "מערכת בעלי חיים"
        };

        // Check if any of the keywords exist in the text
        for (String keyword : animalKeywords) {
            if (text.contains(keyword)) {
                return true;
            }
        }
        return false;
    }
}
