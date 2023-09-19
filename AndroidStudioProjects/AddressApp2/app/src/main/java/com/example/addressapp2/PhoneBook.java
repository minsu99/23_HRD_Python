package com.example.addressapp2;

public class PhoneBook {
    private String phone_id;
    private String phone_name;
    private String phone_number;

    public PhoneBook(String id, String name, String number) {
        this.phone_id = id;
        this.phone_name = name;
        this.phone_number = number;
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
}
