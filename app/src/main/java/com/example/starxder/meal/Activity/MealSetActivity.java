package com.example.starxder.meal.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.starxder.meal.Adapter.MealSettingAdapter;
import com.example.starxder.meal.Bean.Meal;
import com.example.starxder.meal.Bean.Wxorder;
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

/**
 * Created by Administrator on 2017/7/6.
 */

public class MealSetActivity extends Activity{
    private ListView mealsettingListView;
    List<Meal> mealSettinglist = new ArrayList<Meal>();
    MealSettingAdapter mealsettingAdapter;
    OkManager manager;
    UserDao userDao;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acvitivy_mealsetting);
        initView();
        initEvent();
    }



    private void initView() {
        mealsettingListView = (ListView)findViewById(R.id.mealsetting_list);
        manager =  OkManager.getInstance();
        userDao = new UserDao(MealSetActivity.this);

    }

    private void initEvent() {
        String s = "";
        try {
            s = userDao.queryForAll().get(0).getShopnum();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        requestjson(s);


    }

    private void requestjson(String s) {

        String path = CommonUtils.BaseUrl + "web-frame/meal/init.do?shopnum=" +s;
        //登陆同步用户数据
        manager.asyncJsonStringByURL(path, new OkManager.Fun1() {
            @Override
            public void onResponse(String response) {
                Log.i("LoginActivity", response);   //获取JSON字符串
                //                    JSONObject jsonObject = new JSONObject(response);
//                    String error = jsonObject.getString("error");
//                    String result = jsonObject.getString("result");
                if (!response.equals("")) {
                    mealSettinglist = GsonUtils.getMealByGson(response);
                } else {

                }
                mealsettingAdapter = new MealSettingAdapter(mealSettinglist,MealSetActivity.this);
                mealsettingListView.setAdapter(mealsettingAdapter);
            }

            @Override
            public void onFailure(String result) {
                Toast.makeText(MealSetActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
