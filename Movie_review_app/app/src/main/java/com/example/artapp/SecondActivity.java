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

    private EditText addNameEdit;
    private EditText addArtworkEdit, addReviewEdit;
    private RatingBar addRatingBar;
    private Button btnReturn;
    private CustomDB customDB;
    private ImageView mostVotedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle("투표 결과");

        addNameEdit = findViewById(R.id.add_name_edit);
        addArtworkEdit = findViewById(R.id.add_artwork_edit);
        addReviewEdit = findViewById(R.id.add_review_edit);
        addRatingBar = findViewById(R.id.add_rating_bar);
        btnReturn = findViewById(R.id.btnReturn);

        mostVotedImageView = findViewById(R.id.mostVotedImageView);
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
                customDB.addArtwork(name, artworkName, (int) rating, review); // 리뷰 추가

                // 저장 성공 메시지 표시
                Toast.makeText(SecondActivity.this, "작품 등록 성공", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Custom.class);
                startActivity(intent);
            }
        });


        Intent intent = getIntent();
        int[] voteResult = intent.getIntArrayExtra("VoteCount");
        String[] imageName = intent.getStringArrayExtra("ImageName");



        int maxEntry = 0;

        for (int i = 1; i < voteResult.length; i++) {
            if (voteResult[maxEntry] < voteResult[i])
                maxEntry = i;
        }

        Integer imageFileId[] = {
                R.drawable.j, R.drawable.kn, R.drawable.ma,
                R.drawable.fig, R.drawable.g, R.drawable.mo,
                R.drawable.no, R.drawable.one, R.drawable.r
        };

        String mostVotedArtworkName = imageName[maxEntry];
        addArtworkEdit.setText(mostVotedArtworkName);

        mostVotedImageView.setImageResource(imageFileId[maxEntry]);
        //mostVotedTextView.setText(imageName[maxEntry]);

    }
}
