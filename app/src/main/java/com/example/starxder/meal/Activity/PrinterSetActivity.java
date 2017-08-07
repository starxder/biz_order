package com.example.starxder.meal.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.starxder.meal.Bean.Dangkou;
import com.example.starxder.meal.Bean.Printer;
import com.example.starxder.meal.Dao.DangkouDao;
import com.example.starxder.meal.Dao.PrinterDao;
import com.example.starxder.meal.R;
import com.example.starxder.meal.Utils.OkManager;
import com.example.starxder.meal.Adapter.PrinterSpinnerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */

public class PrinterSetActivity extends Activity{
    EditText edtip_1, edtip_2, edtip_3, edtip_4, edtip_5, edtip_6, edtip_7;
    EditText edtProt1, edtProt2, edtProt3, edtProt4, edtProt5, edtProt6, edtProt7;
    Spinner printer1_sp,printer2_sp,printer3_sp,printer4_sp,printer5_sp,printer6_sp,printer7_sp;
    String key1,key2,key3,key4,key5,key6,key7,name1,name2,name3,name4,name5,name6,name7;
    CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7;

    PrinterDao printerDao;
    DangkouDao dangkouDao;
    private OkManager manager;
    List<Dangkou> dangkous;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_printer);
        initView();
        initEvent();
        initData();
    }


    private void initView() {
        printerDao = new PrinterDao(PrinterSetActivity.this);
        dangkouDao = new DangkouDao(PrinterSetActivity.this);

        edtip_1 = (EditText) findViewById(R.id.printer1_ip);
        edtip_2 = (EditText) findViewById(R.id.printer2_ip);
        edtip_3 = (EditText) findViewById(R.id.printer3_ip);
        edtip_4 = (EditText) findViewById(R.id.printer4_ip);
        edtip_5 = (EditText) findViewById(R.id.printer5_ip);
        edtip_6 = (EditText) findViewById(R.id.printer6_ip);
        edtip_7 = (EditText) findViewById(R.id.printer7_ip);
        edtProt1 = (EditText) findViewById(R.id.printer1_port);
        edtProt2 = (EditText) findViewById(R.id.printer2_port);
        edtProt3 = (EditText) findViewById(R.id.printer3_port);
        edtProt4 = (EditText) findViewById(R.id.printer4_port);
        edtProt5 = (EditText) findViewById(R.id.printer5_port);
        edtProt6 = (EditText) findViewById(R.id.printer6_port);
        edtProt7 = (EditText) findViewById(R.id.printer7_port);

        cb1 = (CheckBox) findViewById(R.id.printer1_cb);
        cb2 = (CheckBox) findViewById(R.id.printer2_cb);
        cb3 = (CheckBox) findViewById(R.id.printer3_cb);
        cb4 = (CheckBox) findViewById(R.id.printer4_cb);
        cb5 = (CheckBox) findViewById(R.id.printer5_cb);
        cb6 = (CheckBox) findViewById(R.id.printer6_cb);
        cb7 = (CheckBox) findViewById(R.id.printer7_cb);

        printer1_sp = (Spinner) findViewById(R.id.printer1_sp);
        printer2_sp = (Spinner) findViewById(R.id.printer2_sp);
        printer3_sp = (Spinner) findViewById(R.id.printer3_sp);
        printer4_sp = (Spinner) findViewById(R.id.printer4_sp);
        printer5_sp = (Spinner) findViewById(R.id.printer5_sp);
        printer6_sp = (Spinner) findViewById(R.id.printer6_sp);
        printer7_sp = (Spinner) findViewById(R.id.printer7_sp);

        key1="1";
        key2="1";
        key3="1";
        key4="1";
        key5="1";
        key6="1";
        key7="1";

        name1="全部";
        name2="全部";
        name3="全部";
        name4="全部";
        name5="全部";
        name6="全部";
        name7="全部";

        manager = OkManager.getInstance();
        Log.d("manager",manager.toString());


        PrinterSpinnerAdapter arrayAdapter = new PrinterSpinnerAdapter(this);
        dangkous=dangkouDao.selectAll();
        arrayAdapter.setDatas(dangkous);

        printer1_sp.setAdapter(arrayAdapter);
        printer2_sp.setAdapter(arrayAdapter);
        printer3_sp.setAdapter(arrayAdapter);
        printer4_sp.setAdapter(arrayAdapter);
        printer5_sp.setAdapter(arrayAdapter);
        printer6_sp.setAdapter(arrayAdapter);
        printer7_sp.setAdapter(arrayAdapter);

    }


    private void initEvent() {
        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //选中
                    String ipadress = edtip_1.getText().toString();
                    String prot = edtProt1.getText().toString();
                    key1=((Dangkou)printer1_sp.getSelectedItem()).getValue2();
                    name1=((Dangkou)printer1_sp.getSelectedItem()).getValue1();
                    int id = 1;
                    String checked = "true";
                    Printer p1 = new Printer(ipadress, prot, id, checked, name1, key1);
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
                    key2=((Dangkou)printer2_sp.getSelectedItem()).getValue2();
                    name2=((Dangkou)printer2_sp.getSelectedItem()).getValue1();
                    int id = 2;
                    String checked = "true";
                    Printer p2 = new Printer(ipadress, prot, id, checked, name2, key2);
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
                    key3=((Dangkou)printer3_sp.getSelectedItem()).getValue2();
                    name3=((Dangkou)printer3_sp.getSelectedItem()).getValue1();
                    int id = 3;
                    String checked = "true";
                    Printer p3 = new Printer(ipadress, prot, id, checked, name3, key3);
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
                    key4=((Dangkou)printer4_sp.getSelectedItem()).getValue2();
                    name4=((Dangkou)printer4_sp.getSelectedItem()).getValue1();
                    int id = 4;
                    String checked = "true";
                    Printer p4 = new Printer(ipadress, prot, id, checked, name4, key4);
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
                    key5=((Dangkou)printer5_sp.getSelectedItem()).getValue2();
                    name5=((Dangkou)printer5_sp.getSelectedItem()).getValue1();
                    int id = 5;
                    String checked = "true";
                    Printer p5 = new Printer(ipadress, prot, id, checked, name5, key5);
                    printerDao.insert(p5);
                } else {
                    //取消选中
                    printerDao.delete(printerDao.queryById(5));
                }

            }
        });


        cb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //选中
                    String ipadress = edtip_6.getText().toString();
                    String prot = edtProt6.getText().toString();
                    key6=((Dangkou)printer6_sp.getSelectedItem()).getValue2();
                    name6=((Dangkou)printer6_sp.getSelectedItem()).getValue1();
                    int id = 6;
                    String checked = "true";
                    Printer p6 = new Printer(ipadress, prot, id, checked, name6, key6);
                    printerDao.insert(p6);
                } else {
                    //取消选中
                    printerDao.delete(printerDao.queryById(6));
                }
            }
        });
        cb7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //选中
                    String ipadress = edtip_7.getText().toString();
                    String prot = edtProt7.getText().toString();
                    key7=((Dangkou)printer7_sp.getSelectedItem()).getValue2();
                    name7=((Dangkou)printer7_sp.getSelectedItem()).getValue1();
                    int id = 7;
                    String checked = "true";
                    Printer p7 = new Printer(ipadress, prot, id, checked, name7, key7);
                    printerDao.insert(p7);
                } else {
                    //取消选中
                    printerDao.delete(printerDao.queryById(7));
                }

            }
        });
    }


    private void initData() {
        if (printerDao.queryById(1)!=null){
            edtip_1.setText(printerDao.queryById(1).getIpaddress());
            edtProt1.setText(printerDao.queryById(1).getPort());
            cb1.setChecked(Boolean.valueOf(printerDao.queryById(1).getChecked()).booleanValue());
            printer1_sp.setSelection(Integer.valueOf(printerDao.queryById(1).getDangkoukey()).intValue());
        }

        if (printerDao.queryById(2)!=null){
            edtip_2.setText(printerDao.queryById(2).getIpaddress());
            edtProt2.setText(printerDao.queryById(2).getPort());
            cb2.setChecked(Boolean.valueOf(printerDao.queryById(2).getChecked()).booleanValue());
            printer2_sp.setSelection(Integer.valueOf(printerDao.queryById(2).getDangkoukey()).intValue());
        }

        if (printerDao.queryById(3)!=null){
            edtip_3.setText(printerDao.queryById(3).getIpaddress());
            edtProt3.setText(printerDao.queryById(3).getPort());
            cb3.setChecked(Boolean.valueOf(printerDao.queryById(3).getChecked()).booleanValue());
            printer3_sp.setSelection(Integer.valueOf(printerDao.queryById(3).getDangkoukey()).intValue());
        }

        if (printerDao.queryById(4)!=null){
            edtip_4.setText(printerDao.queryById(4).getIpaddress());
            edtProt4.setText(printerDao.queryById(4).getPort());
            cb4.setChecked(Boolean.valueOf(printerDao.queryById(4).getChecked()).booleanValue());
            printer4_sp.setSelection(Integer.valueOf(printerDao.queryById(4).getDangkoukey()).intValue());
        }

        if (printerDao.queryById(5)!=null){
            edtip_5.setText(printerDao.queryById(5).getIpaddress());
            edtProt5.setText(printerDao.queryById(5).getPort());
            cb5.setChecked(Boolean.valueOf(printerDao.queryById(5).getChecked()).booleanValue());
            printer5_sp.setSelection(Integer.valueOf(printerDao.queryById(5).getDangkoukey()).intValue());
        }

        if (printerDao.queryById(6)!=null){
            edtip_6.setText(printerDao.queryById(6).getIpaddress());
            edtProt6.setText(printerDao.queryById(6).getPort());
            cb6.setChecked(Boolean.valueOf(printerDao.queryById(6).getChecked()).booleanValue());
            printer6_sp.setSelection(Integer.valueOf(printerDao.queryById(6).getDangkoukey()).intValue());
        }

        if (printerDao.queryById(7)!=null){
            edtip_7.setText(printerDao.queryById(7).getIpaddress());
            edtProt7.setText(printerDao.queryById(7).getPort());
            cb7.setChecked(Boolean.valueOf(printerDao.queryById(7).getChecked()).booleanValue());
            printer7_sp.setSelection(Integer.valueOf(printerDao.queryById(7).getDangkoukey()).intValue());
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            return true;
        }
        return false;
    }

}
