package com.example.artapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private EditText addNameEdit, addPhoneEdit, addRatingEdit; // 평가값 입력 필드 추가
    private Button btnReturn;
    private CustomDB customDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle("투표 결과");

        addNameEdit = findViewById(R.id.add_name_edit);
        addPhoneEdit = findViewById(R.id.add_phone_edit);
        addRatingEdit = findViewById(R.id.add_rating_edit); // 평가값 입력 필드 참조 추가
        btnReturn = findViewById(R.id.btnReturn);
        customDB = new CustomDB(this);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 입력값 변수에 담기
                String name = addNameEdit.getText().toString();
                String phone = addPhoneEdit.getText().toString();
                float rating = Float.parseFloat(addRatingEdit.getText().toString()); // 입력값에서 평가값 가져오기

                // DB에 연락처 추가
                customDB.addPhoneNumber(name, phone);

                // 저장 성공 메시지 표시
                Toast.makeText(SecondActivity.this, "저장", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Custom.class);
                startActivity(intent);
            }
        });

        // 앞 화면에서 보낸 투표 결과 값을 받는다.
        Intent intent = getIntent();
        int[] voteResult = intent.getIntArrayExtra("VoteCount");
        String[] imageName = intent.getStringArrayExtra("ImageName");

        Integer imageFileId[] = { R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,
                R.drawable.pic4, R.drawable.pic5, R.drawable.pic6,
                R.drawable.pic7, R.drawable.pic8, R.drawable.pic9 };

        // 1등 그림 이름과 그림 파일을 보여준다.

        ImageView ivTop = findViewById(R.id.ivTop);
        int maxEntry = 0;
        for (int i = 1; i < voteResult.length; i++) {
            if (voteResult[maxEntry] < voteResult[i])
                maxEntry = i;
        }

        ivTop.setImageResource(imageFileId[maxEntry]); // 그림을 뿌린다? 이미지 뷰에

        // 9개의 TextView, RatingBar 객체배열
        TextView tv[] = new TextView[imageName.length];
        RatingBar rbar[] = new RatingBar[imageName.length];

        // 9개의 TextView, RatingBar ID 배열
        Integer tvID[] = { R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5,
                R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9 };
        Integer rbarID[] = { R.id.rbar1, R.id.rbar2, R.id.rbar3, R.id.rbar4,
                R.id.rbar5, R.id.rbar6, R.id.rbar7, R.id.rbar8, R.id.rbar9 };

        // TextView, RatingBar 개체 찾기.
        for (int i = 0; i < voteResult.length; i++) {
            tv[i] = findViewById(tvID[i]);
            rbar[i] = findViewById(rbarID[i]);
        }

        // 각 TextVeiw 및 RatingBar에 넘겨 받은 값을 반영.
        for (int i = 0; i < voteResult.length; i++) {
            tv[i].setText(imageName[i]);
            rbar[i].setRating((float) voteResult[i]);
        }

//        Button btnReturn = findViewById(R.id.btnReturn);
//        btnReturn.setOnClickListener(v -> finish());

    }
}