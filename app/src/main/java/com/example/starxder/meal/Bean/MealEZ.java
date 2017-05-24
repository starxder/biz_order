package com.example.starxder.meal.Bean;

/**
 * Created by Administrator on 2017/5/24.
 */

public class MealEZ {
    private float mealprice;
    private String mealName;
    private int mealNum;

    public MealEZ(float mealprice, String mealName, int mealNum) {
        this.mealprice = mealprice;
        this.mealName = mealName;
        this.mealNum = mealNum;
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
}
