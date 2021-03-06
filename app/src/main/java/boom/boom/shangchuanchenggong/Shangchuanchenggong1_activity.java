package boom.boom.shangchuanchenggong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import boom.boom.FontManager.FontManager;
import boom.boom.R;
import boom.boom.api.SysApplication;
import boom.boom.tianzhan.Tiaozhan_activity;
import boom.boom.tongxunlu.tongxunlu_activity;

/**
 * Created by 刘成英 on 2015/3/12.
 */
public class Shangchuanchenggong1_activity extends Activity {
    private RelativeLayout yaoqinghaoyoutianzhan;
    private Button onRecordFinished;
    private RelativeLayout sccg_fh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.shangchuanchenggong1);
        onRecordFinished = (Button) findViewById(R.id.onRecordFinished);

        SysApplication.getInstance().addActivity(this);
        FontManager.changeFonts(FontManager.getContentView(this), this);//字体
        yaoqinghaoyoutianzhan = (RelativeLayout) findViewById(R.id.yaoqinghaoyoutianzhan);
        sccg_fh = (RelativeLayout) findViewById(R.id.sccg_fh);
        yaoqinghaoyoutianzhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("challenge_id",getIntent().getSerializableExtra("challenge_id"));
                intent.setClass(Shangchuanchenggong1_activity.this, tongxunlu_activity.class);
                startActivity(intent);
            }
        });
        sccg_fh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        onRecordFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent();
                intent.setClass(Shangchuanchenggong1_activity.this, Tiaozhan_activity.class);
                startActivity(intent);*/
                Shangchuanchenggong1_activity.this.finish();
            }
        });
    }
}
