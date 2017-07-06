package com.example.starxder.meal.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.starxder.meal.Activity.LoginActivity;

/**
 * Created by Administrator on 2017/6/18.
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO start your own service
        Toast.makeText(context, "订单系统开启", Toast.LENGTH_LONG).show();
        intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //这一行非常关键，如果不加这个，就不能在这实现Activity的启动。
        context.startActivity(intent);
    }

}