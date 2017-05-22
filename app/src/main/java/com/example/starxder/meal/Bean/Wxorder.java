package com.example.starxder.meal.Bean;

public class Wxorder {
    private String outTradeNo;//订单号

    private String appid;//小程序Appid

    private String mchId;//商户号id

    private String nonceStr;//随机数

    private String sign;//签名

    private String body;//统一下单的介绍

    private String detail;//商品详情

    private String totalFee;//总价

    private String spbillCreateIp;//提交订单的ip

    private String notifyUrl;//通一下但完成之后回调接口

    private String tradeType;//交易类型

    private String ordertime;//下单时间

    private String tablecode;//桌号

    private String openid;//openid

    private String ifpay;//是否支付 true false

    private String paytime;//支付时间

    private String paystyle;//支付类型  wechat alipay cash card other

    private String ifdeal;//是否出票 true false

    private String dateflag;//日期标记

    private String shopnum;//店铺号

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public String getTablecode() {
        return tablecode;
    }

    public void setTablecode(String tablecode) {
        this.tablecode = tablecode;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIfpay() {
        return ifpay;
    }

    public void setIfpay(String ifpay) {
        this.ifpay = ifpay;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getPaystyle() {
        return paystyle;
    }

    public void setPaystyle(String paystyle) {
        this.paystyle = paystyle;
    }

    public String getIfdeal() {
        return ifdeal;
    }

    public void setIfdeal(String ifdeal) {
        this.ifdeal = ifdeal;
    }

    public String getDateflag() {
        return dateflag;
    }

    public void setDateflag(String dateflag) {
        this.dateflag = dateflag;
    }

    public String getShopnum() {
        return shopnum;
    }

    public void setShopnum(String shopnum) {
        this.shopnum = shopnum;
    }

}