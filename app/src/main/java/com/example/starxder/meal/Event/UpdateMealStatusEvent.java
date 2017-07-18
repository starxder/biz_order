package com.example.starxder.meal.Event;

/**
 * Created by Administrator on 2017/7/15.
 */

public class UpdateMealStatusEvent {
    Boolean flag;

    public UpdateMealStatusEvent(Boolean flag) {
        this.flag = flag;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
}
