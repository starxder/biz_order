package com.example.starxder.meal.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2017/5/23.
 */
@DatabaseTable(tableName = "user")
public class User {
    @DatabaseField(generatedId = true, columnName = "id")
    private int id;

    @DatabaseField(columnName = "loginName")
    private String loginName;

    @DatabaseField(columnName = "passWord")
    private String passWord;

    @DatabaseField(columnName = "userName")
    private String userName;

    @DatabaseField(columnName = "shopNum")
    private String shopNum;

    @DatabaseField(columnName = "shopName")
    private String shopName;

    @DatabaseField(columnName = "headpicUrl")
    private String headpicUrl;//100*100px

    public User (){

    }

    public User(int id, String loginName, String passWord, String userName, String shopNum, String shopName, String headpicUrl) {
        this.id = id;
        this.loginName = loginName;
        this.passWord = passWord;
        this.userName = userName;
        this.shopNum = shopNum;
        this.shopName = shopName;
        this.headpicUrl = headpicUrl;
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

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShopNum() {
        return shopNum;
    }

    public void setShopNum(String shopNum) {
        this.shopNum = shopNum;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getHeadpicUrl() {
        return headpicUrl;
    }

    public void setHeadpicUrl(String headpicUrl) {
        this.headpicUrl = headpicUrl;
    }
}
