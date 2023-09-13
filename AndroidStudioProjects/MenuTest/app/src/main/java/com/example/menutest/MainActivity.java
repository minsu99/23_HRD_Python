package com.example.menutest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout layout1; // Add this line

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        layout1 = findViewById(R.id.layout1);
        setSupportActionBar(toolbar);
    }

    // 메뉴 등록 및 모양 만들기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_example, menu);
        return true;
    }

    // 각 메뉴 기능 구현
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuItem1) {

            layout1.setBackgroundColor(Color.rgb(255, 0, 0));
            return true;
        } else if (id == R.id.menuItem2) {
            layout1.setBackgroundColor(Color.rgb(0, 255, 0));

            return true;
        }else if (id == R.id.menuItem3) {
            layout1.setBackgroundColor(Color.rgb(0, 0, 255));

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
