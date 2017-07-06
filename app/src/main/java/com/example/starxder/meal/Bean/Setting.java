package com.example.starxder.meal.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2017/6/21.
 */
@DatabaseTable(tableName = "setting")
public class Setting {
    @DatabaseField(id = true)
    private int id;

    @DatabaseField(columnName = "remPassword")
    private String remPassword;

    @DatabaseField(columnName = "autoLogin")
    private String autoLogin;

    public Setting(){

    }

    public Setting(int id, String remPassword, String autoLogin) {
        this.id = id;
        this.remPassword = remPassword;
        this.autoLogin = autoLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemPassword() {
        return remPassword;
    }

    public void setRemPassword(String remPassword) {
        this.remPassword = remPassword;
    }

    public String getAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(String autoLogin) {
        this.autoLogin = autoLogin;
    }
}


