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

    @DatabaseField(columnName = "prot")
    private String prot;

    @DatabaseField(id = true)
    private int id;

    @DatabaseField(columnName = "checked")
    private String checked;

    public Printer(){

    }

    public Printer(String ipaddress, String prot, int id, String checked) {
        this.ipaddress = ipaddress;
        this.prot = prot;
        this.id = id;
        this.checked = checked;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getProt() {
        return prot;
    }

    public void setProt(String prot) {
        this.prot = prot;
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
}
