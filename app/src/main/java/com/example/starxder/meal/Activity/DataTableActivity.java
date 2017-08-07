package com.example.starxder.meal.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.starxder.meal.Bean.BillDetail;
import com.example.starxder.meal.Bean.MealRecord;
import com.example.starxder.meal.Bean.MealRecordEZ;
import com.example.starxder.meal.Bean.PayDetail;
import com.example.starxder.meal.Bean.SortIncome;
import com.example.starxder.meal.Bean.TypeIncome;
import com.example.starxder.meal.Dao.MealDao;
import com.example.starxder.meal.Dao.PrinterDao;
import com.example.starxder.meal.Event.UserEvent;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */

public class DataTableActivity extends Activity {
    private TextView table_alipay_day, table_cash_day, table_Unionpay_day, table_membercard_day, table_other_day, table_wechatpay_day;
    private TextView table_allIncom_day, table_allNum_day, table_dishIncome_day, table_drinkIncome_day;
    private TextView table_dinnerIncome_day, table_lunchIncome_day, table_totalIncome;
    private  TextView table_cashBackfee_day;
//    private TextView table_alipay_month, table_cash_month, table_Unionpay_month, table_membercard_month, table_other_month, table_wechatpay_month;
//    private TextView table_allIncom_month, table_allNum_month, table_dishIncome_month, table_drinkIncome_month;

    private OkManager manager;
    List<BillDetail> billDetail;
    List<PayDetail> payDetail;
    List<SortIncome> sortIncome;
    List<TypeIncome> typeIncome;
    List<MealRecord> mealRecord;
    List<MealRecordEZ> mealRecordEZs=new ArrayList<>();
    Button print_dateAccount;

    String shopNum, dateflag;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tables);
        EventBus.getDefault().register(this);//注册EventBus
        initDay();
        initView();
        requestData();
    }


    private void initDay() {
        Calendar c = Calendar.getInstance();
        String Y, M, D;
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        Y = year + "";
        if (month < 10) {
            M = "0" + month;
        } else {
            M = month + "";
        }
        if (day < 10) {
            D = "0" + day;
        } else {
            D = day + "";
        }
        dateflag = Y + M + D;
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(UserEvent userEvent) {
        if (userEvent != null) {
            shopNum = userEvent.getUser().getShopnum();
        }
    }


    private void initView() {
        print_dateAccount = (Button) findViewById(R.id.print_dateAccount);
        table_alipay_day = (TextView) findViewById(R.id.table_alipay_day);
        table_cash_day = (TextView) findViewById(R.id.table_cash_day);
        table_Unionpay_day = (TextView) findViewById(R.id.table_Unionpaycard_day);
        table_membercard_day = (TextView) findViewById(R.id.table_member_day);
        table_other_day = (TextView) findViewById(R.id.table_other_day);
        table_wechatpay_day = (TextView) findViewById(R.id.table_wechat_day);
        table_allIncom_day = (TextView) findViewById(R.id.table_paytypeTot_day);

        table_dishIncome_day = (TextView) findViewById(R.id.table_dishIncome_day);
        table_drinkIncome_day = (TextView) findViewById(R.id.table_drinkIncome_day);

        table_dinnerIncome_day = (TextView) findViewById(R.id.table_dinner_day);
        table_lunchIncome_day = (TextView) findViewById(R.id.table_lunch_day);
        table_totalIncome = (TextView) findViewById(R.id.table_dealtypeTot_day);

        table_cashBackfee_day = (TextView)findViewById(R.id.table_cashBackfee_day);

//        table_alipay_month = (TextView) findViewById(R.id.table_alipay_month);
//        table_cash_month = (TextView) findViewById(R.id.table_cash_month);
//        table_Unionpay_month = (TextView) findViewById(R.id.table_Unionpaycard_month);
//        table_membercard_month = (TextView) findViewById(R.id.table_member_month);
//        table_other_month = (TextView) findViewById(R.id.table_other_month);
//        table_wechatpay_month = (TextView) findViewById(R.id.table_wechat_month);
//        table_allIncom_month = (TextView) findViewById(R.id.table_paytypeTot_month);
//        table_dishIncome_month = (TextView) findViewById(R.id.table_dishIncome_month);
//        table_drinkIncome_month = (TextView) findViewById(R.id.table_drinkIncome_month);

    }

    private void setData() {

        table_alipay_day.setText(payDetail.get(0).getAlipay());
        table_cash_day.setText(payDetail.get(0).getCash());
        table_Unionpay_day.setText(payDetail.get(0).getCreditcard());
        table_membercard_day.setText(payDetail.get(0).getMembercard());
        table_other_day.setText(payDetail.get(0).getOtherpay());
        table_wechatpay_day.setText(payDetail.get(0).getWechatpay());
//        Integer.valueOf(payDetail.get(0).getAlipay())+Integer.valueOf(payDetail.get(0).getCash())+Integer.valueOf(payDetail.get(0).getCreditcard())+Integer.valueOf(payDetail.get(0).getMerbercard())+Integer.valueOf(payDetail.get(0).getOtherpay())+Integer.valueOf(payDetail.get(0).getWechatpay())
        table_allIncom_day.setText(billDetail.get(0).getPayFee());
        table_dishIncome_day.setText(sortIncome.get(0).getDishIncome());
        table_drinkIncome_day.setText(sortIncome.get(0).getDrinkIncome());

        table_dinnerIncome_day.setText(typeIncome.get(0).getDinnerIncome());
        table_lunchIncome_day.setText(typeIncome.get(0).getLunchIncome());
        table_totalIncome.setText(typeIncome.get(0).getTotalIncome());

        table_cashBackfee_day.setText(billDetail.get(0).getBackFee());

        print_dateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestDataAccount();
            }
        });
    }

    private void requestDataAccount() {
        manager = OkManager.getInstance();

        //http://www.jiatuokeji.com/web-frame/mealrecord/init.do?dateflag=20170802&shopnum=3
        String jsonpath = CommonUtils.BaseUrl + "/web-frame/mealrecord/init.do?shopnum=" + shopNum + "&dateflag=" + dateflag;

        //登陆同步用户数据
        manager.asyncJsonStringByURL(jsonpath, new OkManager.Fun1() {
            @Override
            public void onResponse(String response) {
                //获取JSON字符串

                mealRecord = GsonUtils.getMealRecordByGson(response);
                MealDao md = new MealDao(DataTableActivity.this);
                MealRecordEZ mrEZ;
                for (MealRecord  mr:mealRecord){
                    mrEZ = new MealRecordEZ();
                    mrEZ.setMealid(mr.getMealid());
                    mrEZ.setMealnum(mr.getMealnum());
                    mrEZ.setMealname(md.queryBymealid(mr.getMealid()).getMealname());
                    mealRecordEZs.add(mrEZ);
                }

                Toast.makeText(getApplicationContext(), "数据加载成功", Toast.LENGTH_SHORT).show();

                PrinterDao printerDao1 = new PrinterDao(DataTableActivity.this);
                PrinterUtils p1;
                p1 = new PrinterUtils(printerDao1.queryById(1).getIpaddress(), 9100,mealRecordEZs);
                p1.PrintDataAccount();
            }

            @Override
            public void onFailure(String result) {
                Toast.makeText(getApplicationContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestData() {
        manager = OkManager.getInstance();
        //http://www.jiatuokeji.com/web-frame/summary/init.do?shopnum=1&dateflag=20170726
        String jsonpath = CommonUtils.BaseUrl + "/web-frame/summary/init.do?shopnum=" + shopNum + "&dateflag=" + dateflag;
        //登陆同步用户数据
        manager.asyncJsonStringByURL(jsonpath, new OkManager.Fun1() {
            @Override
            public void onResponse(String response) {
                //获取JSON字符串
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String bill = jsonObject.getString("billDetail");
                    String pay = jsonObject.getString("payDetail");
                    String sort = jsonObject.getString("sortIncome");
                    String type = jsonObject.getString("typeIncome");

                    billDetail = GsonUtils.getBillDetailByGson("[" + bill + "]");
                    payDetail = GsonUtils.getPayDetailByGson("[" + pay + "]");
                    sortIncome = GsonUtils.getSortIncomeByGson("[" + sort + "]");
                    typeIncome = GsonUtils.getTypeIncomeByGson("[" + type + "]");
                    Toast.makeText(getApplicationContext(), "数据加载成功", Toast.LENGTH_SHORT).show();
                    setData();

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(String result) {
                Toast.makeText(getApplicationContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
