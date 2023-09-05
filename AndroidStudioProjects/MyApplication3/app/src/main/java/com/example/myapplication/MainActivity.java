package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private CarClassifier cls;

    private View.OnTouchListener listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            ImageView imageView = (ImageView) view;

            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();

            // 여기서 텐서플로라이트 분류 모델에 예측을 시킵니다.
            Pair<String, Float> res = cls.classifyImage(bitmap);
            String outStr = String.format(Locale.ENGLISH, "Predicted: %s (%.2f%%)",
                    res.first, res.second * 100.0f);

            // 예측 결과를 출력합니다.
            textView.setText(outStr);

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        ImageView imageView2 = findViewById(R.id.imageView2);
        imageView2.setOnTouchListener(listener);

        cls = new CarClassifier(this);
        try {
            cls.loadModel();
        } catch (Exception e) {
            Log.d("CarClassifier", "Failed to load model", e);
        }
    }

    @Override
    protected void onDestroy() {
        cls.finish();
        super.onDestroy();
    }
}