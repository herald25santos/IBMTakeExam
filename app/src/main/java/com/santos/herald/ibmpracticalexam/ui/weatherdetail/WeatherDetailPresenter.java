package com.santos.herald.ibmpracticalexam.ui.weatherdetail;

import android.content.Context;
import android.support.annotation.NonNull;

import com.santos.herald.ibmpracticalexam.App;
import com.santos.herald.ibmpracticalexam.data.WeatherResponse;
import com.santos.herald.ibmpracticalexam.di.components.ApplicationComponent;
import com.santos.herald.ibmpracticalexam.network.exception.ErrorBundle;
import com.santos.herald.ibmpracticalexam.network.exception.ErrorMessageFactory;
import com.santos.herald.ibmpracticalexam.network.exception.ServerErrorException;
import com.santos.herald.ibmpracticalexam.network.response.ResponseStatus;
import com.santos.herald.ibmpracticalexam.ui.base.BasePresenter;
import com.santos.herald.ibmpracticalexam.ui.base.Presenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class WeatherDetailPresenter extends BasePresenter<WeatherDetailView.View> implements Presenter, WeatherDetailView.Action {

    @Inject
    WeatherDetailInteractor weatherDetailInteractor;

    @Inject
    CompositeDisposable mCompositeDisposable;

    private Context context;

    public static WeatherDetailPresenter newInstance() {
        return new WeatherDetailPresenter();
    }

    @Override
    protected void onViewAttached(Context context, @NonNull WeatherDetailView.View view) {
        ApplicationComponent applicationComponent = ((App) App.getApplication()).getApplicationComponent();
        applicationComponent.inject(this);
        this.context = context;
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        if (isViewAttached()) {
            String errorMessage = ErrorMessageFactory.create(context, errorBundle.getException());
            boolean serverError = errorMessage.equals(ResponseStatus.SERVER_ERROR_MESSAGE);
            getView().dismissLoading();
            getView().showError(serverError ? new ServerErrorException().getMessage() : errorMessage);
        }
    }

    public void fetchWeatherList() {
        if (isViewAttached()) {
            getView().showLoading();
            Disposable disposable = weatherDetailInteractor.fetchWeatherList(this);
            mCompositeDisposable.add(disposable);
        }
    }


    public void findLaunchMode() {
        if (isViewAttached()) {
            getView().onLaunchMode();
        }
    }

    @Override
    protected void onViewDetached() {
        weatherDetailInteractor = null;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void onFetchWeatherDetail(WeatherResponse weatherResponse) {
        if (isViewAttached()) {
            getView().dismissLoading();
            getView().showWeatherDetail(weatherResponse);
        }
    }

    @Override
    public void onEmptyResult() {
        if (isViewAttached()) {
            getView().dismissLoading();
            getView().showEmptyResult();
        }
    }

    @Override
    public void onError(ErrorBundle errorBundle) {
        showErrorMessage(errorBundle);
    }
}
