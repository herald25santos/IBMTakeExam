package com.santos.herald.ibmpracticalexam.ui.weatherdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.santos.herald.ibmpracticalexam.R;
import com.santos.herald.ibmpracticalexam.data.WeatherEntity;
import com.santos.herald.ibmpracticalexam.data.WeatherResponse;
import com.santos.herald.ibmpracticalexam.ui.base.BaseActivity;
import com.santos.herald.ibmpracticalexam.ui.base.PresenterFactory;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.disposables.CompositeDisposable;

public class WeatherDetailActivity extends BaseActivity<WeatherDetailPresenter, WeatherDetailView.View> implements WeatherDetailView.View {

    private static final String TAG = WeatherDetailActivity.class.getSimpleName();
    public static final String EXTRA_MODEL = "model";
    public static final String EXTRA_LOCATION = "location";
    public static final String EXTRA_TEMP = "temp";

    @BindView(R.id.tvLocation)
    AppCompatTextView tvLocation;

    @BindView(R.id.tvWeatherId)
    AppCompatTextView tvWeatherId;

    @BindView(R.id.tvWeather)
    AppCompatTextView tvWeather;

    @BindView(R.id.tvWeatherDesc)
    AppCompatTextView tvWeatherDesc;

    @BindView(R.id.tvTemp)
    AppCompatTextView tvTemp;

    @BindView(R.id.imgWeather)
    AppCompatImageView imgWeather;

    @BindView(R.id.llContent)
    LinearLayout llContent;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    CompositeDisposable mCompositeDisposable;

    @Override
    protected int getContentView() {
        return R.layout.activity_weather_detail;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        init();
        initToolbar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPresenterAvailable()) getPresenter().destroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable.dispose();
        }
    }

    private void init() {
        Intent i = getIntent();
        if (i != null) {
            String location = i.getStringExtra(EXTRA_LOCATION);
            String temp = i.getStringExtra(EXTRA_TEMP);
            WeatherEntity weatherEntity = (WeatherEntity) i.getSerializableExtra(EXTRA_MODEL);
            setViews(location, temp, weatherEntity);
        }
    }

    private void setViews(String location, String temp, WeatherEntity weatherEntity){
        tvLocation.setText("Location: " + location);
        tvWeatherId.setText("Weather ID: " + weatherEntity.id);
        tvWeather.setText("Weather: " + weatherEntity.main);
        tvWeatherDesc.setText("Description: " + weatherEntity.description);
        tvTemp.setText("Temperature: " + temp);

        Glide.with(this)
                .load("http://openweathermap.org/img/w/" + weatherEntity.icon + ".png")
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgWeather);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onInjectDaggerDependency() {
        DaggerWeatherDetailComponent.builder()
                .applicationComponent(getApplicationComponent())
                .weatherDetailModule(new WeatherDetailModule(this))
                .build().inject(this);
    }

    @NonNull
    @Override
    protected PresenterFactory<WeatherDetailPresenter> createPresenterFactory() {
        return this::createPresenter;
    }

    @Override
    protected void onPresenterCreated(@NonNull WeatherDetailPresenter presenter) {
        getPresenter().findLaunchMode();
    }

    @Override
    protected void onPresenterStateRestore(@NonNull WeatherDetailPresenter presenter, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected void onPresenterStateSave(@NonNull WeatherDetailPresenter presenter, @NonNull Bundle outState) {

    }

    @Override
    protected void onPresenterDestroyed() {

    }

    private WeatherDetailPresenter createPresenter() {
        return WeatherDetailPresenter.newInstance();
    }


    @Override
    public void showWeatherDetail(WeatherResponse weatherResponse) {

    }

    @Override
    public void showEmptyResult() {

    }

    @Override
    public void showLoading() {
//        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
//        if (llContent != null) llContent.setVisibility(View.GONE);
    }

    @Override
    public void dismissLoading() {
//        if (progressBar != null) progressBar.setVisibility(View.GONE);
//        if (llContent != null) llContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        Log.e(TAG, "showError: " + message);
    }

    @Override
    public void onLaunchMode() {
        if (isPresenterAvailable()) {

        }
    }
}
