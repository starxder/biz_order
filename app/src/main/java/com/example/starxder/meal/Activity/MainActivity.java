package com.example.starxder.meal.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.starxder.meal.Bean.User;
import com.example.starxder.meal.Bean.Wxorder;
import com.example.starxder.meal.Dao.UserDao;
import com.example.starxder.meal.Event.FlagEvent;
import com.example.starxder.meal.Event.UserEvent;
import com.example.starxder.meal.Fragment.OrderDetailFragment;
import com.example.starxder.meal.Fragment.PayFragment;
import com.example.starxder.meal.Fragment.TakeoutFragment;
import com.example.starxder.meal.Fragment.UnpayFragment;
import com.example.starxder.meal.R;
import com.example.starxder.meal.Utils.CommonUtils;
import com.example.starxder.meal.Utils.GsonUtils;
import com.example.starxder.meal.Utils.OkManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity implements OnClickListener {

    public RelativeLayout unpay_tab;
    public RelativeLayout pay_tab;
    public RelativeLayout takeout_tab;
    public RelativeLayout refresh_tab;
    public RelativeLayout btn_setting;

    public OrderDetailFragment orderDetailFragment;
    public UnpayFragment unpayFragment;
    public PayFragment payFragment;
    public TakeoutFragment takeoutFragment;
    FragmentManager fm;
    private ImageView head_pic;
    String TAG = "MainActivity", loginName, headpic_url, tableName;
    ImageView image_paid, image_unpay, image_takeout;
    ImageView redpoint_paid, redpoint_unpay, redpoint_takeout;
    User user;
    UserDao userDao;
    OkManager manager;
    private static final int SUCCESS = 1;
    private static final int FALL = 2;
    private List<Wxorder> unDealList;

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

        EventBus.getDefault().register(this);//注册EventBus

        //向其他界面发送用户信息
        EventBus.getDefault().postSticky(new UserEvent(user, "1"));
        //刷新数据
        EventBus.getDefault().postSticky(new FlagEvent(true));
        cdt.start();
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
        takeout_tab = (RelativeLayout) findViewById(R.id.btn_takeout);
        btn_setting = (RelativeLayout) findViewById(R.id.btn_more);
        refresh_tab = (RelativeLayout) findViewById(R.id.btn_refresh);
        head_pic = (ImageView) findViewById(R.id.head_pic);
        image_paid = (ImageView) findViewById(R.id.image_btn_paid);
        image_unpay = (ImageView) findViewById(R.id.image_btn_unpay);
        image_takeout = (ImageView) findViewById(R.id.image_btn_takeout);
        redpoint_paid = (ImageView) findViewById(R.id.redPoint_paid);
        redpoint_unpay = (ImageView) findViewById(R.id.redPoint_unpay);
        redpoint_takeout = (ImageView) findViewById(R.id.redPoint_takeout);


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
        manager = OkManager.getInstance();
    }

    private void initEvents() {
        unpay_tab.setOnClickListener(this);
        pay_tab.setOnClickListener(this);
        takeout_tab.setOnClickListener(this);
        refresh_tab.setOnClickListener(this);
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

            case R.id.btn_refresh:
                EventBus.getDefault().postSticky(new FlagEvent(true));
                break;

            case R.id.btn_more:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ToolsActivity.class);      //运行另外一个类的活动
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


    CountDownTimer cdt = new CountDownTimer(99999999, 60 * 1000) {
        @Override
        public void onTick(long l) {
            //刷新view
            EventBus.getDefault().postSticky(new FlagEvent(true));
            //true 为刷新界面
            EventBus.getDefault().postSticky(new FlagEvent(false));
            //false为发送通知
        }

        @Override
        public void onFinish() {

        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(FlagEvent flagEvent) {
        if (flagEvent != null) {
            notification4newOrder(flagEvent.getFlag());
        }
    }
//仅自动刷新可以
    private void notification4newOrder(final Boolean bool) {
        //包括控制红点和发送通知
        String path = CommonUtils.BaseUrl + "/web-frame/wxorder/queryNotDeal.do?shopnum=" + user.getShopnum();
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
                        int a, b, c;
                        a = b = c = 0;
                        unDealList = GsonUtils.getWxOrderByGson(result);
                        for (Wxorder wxorder : unDealList) {
                            if (wxorder.getIfdeal().equals("false")) {
                                //未出票
                                if (wxorder.getTakeout().equals("true")) {
                                    //是外卖
                                    a++;
                                    redpoint_takeout.setVisibility(View.VISIBLE);
                                } else {
                                    if (wxorder.getIfpay().equals("true")) {
                                        //是已支付
                                        b++;
                                        redpoint_paid.setVisibility(View.VISIBLE);
                                    } else if (wxorder.getIfpay().equals("false")) {
                                        //是未支付
                                        c++;
                                        redpoint_unpay.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                        }
                        if ((a + b + c) > 0) {
                            //未出票不为零发送通知
                            if (bool.equals(false)) {
                                shownotification();
                            }

                        }
                        if (a == 0) {
                            redpoint_takeout.setVisibility(View.INVISIBLE);
                        }
                        if (b == 0) {
                            redpoint_paid.setVisibility(View.INVISIBLE);
                        }
                        if (c == 0) {
                            redpoint_unpay.setVisibility(View.INVISIBLE);
                        }
                    } else {

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

    public void shownotification() {

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(MainActivity.this);
        Notification notification;
        Intent appIntent = new Intent(Intent.ACTION_MAIN);

        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.setComponent(new ComponentName(this.getPackageName(), this.getPackageName() + "." + this.getLocalClassName()));
        appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);//关键的一步，设置启动模式

        PendingIntent contentIndent = PendingIntent.getActivity(this, 0, appIntent, 0);
        builder.setContentIntent(contentIndent).setSmallIcon(R.mipmap.ic_order_logo)//设置状态栏里面的图标（小图标） 　　　　　　　　　　　　　　　　　　　　
                // .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.i5))//下拉下拉列表里面的图标（大图标） 　　　　　　　
                // .setTicker("this is bitch!") //设置状态栏的显示的信息
                .setWhen(System.currentTimeMillis())//设置时间发生时间
                .setAutoCancel(false)//设置可以清除
                .setContentTitle("订单系统")//设置下拉列表里的标题
                .setContentText("您有未处理的订单");//设置上下文内容

        notification = builder.getNotification();
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notification.sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.neworder);
        //加i是为了显示多条Notification
        notificationManager.notify(0, notification);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
