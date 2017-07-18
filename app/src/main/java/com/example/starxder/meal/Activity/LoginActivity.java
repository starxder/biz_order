package com.example.starxder.meal.Activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.starxder.meal.Bean.Meal;
import com.example.starxder.meal.Bean.Setting;
import com.example.starxder.meal.Bean.User;
import com.example.starxder.meal.Dao.MealDao;
import com.example.starxder.meal.Dao.SettingDao;
import com.example.starxder.meal.Dao.UserDao;
import com.example.starxder.meal.R;
import com.example.starxder.meal.Utils.CommonUtils;
import com.example.starxder.meal.Utils.GsonUtils;
import com.example.starxder.meal.Utils.OkManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private CheckBox cb_remPassword;
    private CheckBox cb_autoLogin;
    private Button btn_login;
    private OkManager manager;
    private List<Meal> mealList;
    String userName;
    String passWord;
    UserDao userDao;
    SettingDao settingDao;
    private List<User> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mealList = new ArrayList<Meal>();
        mUsernameView = (EditText) findViewById(R.id.Username_Edt);
        mPasswordView = (EditText) findViewById(R.id.Password_Edt);
        cb_remPassword = (CheckBox) findViewById(R.id.cb_remPassword);
        cb_autoLogin = (CheckBox) findViewById(R.id.cb_autoLogin);
        btn_login = (Button) findViewById(R.id.btn_login);
        userDao = new UserDao(LoginActivity.this);
        settingDao = new SettingDao(LoginActivity.this);
        initEvent();
        try {
            HistoryUserSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createNotification();
    }

    private void initEvent() {
        btn_login.setOnClickListener(this);
        if (settingDao.queryById(1) == null) {
            Setting setting = new Setting(1, "false", "false");
            settingDao.insert(setting);
        } else {
            Setting setting = settingDao.queryById(1);
            if (Boolean.valueOf(setting.getRemPassword())) {
                cb_remPassword.setChecked(true);
            } else {
                cb_remPassword.setChecked(false);
            }
            if (Boolean.valueOf(setting.getAutoLogin())) {
                cb_autoLogin.setChecked(true);
            } else {
                cb_autoLogin.setChecked(false);
            }
        }
        cb_remPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Setting setting = settingDao.queryById(1);
                    setting.setRemPassword("true");
                    settingDao.update(setting);
                } else {
                    Setting setting = settingDao.queryById(1);
                    setting.setRemPassword("false");
                    setting.setAutoLogin("false");
                    settingDao.update(setting);
                    cb_autoLogin.setChecked(false);
                }
            }
        });

        cb_autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Setting setting = settingDao.queryById(1);
                    setting.setAutoLogin("true");
                    setting.setRemPassword("true");
                    settingDao.update(setting);
                    cb_remPassword.setChecked(true);
                } else {
                    Setting setting = settingDao.queryById(1);
                    setting.setAutoLogin("false");
                    settingDao.update(setting);
                }
            }
        });
    }

    private void createNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(LoginActivity.this);
        Notification notification;
        PendingIntent contentIndent = PendingIntent.getActivity(LoginActivity.this, 0, new Intent(LoginActivity.this, LoginActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIndent).setSmallIcon(R.mipmap.ic_order_logo)//设置状态栏里面的图标（小图标） 　　　　　　　　　　　　　　　　　　　　
                // .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.i5))//下拉下拉列表里面的图标（大图标） 　　　　　　　
                // .setTicker("this is bitch!") //设置状态栏的显示的信息
                .setWhen(System.currentTimeMillis())//设置时间发生时间
                .setAutoCancel(false)//设置可以清除
                .setContentTitle("订单系统")//设置下拉列表里的标题
                .setContentText("点击进入");//设置上下文内容

        notification = builder.getNotification();
        notification.flags = Notification.FLAG_ONGOING_EVENT;
//        notification.sound = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.neworder);
        //加i是为了显示多条Notification
        notificationManager.notify(0, notification);
    }


    private void HistoryUserSet() throws SQLException {
        if (Boolean.valueOf(settingDao.queryById(1).getRemPassword())) {
            if (userDao.getFirstUser() != null) {
                mUsernameView.setText(userDao.getFirstUser().getLoginName());
                mPasswordView.setText(userDao.getFirstUser().getPassword());
                if (Boolean.valueOf(settingDao.queryById(1).getAutoLogin())) {
                    Login();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Login();
                break;

        }
    }


    private void Login() {
        manager = OkManager.getInstance();
        Log.d("manager", manager.toString());
        userName = mUsernameView.getText().toString();
        passWord = mPasswordView.getText().toString();
        String jsonpath = CommonUtils.BaseUrl + "/web-frame/user/login.do?loginname=" + userName + "&&password=" + passWord;
        //登陆同步用户数据
        manager.asyncJsonStringByURL(jsonpath, new OkManager.Fun1() {
            @Override
            public void onResponse(String response) {
                Log.i("LoginActivity", response);   //获取JSON字符串
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String error = jsonObject.getString("error");
                    String result = jsonObject.getString("result");
                    if (error.equals("")) {
                        userList = GsonUtils.getUserByGson("[" + result + "]");
                        Log.d(TAG, userList.toString());
                        userDao.deleteAll();
                        for (User user : userList) {
                            userDao.insert(user);
                        }
                        Toast.makeText(getApplicationContext(), "用户数据同步成功", Toast.LENGTH_SHORT).show();
                        Synchronize();
                    } else {
                        Toast.makeText(getApplicationContext(), "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        Setting setting = new Setting(1, "false", "false");
                        settingDao.insert(setting);
                        userList = userDao.queryForAll();
                        for (int i = 0; i < userList.size(); i++) {
                            userDao.delete(userList.get(i));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String result) {
                Toast.makeText(getApplicationContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void Synchronize() {
        final User user = userDao.queryByLoginName(userName);
        String synchronizePath = CommonUtils.BaseUrl + "web-frame/meal/init.do?shopnum=" + user.getShopnum();
        manager.asyncJsonStringByURL(synchronizePath, new OkManager.Fun1() {
            @Override
            public void onResponse(String response) {
                Log.i("LoginActivity", response);   //获取JSON字符串
                //                    JSONObject jsonObject = new JSONObject(response);
//                    String error = jsonObject.getString("error");
//                    String result = jsonObject.getString("result");
                if (!response.equals("")) {
                    mealList = GsonUtils.getMealByGson(response);
                    MealDao mealDao = new MealDao(LoginActivity.this);
                    try {
                        mealDao.deleteAll();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    for (Meal meal : mealList) {
                        mealDao.insert(meal);
                    }
                }

                Toast.makeText(getApplicationContext(), "数据同步成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("extra", user.getLoginName());
                intent.setClass(LoginActivity.this, MainActivity.class);      //运行另外一个类的活动
                startActivityForResult(intent, 1);
            }

            @Override
            public void onFailure(String result) {
                Toast.makeText(getApplicationContext(), "数据同步失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

