package com.example.petapp;

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

    private TextView Text1;
    private CheckBox chkAgree;
    private RadioGroup Rgroup1;
    private RadioButton radioDog, radioCat, radioRabbit;
    private Button btnOk;
    private ImageView IMGpet;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("애완동물 사진보기");

        ///////////////              리소스 초기화          //////////////////
        Text1 = findViewById(R.id.Text1);
        chkAgree = findViewById(R.id.chkAgree);
        Rgroup1 = findViewById(R.id.Rgroup1);
        radioDog = findViewById(R.id.radioDog);
        radioCat = findViewById(R.id.radioCat);
        radioRabbit = findViewById(R.id.radioRabbit);
        btnOk = findViewById(R.id.btnOk);
        IMGpet = findViewById(R.id.IMGpet);
        ///////////////////////////////////////////////////////////////////////////////////


        ///////////////              체크박스 이벤트 처리          //////////////////
        chkAgree.setOnCheckedChangeListener((compoundButton, b) -> {
            if (chkAgree.isChecked() == true) {
                Rgroup1.setVisibility(View.VISIBLE);
                btnOk.setVisibility(View.VISIBLE);
                IMGpet.setVisibility(View.VISIBLE);
            } else {
                Rgroup1.setVisibility(View.INVISIBLE);
                btnOk.setVisibility(View.INVISIBLE);
                IMGpet.setVisibility(View.INVISIBLE);
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////


        ///////////////              버튼 이벤트 처리          //////////////////
        btnOk.setOnClickListener(view -> {
//                int selectedId = Rgroup1.getCheckedRadioButtonId();
//
//                if (selectedId == -1) {
//                    Toast.makeText(getApplicationContext(), "동물 선택", Toast.LENGTH_SHORT).show();
//                } else {
//                    if (selectedId == R.id.radioDog) {
//                        IMGpet.setImageResource(R.drawable.dog);
//                    } else if (selectedId == R.id.radioCat) {
//                        IMGpet.setImageResource(R.drawable.cat);
//                    } else if (selectedId == R.id.radioRabbit) {
//                        IMGpet.setImageResource(R.drawable.rabbit);
//                    }
//                }
//            }
//        });
            if (Rgroup1.getCheckedRadioButtonId() == R.id.radioDog) {
                IMGpet.setImageResource(R.drawable.dog);
            } else if (Rgroup1.getCheckedRadioButtonId() == R.id.radioCat) {
                IMGpet.setImageResource(R.drawable.cat);
            } else if (Rgroup1.getCheckedRadioButtonId() == R.id.radioRabbit) {
                IMGpet.setImageResource(R.drawable.rabbit);
            } else {
                Toast.makeText(this, "에러", Toast.LENGTH_SHORT).show();
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////

    }
}