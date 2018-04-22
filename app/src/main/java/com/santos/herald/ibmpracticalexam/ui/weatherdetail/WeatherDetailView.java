package com.santos.herald.ibmpracticalexam.ui.weatherdetail;

import com.santos.herald.ibmpracticalexam.data.WeatherResponse;
import com.santos.herald.ibmpracticalexam.network.exception.ErrorBundle;
import com.santos.herald.ibmpracticalexam.ui.base.BaseView;

public interface WeatherDetailView {

    public interface View extends BaseView {

        void showWeatherDetail(WeatherResponse weatherResponse);

        void showEmptyResult();

        void showLoading();

        void dismissLoading();

        void showError(String message);

    }

    public interface Action {

        void onFetchWeatherDetail(WeatherResponse weatherResponse);

        void onEmptyResult();

        void onError(ErrorBundle errorBundle);

    }

}
