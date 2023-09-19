package com.example.addressapp2;


import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnOk;
    private RecyclerView recyclerView;
    private PhoneBookAdapter adapter;
    private PhoneBookDB phoneBookDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOk = findViewById(R.id.btnOk);
        recyclerView = findViewById(R.id.recyclerView);
        phoneBookDB = new PhoneBookDB(this);

        // 리사이클러뷰 레이아웃 매니저 설정
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 어댑터 초기화 및 리사이클러뷰에 설정
        adapter = new PhoneBookAdapter(this, getAllPhoneNumbers());
        recyclerView.setAdapter(adapter);



        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    // 모든 연락처 가져오기
    private ArrayList<PhoneBook> getAllPhoneNumbers() {
        ArrayList<PhoneBook> phoneBooks = new ArrayList<>();
        Cursor cursor = phoneBookDB.readAllData();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(PhoneBookDB.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(PhoneBookDB.COLUMN_NAME));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(PhoneBookDB.COLUMN_PHONE_NUMBER));
                phoneBooks.add(new PhoneBook(id, name, phoneNumber));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return phoneBooks;
    }
}