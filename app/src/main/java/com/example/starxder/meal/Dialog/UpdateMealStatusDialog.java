package com.example.starxder.meal.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.starxder.meal.Bean.Meal;
import com.example.starxder.meal.Event.UpdateMealStatusEvent;
import com.example.starxder.meal.R;
import com.example.starxder.meal.Utils.CommonUtils;
import com.example.starxder.meal.Utils.OkManager;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/7/15.
 */

public class UpdateMealStatusDialog extends Dialog {
    TextView tv_backmealname;
    private Meal meal;
    private Button btn_updatesoldout,btn_updateunsoldout;
    OkManager manager;

    public UpdateMealStatusDialog(Context context, Meal meal) {
        super(context, R.style.transparent_dialog);
        this.meal = meal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        setContentView(R.layout.dialog_updatamealstatus);
        // 点击Dialog外部消失
        setCanceledOnTouchOutside(true);

        //初始化界面控件
        initView();
        //初始化界面控件的事件
        initEvent();
    }


    private void initView() {
        tv_backmealname = (TextView)findViewById(R.id.backmealname) ;
        manager = OkManager.getInstance();
    }


    private void initEvent() {
        tv_backmealname.setText(meal.getMealname());
        btn_updatesoldout = (Button)findViewById(R.id.updateSoldout);
        btn_updatesoldout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMealStatus(meal,"true");
                EventBus.getDefault().postSticky(new UpdateMealStatusEvent(true));//更新list并关闭dialog
            }
        });

        btn_updateunsoldout = (Button)findViewById(R.id.updateUnsoldout);
        btn_updateunsoldout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMealStatus(meal,"false");
                EventBus.getDefault().postSticky(new UpdateMealStatusEvent(true));
            }
        });
    }


    private void updateMealStatus(Meal model, String b) {
        String path = CommonUtils.BaseUrl + "web-frame/meal/updateByParam.do?shopnum=" + model.getShopnum() + "&mindex=" + model.getMindex() + "&ifsoldout=" + b + "&mealprice=" + model.getMealprice() + "&delflag=false";


        manager.asyncJsonStringByURL(path, new OkManager.Fun1() {
            @Override
            public void onResponse(String response) {
                Log.i("LoginActivity", response);

                if (response.equals("true")) {
                    Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(String result) {
                Toast.makeText(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
