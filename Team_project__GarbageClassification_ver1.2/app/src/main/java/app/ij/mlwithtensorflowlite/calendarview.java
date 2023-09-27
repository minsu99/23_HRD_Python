package app.ij.mlwithtensorflowlite;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class calendarview extends Activity {

    private TextView fruitTextView;
    private SQLiteDatabase sqlDB;
    private Button btnRestart;
    private EditText etxRestart;
    private String calendarviewdata = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendarview);
        fruitTextView = findViewById(R.id.fruitTextView);
        btnRestart = findViewById(R.id.btnRestart);
        etxRestart = findViewById(R.id.etxRestart);

        // 데이터베이스 연결
        fruitselection.myDBHelper dbHelper = new fruitselection.myDBHelper(this);
        sqlDB = dbHelper.getReadableDatabase();

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // 월과 일을 두 자리 숫자로 포맷팅하여 selectedDate를 생성
                String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);

                // etxRestart에 선택한 날짜를 자동으로 작성
                calendarviewdata  = selectedDate;
                etxRestart.setText(calendarviewdata);

                // 데이터베이스에서 날짜에 해당하는 데이터를 조회
                String fruitData = getFruitDataFromDatabase(selectedDate);

                fruitTextView.setText("" + fruitData);
            }
        });


        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // etxRestart에서 입력한 값을 가져옴
                String selectedDate = etxRestart.getText().toString();

                // 데이터베이스에서 선택한 날짜에 해당하는 데이터를 조회
                String fruitData = getFruitDataFromDatabase(selectedDate);

                fruitTextView.setText("" + fruitData);
            }
        });
    }

    // 데이터베이스에서 날짜에 해당하는 과일 데이터를 가져오는 메서드
    private String getFruitDataFromDatabase(String selectedDate) {
        StringBuilder fruitData = new StringBuilder();

        Cursor cursor = sqlDB.rawQuery("SELECT * FROM groupTBL WHERE SUBSTR(gTime, 1, 10) = ?", new String[]{selectedDate});

        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            int number = cursor.getInt(1);
            String time = cursor.getString(2);
            fruitData.append(name).append(" ").append(number).append("개 ").append(time+" ").append("\n");
        }
        cursor.close();

        if (fruitData.length() > 0) {
            // 마지막 쉼표 제거
            fruitData.setLength(fruitData.length() - 2);
        } else {
            fruitData.append("데이터가 없습니다.");
        }

        return fruitData.toString();
    }
}
