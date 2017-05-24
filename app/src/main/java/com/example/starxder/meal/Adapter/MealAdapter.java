package com.example.starxder.meal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.starxder.meal.Bean.Meal;
import com.example.starxder.meal.Bean.MealEZ;
import com.example.starxder.meal.Bean.Wxorder;
import com.example.starxder.meal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/23.
 */

public class MealAdapter extends BaseAdapter{
    private List<MealEZ> common_list = new ArrayList<MealEZ>();
    Context context;
    LayoutInflater inflater;
    private MealHolder holder;

    public MealAdapter(List<MealEZ> common_list, Context context) {
        this.common_list = common_list;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return common_list.size();
    }

    @Override
    public Object getItem(int position) {
        return common_list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        //加载布局为一个视图

        if (view == null) {
            view=inflater.inflate(R.layout.meal_listitem,null);
            holder = new MealAdapter.MealHolder();
            holder.mealName = (TextView) view.findViewById(R.id.meal_name);
            holder.mealNum = (TextView) view.findViewById(R.id.mealnum);
            holder.mealPrice = (TextView) view.findViewById(R.id.meal_price);
            view.setTag(holder);
        } else {
            holder = (MealAdapter.MealHolder) view.getTag();
        }

        final MealEZ model = common_list.get(position);


        holder.mealName.setText(model.getMealName());
        holder.mealNum.setText("×"+model.getMealNum());
        holder.mealPrice.setText(model.getMealprice()+"元");

        return view;
    }

    class MealHolder {
        TextView mealName;
        TextView mealNum;
        TextView mealPrice;
    }
}
