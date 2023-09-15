package com.example.multidatatest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private Button btnResult;
    private TextView txtV1, txtV2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle("Second 액티비티");

        Intent intent = getIntent(); // 받기!!!

        // 계산
        final int Result = intent.getIntExtra("V1",0) +
                intent.getIntExtra("V2",0);


        btnResult = findViewById(R.id.btnResult);
        txtV1 = findViewById(R.id.txtV1);
        txtV2 = findViewById(R.id.txtV2);

        txtV1.setText(intent.getIntExtra("V1",0) + "");  // 문자열도 넣어서 오류 억제
        txtV2.setText(intent.getIntExtra("V2",0) + "");  // 문제 있으면 0으로 처리해라

        // 계산된 결과 리턴
        btnResult.setOnClickListener(view -> {
            Intent retIntent = new Intent(getApplicationContext(), MainActivity.class);
            retIntent.putExtra("RESULT", Result);
            // 인텐트를 되돌려 주는 함수
            setResult(RESULT_OK, retIntent);
            finish();
        });
    }
}