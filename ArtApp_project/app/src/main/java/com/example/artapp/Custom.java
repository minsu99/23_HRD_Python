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

    // 모든 연락처 가져오기
    private ArrayList<CustomBook> getAllPhoneNumbers() {
        ArrayList<CustomBook> phoneBooks = new ArrayList<>();
        Cursor cursor = customDB.readAllData();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(CustomDB.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(CustomDB.COLUMN_NAME));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(CustomDB.COLUMN_PHONE_NUMBER));

                // getInt() 메서드를 사용하여 평점 값을 int로 가져옵니다.
                int rating = cursor.getInt(cursor.getColumnIndex(CustomDB.COLUMN_RATING));

                phoneBooks.add(new CustomBook(id, name, phoneNumber, rating));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return phoneBooks;
    }

}
