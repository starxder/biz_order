package com.example.starxder.meal.Dao;

/**
 * Created by Administrator on 2017/5/23.
 */

import android.content.Context;

import com.example.starxder.meal.Bean.Setting;
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
public class SettingDao {
    private Context context;
    // ORMLite提供的DAO类对象，第一个泛型是要操作的数据表映射成的实体类；第二个泛型是这个实体类中ID的数据类型
    private Dao<Setting, Integer> dao;

    public SettingDao(Context context) {
        this.context = context;
        try {
            this.dao = DatabaseHelper.getInstance(context).getDao(Setting.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 向user表中添加一条数据
    public void insert(Setting data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除user表中的一条数据
    public void delete(Setting data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 修改user表中的一条数据
    public void update(Setting data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询user表中的所有数据
    public List<Setting> selectAll() {
        List<Setting> users = null;
        try {
            users = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // 根据ID取出用户信息
    public Setting queryById(int id) {
        Setting setting = null;
        try {
            setting = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
         return setting;
    }

    // 根据loginName取出用户信息
    public Setting queryByLoginName(String loginName) {
        List<Setting> list = null;
        try {
            list = dao.queryBuilder().where().like("loginName", loginName).query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.get(0);
    }

    // 取出全部信息
    public List<Setting> queryForAll() throws SQLException {
        List<Setting> list = dao.queryForAll();
        return list;
    }

    // 取出ID最靠前的
    public Setting getFirstUser() throws SQLException {
        try{
            List<Setting> list = dao.queryForAll();
            return list.get(0);
        }catch (Exception e){
            return  null;
        }
    }
}