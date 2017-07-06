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

    private String paystyle;//支付类型  wx ali cash card other

    private String ifdeal;//是否出票 true false

    private String dateflag;//日期标记

    private String shopnum;//店铺号

    private String favorFee;// 优惠额度

    private String originFee;// 原价

    private String backDetail;//退菜详情

    private String backFee;//退款金额

    private String takeout;//外卖  true false

    private String takeoutInfo; //name + phoneNumber + address

    private String remark; //订单备注

    public Wxorder(String outTradeNo, String appid, String mchId, String nonceStr, String sign, String body, String detail, String totalFee, String spbillCreateIp, String notifyUrl, String tradeType, String ordertime, String tablecode, String openid, String ifpay, String paytime, String paystyle, String ifdeal, String dateflag, String shopnum, String favorFee, String originFee, String backDetail, String backFee, String takeout, String takeoutInfo, String remark) {
        this.outTradeNo = outTradeNo;
        this.appid = appid;
        this.mchId = mchId;
        this.nonceStr = nonceStr;
        this.sign = sign;
        this.body = body;
        this.detail = detail;
        this.totalFee = totalFee;
        this.spbillCreateIp = spbillCreateIp;
        this.notifyUrl = notifyUrl;
        this.tradeType = tradeType;
        this.ordertime = ordertime;
        this.tablecode = tablecode;
        this.openid = openid;
        this.ifpay = ifpay;
        this.paytime = paytime;
        this.paystyle = paystyle;
        this.ifdeal = ifdeal;
        this.dateflag = dateflag;
        this.shopnum = shopnum;
        this.favorFee = favorFee;
        this.originFee = originFee;
        this.backDetail = backDetail;
        this.backFee = backFee;
        this.takeout = takeout;
        this.takeoutInfo = takeoutInfo;
        this.remark = remark;
    }

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

    public String getFavorFee() {
        return favorFee;
    }

    public void setFavorFee(String favorFee) {
        this.favorFee = favorFee;
    }

    public String getOriginFee() {
        return originFee;
    }

    public void setOriginFee(String originFee) {
        this.originFee = originFee;
    }

    public String getBackDetail() {
        return backDetail;
    }

    public void setBackDetail(String backDetail) {
        this.backDetail = backDetail;
    }

    public String getBackFee() {
        return backFee;
    }

    public void setBackFee(String backFee) {
        this.backFee = backFee;
    }

    public String getTakeout() {
        return takeout;
    }

    public void setTakeout(String takeout) {
        this.takeout = takeout;
    }

    public String getTakeoutInfo() {
        return takeoutInfo;
    }

    public void setTakeoutInfo(String takeoutInfo) {
        this.takeoutInfo = takeoutInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

