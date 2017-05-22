package com.example.starxder.meal.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.starxder.meal.Event.PayEvent;
import com.example.starxder.meal.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Administrator on 2017/5/22.
 */

public class OrderDetailFragment extends Fragment {
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text, container, false);
        textView = (TextView) view.findViewById(R.id.test);
        EventBus.getDefault().register(this);

        return view;
    }

    @Subscribe
    public void onEvent(PayEvent payEvent) {
        if (payEvent != null) {
            textView.setText(payEvent.getIfpay());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }
}
