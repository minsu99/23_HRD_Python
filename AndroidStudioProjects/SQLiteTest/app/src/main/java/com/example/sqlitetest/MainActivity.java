package com.example.sqlitetest;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import static android.provider.BaseColumns._ID;
import static com.example.sqlitetest.Constants.TABLE_NAME;
import static com.example.sqlitetest.Constants.TIME;
import static com.example.sqlitetest.Constants.TITLE;

public class MainActivity extends AppCompatActivity {

    private static String[] FROM = { _ID, TIME, TITLE };
    private static String ORDER_BY = TIME + " DESC"; // 정렬 내림차순

    // SQLiteOpenHelper 객체
    private EventsData events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        events = new EventsData(this);
        addEvents("안녕하세요 안드로이드"); // INSERT INTO

        // 검색 SELECT
        Cursor cursor = getEvents();
        showEvents(cursor);

        // 리소스 반환
        events.close(); // 무조건 닫아줘야 함 중요 ****
    }

    private void addEvents(String string) {
        SQLiteDatabase db = events.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TIME, System.currentTimeMillis());
        values.put(TITLE, string);
        db.insertOrThrow(TABLE_NAME, null, values);
    }

    private Cursor getEvents() {
        SQLiteDatabase db = events.getReadableDatabase();
        return db.query(TABLE_NAME, FROM, null, null, null, null, ORDER_BY);
    }

    private void showEvents(Cursor cursor) {
        StringBuilder builder = new StringBuilder("Saved events:\n");
        while (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            long time = cursor.getLong(1);
            String title = cursor.getString(2);

            builder.append(id).append(":");
            builder.append(time).append(":");
            builder.append(title).append("\n");
        }
        cursor.close(); // Close the cursor when done with it.

        TextView txtContent = findViewById(R.id.txtContent);
        txtContent.setText(builder);
    }
}
