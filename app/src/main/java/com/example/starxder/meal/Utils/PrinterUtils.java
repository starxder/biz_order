package com.example.starxder.meal.Utils;

import android.app.Activity;
import android.util.Log;

import com.example.starxder.meal.Bean.MealEZ;
import com.example.starxder.meal.Bean.MealRecord;
import com.example.starxder.meal.Bean.MealRecordEZ;
import com.example.starxder.meal.Bean.Wxorder;
import com.example.starxder.meal.Dao.MealDao;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/6/13.
 */

public class PrinterUtils {
    String ip_address;
    int mPort;
    List<MealEZ> mealez;
    Wxorder wxorder;
    private Pos pos;
    String indexName;
    List<MealRecordEZ> mealRecordEZ;

    public PrinterUtils(String ip_address, int mPort, List<MealEZ> mealez, Wxorder wxorder) {
        this.ip_address = ip_address;
        this.mPort = mPort;
        this.mealez = mealez;
        this.wxorder = wxorder;
    }

    public PrinterUtils(String ip_address, int mPort, List<MealEZ> mealez, Wxorder wxorder, String indexName) {
        this.ip_address = ip_address;
        this.mPort = mPort;
        this.mealez = mealez;
        this.wxorder = wxorder;
        this.indexName = indexName;
    }

    public PrinterUtils(String ip_address, int mPort, List<MealRecordEZ> mealRecordEZ) {
        this.ip_address = ip_address;
        this.mPort = mPort;
        this.mealRecordEZ = mealRecordEZ;
    }

    public boolean print() {
        if (!ip_address.equals("")) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        pos = new Pos(ip_address, mPort, "GBK");
                        //初始化打印机
                        pos.initPos();


                        pos.printTabSpace(2);
                        pos.printWordSpace(1);
                        pos.bold(false);
                        pos.printText(wxorder.getBody());

                        pos.printLocation(0);
                        pos.printTextNewLine("----------------------------------------------");
                        pos.bold(false);
                        pos.printTextNewLine("订 单 号：" + wxorder.getOutTradeNo());
                        if (wxorder.getTakeout().equals("true")) {
                            String temp = wxorder.getTakeoutInfo();
                            String[] details = temp.split(";");

                            pos.printTextNewLine("姓  名：" + details[0]);
                            pos.printTextNewLine("电  话：" + details[1]);
                            pos.printTextNewLine("地  址：" + details[2]);
                        } else {
                            pos.printTextNewLine("桌  号：" + wxorder.getTablecode() + "号桌");
                        }

                        if (wxorder.getIfpay().equals("true")) {
                            pos.printTextNewLine("订单状态：已支付");
                        } else {
                            pos.printTextNewLine("订单状态：未支付");
                        }

                        if (wxorder.getRemark() == null || wxorder.getRemark().equals("undefined")) {
                            pos.printTextNewLine("备注：无");
                        } else {
                            pos.printTextNewLine("备注：" + wxorder.getRemark());
                        }

                        pos.printLine(2);

                        pos.printText("菜品");
                        pos.printLocation(20, 1);
                        pos.printText("     ");
                        pos.printLocation(99, 1);
                        pos.printWordSpace(1);
                        pos.printText("数量");
                        pos.printWordSpace(3);
                        pos.printText("单价");
                        pos.printTextNewLine("----------------------------------------------");


                        for (MealEZ meals : mealez) {
                            pos.printTextNewLine(meals.getMealName());
                            pos.printLocation(20, 1);
                            pos.printText("         ");
                            pos.printLocation(99, 1);
                            pos.printWordSpace(1);
                            pos.printText(meals.getMealNum() + "");
                            pos.printWordSpace(3);
                            pos.printText(meals.getMealprice() + "元");
                        }

                        pos.printTextNewLine("----------------------------------------------");

                        if ((!wxorder.getFavorFee().equals("0")) && (!wxorder.getFavorFee().equals("")) && (wxorder.getFavorFee() != null)) {
                            pos.printTextNewLine("");
                            pos.printLocation(20, 1);
                            pos.printText("");
                            pos.printLocation(99, 1);
                            pos.printWordSpace(1);
                            pos.printText("原价");
                            pos.printWordSpace(3);
                            pos.printText(wxorder.getOriginFee() + "元");

                            pos.printTextNewLine("");
                            pos.printLocation(20, 1);
                            pos.printText("");
                            pos.printLocation(99, 1);
                            pos.printWordSpace(1);
                            pos.printText("优惠");
                            pos.printWordSpace(3);
                            pos.printText(wxorder.getFavorFee() + "元");
                        }

                        if ((!wxorder.getBonus().equals("0")) && (!wxorder.getBonus().equals("")) && (wxorder.getBonus() != null)) {

                            pos.printTextNewLine("");
                            pos.printLocation(20, 1);
                            pos.printText("");
                            pos.printLocation(99, 1);
                            pos.printWordSpace(1);
                            pos.printText("积分抵扣");
                            pos.printWordSpace(3);
                            pos.printText(Float.valueOf(wxorder.getBonus()) / 10 + "元");
                        }

                        pos.printTextNewLine("");
                        pos.printLocation(20, 1);
                        pos.printText("");
                        pos.printLocation(99, 1);
                        pos.printWordSpace(1);
                        pos.printText("总计");
                        pos.printWordSpace(3);
                        pos.printText(wxorder.getTotalFee() + "元");

                        if (!wxorder.getBackFee().equals("")&&Float.valueOf(wxorder.getBackFee()) > 0) {
                            pos.printTextNewLine("");
                            pos.printLocation(20, 1);
                            pos.printText("");
                            pos.printLocation(99, 1);
                            pos.printWordSpace(1);
                            pos.printText("退款");
                            pos.printWordSpace(3);
                            pos.printText(wxorder.getBackFee() + "元");
                        }

                        pos.printLine(2);

//                    long divider = 2000;
//                    try {
//                        Thread.sleep(divider);
//                        //打印二维码
//                        pos.qrCode("http://blog.csdn.net/haovip123");
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    try {
//                        Thread.sleep(divider);
//                        //切纸
//                        pos.feedAndCut();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }

                        pos.feedAndCut();
                        pos.closeIOAndSocket();
                        pos = null;


                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();


//            new Thread() {
//                @Override
//                public void run() {
//                    try {
//                        pos = new Pos(ip_address, mPort, "GBK");
//                        //初始化打印机
//                        pos.initPos();
//
////                    //打印二维码
////                    pos.qrCode("http://blog.csdn.net/haovip123");
//
//
//                        //切纸
//                        pos.feedAndCut();
//
//                        pos.closeIOAndSocket();
//                        pos = null;
//                    } catch (UnknownHostException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }.start();
        }
        return true;
    }

    public boolean printIndex() {

        if (!ip_address.equals("")) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        pos = new Pos(ip_address, mPort, "GBK");
                        //初始化打印机
                        pos.initPos();


                        pos.bold(true);
                        pos.printTabSpace(2);
                        pos.printWordSpace(1);
                        pos.printText(indexName);

                        pos.printLocation(0);
                        pos.printTextNewLine("----------------------------------------------");
                        pos.bold(false);
                        pos.printTextNewLine("订 单 号：" + wxorder.getOutTradeNo());
                        if (wxorder.getTakeout().equals("true")) {
                            String temp = wxorder.getTakeoutInfo();
                            String[] details = temp.split(";");

                            pos.printTextNewLine("姓  名：" + details[0]);
                            pos.printTextNewLine("电  话：" + details[1]);
                            pos.printTextNewLine("地  址：" + details[2]);
                        } else {
                            pos.printTextNewLine("桌  号：" + wxorder.getTablecode() + "号桌");
                        }

                        if (wxorder.getIfpay().equals("true")) {
                            pos.printTextNewLine("订单状态：已支付");
                        } else {
                            pos.printTextNewLine("订单状态：未支付");
                        }

                        if (wxorder.getRemark() == null || wxorder.getRemark().equals("undefined")) {
                            pos.printTextNewLine("备注：无");
                        } else {
                            pos.printTextNewLine("备注：" + wxorder.getRemark());
                        }

                        pos.printLine(2);

                        pos.printText("菜品");
                        pos.printLocation(20, 1);
                        pos.printText("     ");
                        pos.printLocation(99, 1);
                        pos.printWordSpace(1);
                        pos.printText("数量");
                        pos.printWordSpace(3);
                        pos.printText("单价");
                        pos.printTextNewLine("----------------------------------------------");


                        for (MealEZ meals : mealez) {
                            pos.printTextNewLine(meals.getMealName());
                            pos.printLocation(20, 1);
                            pos.printText("         ");
                            pos.printLocation(99, 1);
                            pos.printWordSpace(1);
                            pos.printText(meals.getMealNum() + "");
                            pos.printWordSpace(3);
                            pos.printText(meals.getMealprice() + "元");
                        }

                        pos.printTextNewLine("----------------------------------------------");


                        pos.printLine(2);


                        pos.feedAndCut();
                        pos.closeIOAndSocket();
                        pos = null;


                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();

        }
        return true;
    }


    public boolean printAccount() {

        if (!ip_address.equals("")) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        pos = new Pos(ip_address, mPort, "GBK");
                        //初始化打印机
                        pos.initPos();


                        pos.printTabSpace(2);
                        pos.printWordSpace(1);
                        pos.bold(false);
                        pos.printText(wxorder.getBody());

                        pos.printLocation(0);
                        pos.printTextNewLine("------------------[结帐单]--------------------");
                        pos.bold(false);
                        pos.printTextNewLine("订 单 号：" + wxorder.getOutTradeNo());
                        if (wxorder.getTakeout().equals("true")) {
                            String temp = wxorder.getTakeoutInfo();
                            String[] details = temp.split(";");

                            pos.printTextNewLine("姓  名：" + details[0]);
                            pos.printTextNewLine("电  话：" + details[1]);
                            pos.printTextNewLine("地  址：" + details[2]);
                        } else {
                            pos.printTextNewLine("桌  号：" + wxorder.getTablecode() + "号桌");
                        }

                        if (wxorder.getIfpay().equals("true")) {
                            pos.printTextNewLine("订单状态：已支付");
                        } else {
                            pos.printTextNewLine("订单状态：未支付");
                        }

                        if (wxorder.getRemark() == null || wxorder.getRemark().equals("undefined")) {
                            pos.printTextNewLine("备注：无");
                        } else {
                            pos.printTextNewLine("备注：" + wxorder.getRemark());
                        }

                        pos.printLine(2);

                        pos.printText("菜品");
                        pos.printLocation(20, 1);
                        pos.printText("     ");
                        pos.printLocation(99, 1);
                        pos.printWordSpace(1);
                        pos.printText("数量");
                        pos.printWordSpace(3);
                        pos.printText("单价");
                        pos.printTextNewLine("----------------------------------------------");


                        for (MealEZ meals : mealez) {
                            pos.printTextNewLine(meals.getMealName());
                            pos.printLocation(20, 1);
                            pos.printText("         ");
                            pos.printLocation(99, 1);
                            pos.printWordSpace(1);
                            pos.printText(meals.getMealNum() + "");
                            pos.printWordSpace(3);
                            pos.printText(meals.getMealprice() + "元");
                        }

                        pos.printTextNewLine("----------------------------------------------");

                        if ((!wxorder.getFavorFee().equals("0")) && (!wxorder.getFavorFee().equals("")) && (wxorder.getFavorFee() != null)) {
                            pos.printTextNewLine("");
                            pos.printLocation(20, 1);
                            pos.printText("");
                            pos.printLocation(99, 1);
                            pos.printWordSpace(1);
                            pos.printText("原价");
                            pos.printWordSpace(3);
                            pos.printText(wxorder.getOriginFee() + "元");

                            pos.printTextNewLine("");
                            pos.printLocation(20, 1);
                            pos.printText("");
                            pos.printLocation(99, 1);
                            pos.printWordSpace(1);
                            pos.printText("优惠");
                            pos.printWordSpace(3);
                            pos.printText(wxorder.getFavorFee() + "元");
                        }

                        if ((!wxorder.getBonus().equals("0")) && (!wxorder.getBonus().equals("")) && (wxorder.getBonus() != null)) {

                            pos.printTextNewLine("");
                            pos.printLocation(20, 1);
                            pos.printText("");
                            pos.printLocation(99, 1);
                            pos.printWordSpace(1);
                            pos.printText("积分抵扣");
                            pos.printWordSpace(3);
                            pos.printText(Float.valueOf(wxorder.getBonus()) / 10 + "元");
                        }

                        pos.printTextNewLine("");
                        pos.printLocation(20, 1);
                        pos.printText("");
                        pos.printLocation(99, 1);
                        pos.printWordSpace(1);
                        pos.printText("总计");
                        pos.printWordSpace(3);
                        pos.printText(wxorder.getTotalFee() + "元");


                        pos.printTextNewLine("");
                        pos.printLocation(20, 1);
                        pos.printText("");
                        pos.printLocation(99, 1);
                        pos.printWordSpace(1);
                        pos.printText("支付方式");
                        pos.printWordSpace(3);
                        switch (wxorder.getPaystyle()) {
                            case "wx":
                                pos.printText("微信");
                                break;
                            case "ali":
                                pos.printText("支付宝");
                                break;
                            case "cash":
                                pos.printText("现金");
                                break;
                            case "card":
                                pos.printText("银行卡");
                                break;
                            case "member":
                                pos.printText("储值卡");
                                break;
                            case "other":
                                pos.printText("其他");
                                break;
                            default:

                                break;
                        }


                        if (!wxorder.getBackFee().equals("")&&Float.valueOf(wxorder.getBackFee()) > 0) {
                            pos.printTextNewLine("");
                            pos.printLocation(20, 1);
                            pos.printText("");
                            pos.printLocation(99, 1);
                            pos.printWordSpace(1);
                            pos.printText("退款");
                            pos.printWordSpace(3);
                            pos.printText(wxorder.getBackFee() + "元");

                            pos.printTextNewLine("");
                            pos.printLocation(20, 1);
                            pos.printText("");
                            pos.printLocation(99, 1);
                            pos.printWordSpace(1);
                            pos.printText("实际消费");
                            pos.printWordSpace(3);
                            pos.printText(Float.valueOf(wxorder.getTotalFee())- Float.valueOf(wxorder.getBackFee()) + "元");
                        }

                        pos.printLine(2);


                        pos.feedAndCut();
                        pos.closeIOAndSocket();
                        pos = null;


                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();

        }
        return true;
    }

    public void PrintDataAccount() {
        if (!ip_address.equals("")) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        pos = new Pos(ip_address, mPort, "GBK");
                        //初始化打印机
                        pos.initPos();


                        pos.printTabSpace(2);
                        pos.printWordSpace(1);
                        pos.bold(false);

                        pos.printLocation(0);
                        pos.printTextNewLine("-----------------[每日菜品汇总]-----------------");
                        pos.bold(false);

                        pos.printLine(2);

                        pos.printText("菜品");
                        pos.printLocation(20, 1);
                        pos.printText("     ");
                        pos.printLocation(99, 1);
                        pos.printWordSpace(1);
                        pos.printText("");
                        pos.printWordSpace(3);
                        pos.printText("数量");
                        pos.printTextNewLine("----------------------------------------------");


                        for (MealRecordEZ meal : mealRecordEZ) {
                            pos.printTextNewLine(meal.getMealname());
                            pos.printLocation(20, 1);
                            pos.printText("         ");
                            pos.printLocation(99, 1);
                            pos.printWordSpace(1);
                            pos.printText("");
                            pos.printWordSpace(3);
                            pos.printText(meal.getMealnum() + "");
                        }

                        pos.printTextNewLine("----------------------------------------------");


                        pos.printLine(2);


                        pos.feedAndCut();
                        pos.closeIOAndSocket();
                        pos = null;


                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }.start();

        }
    }
}
