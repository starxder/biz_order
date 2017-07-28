package com.example.starxder.meal.Bean;

public class BillDetail {
    //消费总额
    String totalFee;
    //折扣额
    String favorFee;
    //结单金额
    String payFee;
    //总单数
    String totalNum;
    //单均消费
    String avgTotalFee;
    //单均实收
    String avgPayFee;

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getFavorFee() {
        return favorFee;
    }

    public void setFavorFee(String favorFee) {
        this.favorFee = favorFee;
    }

    public String getPayFee() {
        return payFee;
    }

    public void setPayFee(String payFee) {
        this.payFee = payFee;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getAvgTotalFee() {
        return avgTotalFee;
    }

    public void setAvgTotalFee(String avgTotalFee) {
        this.avgTotalFee = avgTotalFee;
    }

    public String getAvgPayFee() {
        return avgPayFee;
    }

    public void setAvgPayFee(String avgPayFee) {
        this.avgPayFee = avgPayFee;
    }

}
