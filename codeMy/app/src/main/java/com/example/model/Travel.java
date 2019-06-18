package com.example.model;

import java.util.ArrayList;

public class Travel {
    private int id;
    private String name;
    private String place;
    private String feature;
    private String category_id;
    private double lat;
    private double lng;
    private String created_at;
    private String updated_at;
    private String deleteted_at;
    private ArrayList<String> imageUrl;

    public Travel(int id, String name, String place, String feature, String category_id, double lat, double lng, String created_at, String updated_at, String deleteted_at) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.feature = feature;
        this.category_id = category_id;
        this.lat = lat;
        this.lng = lng;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleteted_at = deleteted_at;
        imageUrl = new ArrayList<>();
    }

    public Travel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleteted_at() {
        return deleteted_at;
    }

    public void setDeleteted_at(String deleteted_at) {
        this.deleteted_at = deleteted_at;
    }

    public ArrayList<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(ArrayList<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void addUrlImage(String s)
    {
        imageUrl.add(s);
    }

    public int getSizeImage()
    {
        return imageUrl.size();
    }
    public String firstImageTravel()
    {
        return imageUrl.get(0);
    }
}
