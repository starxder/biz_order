package com.example.starxder.meal.Fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.starxder.meal.Dialog.BackMealDialog;
import com.example.starxder.meal.Dialog.PaystyleDialog;
import com.example.starxder.meal.Event.BackmealEvent;
import com.example.starxder.meal.Event.FlagEvent;
import com.example.starxder.meal.Event.PayEvent;
import com.example.starxder.meal.Event.PaystyleEvent;
import com.example.starxder.meal.R;
import com.example.starxder.meal.Utils.CommonUtils;
import com.example.starxder.meal.Utils.GsonUtils;
import com.example.starxder.meal.Utils.OkManager;
import com.example.starxder.meal.Utils.PrinterUtils;

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

/**
 * Created by Administrator on 2017/5/22.
 */

public class OrderDetailFragment extends Fragment implements View.OnClickListener {
    TextView tv_tablenum;
    TextView tv_ordercode;
    TextView tv_preprice;
    TextView tv_discount;
    TextView tv_bonus;
    TextView tv_totalFee;
    TextView tv_paystate;
    TextView tv_paytime;
    TextView tv_remark;
    TextView tv_back_detail;
    TextView tv_back_fee;
    TextView tv_turnover;
    List<MealEZ> meallist;
    List<MealEZ> backmeallist;
    ListView listview;
    ListView backlistview;
    MealAdapter adapter;
    MealAdapter backadapter;
    TextView btn_delete;
    TextView btn_paystyle;
    TextView btn_print;
    OkManager manager;
    Wxorder wxorder;
    PaystyleDialog paystyleDialog;
    BackMealDialog backMealDialog;
    private List<Wxorder> nowList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orderdetail_fragment, container, false);
        initView(view);
        EventBus.getDefault().register(this);
        return view;
    }

    private void initView(View view) {
        listview = (ListView) view.findViewById(R.id.meal_list);
        backlistview = (ListView) view.findViewById(R.id.backmeal_list);
        tv_tablenum = (TextView) view.findViewById(R.id.orderdetail_tablenum);
        tv_ordercode = (TextView) view.findViewById(R.id.orderdetail_ordercode);
        tv_preprice = (TextView) view.findViewById(R.id.orderdetail_preprice);
        tv_discount = (TextView) view.findViewById(R.id.orderdetail_discount);
        tv_bonus = (TextView)view.findViewById(R.id.orderdetail_bonus);
        tv_totalFee = (TextView) view.findViewById(R.id.orderdetail_totalFee);
        tv_paystate = (TextView) view.findViewById(R.id.orderdetail_paystate);
        tv_paytime = (TextView) view.findViewById(R.id.orderdetail_paytime);
        tv_remark = (TextView) view.findViewById(R.id.orderdetail_remark);
        tv_back_detail = (TextView) view.findViewById(R.id.back_detail);
        tv_back_fee = (TextView)view.findViewById(R.id.backdetail_backfee);
        tv_turnover = (TextView)view.findViewById(R.id.backdetail_turnover);
        btn_delete = (TextView) view.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(this);
        btn_print = (TextView) view.findViewById(R.id.btn_print);
        btn_print.setOnClickListener(this);
        btn_paystyle = (TextView) view.findViewById(R.id.btn_paystyle);
        btn_paystyle.setOnClickListener(this);
        manager = OkManager.getInstance();
        int i = 1;

    }


    @Subscribe
    public void onEvent(PayEvent payEvent) {
        if (payEvent != null) {

            wxorder = payEvent.getWxorder();
            paystyleDialog = new PaystyleDialog(getActivity(), wxorder);
            setValue(wxorder);
            if (wxorder.getIfpay().equals("true")) {
                btn_paystyle.setVisibility(View.INVISIBLE);
            }
            if (wxorder.getIfpay().equals("false")) {
                btn_paystyle.setVisibility(View.VISIBLE);
            }
        }
        btn_print.setEnabled(true);
        btn_print.setTextColor(Color.argb(255, 255, 255, 255));

    }

    private void setValue(Wxorder wxorder) {
        float faverfee = 0;
        float bonus = 0;
        tv_tablenum.setText("桌号：" + wxorder.getTablecode());
        tv_ordercode.setText("订单号：" + wxorder.getOutTradeNo());
        if (wxorder.getTakeout().equals("true")) {
            String temp = wxorder.getTakeoutInfo();
            String[] details = temp.split(";");

            tv_tablenum.setText("姓名：" + details[0] + "   " + "电话：" + details[1]);
            tv_ordercode.setText("地址：" + details[2]);
        }


        tv_preprice.setText("原价：" + wxorder.getOriginFee()+ "元");


        if (wxorder.getFavorFee().equals("") || wxorder.getFavorFee().equals("0")) {
            faverfee = 0;
            tv_discount.setText("优惠券优惠：" + "无");
        } else {
            faverfee = Float.valueOf(wxorder.getFavorFee());
            tv_discount.setText("优惠券优惠：" + wxorder.getFavorFee() + "元");
        }

        if (wxorder.getBonus().equals("") || wxorder.getBonus().equals("0")) {
            bonus = 0;
            tv_bonus.setText("会员卡积分：未使用积分");
        } else {
            bonus = Float.valueOf(wxorder.getBonus());
            tv_bonus.setText("会员卡积分："+bonus/10+"元");
        }

        tv_totalFee.setText("应付：" + (Float.valueOf(wxorder.getOriginFee()) - faverfee - (bonus/10)) + "元");

        if (wxorder.getRemark() == null || wxorder.getRemark().equals("undefined")) {
            tv_remark.setText("备注：无");
        } else {
            tv_remark.setText("备注：" + wxorder.getRemark());
        }


        switch (wxorder.getPaystyle()) {
            case "wx":
                tv_paystate.setText("支付状态：" + "微信支付");
                break;
            case "ali":
                tv_paystate.setText("支付状态：" + "支付宝支付");
                break;
            case "cash":
                tv_paystate.setText("支付状态：" + "现金支付");
                break;
            case "card":
                tv_paystate.setText("支付状态：" + "刷卡支付");
                break;
            case "other":
                tv_paystate.setText("支付状态：" + "其他支付");
                break;
            default:

                break;
        }

        tv_paytime.setText("支付时间：" + wxorder.getPaytime());

        if (wxorder.getIfpay().equals("false")) {
            tv_paystate.setText("支付状态：" + "未支付");
            tv_paytime.setText("");
        }



        setListView(wxorder);
        setBackListView(wxorder,faverfee);

    }

    private void setListView(final Wxorder wxorder) {
        //在这里根据wxorder.getDetail获取菜ID和数量，在数据库里查询对应的名称和价格
        String temp = wxorder.getDetail();
        String[] details = temp.split(";");
        String[] details_item;
        int length = details.length;
        final int[] mealids = new int[length];
        final int[] mealNums = new int[length];
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
            mealEZ = new MealEZ(mealprice, mealName, mealNum,meal.getMealtype2(),meal.getMealtypename2());
            meallist.add(mealEZ);
        }
        //得到meallist
        adapter = new MealAdapter(meallist, getActivity());
        listview.setAdapter(adapter);
        //meallist 通过adapter绑定到ListView
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int mealid = mealids[i];
                int mealnum = mealNums[i];
                backMealDialog = new BackMealDialog(getActivity(), wxorder, mealid, mealnum);
                backMealDialog.show();
            }
        });
    }

    private void setBackListView(final Wxorder wxorder,float f) {
        //在这里根据wxorder.getDetail获取菜ID和数量，在数据库里查询对应的名称和价格
        String temp = wxorder.getBackDetail();
        if (temp == null) {
            tv_back_detail.setText("");
            tv_back_fee.setText("");
            tv_turnover.setText("");

            backmeallist = new ArrayList<MealEZ>();
            backadapter = new MealAdapter(backmeallist, getActivity());
            backlistview.setAdapter(backadapter);

        } else {
            tv_back_detail.setText("退菜详情");
            tv_back_fee.setText("退款："+wxorder.getBackFee());
            tv_turnover.setText("实际消费："+(Float.valueOf(wxorder.getTotalFee()) - Float.valueOf(wxorder.getBackFee())));

            String[] details = temp.split(";");
            String[] details_item;
            int length = details.length;
            final int[] mealids = new int[length];
            final int[] mealNums = new int[length];
            for (int i = 0; i < length; i++) {
                details_item = details[i].split(",");
                mealids[i] = Integer.parseInt(details_item[0]);
                mealNums[i] = Integer.parseInt(details_item[1]);
            }

            MealDao dao = new MealDao(getActivity());
            String backdetail = wxorder.getDetail();
            backmeallist = new ArrayList<>();
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
                mealEZ = new MealEZ(mealprice, mealName, mealNum,meal.getMealtype2(),meal.getMealtypename2());
                backmeallist.add(mealEZ);
            }
            //得到meallist
            backadapter = new MealAdapter(backmeallist, getActivity());
            backlistview.setAdapter(backadapter);
            //meallist 通过adapter绑定到ListView
        }
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
                if (wxorder != null) {
                    upDateIfdeal();
                    PrinterUtils printerUtils;
                    PrinterDao printerDao = new PrinterDao(getActivity());
                    for (int i = 1; i <= 7; i++) {
                        Printer printer = printerDao.queryById(i);
                        if (printer != null) {
                            if (printer.getChecked().equals("true")) {
                                //如果打印全部
                                if(printer.getDangkoukey().equals("0")){
                                    String ip = printer.getIpaddress();
                                    int port = Integer.valueOf(printer.getPort());
                                    printerUtils = new PrinterUtils(ip, port, meallist, wxorder);
                                    printerUtils.print();
                                }else{
                                    //获取档口索引key
                                    String index = printer.getDangkoukey();
                                    String indexName = printer.getDangkouname();
                                    List<MealEZ> meallistIndex =  new ArrayList<>();
                                    for(MealEZ meal:meallist){
                                        if(meal.getMealtype2().equals(index)){
                                            meallistIndex.add(meal);
                                        }
                                    }
                                    if(meallistIndex.size()>0){
                                        String ip = printer.getIpaddress();
                                        int port = Integer.valueOf(printer.getPort());
                                        printerUtils = new PrinterUtils(ip, port, meallistIndex, wxorder, indexName);
                                        printerUtils.printIndex();
                                    }
                                }

                            }
                        }

                    }
                    btn_print.setEnabled(false);
                    btn_print.setTextColor(Color.argb(55, 255, 255, 255));
                }
                break;

            case R.id.btn_paystyle:
                paystyleDialog.show();
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
                if (a.equals("true")) {
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
                } else {
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
    public void onMessageEvent(PaystyleEvent paystyleEvent) {
        if (paystyleEvent != null) {
            if (paystyleEvent.getFlag()) {
                paystyleDialog.cancel();
                Wxorder wxorder = requestjson(paystyleEvent.getOutTradeNo());
                EventBus.getDefault().post(new PayEvent(wxorder, ""));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(BackmealEvent backmealEvent) {
        if (backmealEvent != null) {
            if (backmealEvent.getFlag()) {
                backMealDialog.cancel();
                requestjson(backmealEvent.getOutTradeNo());
            }
        }
    }

    private Wxorder requestjson(String s) {

        String path = CommonUtils.BaseUrl + "/web-frame/wxorder/queryByNo.do?outTradeNo=" + s;
        //登陆同步用户数据
        manager.asyncJsonStringByURL(path, new OkManager.Fun1() {
            @Override
            public void onResponse(String response) {
                Log.i("LoginActivity", response);   //获取JSON字符串
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String error = jsonObject.getString("error");
                    String result = jsonObject.getString("result");
                    if (error.equals("")) {
                        nowList = GsonUtils.getWxOrderByGson(result);
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setValue(nowList.get(0));
            }

            @Override
            public void onFailure(String result) {
                Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
        return nowList.get(0);
    }

    public static String format(float value) {

        return String.format("%.2f", value).toString();
    }

}
