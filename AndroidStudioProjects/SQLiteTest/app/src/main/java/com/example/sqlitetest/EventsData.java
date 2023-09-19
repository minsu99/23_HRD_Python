package com.example.sqlitetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.sqlitetest.Constants.TABLE_NAME;
import static com.example.sqlitetest.Constants.TIME;
import static com.example.sqlitetest.Constants.TITLE;

import androidx.annotation.Nullable;

public class EventsData extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "events.db";
    private static final int DATABASE_VERSION = 1;
    public EventsData(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);  // 상수가 그냥 들어간 것보다 의미 있는 코드가 들어 가는게 좋다 혼자 한다면
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 테이블 만들기
        String query =
                String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                                " %s INTEGER, %s TEXT NOT NULL);",   // %s로 넣어주는 방법도 있다
                        TABLE_NAME, _ID, TIME, TITLE);
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);  // 테이블이 있으면 다시 만든다
        onCreate(sqLiteDatabase);
    }
}
