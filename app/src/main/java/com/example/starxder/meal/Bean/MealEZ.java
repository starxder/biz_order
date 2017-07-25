package com.example.starxder.meal.Bean;

/**
 * Created by Administrator on 2017/5/24.
 */

public class MealEZ {
    private float mealprice;
    private String mealName;
    private int mealNum;
    private String mealtype2;
    private String mealtypename2;

    public MealEZ(float mealprice, String mealName, int mealNum,String mealtype2,String mealtypename2){
        this.mealprice = mealprice;
        this.mealName = mealName;
        this.mealNum = mealNum;
        this.mealtype2 = mealtype2;
        this.mealtypename2 = mealtypename2;
    }

    public float getMealprice() {
        return mealprice;
    }

    public void setMealprice(float mealprice) {
        this.mealprice = mealprice;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public int getMealNum() {
        return mealNum;
    }

    public void setMealNum(int mealNum) {
        this.mealNum = mealNum;
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
}
