package com.example.starxder.meal.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.starxder.meal.Bean.User;
import com.example.starxder.meal.Dao.UserDao;
import com.example.starxder.meal.R;
import com.example.starxder.meal.Utils.CommonUtils;
import com.example.starxder.meal.Utils.OkManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/4.
 */

public class KeySetActivity extends Activity {

    Button btn_submit_rePasserword;
    String userName,password,newPassword,newPassword2;
    EditText edt_re_Username,edt_re_Password,edt_new_Password,edt_new_Password2;
    UserDao userDao;
    private OkManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key);
        initView();
        initEvent();

    }

    private void initEvent() {
        btn_submit_rePasserword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = edt_re_Username.getText().toString();
                password = edt_re_Password.getText().toString();
                newPassword = edt_new_Password.getText().toString();
                newPassword2 = edt_new_Password2.getText().toString();


                if(newPassword.equals(newPassword2)){


                    String jsonpath = CommonUtils.BaseUrl + "/web-frame/user/login.do?loginname=" + userName + "&&password=" + password;

                    //登陆同步用户数据
                    manager.asyncJsonStringByURL(jsonpath, new OkManager.Fun1() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("LoginActivity", response);   //获取JSON字符串
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String error = jsonObject.getString("error");
                                String result = jsonObject.getString("result");
                                if (error.equals("")) {
                                    //密码验证成功之后
                                    UpdatePassword();//修改密码请求
                                    User user = userDao.queryByLoginName(userName);
                                    user.setPassword(newPassword);
                                    userDao.insert(user);
                                } else {
                                    Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onFailure(String result) {
                            Toast.makeText(getApplicationContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "两次新密码输入不一致", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initView() {
        userDao = new UserDao(KeySetActivity.this);
        edt_re_Username = (EditText)findViewById(R.id.Username_re_Edt);
        edt_re_Password = (EditText)findViewById(R.id.Password_re_Edt);
        edt_new_Password = (EditText)findViewById(R.id.Password_new_Edt);
        edt_new_Password2 = (EditText)findViewById(R.id.Password_new_Edt2);
        btn_submit_rePasserword = (Button)findViewById(R.id.submit_rePassword);
        manager = OkManager.getInstance();
    }

    private void UpdatePassword() {
        String path =CommonUtils.BaseUrl+"/web-frame/user/update.do?loginname=" + userName + "&&password=" + newPassword;
        //登陆同步用户数据
        manager.asyncJsonStringByURL(path, new OkManager.Fun1() {
            @Override
            public void onResponse(String response) {
                Log.i("LoginActivity", response);   //获取JSON字符串
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String error = jsonObject.getString("error");
                    String result = jsonObject.getString("result");
                    if (error.equals("")) {
                        Toast.makeText(getApplicationContext(), "密码修改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "密码修改失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(String result) {
                Toast.makeText(getApplicationContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            return true;
        }
        return false;
    }
}
