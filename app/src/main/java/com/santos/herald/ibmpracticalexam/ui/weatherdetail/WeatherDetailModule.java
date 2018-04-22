package com.santos.herald.ibmpracticalexam.ui.weatherdetail;

import com.santos.herald.ibmpracticalexam.App;
import com.santos.herald.ibmpracticalexam.di.PerActivity;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class WeatherDetailModule {

    private WeatherDetailActivity mView;

    public WeatherDetailModule(WeatherDetailActivity view) {
        mView = view;
    }

    @PerActivity
    @Provides
    WeatherDetailActivity provideView() {
        return mView;
    }

    @PerActivity
    @Provides
    WeatherDetailInteractor provideHomeInteractor() {
        return new WeatherDetailInteractor(App.getApplication());
    }

    @PerActivity
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

}