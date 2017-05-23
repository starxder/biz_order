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
import com.example.starxder.meal.Dao.MealDao;
import com.example.starxder.meal.R;
import com.example.starxder.meal.Utils.CommonUtils;
import com.example.starxder.meal.Utils.GsonUtils;
import com.example.starxder.meal.Utils.OkManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
        String userName = mUsernameView.getText().toString();
        String passWord = mPasswordView.getText().toString();
        String jsonpath = CommonUtils.BaseUrl + "/web-frame/user/login.do?loginname=" + userName + "&&password=" + passWord;

        manager.asyncJsonStringByURL(jsonpath, new OkManager.Fun1() {
            @Override
            public void onFailure(String result) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onResponse(String result) {
                Log.i("LoginActivity", result);   //获取JSON字符串
                if (result.equals("true")) {
                    Synchronize();

                } else {
                    Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void Synchronize() {
        String synchronizePath = CommonUtils.BaseUrl +"web-frame/meal/initByShop.do?shopnum=1";
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
                Toast.makeText(getApplicationContext(), "数据同步成功,", Toast.LENGTH_SHORT).show();
                Intent serverIntent = new Intent(LoginActivity.this, MainActivity.class);      //运行另外一个类的活动
                startActivityForResult(serverIntent, 1);
            }

            @Override
            public void onFailure(String result) {
                Toast.makeText(getApplicationContext(), "数据同步失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

