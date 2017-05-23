package com.example.starxder.meal.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.starxder.meal.Bean.Wxorder;
import com.example.starxder.meal.Event.PayEvent;
import com.example.starxder.meal.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Administrator on 2017/5/22.
 */

public class OrderDetailFragment extends Fragment {
    TextView tv_tablenum;
    TextView tv_ordercode;
    TextView tv_preprice;
    TextView tv_discount;
    TextView tv_totalFee;
    TextView tv_paystate;
    TextView tv_paytime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orderdetail_fragment, container, false);
        initView(view);
        EventBus.getDefault().register(this);
        return view;
    }



    @Subscribe
    public void onEvent(PayEvent payEvent) {
        if (payEvent != null) {
            Wxorder wxorder;
            wxorder = payEvent.getWxorder();
           setValue(wxorder);
        }
    }

    private void setValue(Wxorder wxorder) {
        tv_tablenum.setText("桌号:"+wxorder.getTablecode());
        tv_ordercode.setText("订单号:"+wxorder.getOutTradeNo());
        tv_preprice.setText("原价:"+wxorder.getOriginFee()+"元");
        tv_discount.setText("优惠:"+wxorder.getFavorFee()+"元");
        tv_totalFee.setText("应付:"+wxorder.getTotalFee()+"元");

        switch (wxorder.getPaystyle()){
            case "wx":tv_paystate.setText("支付状态:"+"微信支付");
                break;
            case"ali":tv_paystate.setText("支付状态:"+"支付宝支付");
                break;
            case"cash":tv_paystate.setText("支付状态:"+"现金支付");
                break;
            case"card":tv_paystate.setText("支付状态:"+"刷卡支付");
                break;
            case"other":tv_paystate.setText("支付状态:"+"其他支付");
                break;
            default:
        }

        tv_paytime.setText("支付时间:"+wxorder.getPaytime());
    }

    private void initView(View view) {
        tv_tablenum = (TextView) view.findViewById(R.id.orderdetail_tablenum);
        tv_ordercode = (TextView) view.findViewById(R.id.orderdetail_ordercode);
        tv_preprice = (TextView) view.findViewById(R.id.orderdetail_preprice);
        tv_discount = (TextView) view.findViewById(R.id.orderdetail_discount);
        tv_totalFee = (TextView) view.findViewById(R.id.orderdetail_totalFee);
        tv_paystate = (TextView) view.findViewById(R.id.orderdetail_paystate);
        tv_paytime = (TextView) view.findViewById(R.id.orderdetail_paytime);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
