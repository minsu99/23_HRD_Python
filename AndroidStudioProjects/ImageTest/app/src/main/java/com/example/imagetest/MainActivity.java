package com.example.imagetest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private boolean flag = true; // Add this line for the flag

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if (flag) {
                    imageView.setImageResource(R.drawable.article_23094014506227);
                } else {
                    imageView.setImageResource(R.drawable.kksda2);
                }
                flag = !flag;
                return false;
            }
        });
    }
}
