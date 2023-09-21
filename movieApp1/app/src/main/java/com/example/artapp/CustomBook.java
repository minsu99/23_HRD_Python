package com.example.artapp;

public class CustomBook {
    private String phone_id;
    private String phone_name;
    private String phone_number;

    private String phone_rating;
    private int rating; // Uncomment the field

    private String review;

    private String artwork_name;

    public CustomBook(String id, String name, String artwork_name, int rating,  String review) {
        this.phone_id = id;
        this.phone_name = name;
        //this.phone_number = number;
        this.artwork_name = artwork_name;
        this.rating = rating;
        this.review = review;
        //this.rating = Float.parseFloat(rating); // Convert the rating to float
    }

    public String getPhone_id() {
        return phone_id;
    }

    public String getPhone_name() {
        return phone_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getPhone_rating() {
        return phone_rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getArtwork_name() {
        return artwork_name;
    }

    public void setArtwork_name(String artwork_name) {
        this.artwork_name = artwork_name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }



}