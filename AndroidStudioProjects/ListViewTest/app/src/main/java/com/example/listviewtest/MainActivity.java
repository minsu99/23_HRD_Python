package com.example.listviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView lvSubway;
    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvSubway = findViewById(R.id.lvSubway);

        adapter = ArrayAdapter.createFromResource(this, R.array.subway,
                android.R.layout.simple_list_item_single_choice);

        lvSubway.setAdapter(adapter);

    }
}