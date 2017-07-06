package com.example.starxder.meal.Event;

/**
 * Created by Administrator on 2017/7/5.
 */

public class BackmealEvent {
    Boolean flag;
    String OutTradeNo;

    public BackmealEvent(Boolean flag) {
        this.flag = flag;
    }

    public Boolean getFlag() {
        return flag;
    }

    public String getOutTradeNo() {
        return OutTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        OutTradeNo = outTradeNo;
    }

    public BackmealEvent(Boolean flag, String outTradeNo) {
        this.flag = flag;
        OutTradeNo = outTradeNo;
    }

}
