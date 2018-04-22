package com.santos.herald.ibmpracticalexam.ui.weatherlist;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.santos.herald.ibmpracticalexam.App;
import com.santos.herald.ibmpracticalexam.di.components.ApplicationComponent;
import com.santos.herald.ibmpracticalexam.network.ApiServices;
import com.santos.herald.ibmpracticalexam.network.exception.DefaultErrorBundle;
import com.santos.herald.ibmpracticalexam.network.response.ResponseStatus;
import com.santos.herald.ibmpracticalexam.ui.base.BaseInteractor;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WeatherListInteractor extends BaseInteractor {

    @Inject
    ApiServices mApiServices;

    @Inject
    Context context;

    @Inject
    public WeatherListInteractor(Application application) {
        ApplicationComponent applicationComponent = ((App) application).getApplicationComponent();
        applicationComponent.inject(this);
    }

    public Disposable fetchWeatherList(WeatherListView.Action action) {
        return mApiServices.fetchWeatherList("{London,Prague,San Francisco}","b6907d289e10d714a6e88b30761fae22")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            if (!response.weather.isEmpty()) {
                                action.onFetchWeatherList(response);
                            } else {
                                action.onEmptyResult();
                            }
                        }, e -> {
                            e.printStackTrace();
                            Log.e(TAG, "onError -> Goes here " + e.getMessage());
                            action.onError(new DefaultErrorBundle((Exception) e));
                        });

    }

}
