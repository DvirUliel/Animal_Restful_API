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
import com.example.animal_restful_api.models.AnimalEn;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Adapter class for displaying a list of animals in a RecyclerView.
 * This adapter binds the data from the AnimalEn model to the UI components in each RecyclerView item.
 */
public class AnimalAdapterEn extends RecyclerView.Adapter<AnimalAdapterEn.AnimalViewHolder> {

    private List<AnimalEn> animalList; // List of animals to be displayed
    private OnItemClickListener clickListener; // Click listener for handling item clicks
    private String lastAnimalInfo = ""; // Stores information of the last displayed animal

    /**
     * Interface for handling click events on animal items.
     */
    public interface OnItemClickListener {
        void onItemClick(AnimalEn animal);
    }

    /**
     * Constructor for initializing the adapter with a list of animals and a click listener.
     * @param animalList List of AnimalEn objects to be displayed.
     * @param clickListener Listener for handling item clicks.
     */
    public AnimalAdapterEn(List<AnimalEn> animalList, OnItemClickListener clickListener) {
        this.animalList = animalList;
        this.clickListener = clickListener;
    }

    /**
     * Returns the last stored animal information.
     * @return Last animal information string.
     */
    public String getLastAnimalInfo() {
        return lastAnimalInfo;
    }

    /**
     * Creates a new ViewHolder when there are no existing ViewHolders available for reuse.
     * @param parent The ViewGroup into which the new View will be added.
     * @param viewType The view type of the new View.
     * @return A new AnimalViewHolder instance.
     */
    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_item, parent, false);
        return new AnimalViewHolder(view);
    }

    /**
     * Binds data to a ViewHolder at a specific position.
     * @param holder The ViewHolder that should be updated.
     * @param position The position of the item in the dataset.
     */
    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        AnimalEn animal = animalList.get(position);

        // Set the text displaying animal name and locations
        String info = "Name: " + animal.getName() + "\nLocation: " + String.join(", ", animal.getLocations());
        holder.animalTextView.setText(info);
        lastAnimalInfo = info; // Store the last displayed animal info

        // Fetch additional data (e.g., image and description) from Wikipedia API
        fetchWikipediaData(animal, holder.animalImageView);

        // Handle item click events
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(animal);
            }
        });
    }

    /**
     * Returns the total number of items in the dataset.
     * @return The size of the animal list.
     */
    @Override
    public int getItemCount() {
        return animalList.size();
    }

    /**
     * ViewHolder class to hold references to the views in each RecyclerView item.
     */
    static class AnimalViewHolder extends RecyclerView.ViewHolder {
        TextView animalTextView; // TextView for displaying animal name and location
        ImageView animalImageView; // ImageView for displaying animal image

        /**
         * Constructor that initializes the views.
         * @param itemView The root view of the item layout.
         */
        public AnimalViewHolder(View itemView) {
            super(itemView);
            animalTextView = itemView.findViewById(R.id.animalTextView);
            animalImageView = itemView.findViewById(R.id.animalImageView);
        }
    }

    /**
     * Fetches animal data from Wikipedia API, including image, description, and page URL.
     * @param animal The AnimalEn object whose data is being retrieved.
     * @param imageView The ImageView to update with the fetched image.
     */
    private void fetchWikipediaData(AnimalEn animal, ImageView imageView) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://en.wikipedia.org/api/rest_v1/page/summary/" + animal.getName().replace(" ", "_");

        Request request = new Request.Builder().url(url).get().build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace(); // Log network errors
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseData);

                        // If the response contains a description, set it in the AnimalEn object
                        if (jsonObject.has("extract")) {
                            String description = jsonObject.getString("extract");
                            animal.setDescription(description);
                        }

                        // If the response contains a Wikipedia page URL, set it in the AnimalEn object
                        if (jsonObject.has("content_urls")) {
                            String pageUrl = jsonObject.getJSONObject("content_urls").getJSONObject("mobile").getString("page");
                            animal.setPageUrl(pageUrl);
                        }

                        // If the response contains an image URL, update the ImageView using Glide
                        if (jsonObject.has("thumbnail")) {
                            String imageUrl = jsonObject.getJSONObject("thumbnail").getString("source");
                            animal.setImageUrl(imageUrl);
                            imageView.post(() -> Glide.with(imageView.getContext())
                                    .load(imageUrl)
                                    .placeholder(R.drawable.noimageavailable) // Placeholder while loading
                                    .error(R.drawable.noimageavailable) // Fallback if loading fails
                                    .into(imageView));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace(); // Log JSON parsing errors
                    }
                }
            }
        });
    }
}
