package com.example.artapp;

public class CustomBook {
    private String phone_id;
    private String phone_name;
    private String phone_number;
    //private float rating; // 그림에 대한 별점 필드 추가

    public CustomBook(String id, String name, String number) {
        this.phone_id = id;
        this.phone_name = name;
        this.phone_number = number;
        //this.rating = rating;
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

//    public float getRating() {
//        return rating;
//    }
//
//    public void setRating(float rating) {
//        this.rating = rating;
//    }
}
