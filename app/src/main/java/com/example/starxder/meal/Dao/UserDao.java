package com.example.starxder.meal.Dao;

/**
 * Created by Administrator on 2017/5/23.
 */

import android.content.Context;

import com.example.starxder.meal.Bean.Meal;
import com.example.starxder.meal.Bean.User;
import com.example.starxder.meal.Database.DatabaseHelper;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.List;

/**
 * 操作User数据表的Dao类，封装这操作User表的所有操作
 * 通过DatabaseHelper类中的方法获取ORMLite内置的DAO类进行数据库中数据的操作
 * <p>
 * 调用dao的create()方法向表中添加数据
 * 调用dao的delete()方法删除表中的数据
 * 调用dao的update()方法修改表中的数据
 * 调用dao的queryForAll()方法查询表中的所有数据
 */
public class UserDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<User, Integer> dao;

    public UserDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 向user表中添加一条数据
    public void insert(User data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除user表中的一条数据
    public void delete(User data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改user表中的一条数据
    public void update(User data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询user表中的所有数据
    public List<User> selectAll() {
        List<User> users = null;
        try {
            users = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // 根据ID取出用户信息
    public User queryById(int id) {
        User user = null;
        try {
            user = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // 根据loginName取出用户信息
    public User queryByLoginName(String loginName) {
        List<User> list = null;
        try {
            list = dao.queryBuilder().where().like("loginName", loginName).query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.get(0);
    }

    // 取出全部信息
    public List<User> queryForAll() throws SQLException {
        List<User> list = dao.queryForAll();
        return list;
    }

    // 取出ID最靠前的
    public User getFirstUser() throws SQLException {
        try{
            List<User> list = dao.queryForAll();
            return list.get(0);
        }catch (Exception e){
            return  null;
        }
    }
    // 删除全部信息
    public void deleteAll() throws SQLException {
        List<User> list =  queryForAll();
        for(User user :list){
            delete(user);
        }
    }
}