package com.example.starxder.meal.Bean;

public class PayDetail {
    //现金
    String cash;
    //微信
    String wechatpay;
    //支付宝
    String alipay;
    //会员卡
    String membercard;
    //刷卡
    String creditcard;
    //其他
    String otherpay;

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getWechatpay() {
        return wechatpay;
    }

    public void setWechatpay(String wechatpay) {
        this.wechatpay = wechatpay;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getMembercard() {
        return membercard;
    }

    public void setMembercard(String membercard) {
        this.membercard = membercard;
    }

    public String getCreditcard() {
        return creditcard;
    }

    public void setCreditcard(String creditcard) {
        this.creditcard = creditcard;
    }

    public String getOtherpay() {
        return otherpay;
    }

    public void setOtherpay(String otherpay) {
        this.otherpay = otherpay;
    }
}
