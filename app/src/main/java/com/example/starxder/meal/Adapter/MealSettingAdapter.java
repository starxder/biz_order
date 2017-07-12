package com.example.starxder.meal.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.starxder.meal.Bean.Meal;
import com.example.starxder.meal.R;
import com.example.starxder.meal.Utils.CommonUtils;
import com.example.starxder.meal.Utils.OkManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/6.
 */

public class MealSettingAdapter extends BaseAdapter {
    private List<Meal> common_list = new ArrayList<Meal>();
    Context context;
    LayoutInflater inflater;
    private MealSettingAdapter.MealHolder holder;
    OkManager manager;


    public MealSettingAdapter(List<Meal> common_list, Context context) {
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
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        manager = OkManager.getInstance();

        if (view == null) {
            view=inflater.inflate(R.layout.mealsetting_listitem,null);
            holder = new MealSettingAdapter.MealHolder();
            holder.mealName = (TextView) view.findViewById(R.id.mealsetting_mealname);
            holder.mealPrice = (TextView) view.findViewById(R.id.mealsetting_mealprice);
            holder.ifSoldout = (CheckBox)view.findViewById(R.id.mealsetting_ifsoldout) ;

            view.setTag(holder);
        } else {
            holder = (MealSettingAdapter.MealHolder) view.getTag();
        }

        final Meal model = common_list.get(position);


        holder.mealName.setText(model.getMealname());
        holder.mealPrice.setText(model.getMealprice()+"元");
        if(Boolean.valueOf(model.getIfsoldout())){
            holder.ifSoldout.setChecked(true);
        }else{
            holder.ifSoldout.setChecked(false);
        }

        holder.ifSoldout.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //选中
//                    http://www.jiatuokeji.com/web-frame/meal/updateByParam.do?shopnum=1&mindex=1&ifsoldout=true&mealprice=1&delflag=false
                    request(model,"true");
                } else {
                    //取消选中
                    request(model,"false");
                }
            }
        });





        return view;
    }

    class MealHolder{
        TextView mealName;
        TextView mealPrice;
        CheckBox ifSoldout;
    }


    private void request(Meal model,String b) {
        String path = CommonUtils.BaseUrl + "/web-frame/meal/updateByParam.do?shopnum="+model.getShopnum()+"&mindex="+model.getMindex()+"&ifsoldout="+b+"&mealprice="+model.getMealprice()+"&delflag=false" ;
        manager.synaGetByUrl(path);
    }
}
