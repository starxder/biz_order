package com.example.starxder.meal.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.starxder.meal.Bean.Wxorder;
import com.example.starxder.meal.Dao.MealDao;
import com.example.starxder.meal.Event.BackmealEvent;
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
 * Created by Administrator on 2017/7/4.
 */

public class BackMealDialog extends Dialog implements View.OnClickListener {

    Wxorder wxorder;
    Context context;
    String backMealnum, mealid, backfee;
    TextView mealname;
    private int mealID,mealNUM,backnum;
    Button btn_backnumplus, btn_backnumsub, btn_backsubmit;
    EditText edt_backnum;
    MealDao mealDao;

    public BackMealDialog(Context context, Wxorder wxorder, int i,int j) {
        super(context, R.style.transparent_dialog);
        this.wxorder = wxorder;
        this.context = context;
        this.mealID = i;
        this.mealNUM = j;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置
        setContentView(R.layout.dialog_backmeal);
        // 点击Dialog外部消失
        setCanceledOnTouchOutside(true);

        //初始化界面控件
        initView();
        //初始化界面控件的事件
        initEvent();
    }


    private void initView() {
        mealDao = new MealDao(getContext());
        mealname = (TextView) findViewById(R.id.backmealname);
        btn_backnumplus = (Button) findViewById(R.id.btn_backnumplus);
        btn_backnumsub = (Button) findViewById(R.id.btn_backnumsub);
        btn_backsubmit = (Button) findViewById(R.id.btn_backsubmit);
        edt_backnum = (EditText) findViewById(R.id.edt_backnum);
    }

    private void initEvent() {
        btn_backnumplus.setOnClickListener(this);
        btn_backnumsub.setOnClickListener(this);
        btn_backsubmit.setOnClickListener(this);
        backnum = 1;
        edt_backnum.setText(backnum+"");
        mealid = mealID + "";
        mealname.setText(mealDao.queryBymealid(mealid).getMealname());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_backnumplus:
                backnum = backnum+1;
                if(backnum > mealNUM ){
                    backnum = mealNUM;
                }
                edt_backnum.setText(backnum + "");
                break;

            case R.id.btn_backnumsub:
                backnum = backnum-1;
                if(backnum <= 0 ){
                    backnum = 1;
                }
                edt_backnum.setText(backnum + "");
                break;

            case R.id.btn_backsubmit:
                backMealnum = String.valueOf(edt_backnum.getText());
                String backDetail = "";
                if (wxorder.getBackDetail() != null&&wxorder.getBackDetail()!= "") {
                    backfee = (Float.valueOf(wxorder.getBackFee())+ Float.valueOf(mealDao.queryBymealid(mealid).getMealprice()) * Float.valueOf(backMealnum)) + "";
                    backDetail = wxorder.getBackDetail() + ";" + mealid + "," + backMealnum;
                } else {
                    backDetail = mealid + "," + backMealnum;
                    backfee = Float.valueOf(mealDao.queryBymealid(mealid).getMealprice()) * Float.valueOf(backMealnum) + "";

                }
//                http://www.jiatuokeji.com/web-frame/wxorder/updateBackByCode.do?outTradeNo=20170613104257114&backFee=100&backDetail=53,1
                request("http://www.jiatuokeji.com/web-frame/wxorder/updateBackByCode.do?outTradeNo=" + wxorder.getOutTradeNo() + "&backFee=" + backfee + "&backDetail=" + backDetail);
                break;
        }
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
                EventBus.getDefault().postSticky(new BackmealEvent(true,wxorder.getOutTradeNo()));
                EventBus.getDefault().postSticky(new FlagEvent(true));

            }
        });
    }
}
