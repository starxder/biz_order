package com.example.starxder.meal.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.starxder.meal.Adapter.CommonPayAdapter;
import com.example.starxder.meal.Bean.User;
import com.example.starxder.meal.Bean.Wxorder;
import com.example.starxder.meal.Event.FlagEvent;
import com.example.starxder.meal.Event.PayEvent;
import com.example.starxder.meal.Event.UserEvent;
import com.example.starxder.meal.R;
import com.example.starxder.meal.Utils.CommonUtils;
import com.example.starxder.meal.Utils.GsonUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class TakeoutFragment extends Fragment {

    List<Wxorder> pay_list = null;
    CommonPayAdapter adapter;
    ListView listview;
    String shopNum;
    User user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pay_fragment, container, false);
        pay_list = new ArrayList<Wxorder>();
        listview = (ListView) view.findViewById(R.id.pay_orderlist);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EventBus.getDefault().post(new PayEvent(pay_list.get(i), i + ""));
            }
        });
        //注册EventBus
        EventBus.getDefault().register(this);

        return view;


    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(UserEvent userEvent) {
        if (userEvent != null) {
            user = userEvent.getUser();
            shopNum = user.getShopnum();
            loadData(shopNum);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(FlagEvent flagEvent) {
        if (flagEvent != null) {
            if (flagEvent.getFlag()) {
                loadData(shopNum);
            }
        }
    }


    //加载数据
    private void loadData(String shopNum) {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60 * 1000, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(60 * 1000, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(60 * 1000, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        //使用Request.Builder来创建请求对象
        Request.Builder builder = new Request.Builder();
        //指定使用GET请求,并且指定要请求的地址
        Request request = builder.get().url(CommonUtils.BaseUrl + "web-frame/wxorder/queryTakeoutByParam.do?shopnum=" + shopNum).build();
        //将请求加入请求队列,将请求封装成Call对象
        Call call = client.newCall(request);
        //使用异步的方式来得到请求的响应并且处理
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String error;
                String result;
                String abc = response.body().string();
                Log.d(TAG, "onResponse: " + abc);
                try {
                    JSONObject jsonObject = new JSONObject(abc);
                    error = jsonObject.getString("error");
                    result = jsonObject.getString("result");
                    if (error.equals("")) {
                        pay_list = GsonUtils.getWxOrderByGson(result);
                        adapter = new CommonPayAdapter(getActivity(), pay_list);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listview.setAdapter(adapter);
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
