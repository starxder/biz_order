package com.example.starxder.meal.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.starxder.meal.Bean.Wxorder;
import com.example.starxder.meal.R;

import java.util.ArrayList;
import java.util.List;

public class CommonPayAdapter extends BaseAdapter{
	
	private List<Wxorder> common_list = new ArrayList<Wxorder>();
	Context ctx;
	LayoutInflater inflater;
	private HlaHolder holder;
	
	public CommonPayAdapter(Context ctx, List<Wxorder> common_list) {
		this.common_list = common_list;
		this.ctx = ctx;
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if (view == null) {
			view = inflater.inflate(R.layout.order_listitem, null);
			holder = new HlaHolder();
			holder.outTradeNo = (TextView) view.findViewById(R.id.order_code);
			holder.tablecode = (TextView) view.findViewById(R.id.table_code);
			holder.totalFee = (TextView) view.findViewById(R.id.totalFee);
			holder.ifprint = (TextView) view.findViewById(R.id.ifprint);
			view.setTag(holder);
		} else {
			holder = (HlaHolder) view.getTag();
		}
		final Wxorder model = common_list.get(position);

		holder.outTradeNo.setText("订单号:"+model.getOutTradeNo());
		holder.tablecode.setText(model.getTablecode());
		holder.totalFee.setText("总价:"+model.getTotalFee());
		if(model.getTakeout().equals("true")){
			String temp = model.getTakeoutInfo();
			String[] details = temp.split(";");
			holder.tablecode.setText(details[1]);
		}

		if(model.getIfdeal().equals("false")){
			holder.ifprint.setText("未出票");
			holder.ifprint.setTextColor(0xffff0000);
		}else {
			holder.ifprint.setText("已出票");
			holder.ifprint.setTextColor(0xffffffff);
		}

		return view;
	}

	class HlaHolder {
		TextView outTradeNo;
		TextView tablecode;
		TextView totalFee;
		TextView ifprint;
	}
}
