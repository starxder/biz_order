package com.example.starxder.meal.Event;

import com.example.starxder.meal.Bean.User;

/**
 * Created by Administrator on 2017/5/22.
 */

public class UserEvent {

    private User user;

    String tag;

    public UserEvent(User user, String tag) {
        this.user = user;
        this.tag = tag;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
