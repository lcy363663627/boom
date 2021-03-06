package boom.boom.api;



import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 1eekai on 2015/1/17.
 */

/*
 *  用途：用户注册。
 *  使用示例（伪代码）：
 *
 *  LoginUser user = new LoginUser("likai", "123456");  // 新建对象，传参，用户名为likai，密码为123456
 *
 */

public class LoginUser {
    private static final String USER_REGISTER_URL = "/api/userRegister.php";

    private String user;
    private String pass;
    private boolean reg_ok;
    private List<NameValuePair> userdata;
    private String LastError;
    public LoginUser(String _user, String _pass){
        this.user = _user;
        this.pass = _pass;
  //      this.gotUserDataArray(userd);     注册的时候暂时不要求用户输入个人信息
    }
    public String GetLastError(){
        return this.LastError;
    }
    public void setUserDataArray(List<NameValuePair> userd){
        userdata = userd;
    }

    public boolean AttemptToRegisted(){
        /*
         *  尝试注册。
         */
//        HttpIO io = new HttpIO(Utils.serveraddr + USER_REGISTER_URL);
          Utils.GetBuilder getMethod = new Utils.GetBuilder(Utils.serveraddr + this.USER_REGISTER_URL);
          getMethod.addItem("action","newuser");
          getMethod.addItem("user",this.user);
          getMethod.addItem("passhash", Utils.StrToMD5(this.pass));
          final HttpIO io = new HttpIO(getMethod.toString());
//        List<NameValuePair> post = new ArrayList<NameValuePair>();
//        post.add(new BasicNameValuePair("user", this.user));
//        post.add(new BasicNameValuePair("passhash", Utils.StrToMD5(this.pass)));
//        io.POSTToHTTPServer(post);
          new Thread(new Runnable() {
              @Override
              public void run() {
                  io.GETToHTTPServer();
              }
          }).start();
        while(io.getResultData() == null);
        try {
            JSONObject obj = new JSONObject(io.getResultData());
            String state=obj.getString("state");
            if (state.equalsIgnoreCase("SUCCESS"))    reg_ok = true;
            if (state.equalsIgnoreCase("FAILED")){
                this.LastError = obj.getString("reason");
                reg_ok = false;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reg_ok;
    }
}
