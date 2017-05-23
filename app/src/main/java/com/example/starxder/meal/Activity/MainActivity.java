package com.example.starxder.meal.Activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;

import com.example.starxder.meal.Bean.User;
import com.example.starxder.meal.Dao.UserDao;
import com.example.starxder.meal.Fragment.OrderDetailFragment;
import com.example.starxder.meal.Fragment.PayFragment;
import com.example.starxder.meal.Fragment.UnpayFragment;
import com.example.starxder.meal.R;

import org.greenrobot.eventbus.EventBus;

public class MainActivity extends Activity implements OnClickListener {

    public RelativeLayout unpay_tab;
    public RelativeLayout pay_tab;

    public OrderDetailFragment orderDetailFragment;
    public UnpayFragment unpayFragment;
    public PayFragment payFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initData();
        intViews();
        intEvents();
        setContent(0);
    }

    // 初始化数据
    private void initData() {
        // 添加用户数据
        User userData = new User(1,"admin", "1", "嘉拓科技", "1","嘉拓科技","");
        new UserDao(MainActivity.this).insert(userData);
    }

    private void intViews() {
        unpay_tab = (RelativeLayout) findViewById(R.id.btn_unpay);
        pay_tab = (RelativeLayout) findViewById(R.id.btn_paid);

        unpayFragment = new UnpayFragment();
        payFragment = new PayFragment();
        orderDetailFragment = new OrderDetailFragment();

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.orderdetail_fragment, orderDetailFragment);
        transaction.commit();
    }

    private void intEvents() {
        unpay_tab.setOnClickListener(this);
        pay_tab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        resetImage();
        switch (v.getId()) {
            case R.id.btn_unpay:
                setContent(1);
                break;
            case R.id.btn_paid:
                setContent(0);
                break;
            default:
                break;
        }
    }

    private void setContent(int item) {

        FragmentManager fm = getFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction();
        switch (item) {
            case 0:
                transaction.replace(R.id.orderlist_fragment, payFragment);
//                weixin_img.setImageResource(R.drawable.tab_weixin_pressed);
                break;
            case 1:
                transaction.replace(R.id.orderlist_fragment, unpayFragment);
//                frd_img.setImageResource(R.drawable.tab_find_frd_pressed);
                break;
            default:
                break;
        }
        //��������  
        transaction.commit();
    }

    private void resetImage() {
//        weixin_img.setImageResource(R.drawable.tab_weixin_normal);
//        frd_img.setImageResource(R.drawable.tab_find_frd_normal);
    }

}
