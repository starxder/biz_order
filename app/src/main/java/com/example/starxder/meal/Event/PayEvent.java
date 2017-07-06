package com.example.starxder.meal.Event;

import com.example.starxder.meal.Bean.Wxorder;

/**
 * Created by Administrator on 2017/5/22.
 */

public class PayEvent {

    private Wxorder wxorder;
    private String ifpay;

    public PayEvent(Wxorder wxorder, String ifpay) {
        this.wxorder = wxorder;
        this.ifpay = ifpay;
    }

    public Wxorder getWxorder() {
        return wxorder;
    }

    public void setWxorder(Wxorder wxorder) {
        this.wxorder = wxorder;
    }

    public String getIfpay() {
        return ifpay;
    }

    public void setIfpay(String ifpay) {
        this.ifpay = ifpay;
    }


}
