package app.ij.mlwithtensorflowlite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class fruitselection extends AppCompatActivity {

    myDBHelper myHelper;
    EditText edtName, edtNumber;
    Button btnInit, btnInsert, btnSelect;
    SQLiteDatabase sqlDB;
    ListView listView;
    FruitAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruitselection);

        // Intent로 전달된 텍스트 값 가져오기
        Intent intent = getIntent();
        String text = intent.getStringExtra("TEXT");

        edtName = findViewById(R.id.edtName);
        edtNumber = findViewById(R.id.edtNumber);
        btnInit = findViewById(R.id.btnInit);
        btnInsert = findViewById(R.id.btnInsert);
        btnSelect = findViewById(R.id.btnSelect);
        myHelper = new myDBHelper(this);

        // ListView 초기화
        listView = findViewById(R.id.listView);
        adapter = new FruitAdapter(this, R.layout.list_item_fruit, new ArrayList<FruitItem>());
        listView.setAdapter(adapter);

        // TextView 찾기
        EditText editTextText = findViewById(R.id.edtName); // 여기서 'textViewId'는 실제로 사용하는 TextView의 ID로 변경해야 합니다.

        // TextView에 텍스트 설정
        editTextText.setText(text);

        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqlDB, 1, 2);
                sqlDB.close();
                performSelect();
                Toast.makeText(getApplicationContext(), "초기화 완료", Toast.LENGTH_SHORT).show();
            }
        });

        //  총합계 데이터 베이스로 이동
        Button btnTotaldata = (Button) findViewById(R.id.btnTotaldata);
        btnTotaldata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), totalfruit.class);
                startActivity(intent);
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                String name = edtName.getText().toString();
                String number = edtNumber.getText().toString();

                // 공백 문자 제거한 문자열 얻기
                String trimmedName = name.trim();
                String trimmedNumber = number.trim();

                // 입력 필드 중 하나라도 공백이 있는 경우
                if (trimmedName.isEmpty() || trimmedNumber.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "쓰레기와 수량을 모두 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // 현재 시간을 가져옵니다.
                    long now = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                    String currentTime = sdf.format(new Date(now));

                    String insertQuery = "INSERT INTO groupTBL (gName, gNumber, gTime) VALUES ('" + trimmedName + "', " + trimmedNumber + ", '" + currentTime + "');";
                    sqlDB.execSQL(insertQuery);
                    sqlDB.close();

                    edtName.setText("");
                    edtNumber.setText("");

                    // 입력 후 자동으로 조회 실행
                    performSelect();

                    Toast.makeText(getApplicationContext(), "입력됨", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSelect();
            }
        });

        // ListView 항목 롱 클릭 리스너 추가
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(fruitselection.this);
                builder.setTitle("삭제 확인");
                builder.setMessage("이 항목을 삭제하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FruitItem itemToDelete = adapter.getItem(position);

                        // 아이템을 삭제하려면 이름, 수량, 시간이 모두 일치해야 함
                        deleteItemFromDatabase(itemToDelete.getName(), itemToDelete.getNumber(), itemToDelete.getTime());

                        // 롱 클릭된 항목 삭제
                        adapter.removeItem(position);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        });

        // 앱 시작 시 초기 조회 실행
        performSelect();
    }

    // 데이터베이스에서 항목 삭제하는 메서드
    private void deleteItemFromDatabase(String itemName, int itemNumber, String itemTime) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        db.execSQL("DELETE FROM groupTBL WHERE gName = ? AND gNumber = ? AND gTime = ?",
                new String[]{itemName, String.valueOf(itemNumber), itemTime});
        db.close();
    }

    // 자동으로 데이터베이스 조회를 수행하는 메서드 추가
    private void performSelect() {
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null);

        ArrayList<FruitItem> fruitList = new ArrayList<>();

        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            int number = cursor.getInt(1);
            String time = cursor.getString(2);

            fruitList.add(new FruitItem(name, number, time));
        }

        cursor.close();
        sqlDB.close();

        // 어댑터에 데이터 설정
        adapter.clear();
        adapter.addAll(fruitList);
        adapter.notifyDataSetChanged();
    }

    public static class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "groupDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE groupTBL (gName CHAR(20), gNumber INTEGER, gTime DATETIME);");
            // UNIQUE 제약 조건 제거
            db.execSQL("CREATE INDEX IF NOT EXISTS idx_gName ON groupTBL (gName);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS groupTBL");
            onCreate(db);
        }
    }
}
