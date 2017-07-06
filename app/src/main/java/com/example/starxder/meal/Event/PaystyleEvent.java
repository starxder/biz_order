package com.example.starxder.meal.Event;

/**
 * Created by Administrator on 2017/6/22.
 */

public class PaystyleEvent {
    Boolean flag;
    String OutTradeNo;

    public PaystyleEvent(Boolean flag, String outTradeNo) {
        this.flag = flag;
        OutTradeNo = outTradeNo;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getOutTradeNo() {
        return OutTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        OutTradeNo = outTradeNo;
    }
}
