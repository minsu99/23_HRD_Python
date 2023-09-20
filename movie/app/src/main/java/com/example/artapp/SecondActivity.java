package com.example.artapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private EditText addNameEdit;
    private EditText addArtworkEdit, addReviewEdit;
    private RatingBar addRatingBar;
    private Button btnReturn;
    private CustomDB customDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle("투표 결과");

        addNameEdit = findViewById(R.id.add_name_edit);
        addArtworkEdit = findViewById(R.id.add_name2_edit);
        addReviewEdit = findViewById(R.id.add_review_edit);
        addRatingBar = findViewById(R.id.add_rating_bar);
        btnReturn = findViewById(R.id.btnReturn);

        customDB = new CustomDB(this);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 입력값 변수에 담기
                String name = addNameEdit.getText().toString(); // 이름 읽어오기
                String artworkName = addArtworkEdit.getText().toString(); // 작품명 읽어오기
                float rating = addRatingBar.getRating(); // RatingBar에서 평가값 가져오기
                String review = addReviewEdit.getText().toString(); // 리뷰 읽어오기

                // DB에 이름, 작품명, 별점, 리뷰 추가
                customDB.addArtwork(name, artworkName, rating, review); // 리뷰 추가

                // 저장 성공 메시지 표시
                Toast.makeText(SecondActivity.this, "작품 등록 성공", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Custom.class);
                startActivity(intent);
            }
        });


        Intent intent = getIntent();
        int[] voteResult = intent.getIntArrayExtra("VoteCount");
        String[] imageName = intent.getStringArrayExtra("ImageName");

        Integer imageFileId[] = { R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,
                R.drawable.pic4, R.drawable.pic5, R.drawable.pic6,
                R.drawable.pic7, R.drawable.pic8, R.drawable.pic9 };

        int maxEntry = 0;
        for (int i = 1; i < voteResult.length; i++) {
            if (voteResult[maxEntry] < voteResult[i])
                maxEntry = i;
        }

        TextView tv[] = new TextView[imageName.length];
        RatingBar rbar[] = new RatingBar[imageName.length];

        Integer tvID[] = { R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5,
                R.id.tv6, R.id.tv7, R.id.tv8, R.id.tv9 };
        Integer rbarID[] = { R.id.rbar1, R.id.rbar2, R.id.rbar3, R.id.rbar4,
                R.id.rbar5, R.id.rbar6, R.id.rbar7, R.id.rbar8, R.id.rbar9 };

        for (int i = 0; i < voteResult.length; i++) {
            tv[i] = findViewById(tvID[i]);
            rbar[i] = findViewById(rbarID[i]);
        }

        for (int i = 0; i < voteResult.length; i++) {
            tv[i].setText(imageName[i]);
            rbar[i].setRating(voteResult[i]);
        }
    }
}
