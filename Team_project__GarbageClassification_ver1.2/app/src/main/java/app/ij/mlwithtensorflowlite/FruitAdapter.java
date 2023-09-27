package app.ij.mlwithtensorflowlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FruitAdapter extends ArrayAdapter<FruitItem> {

    private int resourceLayout;

    //  부모 클래스인 ArrayAdapter의 생성자를 호출하여 데이터와 레이아웃 리소스 ID를 설정합니다.
    public FruitAdapter(Context context, int resource, List<FruitItem> items) {
        super(context, resource, items);
        resourceLayout = resource;
    }

    // 리스트 항목 삭제 메서드 추가
    public void removeItem(int position) {
        if (position >= 0 && position < getCount()) {
            remove(getItem(position));
        }
    }

    //  getView 메서드는 각 항목을 화면에 표시하기 위한 메서드입니다. 이 메서드는 리스트뷰나 스피너 등에서 각 항목을 그리는 역할을 합니다.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(resourceLayout, null);
        }

        FruitItem item = getItem(position);

        if (item != null) {
            //   데이터 바인딩은 데이터와 UI 요소를 직접 연결하는 기술
            TextView nameView = v.findViewById(R.id.nameTextView);
            TextView numberView = v.findViewById(R.id.numberTextView);
            TextView timeView = v.findViewById(R.id.timeTextView);

            if (nameView != null) {
                nameView.setText(item.getName());
            }

            if (numberView != null) {
                numberView.setText(String.valueOf(item.getNumber()));
            }

            if (timeView != null) {
                timeView.setText(item.getTime());
            }
        }

        return v;
    }
}
