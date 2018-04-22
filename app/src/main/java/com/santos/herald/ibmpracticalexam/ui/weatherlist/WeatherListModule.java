package com.santos.herald.ibmpracticalexam.ui.weatherlist;

import com.santos.herald.ibmpracticalexam.App;
import com.santos.herald.ibmpracticalexam.di.PerActivity;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class WeatherListModule {

    private WeatherListFragment mView;

    public WeatherListModule(WeatherListFragment view) {
        mView = view;
    }

    @PerActivity
    @Provides
    WeatherListFragment provideView() {
        return mView;
    }

    @PerActivity
    @Provides
    WeatherListInteractor provideHomeInteractor() {
        return new WeatherListInteractor(App.getApplication());
    }

    @PerActivity
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @PerActivity
    @Provides
    WeatherListAdapter provideWeatherListAdapter(){
        return new WeatherListAdapter(mView.getContext(), new ArrayList<>(), false, false);
    }
}