package app.ij.mlwithtensorflowlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class totalfruit extends AppCompatActivity {
    private ListView listView;
    private TextView totalTextView;
    private SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totalfruit);

        listView = findViewById(R.id.totalListView);
        totalTextView = findViewById(R.id.totalTextView);

        // 데이터베이스 연결
        fruitselection.myDBHelper myHelper = new fruitselection.myDBHelper(this);
        sqlDB = myHelper.getReadableDatabase();

        // 데이터베이스에서 과일 데이터를 가져와서 합계를 계산
        ArrayList<FruitItem> fruitList = getDataFromDatabase();

        // 합계를 계산하고 표시
        int totalNumber = calculateTotal(fruitList);
        totalTextView.setText("총 합계: " + totalNumber + "개");

        // 합계를 제외한 과일 목록을 리스트뷰에 표시
        ArrayList<FruitItem> itemListWithoutTotal = removeTotalItem(fruitList);
        ArrayAdapter<FruitItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemListWithoutTotal);
        listView.setAdapter(adapter);
    }

    private ArrayList<FruitItem> getDataFromDatabase() {
        ArrayList<FruitItem> fruitList = new ArrayList<>();

        // 데이터베이스에서 과일 데이터 조회
        Cursor cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            int number = cursor.getInt(1);
            String time = cursor.getString(2);

            // 조회한 데이터를 리스트에 추가
            fruitList.add(new FruitItem(name, number, time));
        }

        cursor.close();

        // 중복된 이름의 합계를 계산하여 새로운 리스트 생성
        ArrayList<FruitItem> consolidatedList = consolidateFruitList(fruitList);

        return consolidatedList;
    }

    private int calculateTotal(ArrayList<FruitItem> fruitList) {
        int total = 0;
        for (FruitItem item : fruitList) {
            total += item.getNumber();
        }
        return total;
    }

    private ArrayList<FruitItem> consolidateFruitList(ArrayList<FruitItem> originalList) {
        ArrayList<FruitItem> consolidatedList = new ArrayList<>();

        for (FruitItem item : originalList) {
            String name = item.getName();
            int number = item.getNumber();
            boolean isConsolidated = false;

            // 중복된 이름을 찾아서 합산
            for (FruitItem consolidatedItem : consolidatedList) {
                if (consolidatedItem.getName().equalsIgnoreCase(name)) {
                    consolidatedItem.setNumber(consolidatedItem.getNumber() + number);
                    isConsolidated = true;
                    break;
                }
            }

            // 중복된 이름이 아니라면 그대로 추가
            if (!isConsolidated) {
                consolidatedList.add(item);
            }
        }

        return consolidatedList;
    }

    private ArrayList<FruitItem> removeTotalItem(ArrayList<FruitItem> fruitList) {
        ArrayList<FruitItem> itemListWithoutTotal = new ArrayList<>();
        for (FruitItem item : fruitList) {
            if (!item.getName().equalsIgnoreCase("total")) {
                itemListWithoutTotal.add(item);
            }
        }
        return itemListWithoutTotal;
    }
}
