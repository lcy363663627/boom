package boom.boom.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import boom.boom.R;

/**
 * Created by Administrator on 2015/5/20.
 */
public class FriendList {
    public String ServerErr;
    ///api/newfriend.php?action=query
    private final String USER_LOGIN_URL = Utils.serveraddr + "api/newfriend.php";

    public ArrayList<HashMap<String, Object>> GetFriendList() {
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();//*在数组中存放数据*//*
        try {
            Utils.GetBuilder get = new Utils.GetBuilder(USER_LOGIN_URL);
            get.addItem("action", "query");
            String url_request = get.toString();
            HttpIO io = new HttpIO(url_request);
            io.SetCustomSessionID(Static.session_id);
            io.GETToHTTPServer();
            if (io.LastError == 0) {
                String httpResult = io.getResultData();
                //JSONArray jsonArray = JSONArray.fromObject(str);
                JSONObject obj = new JSONObject(httpResult);
                JSONObject response = Utils.GetSubJSONObject(obj,"response");

                String status = response.getString("state");
                if (status.equalsIgnoreCase("FAILED"))
                {
                    ServerErr = "获取好友列表失败!";
                    return null;
                }else if (status.equalsIgnoreCase("SUCCESS")) {
                    int limit = response.getInt("limit");
                    for(int i=0;i<limit;i++)
                    {
                        JSONObject tmp = Utils.GetSubJSONObject(obj,""+i);
                        String nickname = tmp.getString("guest_nickname");
                        String avatar = tmp.getString("guest_avatar");
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        //map.put("avatar",avatar);
                        map.put("avatar", avatar);
                        map.put("nickname",nickname);
                        listItem.add(map);
                    }
                    return listItem;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}