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
import com.example.animal_restful_api.models.Animal;
import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder> {
    private List<Animal> animalList;
    private String lastAnimalInfo = "";

    public AnimalAdapter(List<Animal> animalList) {
        this.animalList = animalList;
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
        Animal animal = animalList.get(position);

        // Set animal name and info
        String info = "Name: " + animal.getName() + "\nLocation: " + String.join(", ", animal.getLocations()) + "\nGroup: " + animal.getCharacteristics().get("group");
        holder.animalTextView.setText(info);
        lastAnimalInfo = info;

        // Generate Wikimedia image URL (may need API lookup for better accuracy)
        String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6a/" + animal.getName() + ".jpg/500px-" + animal.getName() + ".jpg";

        // Load image using Glide
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.noimageavailable) // Placeholder while loading
                .error(R.drawable.noimageavailable) // Show placeholder if loading fails
                .into(holder.animalImageView);
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
}
