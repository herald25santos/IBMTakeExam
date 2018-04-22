package com.santos.herald.ibmpracticalexam.ui.weatherlist;

import com.santos.herald.ibmpracticalexam.di.PerActivity;
import com.santos.herald.ibmpracticalexam.di.components.ApplicationComponent;

import dagger.Component;

@PerActivity
@Component(modules = WeatherListModule.class, dependencies = ApplicationComponent.class)
public interface WeatherListComponent {

    void inject(WeatherListFragment weatherListFragment);

}
