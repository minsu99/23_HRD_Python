package com.example.map;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private boolean isMapVisible = false; // 지도 표시 여부를 추적하는 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMapVisible) {
                    // 버튼 클릭 시 지도가 표시되어 있으면 지도 제거
                    getSupportFragmentManager()
                            .beginTransaction()
                            .remove(getSupportFragmentManager().findFragmentById(R.id.fragment_container))
                            .commit();
                    isMapVisible = false;
                } else {
                    // 버튼 클릭 시 지도가 표시되어 있지 않으면 지도 추가
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, new MapFragment())
                            .commit();
                    isMapVisible = true;
                }
            }
        });
    }
}


