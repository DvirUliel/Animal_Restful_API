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
    private List<AnimalHe> animalList;
    private OnItemClickListener clickListener;
    private String lastAnimalInfo = "";

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_item, parent, false);
        return new AnimalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalViewHolder holder, int position) {
        AnimalHe animal = animalList.get(position);

        // מציג את שם החיה בהתחלה
        String info = "שם: " + animal.getTitle();
        holder.animalTextView.setText(info);
        lastAnimalInfo = info;

        // מבצע בקשה לוויקיפדיה כדי להחזיר את כל פרטי החיה (summary, description, image)
        fetchWikipediaData(animal, holder.animalImageView, holder);

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

    private void fetchWikipediaData(AnimalHe animal, ImageView imageView, AnimalViewHolder holder) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://he.wikipedia.org/api/rest_v1/page/summary/" + animal.getTitle().replace(" ", "_");

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

                        // אם יש סיכום, עדכון
                        if (jsonObject.has("extract")) {
                            String summary = jsonObject.getString("extract");
                            animal.setSummary(summary);
                        }

                        // אם יש תיאור, עדכון
                        if (jsonObject.has("description")) {
                            String description = jsonObject.getString("description");
                            animal.setDescription(description);
                        }

                        // אם יש קישור לדף, עדכון
                        if (jsonObject.has("content_urls")) {
                            String pageUrl = jsonObject.getJSONObject("content_urls").getJSONObject("mobile").getString("page");
                            animal.setPageUrl(pageUrl);
                        }

                        // אם יש תמונה, עדכון
                        if (jsonObject.has("thumbnail")) {
                            String imageUrl = jsonObject.getJSONObject("thumbnail").getString("source");
                            animal.setImageUrl(imageUrl);

                            // עדכון התמונה ב-ImageView
                            imageView.post(() -> Glide.with(imageView.getContext())
                                    .load(imageUrl)
                                    .placeholder(R.drawable.noimageavailable)
                                    .error(R.drawable.noimageavailable)
                                    .into(imageView));
                        }

                        // עדכון ה-UI צריך לקרות על ת'רד הראשי
                        holder.animalTextView.post(() -> {
                            // עדכון הטקסט ב-TextView עם כל המידע (שם, תיאור, סיכום)
                            String info = "שם: " + animal.getTitle() + "\nתיאור: " + animal.getDescription();
                            holder.animalTextView.setText(info);
                            lastAnimalInfo = info;
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
