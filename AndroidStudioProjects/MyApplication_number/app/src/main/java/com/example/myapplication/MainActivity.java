package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    Classifier cls;

    View.OnTouchListener listener= new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            ImageView imageView = (ImageView) view;

            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();

            // 여기서 텐서플로라이트 분류 모델에 예측을 시킨다.
            Pair<Integer,Float> res = cls.classify(bitmap);
            String outStr = String.format(Locale.ENGLISH, "%d, %.0f%%",
                    res.first, res.second*100.0f);

            // 예측 결과를 출력한다.
            textView.setText(outStr);

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);
        imageView1.setOnTouchListener(listener);
        imageView2.setOnTouchListener(listener);

        cls = new Classifier(this);
        try {
            cls.init();
        } catch (IOException ioe) {
            Log.d("DigitClassifier", "failed to init Classifier", ioe);
        }
    }

    @Override
    protected void onDestroy() {
        cls.finish();
        super.onDestroy();
    }
}