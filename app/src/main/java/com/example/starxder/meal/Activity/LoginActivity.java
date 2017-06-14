package com.example.starxder.meal.Activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.starxder.meal.Bean.Meal;
import com.example.starxder.meal.Bean.User;
import com.example.starxder.meal.Dao.MealDao;
import com.example.starxder.meal.Dao.PrinterDao;
import com.example.starxder.meal.Dao.UserDao;
import com.example.starxder.meal.R;
import com.example.starxder.meal.Utils.CommonUtils;
import com.example.starxder.meal.Utils.GsonUtils;
import com.example.starxder.meal.Utils.OkManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private Button btn_login;
    private OkManager manager;
    private List<Meal> mealList;
    String userName;
    String passWord;
    UserDao userDao;
    PrinterDao printerDao;
    private List<User> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        manager = OkManager.getInstance();
        mealList = new ArrayList<Meal>();
        mUsernameView = (EditText) findViewById(R.id.Username_Edt);
        mPasswordView = (EditText) findViewById(R.id.Password_Edt);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        userDao = new UserDao(LoginActivity.this);
//        printerDao = new PrinterDao(LoginActivity.this);
        try {
            HistoryUserSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void HistoryUserSet() throws SQLException {
        if(userDao.getFirstUser()!=null){
            mUsernameView.setText(userDao.getFirstUser().getLoginName());
            mPasswordView.setText(userDao.getFirstUser().getPassword());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:

                Login();

                break;

        }
    }


    private void Login() {
        userName = mUsernameView.getText().toString();
        passWord = mPasswordView.getText().toString();
        String jsonpath = CommonUtils.BaseUrl + "/web-frame/user/login.do?loginname=" + userName + "&&password=" + passWord;
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
                        userList = GsonUtils.getUserByGson("[" + result + "]");
                        Log.d(TAG, userList.toString());


                        for (User user : userList) {
                            userDao.insert(user);
                        }
                        Toast.makeText(getApplicationContext(), "用户数据同步成功", Toast.LENGTH_SHORT).show();
                        Synchronize();
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
    }



    private void Synchronize() {
        final User user = userDao.queryByLoginName(userName);
        String synchronizePath = CommonUtils.BaseUrl +"web-frame/meal/initByShop.do?shopnum="+ user.getShopnum();
        manager.asyncJsonStringByURL(synchronizePath, new OkManager.Fun1() {
            @Override
            public void onResponse(String response) {
                Log.i("LoginActivity", response);   //获取JSON字符串
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String error = jsonObject.getString("error");
                    String result = jsonObject.getString("result");
                    if (error.equals("")) {
                        mealList = GsonUtils.getMealByGson(result);
                        MealDao mealDao = new MealDao(LoginActivity.this);
                        for(Meal meal:mealList){
                            mealDao.insert(meal);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "数据同步成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("extra",user.getLoginName());
                intent.setClass(LoginActivity.this, MainActivity.class);      //运行另外一个类的活动
                startActivityForResult(intent, 1);
            }

            @Override
            public void onFailure(String result) {
                Toast.makeText(getApplicationContext(), "数据同步失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

