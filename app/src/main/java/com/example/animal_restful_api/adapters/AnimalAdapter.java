package com.example.animal_restful_api.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.animal_restful_api.R;
import com.example.animal_restful_api.activities.AnimalDetailActivity; // עמוד פרטי חיה
import com.example.animal_restful_api.models.Animal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {
    private List<Animal> animalList;
    private String lastAnimalInfo = "";  // שדה עבור המידע האחרון שנצפה

    public AnimalAdapter(List<Animal> animalList) {
        this.animalList = animalList;
    }

    // פונקציה לקבלת המידע האחרון
    public String getLastAnimalInfo() {
        return lastAnimalInfo;
    }

    @NonNull
    @Override
    public AnimalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_item, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        Animal animal = animalList.get(position);

        // הצגת שם החיה ומידע
        String info = "Name: " + animal.getName() + "\nLocation: " + String.join(", ", animal.getLocations()) ;
        holder.animalTextView.setText(info);
        lastAnimalInfo = info;  // עדכון המידע האחרון

        // שליפת התמונה מ-Wikipedia API
        fetchWikipediaImage(animal, holder.animalImageView);

        // Listener עבור לחיצה על כרטיס החיה
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AnimalDetailActivity.class);
            intent.putExtra("animal_name", animal.getName());
            intent.putExtra("animal_image_url", animal.getImageUrl());  // שליחת ה-URL של התמונה
            intent.putExtra("animal_locations", String.join(", ", animal.getLocations()));
            intent.putExtra("animal_weight", animal.getCharacteristics().get("weight"));
            intent.putExtra("animal_height", animal.getCharacteristics().get("height"));
            intent.putExtra("animal_length", animal.getCharacteristics().get("length"));
            intent.putExtra("animal_diet", animal.getCharacteristics().get("diet"));
            intent.putExtra("animal_slogan", animal.getCharacteristics().get("slogan"));
            intent.putExtra("animal_description", animal.getDescription());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return animalList.size();
    }

    static class AnimalViewHolder extends RecyclerView.ViewHolder {
        TextView animalTextView;
        ImageView animalImageView;

        public AnimalViewHolder(View itemView) {
            super(itemView);
            animalTextView = itemView.findViewById(R.id.animalTextView);
            animalImageView = itemView.findViewById(R.id.animalImageView);
        }
    }

    // שליפת תמונה מ-Wikipedia API
    private void fetchWikipediaImage(Animal animal, ImageView imageView) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://en.wikipedia.org/api/rest_v1/page/summary/" + animal.getName().replace(" ", "_");

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Wikipedia API", "Error fetching image: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseData);
                        if(jsonObject.has("extract")){
                            String description =jsonObject.getString("extract");
                            animal.setDescription(description);
                        }
                        if (jsonObject.has("thumbnail")) {
                            String imageUrl = jsonObject.getJSONObject("thumbnail").getString("source");

                            // עדכון המודל Animal עם ה-URL של התמונה
                            animal.setImageUrl(imageUrl);

                            // טעינת התמונה על UI thread
                            imageView.post(() -> Glide.with(imageView.getContext())
                                    .load(imageUrl)
                                    .placeholder(R.drawable.noimageavailable) // תמונה ממתינה במקרה של טעות
                                    .error(R.drawable.noimageavailable) // תמונה במקרה של טעות
                                    .into(imageView));
                        }
                    } catch (JSONException e) {
                        Log.e("Wikipedia API", "JSON Parsing Error: " + e.getMessage());
                    }
                }
            }
        });
    }
}
