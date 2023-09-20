package com.example.artapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Custom extends AppCompatActivity {

    private Button btnBBack;
    private CustomDB customDB;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        customDB = new CustomDB(this);
        recyclerView = findViewById(R.id.recyclerView);

        btnBBack = findViewById(R.id.btnBBack);
        btnBBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // 리사이클러뷰 레이아웃 매니저 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 어댑터 초기화 및 리사이클러뷰에 설정
        adapter = new CustomAdapter(this, getAllPhoneNumbers());
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<CustomBook> getAllPhoneNumbers() {
        ArrayList<CustomBook> phoneBooks = new ArrayList<>();
        Cursor cursor = customDB.readAllData();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(CustomDB.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(CustomDB.COLUMN_NAME));
                String artworkName = cursor.getString(cursor.getColumnIndexOrThrow(CustomDB.COLUMN_PHONE_NUMBER));
                int rating = cursor.getInt(cursor.getColumnIndexOrThrow(CustomDB.COLUMN_RATING));
                String review = cursor.getString(cursor.getColumnIndexOrThrow(CustomDB.COLUMN_REVIEW));
                phoneBooks.add(new CustomBook(id, name, artworkName, rating, review));
            }
            cursor.close();
        }
        return phoneBooks;
    }





}
