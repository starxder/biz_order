package com.example.starxder.meal.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user")
public class User {
    @DatabaseField(columnName = "description")
    private String description;
    @DatabaseField(columnName = "groupId")
    private int groupId;
    @DatabaseField(columnName = "headpicurl")
    private String headpicurl;
    @DatabaseField(id = true)
    private int id;
    @DatabaseField(columnName = "loginName")
    private String loginName;
    @DatabaseField(columnName = "modifyTime")
    private String modifyTime;
    @DatabaseField(columnName = "ordertable")
    private String ordertable;
    @DatabaseField(columnName = "password")
    private String password;
    @DatabaseField(columnName = "shopnum")
    private String shopnum;
    @DatabaseField(columnName = "tabletype")
    private String tabletype;
    @DatabaseField(columnName = "userName")
    private String userName;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getHeadpicurl() {
        return headpicurl;
    }

    public void setHeadpicurl(String headpicurl) {
        this.headpicurl = headpicurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getOrdertable() {
        return ordertable;
    }

    public void setOrdertable(String ordertable) {
        this.ordertable = ordertable;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShopnum() {
        return shopnum;
    }

    public void setShopnum(String shopnum) {
        this.shopnum = shopnum;
    }

    public String getTabletype() {
        return tabletype;
    }

    public void setTabletype(String tabletype) {
        this.tabletype = tabletype;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}