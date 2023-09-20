package com.example.artapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("명화 선호도 투표 (개선)");

        final int voteCount[] = new int[9];
            for(int i=0; i<9; i++)
                voteCount[i] = 0;

            ImageView image[] = new ImageView[9];  // 9개의 이미지 버튼 객체배열

            Integer imageId[] = { R.id.iv1, R.id.iv2, R.id.iv3, R.id.iv4, R.id.iv5,
                    R.id.iv6, R.id.iv7, R.id.iv8, R.id.iv9 }; // 9개의 이미지버튼 ID 배열

            final String imgName[] = { "장화홍련", "똑똑똑", "마스크걸",
                    "파이트클럽", "짱구는 못말려", "무빙", "놉", "원피스",
                    "알포인트" };

            for(int i=0; i<imageId.length; i++){
                final int index; // 주의! 꼭 필요함.. final과 같은 상수로 처리해야 안전!!
                index = i;
                image[index] = findViewById(imageId[index]);
                image[index].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 투표수 증가
                        voteCount[index]++;
                        Toast.makeText(getApplicationContext(),
                                imgName[index] + ": 총 " + voteCount[index] + " 표",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }

            Button btnFinsh = findViewById(R.id.btnGo);
            btnFinsh.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("VoteCount", voteCount);
                intent.putExtra("ImageName", imgName);

                startActivity(intent);
            });

            Button btnBack = findViewById(R.id.btnBack);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), Custom.class);
                    startActivity(intent);
                }
            });

    }
}