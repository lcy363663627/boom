package boom.boom.bianjixinxi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import boom.boom.FontManager.FontManager;
import boom.boom.R;
import boom.boom.api.SysApplication;
import boom.boom.myview.SildingFinishLayout;
import boom.boom.shezhi.Shezhi_activity;
import boom.boom.zhujiemian.Main_activity;

/**
 * Created by 刘成英 on 2015/3/19.
 */
public class Bianjixinxi_activity  extends Activity {
    private List<String> list1 = new ArrayList<String>();
    private List<String> list = new ArrayList<String>();

    private Spinner mySpinner;
    private Spinner MySpinner1;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    private LinearLayout sz_touxiang;
    private Button button;
    private Button confirmButton;
    private Button cancleButton;
    private PopupWindow popupWindow;
    private View popupWindowView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bianjiziliao);
        SildingFinishLayout mSildingFinishLayout = (SildingFinishLayout) findViewById(R.id.sildingFinishLayout);
        mSildingFinishLayout
                .setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {

                    @Override
                    public void onSildingFinish() {
                        Bianjixinxi_activity.this.finish();
                    }
                });

        mSildingFinishLayout.setTouchView(mSildingFinishLayout);
        SysApplication.getInstance().addActivity(this);
        FontManager.changeFonts(FontManager.getContentView(this), this);//字体
        LinearLayout fanhui = (LinearLayout) findViewById(R.id.bianjixinxi_fh);

        list.add("男");
        list.add("女");
        mySpinner = (Spinner) findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
        mySpinner.setAdapter(adapter);
        MySpinner1 = (Spinner) findViewById(R.id.spinner1);


        list1.add("摩羯座");
        list1.add("水瓶座");
        list1.add("双鱼座");
        list1.add("白羊座");
        list1.add("金牛座");
        list1.add("双子座");
        list1.add("巨蟹座");
        list1.add("狮子座");
        list1.add("处女座");
        list1.add("天枰座");
        list1.add("天蝎座");
        list1.add("射手座");
        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list1);
        adapter1.setDropDownViewResource(android.R.layout.select_dialog_item);
        MySpinner1.setAdapter(adapter1);

        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.base_slide_right_out);
            }
        });


        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        popupWindowView = inflater.inflate(R.layout.shezhi_touxiang, null);
        popupWindow = new PopupWindow(popupWindowView, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        sz_touxiang = (LinearLayout) findViewById(R.id.sz_touxiang);
        sz_touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //设置PopupWindow的弹出和消失效果
                popupWindow.setAnimationStyle(R.style.popupAnimation);
                confirmButton = (Button) popupWindowView.findViewById(R.id.sz_touxiang_paishe);

                cancleButton = (Button) popupWindowView.findViewById(R.id.cancleButton);
                button = (Button) popupWindowView.findViewById(R.id.sz_touxiang_bendi);
                popupWindow.showAtLocation(confirmButton, Gravity.CENTER, 0, 0);
                confirmButton.setOnClickListener(Itemclick);
                cancleButton.setOnClickListener(Itemclick);
                button.setOnClickListener(Itemclick);

            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.base_slide_right_out);
    }

    private View.OnClickListener Itemclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (popupWindow.isShowing()){
                popupWindow.setAnimationStyle(R.style.popupAnimation);
                popupWindow.dismiss();


            switch (v.getId()) {
                case R.id.sz_touxiang_paishe:

                    break;
                case R.id.sz_touxiang_bendi:

                    break;
                default:
                    break;
            }
            }


        }
    };
}