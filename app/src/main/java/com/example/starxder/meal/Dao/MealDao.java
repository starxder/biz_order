package com.example.starxder.meal.Dao;

import android.content.Context;

import com.example.starxder.meal.Bean.Meal;
import com.example.starxder.meal.Database.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 */

public class MealDao {

    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<Meal, Integer> dao;

    public MealDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(Meal.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // 向meal表中添加一条数据
    public void insert(Meal data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除meal表中的一条数据
    public void delete(Meal data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改meal表中的一条数据
    public void update(Meal data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询meal表中的所有数据
    public List<Meal> selectAll() {
        List<Meal> meals = null;
        try {
            meals = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meals;
    }

    // 根据ID取出用户信息
    public Meal queryById(int id) {
        Meal meals = null;
        try {
            meals = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return meals;
    }
}
