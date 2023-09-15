package com.example.listviewtest3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd;
    private ListView list;
    private final ArrayList<String> midList = new ArrayList<String>();
    private ArrayAdapter<String> adapter; // String을 사용하여 어댑터 초기화

    //private ArrayAdapter<CharSequence> adapter;
    private EditText edtItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 데이터 삽입
        midList.add("사과");
        midList.add("배");
        midList.add("딸기");
        midList.add("포도");
        midList.add("수박");

        list = findViewById(R.id.listView1);
        edtItem = findViewById(R.id.edtitem);
        btnAdd = findViewById(R.id.btnAdd);

        // 어댑터를 String을 사용하여 초기화
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, midList);

//        adapter = ArrayAdapter.createFromResource(this, R.array.ff,
//                android.R.layout.simple_list_item_1);

        list.setAdapter(adapter); // ListView에 어댑터 설정

        // 버튼 이벤트
        btnAdd.setOnClickListener(view -> {
            midList.add(edtItem.getText().toString());
            adapter.notifyDataSetChanged(); // 데이터 변경 후 갱신
        });

        // ListView에서 항목을 오랫동안 클릭하면 삭제되도록 설정
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {


                midList.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });


    }
}
