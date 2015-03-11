package boom.boom.woyaotiaozhan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import boom.boom.R;
import boom.boom.api.Challenge;
import boom.boom.api.Msg;
import boom.boom.guizejieshao.Guizejieshao_activity;

/**
 * Created by Lyp on 2015/1/22
 */

public class woyaotiaozhan_activity extends Activity {
      private ListView list;
      protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.woyaotiaozhan);
        Button fh=(Button)findViewById(R.id.wytz_fanhui);
//        Button rightSideButton;
        final ArrayList<Map<String, Object>> listItem = new ArrayList<>();
        list = (ListView)findViewById(R.id.challenge1_list);
        while (true) {
            Challenge challenge = new Challenge();
            Map<String, Object> map = new HashMap<String, Object>();
            map = challenge.GetSimpleMap();
            if (map == null) {
                break;
            }
            listItem.add(map);
        }
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,
                  R.layout.challenge_listitem,
                  new String[] {"label", "text"},
                  new int[] {R.id.layout_list_label, R.id.layout_list_text}
        );
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("challenge_number", position);
                intent.setClass(woyaotiaozhan_activity.this, Guizejieshao_activity.class);
                startActivity(intent);
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "删除、回信功能尚未实现，敬请期待。", Toast.LENGTH_SHORT);
                return false;
            }
        });
        fh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}