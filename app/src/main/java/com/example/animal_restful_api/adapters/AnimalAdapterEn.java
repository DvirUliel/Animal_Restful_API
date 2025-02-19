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

public class AnimalAdapterEn extends RecyclerView.Adapter<AnimalAdapterEn.AnimalViewHolder> {
    private List<AnimalEn> animalList;
    private OnItemClickListener clickListener;
    private String lastAnimalInfo = "";

    public interface OnItemClickListener {
        void onItemClick(AnimalEn animal);
    }

    public AnimalAdapterEn(List<AnimalEn> animalList, OnItemClickListener clickListener) {
        this.animalList = animalList;
        this.clickListener = clickListener;
    }

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
        AnimalEn animal = animalList.get(position);

        String info = "Name: " + animal.getName() + "\nLocation: " + String.join(", ", animal.getLocations());
        holder.animalTextView.setText(info);
        lastAnimalInfo = info;

        fetchWikipediaData(animal, holder.animalImageView);

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(animal);
            }
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

    private void fetchWikipediaData(AnimalEn animal, ImageView imageView) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://en.wikipedia.org/api/rest_v1/page/summary/" + animal.getName().replace(" ", "_");

        Request request = new Request.Builder().url(url).get().build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String responseData = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseData);
                        if (jsonObject.has("extract")) {
                            String description = jsonObject.getString("extract");
                            animal.setDescription(description);
                        }
                        if (jsonObject.has("content_urls")) {
                            String pageUrl = jsonObject.getJSONObject("content_urls").getJSONObject("mobile").getString("page");
                            animal.setPageUrl(pageUrl);
                        }
                        if (jsonObject.has("thumbnail")) {
                            String imageUrl = jsonObject.getJSONObject("thumbnail").getString("source");
                            animal.setImageUrl(imageUrl);
                            imageView.post(() -> Glide.with(imageView.getContext())
                                    .load(imageUrl)
                                    .placeholder(R.drawable.noimageavailable)
                                    .error(R.drawable.noimageavailable)
                                    .into(imageView));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
