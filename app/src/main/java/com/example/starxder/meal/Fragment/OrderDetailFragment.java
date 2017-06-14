package com.example.starxder.meal.Fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.starxder.meal.Adapter.MealAdapter;
import com.example.starxder.meal.Bean.Meal;
import com.example.starxder.meal.Bean.MealEZ;
import com.example.starxder.meal.Bean.Printer;
import com.example.starxder.meal.Bean.Wxorder;
import com.example.starxder.meal.Dao.MealDao;
import com.example.starxder.meal.Dao.PrinterDao;
import com.example.starxder.meal.Dialog.PaystyleDialog;
import com.example.starxder.meal.Event.FlagEvent;
import com.example.starxder.meal.Event.PayEvent;
import com.example.starxder.meal.R;
import com.example.starxder.meal.Utils.CommonUtils;
import com.example.starxder.meal.Utils.OkManager;
import com.example.starxder.meal.Utils.PrinterUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/22.
 */

public class OrderDetailFragment extends Fragment implements View.OnClickListener {
    TextView tv_tablenum;
    TextView tv_ordercode;
    TextView tv_preprice;
    TextView tv_discount;
    TextView tv_totalFee;
    TextView tv_paystate;
    TextView tv_paytime;
    List<MealEZ> meallist;
    ListView listview;
    MealAdapter adapter;
    TextView btn_delete;
    TextView btn_paystyle;
    TextView btn_print;
    OkManager manager;
    Wxorder wxorder;
    PaystyleDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orderdetail_fragment, container, false);
        listview = (ListView) view.findViewById(R.id.meal_list);
        initView(view);
        EventBus.getDefault().register(this);
        return view;
    }

    private void initView(View view) {
        tv_tablenum = (TextView) view.findViewById(R.id.orderdetail_tablenum);
        tv_ordercode = (TextView) view.findViewById(R.id.orderdetail_ordercode);
        tv_preprice = (TextView) view.findViewById(R.id.orderdetail_preprice);
        tv_discount = (TextView) view.findViewById(R.id.orderdetail_discount);
        tv_totalFee = (TextView) view.findViewById(R.id.orderdetail_totalFee);
        tv_paystate = (TextView) view.findViewById(R.id.orderdetail_paystate);
        tv_paytime = (TextView) view.findViewById(R.id.orderdetail_paytime);
        btn_delete = (TextView) view.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(this);
        btn_print = (TextView) view.findViewById(R.id.btn_print);
        btn_print.setOnClickListener(this);
        btn_paystyle = (TextView) view.findViewById(R.id.btn_paystyle);
        btn_paystyle.setOnClickListener(this);
        manager = OkManager.getInstance();
        int i=1;

    }


    @Subscribe
    public void onEvent(PayEvent payEvent) {
        if (payEvent != null) {

            wxorder = payEvent.getWxorder();
            dialog = new PaystyleDialog(getActivity(), wxorder);
            setValue(wxorder);
            if (wxorder.getIfpay().equals("true")) {
                btn_paystyle.setVisibility(View.INVISIBLE);
            }
            if (wxorder.getIfpay().equals("false")) {
                btn_paystyle.setVisibility(View.VISIBLE);
            }
        }
        btn_print.setEnabled(true);
        btn_print.setTextColor(Color.argb(255,255,255,255));

    }

    private void setValue(Wxorder wxorder) {
        tv_tablenum.setText("桌号:" + wxorder.getTablecode());
        tv_ordercode.setText("订单号:" + wxorder.getOutTradeNo());
        setListView(wxorder);
        if (wxorder.getOriginFee().equals("")) {
            tv_preprice.setText("原价:" + wxorder.getTotalFee() + "元");
        } else {
            tv_preprice.setText("原价:" + wxorder.getOriginFee() + "元");
        }

        if (wxorder.getFavorFee().equals("")) {
            tv_discount.setText("优惠:" + "暂无优惠");
        } else {
            tv_discount.setText("优惠:" + wxorder.getFavorFee() + "元");
        }

        tv_totalFee.setText("应付:" + wxorder.getTotalFee() + "元");

        switch (wxorder.getPaystyle()) {
            case "wx":
                tv_paystate.setText("支付状态:" + "微信支付");
                break;
            case "ali":
                tv_paystate.setText("支付状态:" + "支付宝支付");
                break;
            case "cash":
                tv_paystate.setText("支付状态:" + "现金支付");
                break;
            case "card":
                tv_paystate.setText("支付状态:" + "刷卡支付");
                break;
            case "other":
                tv_paystate.setText("支付状态:" + "其他支付");
                break;
            default:

                break;
        }

        tv_paytime.setText("支付时间:" + wxorder.getPaytime());

        if (wxorder.getIfpay().equals("false")) {
            tv_paystate.setText("支付状态:" + "未支付");
            tv_paytime.setText("");
        }


    }

    private void setListView(Wxorder wxorder) {
        //在这里根据wxorder.getDetail获取菜ID和数量，在数据库里查询对应的名称和价格
        String temp = wxorder.getDetail();
        String[] details = temp.split(";");
        String[] details_item;
        int length = details.length;
        int[] mealids = new int[length];
        int[] mealNums = new int[length];
        for (int i = 0; i < length; i++) {
            details_item = details[i].split(",");
            mealids[i] = Integer.parseInt(details_item[0]);
            mealNums[i] = Integer.parseInt(details_item[1]);
        }

        MealDao dao = new MealDao(getActivity());
        String detail = wxorder.getDetail();
        meallist = new ArrayList<MealEZ>();
        Meal meal;
        MealEZ mealEZ;
        float mealprice;
        String mealName;
        int mealNum;
        for (int i = 0; i < length; i++) {
            meal = dao.queryBymealid(mealids[i] + "");
            mealprice = Float.valueOf(meal.getMealprice());
            mealName = meal.getMealname();
            mealNum = mealNums[i];
            mealEZ = new MealEZ(mealprice, mealName, mealNum);
            meallist.add(mealEZ);
        }
        //得到meallist
        adapter = new MealAdapter(meallist, getActivity());
        listview.setAdapter(adapter);
        //meallist 通过adapter绑定到ListView
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_delete:
                try {
                    AlertDialog.Builder isExit = new AlertDialog.Builder(getActivity());
                    isExit.setTitle("删除订单");
                    isExit.setMessage("确定要删除订单号为" + wxorder.getOutTradeNo() + "的订单吗？删除后将不可找回。");
                    isExit.setCancelable(false);
                    isExit.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteOrder();
                        }
                    });
                    isExit.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    isExit.show();
                } catch (Exception e) {

                }

                break;
            case R.id.btn_print:
                if (wxorder!=null){
                    upDateIfdeal();
                    PrinterUtils printerUtils;
                    PrinterDao printerDao = new PrinterDao(getActivity());
                    for (int i=0 ;i<5;i++){
                        if (printerDao.queryById(1).getChecked().equals("true")){
                            String ip = printerDao.queryById(1).getIpaddress();
                            int prot = Integer.valueOf(printerDao.queryById(1).getProt());

                            printerUtils = new PrinterUtils(ip,prot,meallist,wxorder);
                            printerUtils.print();
                        }
                    }


                    btn_print.setEnabled(false);
                    btn_print.setTextColor(Color.argb(55,255,255,255));

                }
                break;

            case R.id.btn_paystyle:
                dialog.show();
                break;
            default:
        }
    }


    private void upDateIfdeal() {

        String ifDealPath = CommonUtils.BaseUrl + "web-frame/wxorder/updateDealByCode.do?outTradeNo=" + wxorder.getOutTradeNo() + "&ifdeal=true";
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60 * 1000, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(60 * 1000, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(60 * 1000, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        //使用Request.Builder来创建请求对象
        Request.Builder builder = new Request.Builder();
        //指定使用GET请求,并且指定要请求的地址
        Request request = builder.get().url(ifDealPath).build();
        //将请求加入请求队列,将请求封装成Call对象
        Call call = client.newCall(request);
        //使用异步的方式来得到请求的响应并且处理
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().postSticky(new FlagEvent(true));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String a = response.body().string();
                if (a.equals("true")){
                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "出票成功", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().postSticky(new FlagEvent(true));
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "请求出错", Toast.LENGTH_SHORT).show();
                                EventBus.getDefault().postSticky(new FlagEvent(true));
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }

    private void deleteOrder() {
        String deletePath = CommonUtils.BaseUrl + "web-frame/wxorder/deleteByCode.do?outTradeNo=" + wxorder.getOutTradeNo();

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60 * 1000, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(60 * 1000, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(60 * 1000, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        //使用Request.Builder来创建请求对象
        Request.Builder builder = new Request.Builder();
        //指定使用GET请求,并且指定要请求的地址
        Request request = builder.get().url(deletePath).build();
        //将请求加入请求队列,将请求封装成Call对象
        Call call = client.newCall(request);
        //使用异步的方式来得到请求的响应并且处理
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "网络异常，删除失败", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().postSticky(new FlagEvent(true));
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().postSticky(new FlagEvent(true));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(FlagEvent flagEvent) {
        if (flagEvent != null) {
            if (flagEvent.getFlag()) {
                dialog.cancel();
            }
        }
    }

}
