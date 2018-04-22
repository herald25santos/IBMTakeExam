package com.santos.herald.ibmpracticalexam.ui.weatherlist;

import com.santos.herald.ibmpracticalexam.data.WeatherEntity;
import com.santos.herald.ibmpracticalexam.data.WeatherResponse;
import com.santos.herald.ibmpracticalexam.network.exception.ErrorBundle;
import com.santos.herald.ibmpracticalexam.ui.base.BaseView;

import java.util.List;

public interface WeatherListView {

    public interface View extends BaseView {

        void showWeatherList(WeatherResponse weatherResponse);

        void showEmptyResult();

        void showLoading();

        void dismissLoading();

        void showError(String message);

    }

    public interface Action {

        void onFetchWeatherList(WeatherResponse weatherResponse);

        void onEmptyResult();

        void onError(ErrorBundle errorBundle);

    }

}
