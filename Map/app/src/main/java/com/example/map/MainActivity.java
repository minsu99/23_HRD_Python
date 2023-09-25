package com.example.map;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 버튼 클릭 시 MapFragment를 표시
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new MapFragment())
                        .commit();
            }
        });
    }
}
