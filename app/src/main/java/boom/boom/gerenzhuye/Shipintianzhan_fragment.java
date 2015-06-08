package boom.boom.gerenzhuye;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import boom.boom.R;
import boom.boom.api.HttpIO;
import boom.boom.api.Static;
import boom.boom.api.Utils;

import boom.boom.myview.XListView;
import boom.boom.shipintianzhanpinglun.Shipintianzhan_pinglun;


/**
 * Created by 刘成英 on 2015/3/12.
 */
public class Shipintianzhan_fragment extends Fragment implements XListView.IXListViewListener
{

    private XListView lv;
    private android.os.Handler mHandler;
    private final static String DATE_FORMAT_STR = "yyyy-MM-dd HH:mm";
    private SimpleAdapter mSimpleAdapter;
    private Button tianjiahaoyou_button;
    private String guestID;
    LinearLayout all;
    boolean animating;
    boolean upordown;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.tianjiahaoyou1, container, false);
        lv= (XListView) v.findViewById(R.id.listView4);
        guestID = getFragmentManager().findFragmentByTag("179521").getArguments().getString("guestID");
        lv.setPullLoadEnable(true);
        mHandler = new android.os.Handler();
        onSyncDataFromServer();
        lv.setPullLoadEnable(true);
        lv.setPullRefreshEnable(true);
        lv.setXListViewListener(this);
        lv.setAdapter(mSimpleAdapter);
        all = ((Gerenzhuye_activity)getActivity()).allLinear;
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem==2)
                {
                    if(!animating) {
                        if(!upordown) {
                            animating = true;
                            AnimationSet animationSet = new AnimationSet(true);
                            TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, -620);
                            translateAnimation.setDuration(1000);
                            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    all.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                            all.getLayoutParams());

                                    params.setMargins(0, -620, 0, 0);

                                    all.clearAnimation();
                                    all.setLayoutParams(params);
                                    all.setVisibility(View.VISIBLE);
                                    upordown = true;
                                    animating = false;
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            animationSet.addAnimation(translateAnimation);
                            animationSet.setFillAfter(true);
                            all.startAnimation(animationSet);
                            Toast.makeText(getActivity(), "5", Toast.LENGTH_SHORT).show();
                        }else {
                            animating = true;
                            AnimationSet animationSet = new AnimationSet(true);
                            TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, 620);
                            translateAnimation.setDuration(1000);
                            translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {
                                    all.setVisibility(View.GONE);
                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                            all.getLayoutParams());

                                    params.setMargins(0, 0, 0, 0);

                                    all.clearAnimation();
                                    all.setLayoutParams(params);
                                    all.setVisibility(View.VISIBLE);
                                    upordown = false;
                                    animating = false;
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            animationSet.addAnimation(translateAnimation);
                            animationSet.setFillAfter(true);
                            all.startAnimation(animationSet);
                            Toast.makeText(getActivity(), "5", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent localIntent = new Intent(getActivity(), Shipintianzhan_pinglun.class);
                startActivity(localIntent);

            }
        });

        return v;
    }
    private void onLoad() {
        lv.stopRefresh();
        lv.stopLoadMore();
        lv.setRefreshTime(new SimpleDateFormat(DATE_FORMAT_STR, Locale.CHINA).format(new Date()));
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                onLoad();
            }
        }, 2000);

    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                onLoad();
            }
        }, 2000);

    }

    public void onSyncDataFromServer(){
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,     Object>>();
        //http://172.24.10.118/api/rank.php?action=getFriendsrank&guest_id=10000
        Utils.GetBuilder get = new Utils.GetBuilder(Utils.serveraddr + "/api/rank.php");
        get.addItem("action", "getFriendsrank");
        get.addItem("guest_id",guestID);
        HttpIO io = new HttpIO(get.toString());
        io.SetCustomSessionID(Static.session_id);
        Gerenzhuye_activity.obj = null;
        int round = 0;
        io.GETToHTTPServer();
        try {
            Gerenzhuye_activity.obj = new JSONObject(io.getResultData());
            JSONObject tmp = Utils.GetSubJSONObject(Gerenzhuye_activity.obj, "response");
            round = Integer.parseInt(tmp.getString("limit"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(int i=0;i<round;i++){
            String title = null, text = null, location = null, assign_time = null, elapsed = null;
            if (Gerenzhuye_activity.obj != null) try {
                JSONObject tmp = Utils.GetSubJSONObject(Gerenzhuye_activity.obj, "line"+i);
                title = tmp.getString("frontname");
                text = "观看次数" + tmp.getString("play_time") + "次";
                location = tmp.getString("location_intent");
                assign_time = tmp.getString("date");
                elapsed = tmp.getString("elapsed_time");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("title",title);
            map.put("count", text);
            map.put("location", location);
            map.put("assign_time", assign_time);
            map.put("elapsed", elapsed);
            listItem.add(map);
        }
        mSimpleAdapter = new SimpleAdapter(getActivity(),listItem,//需要绑定的数据
                R.layout.shipintiaozhan_item,//每一行的布局//动态数组中的数据源的键对应到定义布局的View中
                new String[] {
                        "title", "count" , "location", "assign_time", "elapsed"},
                new int[] {R.id.title,R.id.count,R.id.location,R.id.assign_time,R.id.elapsed}
        );
    }
}