package com.example.addressapp2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private Button btnBack, btnOk, btnDelete;
    private EditText addNameEdit, addPhoneEdit;
    private PhoneBookDB phoneBookDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btnBack = findViewById(R.id.btnBack);
        addNameEdit = findViewById(R.id.add_name_edit);
        addPhoneEdit = findViewById(R.id.add_phone_edit);
        btnDelete = findViewById(R.id.btnDelete);
        btnOk = findViewById(R.id.btnOk);
        phoneBookDB = new PhoneBookDB(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 입력값 변수에 담기
                String name = addNameEdit.getText().toString();
                String phone = addPhoneEdit.getText().toString();

                // DB에 연락처 추가
                phoneBookDB.addPhoneNumber(name, phone);

                // 저장 성공 메시지 표시
                Toast.makeText(SecondActivity.this, "연락처가 저장되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 삭제할 연락처의 이름과 전화번호를 가져옵니다.
                String name = addNameEdit.getText().toString();
                String phone = addPhoneEdit.getText().toString();

                // 연락처를 삭제합니다.
                phoneBookDB.deletePhoneNumber(name, phone);

                // 삭제 성공 메시지 표시
                Toast.makeText(SecondActivity.this, "연락처가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
