package com.example.animal_restful_api.models;

import java.util.Map;

public class Animal {
    private String name;
    private String[] locations;
    private Map<String, String> characteristics;
    private String imageUrl;  // שדה חדש לשמירת קישור התמונה

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() { return name; }
    public String[] getLocations() { return locations; }
    public Map<String, String> getCharacteristics() { return characteristics; }
    public String getImageUrl() { return imageUrl; }  // גטר לשדה החדש

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;  // סטר לשדה החדש
    }
}
