package boom.boom.shezhi;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.os.Vibrator;

import boom.boom.FontManager.FontManager;
import boom.boom.R;
import boom.boom.api.SysApplication;
import boom.boom.bangzhuyufankui.Bangzhuyufankui_activity;
import boom.boom.bianjixinxi.Bianjixinxi_activity;
import boom.boom.guanyuwomen.Guanyuwomen_activity;
import boom.boom.mimaxiugai.Mimaxiugai_activity;
import boom.boom.myview.SildingFinishLayout;
import boom.boom.zhujiemian.Main_activity;

/**
 * Created by 刘成英 on 2015/1/15.
 */

public class Shezhi_activity extends Activity{
    private LinearLayout shezhifanhui;
    private Button tuichuzhanghu;
    private Vibrator vibrator;
    private LinearLayout sz_jinzijikejian;
    private LinearLayout sz_jinhaoyoukejian;
    private LinearLayout sz_suoyourenkejian;
    private ImageView sz_img1;
    private ImageView sz_img2;
    private ImageView sz_img3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.shezhi);
        SildingFinishLayout mSildingFinishLayout = (SildingFinishLayout) findViewById(R.id.sildingFinishLayout);
        mSildingFinishLayout
                .setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {

                    @Override
                    public void onSildingFinish() {
                        Shezhi_activity.this.finish();
                    }
                });

        mSildingFinishLayout.setTouchView(mSildingFinishLayout);


        SysApplication.getInstance().addActivity(this);
        FontManager.changeFonts(FontManager.getContentView(this), this);//字体
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        shezhifanhui = (LinearLayout) findViewById(R.id.shezhifanhui);
        sz_jinzijikejian = (LinearLayout) findViewById(R.id.sz_jinzijikejian);
        sz_jinhaoyoukejian = (LinearLayout) findViewById(R.id.sz_jinhaoyoukejian);
        sz_suoyourenkejian = (LinearLayout) findViewById(R.id.sz_suoyourenkejian);
        sz_img1 = (ImageView) findViewById(R.id.sz_img1);
        sz_img2 = (ImageView) findViewById(R.id.sz_img2);
        sz_img3 = (ImageView) findViewById(R.id.sz_img3);
        sz_img();

        LinearLayout mmxg = (LinearLayout)findViewById(R.id.sz_mmxg);
        mmxg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Shezhi_activity.this, Mimaxiugai_activity.class);
                startActivity(intent);
            }
        });
        TextView xggezl1 = (TextView)findViewById(R.id.sz_xggrzl1);
        xggezl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Shezhi_activity.this, Bianjixinxi_activity.class);
                startActivity(intent);
            }
        });
        LinearLayout xggezl = (LinearLayout)findViewById(R.id.sz_xggrzl);
        xggezl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Shezhi_activity.this, Bianjixinxi_activity.class);
                startActivity(intent);
            }
        });
        LinearLayout sz_bzyfk = (LinearLayout) findViewById(R.id.sz_bbyfk);
        sz_bzyfk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Shezhi_activity.this, Bangzhuyufankui_activity.class);
                startActivity(intent);
            }
        });
        LinearLayout sz_gywm = (LinearLayout) findViewById(R.id.sz_gywm);
        sz_gywm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Shezhi_activity.this, Guanyuwomen_activity.class);
                startActivity(intent);
            }
        });
        shezhifanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.base_slide_right_out);
            }
        });
        tuichuzhanghu = (Button)findViewById(R.id.tuichuzhanghu);
        tuichuzhanghu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SysApplication.getInstance().exit();

            }
        });
        final ToggleButton mTogBtn = (ToggleButton) findViewById(R.id.sz_toggle1);
        final ToggleButton mTogBtn1 = (ToggleButton) findViewById(R.id.sz_toggle2);
        final ToggleButton mTogBtn2 = (ToggleButton) findViewById(R.id.sz_toggle3);// 获取到控件

        mTogBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {

                    vibrator.cancel();
                 }else{
                    vibrator.vibrate(new long[]{10, 100, 0, 0, 0}, -1);

                }
            }
        });
        mTogBtn1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    //isChecken = ture   条件下的操作
                }else{
                    //isChecken = ture
                }
            }
        });
        mTogBtn2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    //isChecken = ture   条件下的操作
                }else{
                    //isChecken = ture
                }
            }
        });

    }
    private void sz_img(){
        sz_jinzijikejian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sz_img1.setVisibility(View.VISIBLE);
                sz_img2.setVisibility(View.INVISIBLE);
                sz_img3.setVisibility(View.INVISIBLE);
            }
        });
        sz_jinhaoyoukejian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sz_img2.setVisibility(View.VISIBLE);
                sz_img1.setVisibility(View.INVISIBLE);
                sz_img3.setVisibility(View.INVISIBLE);
            }
        });
        sz_suoyourenkejian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sz_img3.setVisibility(View.VISIBLE);
                sz_img2.setVisibility(View.INVISIBLE);
                sz_img1.setVisibility(View.INVISIBLE);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.base_slide_right_out);
    }
}
