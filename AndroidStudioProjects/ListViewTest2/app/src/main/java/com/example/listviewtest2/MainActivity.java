package com.example.listviewtest2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] mid = {"히어로즈", "24시", "로스트", "로스트룸", "스몰빌", "탐정몽크"};



        listView=findViewById(R.id.listView);
//        adapter = ArrayAdapter.createFromResource(this, R.array.fruits,
//                android.R.layout.simple_list_item_multiple_choice);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_single_choice, mid);
        listView.setAdapter(adapter);
        listView.setChoiceMode(listView.CHOICE_MODE_SINGLE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Toast.makeText(MainActivity.this, mid[index], Toast.LENGTH_SHORT).show();
            }
        });



    }
}