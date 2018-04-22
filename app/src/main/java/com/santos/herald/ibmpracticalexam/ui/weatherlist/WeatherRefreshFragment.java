package com.santos.herald.ibmpracticalexam.ui.weatherlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santos.herald.ibmpracticalexam.R;
import com.santos.herald.ibmpracticalexam.utils.RxBus;
import com.santos.herald.ibmpracticalexam.utils.RxData;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherRefreshFragment extends Fragment {


    public static WeatherRefreshFragment newInstance() {
        return new WeatherRefreshFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_button, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnRefresh)
    public void ClickRefresh(){
        RxBus.publish(new RxData(RxData.ACTION_REFRESH));
    }

}
