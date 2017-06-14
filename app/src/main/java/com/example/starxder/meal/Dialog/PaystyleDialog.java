package com.example.starxder.meal.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.starxder.meal.Activity.MainActivity;
import com.example.starxder.meal.Bean.Wxorder;
import com.example.starxder.meal.Event.FlagEvent;
import com.example.starxder.meal.R;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/13.
 */

public class PaystyleDialog extends Dialog {

    RelativeLayout btn_alipay,btn_card,btn_cash;
    Wxorder wxorder;
    Context context;

    public PaystyleDialog(Context context, Wxorder wxorder) {
        super(context, R.style.Theme_AppCompat_Dialog);
        this.wxorder = wxorder;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        setContentView(R.layout.dialog_paystyle);
        // 点击Dialog外部消失
        setCanceledOnTouchOutside(true);

        //初始化界面控件
        initView();
        //初始化界面控件的事件
        initEvent();

    }



    private void initView() {
        btn_alipay = (RelativeLayout)findViewById(R.id.btn_alipay);
        btn_cash = (RelativeLayout)findViewById(R.id.btn_cash);
        btn_card = (RelativeLayout)findViewById(R.id.btn_card);
    }
    private void initEvent() {
        btn_alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request("http://www.jiatuokeji.com/web-frame/wxorder/updateByCode.do?outTradeNo="+wxorder.getOutTradeNo()+"&payStyle=ali&ifpay=true");
            }
        });
        btn_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request("http://www.jiatuokeji.com/web-frame/wxorder/updateByCode.do?outTradeNo="+wxorder.getOutTradeNo()+"&payStyle=cash&ifpay=true");
            }
        });
        btn_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request("http://www.jiatuokeji.com/web-frame/wxorder/updateByCode.do?outTradeNo="+wxorder.getOutTradeNo()+"&payStyle=card&ifpay=true");
            }
        });
    }

    private void request(String path) {

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60 * 1000, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(60 * 1000, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(60 * 1000, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        //使用Request.Builder来创建请求对象
        Request.Builder builder = new Request.Builder();
        //指定使用GET请求,并且指定要请求的地址
        Request request = builder.get().url(path).build();
        //将请求加入请求队列,将请求封装成Call对象
        Call call = client.newCall(request);
        //使用异步的方式来得到请求的响应并且处理
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                EventBus.getDefault().postSticky(new FlagEvent(true));

            }
        });
    }
}
