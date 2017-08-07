package com.example.starxder.meal.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "meal")
public class Meal {
    @DatabaseField(id = true)
    private int mealid;

    @DatabaseField(columnName = "mealname")
    private String mealname;

    @DatabaseField(columnName = "mealprice")
    private String mealprice;

    @DatabaseField(columnName = "memberprice")
    private String memberprice;

    @DatabaseField(columnName = "mealurl")
    private String mealurl;

    @DatabaseField(columnName = "mealtype")
    private String mealtype;

    @DatabaseField(columnName = "mealtypename")
    private String mealtypename;

    @DatabaseField(columnName = "selected")
    private String selected;

    @DatabaseField(columnName = "mealtype2")
    private String mealtype2;

    @DatabaseField(columnName = "mealtypename2")
    private String mealtypename2;

    @DatabaseField(columnName = "shopnum")
    private String shopnum;

    @DatabaseField(columnName = "ifsoldout")
    private String ifsoldout;

    @DatabaseField(columnName = "mindex")
    private String mindex;

    public String getMindex() {
        return mindex;
    }

    public void setMindex(String mindex) {
        this.mindex = mindex;
    }

    public String getIfsoldout() {
        return ifsoldout;
    }

    public void setIfsoldout(String ifsoldout) {
        this.ifsoldout = ifsoldout;
    }

    public int getMealid() {
        return mealid;
    }

    public void setMealid(int mealid) {
        this.mealid = mealid;
    }

    public String getMealname() {
        return mealname;
    }

    public void setMealname(String mealname) {
        this.mealname = mealname;
    }

    public String getMealprice() {
        return mealprice;
    }

    public void setMealprice(String mealprice) {
        this.mealprice = mealprice;
    }

    public String getMealurl() {
        return mealurl;
    }

    public void setMealurl(String mealurl) {
        this.mealurl = mealurl;
    }

    public String getMealtype() {
        return mealtype;
    }

    public void setMealtype(String mealtype) {
        this.mealtype = mealtype;
    }

    public String getMealtypename() {
        return mealtypename;
    }

    public void setMealtypename(String mealtypename) {
        this.mealtypename = mealtypename;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getMealtype2() {
        return mealtype2;
    }

    public void setMealtype2(String mealtype2) {
        this.mealtype2 = mealtype2;
    }

    public String getMealtypename2() {
        return mealtypename2;
    }

    public void setMealtypename2(String mealtypename2) {
        this.mealtypename2 = mealtypename2;
    }

    public String getShopnum() {
        return shopnum;
    }

    public void setShopnum(String shopnum) {
        this.shopnum = shopnum;
    }

    public String getMemberprice() {
        return memberprice;
    }

    public void setMemberprice(String memberprice) {
        this.memberprice = memberprice;
    }

}