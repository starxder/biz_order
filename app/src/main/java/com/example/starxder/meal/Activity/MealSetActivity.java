package com.example.starxder.meal.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.starxder.meal.Adapter.MealSettingAdapter;
import com.example.starxder.meal.Bean.Meal;
import com.example.starxder.meal.Bean.Wxorder;
import com.example.starxder.meal.Dao.UserDao;
import com.example.starxder.meal.Dialog.BackMealDialog;
import com.example.starxder.meal.Dialog.UpdateMealStatusDialog;
import com.example.starxder.meal.Event.UpdateMealStatusEvent;
import com.example.starxder.meal.R;
import com.example.starxder.meal.Utils.CommonUtils;
import com.example.starxder.meal.Utils.GsonUtils;
import com.example.starxder.meal.Utils.OkManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
    UpdateMealStatusDialog updateMealStatusDialog;
    int curItem;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acvitivy_mealsetting);
        initView();
        initEvent();
        setOnItemclick();
        EventBus.getDefault().register(this);
    }




    private void initView() {
        mealsettingListView = (ListView)findViewById(R.id.mealsetting_list);
        manager =  OkManager.getInstance();
        userDao = new UserDao(MealSetActivity.this);
        curItem = 1;

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

    private void setOnItemclick() {

        mealsettingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Meal meal =  mealSettinglist.get(i);
                updateMealStatusDialog = new UpdateMealStatusDialog(MealSetActivity.this, meal);
                updateMealStatusDialog.show();
                mealsettingListView.setSelection(curItem);
            }
        });

        mealsettingListView.setOnScrollListener(new AbsListView.OnScrollListener(){
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //滚动时一直回调，直到停止滚动时才停止回调。单击时回调一次。
                //firstVisibleItem：当前能看见的第一个列表项ID（从0开始）
                //visibleItemCount：当前能看见的列表项个数（小半个也算）
                //totalItemCount：列表项共数
                curItem = firstVisibleItem;
            }
            @Override
            public void onScrollStateChanged(AbsListView view , int scrollState){
                //正在滚动时回调，回调2-3次，手指没抛则回调2次。scrollState = 2的这次不回调
                //回调顺序如下
                //第1次：scrollState = SCROLL_STATE_TOUCH_SCROLL(1) 正在滚动
                //第2次：scrollState = SCROLL_STATE_FLING(2) 手指做了抛的动作（手指离开屏幕前，用力滑了一下）
                //第3次：scrollState = SCROLL_STATE_IDLE(0) 停止滚动
            }
        });
    }







    private void requestjson(String s) {

        String path = CommonUtils.BaseUrl + "web-frame/meal/init.do?shopnum=" +s;

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
                mealsettingListView.setSelection(curItem);
            }

            @Override
            public void onFailure(String result) {
                Toast.makeText(MealSetActivity.this, "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(UpdateMealStatusEvent updateMealStatusEvent) {
        if (updateMealStatusEvent != null) {
            if (updateMealStatusEvent.getFlag()) {
                updateMealStatusDialog.cancel();

                CountDownTimer cdt = new CountDownTimer(300, 300) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }
                    @Override
                    public void onFinish() {
                        initEvent();//放到定时器中等待300ms ，dialog中请求完毕，再去发送请求
                    }
                };

                cdt.start();




            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
