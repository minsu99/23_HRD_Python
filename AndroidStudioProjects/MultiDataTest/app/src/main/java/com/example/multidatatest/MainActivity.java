package com.example.multidatatest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etV1, etV2;
    private Button btnOpen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpen = findViewById(R.id.btnOpen);
        etV1 = findViewById(R.id.etV1);
        etV2 = findViewById(R.id.etV2);

        // 데이터 첨부
        btnOpen.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), SecondActivity.class);

            // Intent 데이터를 첨부
            intent.putExtra("V1", Integer.parseInt(etV1.getText().toString()));
            intent.putExtra("V2", Integer.parseInt(etV2.getText().toString()));  // 300, 500을 첨부

            startActivityForResult(intent, 0);  // 전달하는 리퀘스트 0번 이라는 아이다를 던짐
        });
    }// end of onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            int plus = data.getIntExtra("RESULT", 0);
            Toast.makeText(this, "합계 : " + plus, Toast.LENGTH_SHORT).show();
        }
    }
}