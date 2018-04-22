package com.santos.herald.ibmpracticalexam.di.components;

import com.santos.herald.ibmpracticalexam.di.modules.ApplicationModule;
import com.santos.herald.ibmpracticalexam.di.modules.NetModule;
import com.santos.herald.ibmpracticalexam.ui.weatherdetail.WeatherDetailInteractor;
import com.santos.herald.ibmpracticalexam.ui.weatherdetail.WeatherDetailPresenter;
import com.santos.herald.ibmpracticalexam.ui.weatherlist.WeatherListInteractor;
import com.santos.herald.ibmpracticalexam.ui.weatherlist.WeatherListPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetModule.class})
public interface ApplicationComponent {

    void inject (WeatherListPresenter weatherListPresenter);
    void inject (WeatherListInteractor weatherListInteractor);

    void inject (WeatherDetailPresenter weatherDetailPresenter);
    void inject (WeatherDetailInteractor weatherDetailInteractor);

}