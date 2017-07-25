package com.example.starxder.meal.Dao;

import android.content.Context;

import com.example.starxder.meal.Bean.Dangkou;
import com.example.starxder.meal.Database.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by 于春晓 on 2017/7/24.
 *
 * 操作Dangkou数据表的Dao类，封装这操作Dangkou表的所有操作
 * 通过DatabaseHelper类中的方法获取ORMLite内置的DAO类进行数据库中数据的操作
 * <p>
 * 调用dao的create()方法向表中添加数据
 * 调用dao的delete()方法删除表中的数据
 * 调用dao的update()方法修改表中的数据
 * 调用dao的queryForAll()方法查询表中的所有数据
 */

public class DangkouDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<Dangkou, Integer> dao;

    public DangkouDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(Dangkou.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 向Dangkou表中添加一条数据
    public void insert(Dangkou data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除Dangkou表中的一条数据
    public void delete(Dangkou data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改Dangkou表中的一条数据
    public void update(Dangkou data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询Dangkou表中的所有数据
    public List<Dangkou> selectAll() {
        List<Dangkou> dangkous = null;
        try {
            dangkous = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dangkous;
    }

    // 根据ID取出档口信息
    public Dangkou queryById(int id) {
        Dangkou dangkou = null;
        try {
            dangkou = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dangkou;
    }


    // 取出全部信息
    public List<Dangkou> queryForAll() throws SQLException {
        List<Dangkou> list = dao.queryForAll();
        return list;
    }

    // 删除全部信息
    public void deleteAll() throws SQLException {
        List<Dangkou> list =  queryForAll();
        for(Dangkou user :list){
            delete(user);
        }
    }
}
