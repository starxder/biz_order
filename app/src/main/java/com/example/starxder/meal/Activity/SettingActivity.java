package com.example.starxder.meal.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.example.starxder.meal.Bean.Printer;
import com.example.starxder.meal.Dao.PrinterDao;
import com.example.starxder.meal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */

public class SettingActivity extends Activity {
    EditText edtip_1, edtip_2, edtip_3, edtip_4, edtip_5;
    EditText edtProt1, edtProt2, edtProt3, edtProt4, edtProt5;
    CheckBox cb1, cb2, cb3, cb4, cb5;

    PrinterDao printerDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        initView();
        initEvent();
        initData();
    }


    private void initView() {
        printerDao = new PrinterDao(SettingActivity.this);
        edtip_1 = (EditText) findViewById(R.id.printer1_ip);
        edtip_2 = (EditText) findViewById(R.id.printer2_ip);
        edtip_3 = (EditText) findViewById(R.id.printer3_ip);
        edtip_4 = (EditText) findViewById(R.id.printer4_ip);
        edtip_5 = (EditText) findViewById(R.id.printer5_ip);
        edtProt1 = (EditText) findViewById(R.id.printer1_port);
        edtProt2 = (EditText) findViewById(R.id.printer2_port);
        edtProt3 = (EditText) findViewById(R.id.printer3_port);
        edtProt4 = (EditText) findViewById(R.id.printer4_port);
        edtProt5 = (EditText) findViewById(R.id.printer5_port);
        cb1 = (CheckBox) findViewById(R.id.printer1_cb);
        cb2 = (CheckBox) findViewById(R.id.printer2_cb);
        cb3 = (CheckBox) findViewById(R.id.printer3_cb);
        cb4 = (CheckBox) findViewById(R.id.printer4_cb);
        cb5 = (CheckBox) findViewById(R.id.printer5_cb);
    }


    private void initEvent() {
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //选中
                    String ipadress = edtip_1.getText().toString();
                    String prot = edtProt1.getText().toString();
                    int id = 1;
                    String checked = "true";
                    Printer p1 = new Printer(ipadress, prot, id, checked);
                    printerDao.insert(p1);
                } else {
                    //取消选中
                    printerDao.delete(printerDao.queryById(1));
                }
            }
        });

        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //选中
                    String ipadress = edtip_2.getText().toString();
                    String prot = edtProt2.getText().toString();
                    int id = 2;
                    String checked = "true";
                    Printer p2 = new Printer(ipadress, prot, id, checked);
                    printerDao.insert(p2);
                } else {
                    //取消选中
                    printerDao.delete(printerDao.queryById(2));
                }
            }
        });

        cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //选中
                    String ipadress = edtip_3.getText().toString();
                    String prot = edtProt3.getText().toString();
                    int id = 3;
                    String checked = "true";
                    Printer p3 = new Printer(ipadress, prot, id, checked);
                    printerDao.insert(p3);
                } else {
                    //取消选中
                    printerDao.delete(printerDao.queryById(3));
                }
            }
        });

        cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //选中
                    String ipadress = edtip_4.getText().toString();
                    String prot = edtProt4.getText().toString();
                    int id = 4;
                    String checked = "true";
                    Printer p4 = new Printer(ipadress, prot, id, checked);
                    printerDao.insert(p4);
                } else {
                    //取消选中
                    printerDao.delete(printerDao.queryById(4));
                }
            }
        });
        cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //选中
                    String ipadress = edtip_5.getText().toString();
                    String prot = edtProt5.getText().toString();
                    int id = 5;
                    String checked = "true";
                    Printer p5 = new Printer(ipadress, prot, id, checked);
                    printerDao.insert(p5);
                } else {
                    //取消选中
                    printerDao.delete(printerDao.queryById(5));
                }
            }
        });

    }

    private void initData() {
        if (printerDao.queryById(1)!=null){
            edtip_1.setText(printerDao.queryById(1).getIpaddress());
            edtProt1.setText(printerDao.queryById(1).getProt());
            cb1.setChecked(Boolean.valueOf(printerDao.queryById(1).getChecked()).booleanValue());
        }

        if (printerDao.queryById(2)!=null){
            edtip_2.setText(printerDao.queryById(2).getIpaddress());
            edtProt2.setText(printerDao.queryById(2).getProt());
            cb2.setChecked(Boolean.valueOf(printerDao.queryById(2).getChecked()).booleanValue());
        }

        if (printerDao.queryById(3)!=null){
            edtip_3.setText(printerDao.queryById(3).getIpaddress());
            edtProt3.setText(printerDao.queryById(3).getProt());
            cb3.setChecked(Boolean.valueOf(printerDao.queryById(3).getChecked()).booleanValue());
        }

        if (printerDao.queryById(4)!=null){
            edtip_4.setText(printerDao.queryById(4).getIpaddress());
            edtProt4.setText(printerDao.queryById(4).getProt());
            cb4.setChecked(Boolean.valueOf(printerDao.queryById(4).getChecked()).booleanValue());
        }

        if (printerDao.queryById(5)!=null){
            edtip_5.setText(printerDao.queryById(5).getIpaddress());
            edtProt5.setText(printerDao.queryById(5).getProt());
            cb5.setChecked(Boolean.valueOf(printerDao.queryById(5).getChecked()).booleanValue());
        }
    }
}
