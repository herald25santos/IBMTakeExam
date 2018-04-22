package com.santos.herald.ibmpracticalexam.ui.weatherdetail;

import com.santos.herald.ibmpracticalexam.di.PerActivity;
import com.santos.herald.ibmpracticalexam.di.components.ApplicationComponent;
import com.santos.herald.ibmpracticalexam.ui.weatherlist.WeatherListFragment;

import dagger.Component;

@PerActivity
@Component(modules = WeatherDetailModule.class, dependencies = ApplicationComponent.class)
public interface WeatherDetailComponent {

    void inject(WeatherDetailActivity weatherDetailActivity);

}
