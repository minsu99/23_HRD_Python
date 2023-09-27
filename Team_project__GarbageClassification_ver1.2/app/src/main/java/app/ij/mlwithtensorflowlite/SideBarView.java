package app.ij.mlwithtensorflowlite;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class SideBarView extends RelativeLayout implements View.OnClickListener{

    /** 메뉴버튼 클릭 이벤트 리스너 */
    public EventListener listener;

    public void setEventListener(EventListener l) {
        listener = l;
    }

    /** 메뉴버튼 클릭 이벤트 리스너 인터페이스 */
    public interface EventListener {

        // 닫기 버튼 클릭 이벤트
        void btnCancel();
        void btnLevel1();
        void btnLevel2();
        void btnLevel3();
    }

    public SideBarView(Context context) {
        this(context, null);
        init();
    }

    public SideBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init(){

        LayoutInflater.from(getContext()).inflate(R.layout.sidbar, this, true);

        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.btn_side_level_1).setOnClickListener(this);
        findViewById(R.id.btn_side_level_2).setOnClickListener(this);
        findViewById(R.id.btn_side_level_3).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()){

            case R.id.btn_cancel :

                listener.btnCancel();
                break;

            case R.id.btn_side_level_1 :

                listener.btnLevel1();
                break;
            case R.id.btn_side_level_2 :

                listener.btnLevel2();
                break;
            case R.id.btn_side_level_3 :

                listener.btnLevel3();
                break;

            default:

                break;
        }
    }
}