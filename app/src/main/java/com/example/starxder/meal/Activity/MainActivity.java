package com.example.starxder.meal.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.starxder.meal.Bean.User;
import com.example.starxder.meal.Dao.UserDao;
import com.example.starxder.meal.Event.FlagEvent;
import com.example.starxder.meal.Event.UserEvent;
import com.example.starxder.meal.Fragment.OrderDetailFragment;
import com.example.starxder.meal.Fragment.PayFragment;
import com.example.starxder.meal.Fragment.TakeoutFragment;
import com.example.starxder.meal.Fragment.UnpayFragment;
import com.example.starxder.meal.R;
import com.example.starxder.meal.Utils.CommonUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity implements OnClickListener {

    public RelativeLayout unpay_tab;
    public RelativeLayout pay_tab;
    public RelativeLayout takeout_tab;
    public RelativeLayout btn_setting;
    public OrderDetailFragment orderDetailFragment;
    public UnpayFragment unpayFragment;
    public PayFragment payFragment;
    public TakeoutFragment takeoutFragment;
    FragmentManager fm;
    private ImageView head_pic;
    String TAG = "MainActivity", loginName, headpic_url, tableName;
    ImageView image_paid, image_unpay,image_takeout;
    User user;
    UserDao userDao;
    private static final int SUCCESS = 1;
    private static final int FALL = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initViews();
        initEvents();
        initUser();
        setContent(0);
        image_paid.setImageResource(R.mipmap.icon_paid);
        image_unpay.setImageResource(R.mipmap.icon_unpay_unpick);
        image_takeout.setImageResource(R.mipmap.icon_takeout_unpick);
        //向其他界面发送用户信息
        EventBus.getDefault().postSticky(new UserEvent(user, "1"));
        //刷新数据
        EventBus.getDefault().postSticky(new FlagEvent(true));
    }

    private void initUser() {
        Intent intent = getIntent();
        loginName = intent.getStringExtra("extra");
        user = userDao.queryByLoginName(loginName);

        tableName = user.getOrdertable();
        Log.i(TAG, "onCreate: " + tableName);

        headpic_url = user.getHeadpicurl();


        Log.i(TAG, "initUser: " + CommonUtils.BaseUrl + headpic_url);
        //加载用户头像图片
        //1.创建一个okhttpclient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建Request.Builder对象，设置参数，请求方式如果是Get，就不用设置，默认就是Get
        Request request = new Request.Builder()
                .url(CommonUtils.BaseUrl + headpic_url)
                .build();
        //3.创建一个Call对象，参数是request对象，发送请求
        Call call = okHttpClient.newCall(request);
        //4.异步请求，请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到从网上获取资源，转换成我们想要的类型
                byte[] Picture_bt = response.body().bytes();
                //通过handler更新UI
                Message message = headpichandler.obtainMessage();
                message.obj = Picture_bt;
                message.what = SUCCESS;
                headpichandler.sendMessage(message);
            }

        });
    }

    private void initViews() {
        unpay_tab = (RelativeLayout) findViewById(R.id.btn_unpay);
        pay_tab = (RelativeLayout) findViewById(R.id.btn_paid);
        takeout_tab = (RelativeLayout)findViewById(R.id.btn_takeout);
        btn_setting = (RelativeLayout)findViewById(R.id.btn_setting);
        head_pic = (ImageView) findViewById(R.id.head_pic);
        image_paid = (ImageView) findViewById(R.id.image_btn_paid);
        image_unpay = (ImageView) findViewById(R.id.image_btn_unpay);
        image_takeout = (ImageView)findViewById(R.id.image_btn_takeout);


        fm = getFragmentManager();
        unpayFragment = new UnpayFragment();
        payFragment = new PayFragment();
        takeoutFragment = new TakeoutFragment();
        orderDetailFragment = new OrderDetailFragment();


        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.orderdetail_fragment, orderDetailFragment);
        transaction.add(R.id.orderlist_fragment, payFragment);
        transaction.add(R.id.orderlist_fragment, unpayFragment);
        transaction.add(R.id.orderlist_fragment, takeoutFragment);
        transaction.commit();

        userDao = new UserDao(MainActivity.this);
    }

    private void initEvents() {
        unpay_tab.setOnClickListener(this);
        pay_tab.setOnClickListener(this);
        takeout_tab.setOnClickListener(this);
        btn_setting.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_paid:
                setContent(0);
                image_paid.setImageResource(R.mipmap.icon_paid);
                image_unpay.setImageResource(R.mipmap.icon_unpay_unpick);
                image_takeout.setImageResource(R.mipmap.icon_takeout_unpick);
                break;

            case R.id.btn_unpay:
                setContent(1);
                image_paid.setImageResource(R.mipmap.icon_paid_unpick);
                image_unpay.setImageResource(R.mipmap.icon_unpay);
                image_takeout.setImageResource(R.mipmap.icon_takeout_unpick);
                break;

            case R.id.btn_takeout:
                setContent(2);
                image_paid.setImageResource(R.mipmap.icon_paid_unpick);
                image_unpay.setImageResource(R.mipmap.icon_unpay_unpick);
                image_takeout.setImageResource(R.mipmap.icon_takeout_picked);
                break;

            case R.id.btn_setting:
                Intent intent = new Intent();
                intent.setClass( MainActivity.this, SettingActivity.class);      //运行另外一个类的活动
                startActivityForResult(intent, 1);
                break;


            default:

        }
    }

    private void setContent(int item) {
        FragmentTransaction transaction = fm.beginTransaction();

        switch (item) {
            case 0:
                transaction.hide(unpayFragment).hide(takeoutFragment).show(payFragment).commit();
                EventBus.getDefault().postSticky(new FlagEvent(true));
                break;
            case 1:
                transaction.hide(payFragment).hide(takeoutFragment).show(unpayFragment).commit();
                EventBus.getDefault().postSticky(new FlagEvent(true));
                break;
            case 2:
                transaction.hide(unpayFragment).hide(payFragment).show(takeoutFragment).commit();
                EventBus.getDefault().postSticky(new FlagEvent(true));
                break;
            default:
                break;
        }
    }

    private Handler headpichandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //加载网络成功进行UI的更新,处理得到的图片资源
                case SUCCESS:
                    //通过message，拿到字节数组
                    byte[] Picture = (byte[]) msg.obj;
                    //使用BitmapFactory工厂，把字节数组转化为bitmap
                    Bitmap bitmap = BitmapFactory.decodeByteArray(Picture, 0, Picture.length);
                    //通过imageview，设置图片
                    head_pic.setImageBitmap(bitmap);

                    break;
                //当加载网络失败执行的逻辑代码
                case FALL:
                    Toast.makeText(MainActivity.this, "网络出现了问题", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
    }
}
