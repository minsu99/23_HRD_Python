package com.example.girlsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private RadioGroup Rgroup1;
    private RadioButton kanye, newj, lass;
    private Button btnOk;
    private ImageView IMGpet;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("사진");



        Rgroup1 = findViewById(R.id.Rgroup1);
        newj = findViewById(R.id.newj);
        lass = findViewById(R.id.lass);
        kanye = findViewById(R.id.kanye);
        btnOk = findViewById(R.id.btnOk);
        IMGpet = findViewById(R.id.IMGpet);









        btnOk.setOnClickListener(view -> {

            if (Rgroup1.getCheckedRadioButtonId() == R.id.newj) {
                IMGpet.setImageResource(R.drawable.newj);
            } else if (Rgroup1.getCheckedRadioButtonId() == R.id.lass) {
                IMGpet.setImageResource(R.drawable.lass);
            } else if (Rgroup1.getCheckedRadioButtonId() == R.id.kanye) {
                IMGpet.setImageResource(R.drawable.kanye);
            } else {
                Toast.makeText(this, "에러", Toast.LENGTH_SHORT).show();
            }
        });



    }
}