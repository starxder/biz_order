package com.example.starxder.meal.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.example.starxder.meal.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/4.
 */

public class ToolsActivity extends Activity {

    private GridView gview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    // 图片封装为一个数组
    private int[] icon = {R.mipmap.icon_printersetting,
            R.mipmap.icon_keysetting,
            R.mipmap.icon_mealsetting,
            R.mipmap.icon_tables,
            R.mipmap.ic_launcher  };
    private String[] iconName = {"打印机设置", "密码设置", "菜品设置", "数据报表", "待开发"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
        gview = (GridView) findViewById(R.id.gview);
        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String[] from = {"image", "text"};
        int[] to = {R.id.grid_image, R.id.grid_text};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.grid_item, from, to);
        //配置适配器
        gview.setAdapter(sim_adapter);
        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (icon[i]){  //构建起来的图标数组
                    case R.mipmap.icon_printersetting:
                        Intent intent = new Intent();
                        intent.setClass(ToolsActivity.this, PrinterSetActivity.class);      //运行另外一个类的活动
                        startActivityForResult(intent, 1);
                        break;
                    case R.mipmap.icon_keysetting:
                        Intent intent1 = new Intent();
                        intent1.setClass(ToolsActivity.this, KeySetActivity.class);      //运行另外一个类的活动
                        startActivityForResult(intent1, 1);
                        break;
                    case R.mipmap.icon_mealsetting:
                        Intent intent2 = new Intent();
                        intent2.setClass(ToolsActivity.this, MealSetActivity.class);      //运行另外一个类的活动
                        startActivityForResult(intent2, 1);
                        break;
                    case R.mipmap.icon_tables:
                        Intent intent3 = new Intent();
                        intent3.setClass(ToolsActivity.this, DataTableActivity.class);      //运行另外一个类的活动
                        startActivityForResult(intent3, 1);
                        break;

                    default:
                        break;
                }
            }
        });

    }


    public List<Map<String, Object>> getData() {
        //icon和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }

        return data_list;
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
