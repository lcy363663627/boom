package boom.boom.xinxizhongxin;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import boom.boom.FontManager.FontManager;
import boom.boom.R;
import boom.boom.api.Msg;
import boom.boom.api.SysApplication;
import boom.boom.ExpandableTextView.ExpandableTextView;
import boom.boom.gerenzhuye.Gerenzhuye_activity;
import boom.boom.guizejieshao.Guizejieshao_activity;
import boom.boom.liuyanbanpinglun.Liuyanban_pinglun;
import boom.boom.myview.SildingFinishLayout;
import boom.boom.myview.XListView;
import boom.boom.shipintianzhanpinglun.Shipintianzhan_pinglun;

/**
 * Created by Lyp on 2015/1/22
 */

public class xinxizhongxin_activity extends Activity implements XListView.IXListViewListener{
    private XListView list;
    private final static String DATE_FORMAT_STR = "yyyy-MM-dd HH:mm";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xinxizhongxin);
        SildingFinishLayout mSildingFinishLayout = (SildingFinishLayout) findViewById(R.id.sildingFinishLayout);
        mSildingFinishLayout
                .setOnSildingFinishListener(new SildingFinishLayout.OnSildingFinishListener() {

                    @Override
                    public void onSildingFinish() {
                        xinxizhongxin_activity.this.finish();
                    }
                });

        mSildingFinishLayout.setTouchView(mSildingFinishLayout);
        SysApplication.getInstance().addActivity(this);
        FontManager.changeFonts(FontManager.getContentView(this), this);//字体
        LinearLayout fh = (LinearLayout) findViewById(R.id.xxzx_fanhui);


//        Button rightSideButton;
        final ArrayList<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();
        list = (XListView) findViewById(R.id.challenge_list);
        list.setPullLoadEnable(true);
        list.setPullRefreshEnable(true);
        list.setXListViewListener(this);
        /*while (true) {
            Msg msg = new Msg();
            Map<String, Object> map = new HashMap<String, Object>();
            if (msg.LastError == 0) {
                //map = msg.GetSimpleMap();
                if (map == null) {
                    break;
                }
                listItem.add(map);
            } else {
                Toast.makeText(getApplicationContext(), "数据错误！", Toast.LENGTH_LONG).show();
                break;
            }
            map.put("label", "1");
            map.put("text", "2");
            listItem.add(map);
        }
        SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem,
                R.layout.msgcenter_listitem,
                new String[]{"label", "text"},
                new int[]{R.id.msg_center_list_label, R.id.msg_center_list_text}
        );*/
        Msg msg = new Msg();
        ArrayList<HashMap<String,Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        arrayList = msg.GetList(false,this);
        final ExpandableAdapter adapter = new ExpandableAdapter(this,arrayList);
        list.setAdapter(adapter);
        // ExpandableAdapter.setListViewHeightBasedOnChildren(list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int type = (int) adapter.list.get(position-1).get("type");
                switch (type) {
                    case 1:
                        break;
                    case 2:
                        Intent intent1 = new Intent();
                        intent1.putExtra("challenge_number",(String)adapter.list.get(position-1).get("identifyDigit"));
                        intent1.putExtra("ifFaqi",1);
                        intent1.setClass(xinxizhongxin_activity.this, Guizejieshao_activity.class);
                        startActivity(intent1);
                        break;
                    case 3:
                        Intent intent2 = new Intent();
                        intent2.putExtra("cl_id",(String)adapter.list.get(position-1).get("challenge_id"));
                        intent2.putExtra("nickname",(String)adapter.list.get(position-1).get("nickname"));
                        intent2.putExtra("cl_name",(String)adapter.list.get(position-1).get("cl_name"));
                        intent2.putExtra("date",(String)adapter.list.get(position-1).get("date"));
                        intent2.putExtra("elapsed",(String)adapter.list.get(position-1).get("elapsed"));
                        intent2.setClass(xinxizhongxin_activity.this, Shipintianzhan_pinglun.class);
                        startActivity(intent2);
                        break;
                    case 4:
                        ExpandableTextView e = (ExpandableTextView) view.findViewById(R.id.expand_text_view);
                        e.onClick(view);
                        break;
                    case 5:
                        Intent intent3 = new Intent();
                        intent3.putExtra("guestID",(String)adapter.list.get(position-1).get("host_id"));
                        intent3.putExtra("type",4);
                        intent3.putExtra("ID",(String)adapter.list.get(position-1).get("ID"));
                        intent3.setClass(xinxizhongxin_activity.this, Gerenzhuye_activity.class);
                        startActivity(intent3);
                        break;
                    case 6:
                        Intent intent4 = new Intent();
                        intent4.putExtra("cl_id",(String)adapter.list.get(position-1).get("challenge_id"));
                        intent4.putExtra("nickname",(String)adapter.list.get(position-1).get("nickname"));
                        intent4.putExtra("cl_name",(String)adapter.list.get(position-1).get("cl_name"));
                        intent4.putExtra("date",(String)adapter.list.get(position-1).get("date"));
                        intent4.putExtra("elapsed",(String)adapter.list.get(position-1).get("elapsed"));
                        intent4.setClass(xinxizhongxin_activity.this, Shipintianzhan_pinglun.class);
                        startActivity(intent4);
                        break;
                    case 7:
                        if((int)adapter.list.get(position-1).get("comment_type")<3){
                            Intent intent5 = new Intent();
                            intent5.putExtra("cl_id",(String)adapter.list.get(position-1).get("challenge_id"));
                           /* intent5.putExtra("nickname",(String)adapter.list.get(position-1).get("nickname"));
                            intent5.putExtra("cl_name",(String)adapter.list.get(position-1).get("text"));
                            intent5.putExtra("date",(String)adapter.list.get(position-1).get("date"));
                            intent5.putExtra("elapsed",(String)adapter.list.get(position-1).get("elapsed"));*/
                            intent5.setClass(xinxizhongxin_activity.this, Shipintianzhan_pinglun.class);
                            startActivity(intent5);
                        }else{
                            Intent intent6 = new Intent();
                            intent6.putExtra("ID",(String)adapter.list.get(position-1).get("ID"));
                           /* intent6.putExtra("nickname",(String)adapter.list.get(position-1).get("nickname"));
                            intent6.putExtra("text",(String)adapter.list.get(position-1).get("text"));
                            intent6.putExtra("date",(String)adapter.list.get(position-1).get("date"));*/
                            intent6.setClass(xinxizhongxin_activity.this, Liuyanban_pinglun.class);
                            startActivity(intent6);
                        }
                        break;
                    case 8:
                        Intent intent7 = new Intent();
                        intent7.putExtra("cl_id",(String)adapter.list.get(position-1).get("cl_id"));
                        intent7.putExtra("ifFaqi",1);
                        intent7.setClass(xinxizhongxin_activity.this,Guizejieshao_activity.class);
                        startActivity(intent7);
                        break;
                    case 9:
                        Intent intent8 = new Intent();
                        intent8.putExtra("guest_id",(String)adapter.list.get(position-1).get("guest_id"));
                        intent8.putExtra("type",(boolean)adapter.list.get(position-1).get("pass")?1:2);
                        intent8.setClass(xinxizhongxin_activity.this,Gerenzhuye_activity.class);
                        startActivity(intent8);
                        break;
                    case 10:
                        break;
                    case 11:
                        Intent intent9 = new Intent();
                        intent9.putExtra("challenge_number",(String)adapter.list.get(position-1).get("identifyDigit"));
                        intent9.putExtra("ifFaqi",1);
                        intent9.setClass(xinxizhongxin_activity.this, Guizejieshao_activity.class);
                        startActivity(intent9);
                        break;
                }
            }
        });
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "删除、回信功能尚未实现，敬请期待。", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        fh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, R.anim.base_slide_right_out);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.base_slide_right_out);
    }
    private void onLoad() {
        list.stopRefresh();
        list.stopLoadMore();
        list.setRefreshTime(new SimpleDateFormat(DATE_FORMAT_STR, Locale.CHINA).format(new Date()));
    }
    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
    class ExpandableAdapter extends BaseAdapter {

    private final Context mContext;
    private final SparseBooleanArray mCollapsedStatus;
    private final SparseIntArray mCollapsedHeight;
    //private final String[] sampleStrings;
    public ArrayList<HashMap<String,Object>> list;
    private ArrayList<View> viewList = new ArrayList<>();
    public ExpandableAdapter(Context context,ArrayList<HashMap<String,Object>> arrlist) {
        mContext  = context;
        mCollapsedStatus = new SparseBooleanArray();
        mCollapsedHeight = new SparseIntArray();
        list = arrlist;
        //sampleStrings = mContext.getResources().getStringArray(R.array.sampleStrings);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

            HashMap<String, Object> hashMap = list.get(position);
            int type = (int) hashMap.get("type");
            switch (type) {
                case 4:
                    view = LayoutInflater.from(mContext).inflate(R.layout.xiaoxixiang, parent, false);
                    ExpandableTextView expandableTextView = (ExpandableTextView) view.findViewById(R.id.expand_text_view);
                    expandableTextView.setText(((String) hashMap.get("content")), mCollapsedStatus,mCollapsedHeight, position);
                    TextView title = (TextView) view.findViewById(R.id.xxx_title);
                    TextView date = (TextView) view.findViewById(R.id.xxx_time);
                    ImageView icon = (ImageView) view.findViewById(R.id.xxx_cursor);
                    title.setText((String) hashMap.get("title"));
                    date.setText((String) hashMap.get("date"));
                    icon.setImageBitmap((Bitmap) hashMap.get("icon"));
                    break;
                case 1:
                case 2:
                case 3:
                case 5:
                case 6:
                case 7:
                case 8:
                default:
                    view = LayoutInflater.from(mContext).inflate(R.layout.xiaoxizhongxin_item2, parent, false);
                    TextView title2 = (TextView) view.findViewById(R.id.xxzx_item_title);
                    TextView text2 = (TextView) view.findViewById(R.id.xxzx_item_text);
                    TextView date2 = (TextView) view.findViewById(R.id.xxzx_item_date);
                    ImageView icon2 = (ImageView) view.findViewById(R.id.xxzx_item_icon);
                    ImageView smallicon = (ImageView) view.findViewById(R.id.xxzx_item_smallicon);
                    title2.setText((String) hashMap.get("title"));
                    text2.setText((String) hashMap.get("content"));
                    date2.setText((String) hashMap.get("date"));
                    icon2.setImageBitmap((Bitmap) hashMap.get("icon"));
                    Bitmap bmSmallicon = (Bitmap) hashMap.get("smallicon");
                    if (bmSmallicon == null) {
                        smallicon.setVisibility(View.INVISIBLE);
                    } else {
                        smallicon.setImageBitmap(bmSmallicon);
                    }

            }
            return view;
        }


}