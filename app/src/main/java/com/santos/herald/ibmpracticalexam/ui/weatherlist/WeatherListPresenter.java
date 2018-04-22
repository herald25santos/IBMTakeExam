package com.santos.herald.ibmpracticalexam.ui.weatherlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.santos.herald.ibmpracticalexam.App;
import com.santos.herald.ibmpracticalexam.data.WeatherEntity;
import com.santos.herald.ibmpracticalexam.data.WeatherResponse;
import com.santos.herald.ibmpracticalexam.di.components.ApplicationComponent;
import com.santos.herald.ibmpracticalexam.network.exception.ErrorBundle;
import com.santos.herald.ibmpracticalexam.network.exception.ErrorMessageFactory;
import com.santos.herald.ibmpracticalexam.network.exception.ServerErrorException;
import com.santos.herald.ibmpracticalexam.network.response.ResponseStatus;
import com.santos.herald.ibmpracticalexam.ui.base.BasePresenter;
import com.santos.herald.ibmpracticalexam.ui.base.Presenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class WeatherListPresenter extends BasePresenter<WeatherListView.View> implements Presenter, WeatherListView.Action {

    @Inject
    WeatherListInteractor weatherListInteractor;

    @Inject
    CompositeDisposable mCompositeDisposable;

    private Context context;

    public static WeatherListPresenter newInstance() {
        return new WeatherListPresenter();
    }

    @Override
    protected void onViewAttached(Context context, @NonNull WeatherListView.View view) {
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
            Disposable disposable = weatherListInteractor.fetchWeatherList(this);
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
        weatherListInteractor = null;
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
    public void onFetchWeatherList(WeatherResponse weatherResponse) {
        if (isViewAttached()) {
            getView().dismissLoading();
            getView().showWeatherList(weatherResponse);
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
