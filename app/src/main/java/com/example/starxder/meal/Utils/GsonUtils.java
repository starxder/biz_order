package com.example.starxder.meal.Utils;

import com.example.starxder.meal.Bean.Dangkou;
import com.example.starxder.meal.Bean.Meal;
import com.example.starxder.meal.Bean.User;
import com.example.starxder.meal.Bean.Wxorder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/5/22.
 */

public class GsonUtils {
    public static List<Wxorder> getWxOrderByGson(String jsonString) {
        List<Wxorder> list = new ArrayList<Wxorder>();
        Gson gson = new Gson();
        list = gson.fromJson(jsonString, new TypeToken<List<Wxorder>>() {

        }.getType());
        return list;
    }

    public static List<Meal> getMealByGson(String jsonString) {
        List<Meal> list = new ArrayList<Meal>();
        Gson gson = new Gson();
        list = gson.fromJson(jsonString, new TypeToken<List<Meal>>() {

        }.getType());
        return list;
    }

    public static List<User> getUserByGson(String jsonString) {
        List<User> list = new ArrayList<User>();
        Gson gson = new Gson();
        list = gson.fromJson(jsonString, new TypeToken<List<User>>() {

        }.getType());
        return list;
    }

    public static List<Dangkou> getDangkouByGson(String jsonString) {
        List<Dangkou> list = new ArrayList<Dangkou>();
        Gson gson = new Gson();
        list = gson.fromJson(jsonString, new TypeToken<List<Dangkou>>() {

        }.getType());
        return list;
    }
}

