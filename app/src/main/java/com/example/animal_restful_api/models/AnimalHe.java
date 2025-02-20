package com.example.animal_restful_api.models;

public class AnimalHe {
    private String title;
    private String imageUrl;
    private String pageUrl;
    private String description;
    private String summary;

    // Constructor for just the title (used for search)
    public AnimalHe(String title) {
        this.title = title;
    }
    // Constructor with all fields (for creating a full object after API response)
    public AnimalHe(String title, String imageUrl, String pageUrl, String description, String summary) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.pageUrl = pageUrl;
        this.description = description;
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
