package com.example.starxder.meal.Bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2017/6/14.
 */


@DatabaseTable(tableName = "printer")
public class Printer {

    @DatabaseField(columnName = "ipaddress")
    private String ipaddress;

    @DatabaseField(columnName = "port")
    private String port;

    @DatabaseField(id = true)
    private int id;

    @DatabaseField(columnName = "checked")
    private String checked;

    @DatabaseField(columnName = "dangkoukey")
    private String dangkoukey;

    @DatabaseField(columnName = "dangkouname")
    private String dangkouname;

    public Printer(){

    }

    public Printer(String ipaddress, String port, int id, String checked) {
        this.ipaddress = ipaddress;
        this.port = port;
        this.id = id;
        this.checked = checked;
    }

    public Printer(String ipaddress, String port, int id, String checked, String dangkouname, String dangkoukey) {
        this.ipaddress = ipaddress;
        this.port = port;
        this.id = id;
        this.checked = checked;
        this.dangkouname = dangkouname;
        this.dangkoukey = dangkoukey;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getDangkoukey() {
        return dangkoukey;
    }

    public void setDangkoukey(String dangkoukey) {
        this.dangkoukey = dangkoukey;
    }

    public String getDangkouname() {
        return dangkouname;
    }

    public void setDangkouname(String dangkouname) {
        this.dangkouname = dangkouname;
    }
}
