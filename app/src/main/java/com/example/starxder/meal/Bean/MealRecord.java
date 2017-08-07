package com.example.starxder.meal.Bean;

public class MealRecord {
    private Integer id;

    private String mealid;

    private String mealnum;

    private String dateflag;

    private String shopnum;

    private String orderno;

    private String delflag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMealid() {
        return mealid;
    }

    public void setMealid(String mealid) {
        this.mealid = mealid == null ? null : mealid.trim();
    }

    public String getMealnum() {
        return mealnum;
    }

    public void setMealnum(String mealnum) {
        this.mealnum = mealnum == null ? null : mealnum.trim();
    }

    public String getDateflag() {
        return dateflag;
    }

    public void setDateflag(String dateflag) {
        this.dateflag = dateflag == null ? null : dateflag.trim();
    }

    public String getShopnum() {
        return shopnum;
    }

    public void setShopnum(String shopnum) {
        this.shopnum = shopnum == null ? null : shopnum.trim();
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
    }

    public String getDelflag() {
        return delflag;
    }

    public void setDelflag(String delflag) {
        this.delflag = delflag == null ? null : delflag.trim();
    }
}