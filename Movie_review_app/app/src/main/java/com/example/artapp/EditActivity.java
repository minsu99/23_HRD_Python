package com.example.artapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    private EditText edtName, edtArtworkName, edtRating, edtReview;
    private Button btnSave;
    private int selectedPosition; // 수정된 아이템의 위치를 저장할 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit); // activity_edit.xml 레이아웃 파일과 연결

        // XML에서 UI 요소 찾기
        edtName = findViewById(R.id.edtName);
        edtArtworkName = findViewById(R.id.edtArtworkName);
        edtRating = findViewById(R.id.edtRating);
        edtReview = findViewById(R.id.edtReview);
        btnSave = findViewById(R.id.btnSave);

        // 이전 액티비티에서 전달된 데이터 받기
        int phoneId = getIntent().getIntExtra("phone_id", -1);
        String name = getIntent().getStringExtra("name");
        String artworkName = getIntent().getStringExtra("artworkName");
        int rating = getIntent().getIntExtra("rating", 0);
        String review = getIntent().getStringExtra("review");
        selectedPosition = getIntent().getIntExtra("position", -1); // 수정된 아이템의 위치 받기

        // 받은 데이터를 UI에 표시
        edtName.setText(name);
        edtArtworkName.setText(artworkName);
        edtRating.setText(String.valueOf(rating));
        edtReview.setText(review);

        // 저장 버튼 클릭 리스너 설정
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 수정된 데이터를 저장하는 로직 추가
                // 예: 데이터베이스 업데이트 또는 다른 저장 방법 사용
                saveData(phoneId);
            }
        });
    }

    private void saveData(int phoneId) {
        // 여기에서 수정된 데이터를 저장하는 코드를 구현
        // 기존 데이터 삭제 후 새 데이터 추가

        String newName = edtName.getText().toString();
        String newArtworkName = edtArtworkName.getText().toString();
        int newRating = Integer.parseInt(edtRating.getText().toString());
        String newReview = edtReview.getText().toString();

        // 데이터베이스 또는 다른 저장 방법을 사용하여 기존 데이터 삭제
        CustomDB customDB = new CustomDB(this);
        customDB.deleteArtwork(phoneId);

        // 삭제된 후에 새 데이터 추가
        // 예: 데이터베이스에 새 데이터 추가
        customDB.addArtwork(newName, newArtworkName, newRating, newReview);

        // 수정이 완료되면 액티비티 종료
        setResult(RESULT_OK);
        finish();
    }
}

