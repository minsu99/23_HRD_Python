package com.example.simplesqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db; // 파일
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SQLiteOpenHelper 객체를 생성합니다.
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // 데이터베이스를 엽니다. 파일을 만든다?
        db = dbHelper.getWritableDatabase();

        // 데이터를 삽입합니다.
        db.execSQL("INSERT INTO users (name, age) VALUES ('John Doe', 30)");
        db.execSQL("INSERT INTO users (name, age) VALUES ('Jane Doe', 25)");

        // 데이터를 읽습니다. read 권한
        Cursor cursor = db.query("users", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") int age = cursor.getInt(cursor.getColumnIndex("age"));

            Log.d("SQLite", "name: " + name + ", age: " + age); // 로그에 남긴다.
        }


        // 데이터베이스를 닫습니다.
        db.close();
    }
}