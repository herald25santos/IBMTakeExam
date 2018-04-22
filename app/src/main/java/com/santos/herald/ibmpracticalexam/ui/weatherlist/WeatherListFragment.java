package com.santos.herald.ibmpracticalexam.ui.weatherlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.santos.herald.ibmpracticalexam.R;
import com.santos.herald.ibmpracticalexam.data.WeatherEntity;
import com.santos.herald.ibmpracticalexam.data.WeatherResponse;
import com.santos.herald.ibmpracticalexam.ui.base.BaseFragment;
import com.santos.herald.ibmpracticalexam.ui.base.PresenterFactory;
import com.santos.herald.ibmpracticalexam.ui.main.MainActivity;
import com.santos.herald.ibmpracticalexam.ui.weatherdetail.WeatherDetailActivity;
import com.santos.herald.ibmpracticalexam.utils.ActivityUtils;
import com.santos.herald.ibmpracticalexam.utils.RxBus;
import com.santos.herald.ibmpracticalexam.utils.RxData;
import com.santos.herald.ibmpracticalexam.utils.listener.onRecyclerViewListener;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class WeatherListFragment extends BaseFragment<WeatherListPresenter, WeatherListView.View> implements WeatherListView.View, onRecyclerViewListener {

    public static final String EXTRA_TITLE = "title";

    private static final String TAG = WeatherListFragment.class.getSimpleName();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private String title;

    @Inject
    CompositeDisposable mCompositeDisposable;

    @Inject
    WeatherListAdapter weatherListAdapter;

    public static WeatherListFragment newInstance(String title) {
        WeatherListFragment fragment = new WeatherListFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_weather_list;
    }

    @Override
    protected void onInjectDaggerDependency() {
        DaggerWeatherListComponent.builder()
                .applicationComponent(getApplicationComponent())
                .weatherListModule(new WeatherListModule(this))
                .build().inject(this);
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState) {
        super.onViewReady(savedInstanceState);
        init();
        initViews();
        initRxEventBus();
    }

    private void initViews() {
        ((MainActivity) getActivity()).setToolbarTitle(title);
    }

    private void init() {
        if (getArguments() != null) {
            Bundle b = getArguments();
            title = b.getString(EXTRA_TITLE);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        weatherListAdapter.setOnClickListener(this);
        recyclerView.setAdapter(weatherListAdapter);
    }

    private void initRxEventBus() {
        Disposable disposableRxBus = RxBus.subscribe((data) -> {
            if (data == null) return;
            if (data instanceof RxData) {
                RxData rxData = (RxData) data;
                if (rxData.action == RxData.ACTION_REFRESH) {
                    getPresenter().fetchWeatherList();
                    Log.e(TAG, "test");
                }
            }
        });
        mCompositeDisposable.add(disposableRxBus);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isPresenterAvailable()) getPresenter().resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isPresenterAvailable()) getPresenter().pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (isPresenterAvailable()) getPresenter().destroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable.dispose();
        }
    }


    @NonNull
    @Override
    protected PresenterFactory<WeatherListPresenter> createPresenterFactory() {
        return this::createPresenter;
    }

    @Override
    protected void onPresenterCreated(@NonNull WeatherListPresenter presenter, @Nullable Bundle savedInstanceState) {
        getPresenter().findLaunchMode();
    }

    @Override
    protected void onPresenterStateSave(@NonNull WeatherListPresenter presenter, @NonNull Bundle outState) {

    }

    @Override
    protected void onPresenterDestroyed() {

    }

    private WeatherListPresenter createPresenter() {
        return WeatherListPresenter.newInstance();
    }

    @Override
    public void showWeatherList(WeatherResponse weatherResponse) {
        if(weatherResponse != null && weatherListAdapter != null) {
            if(!weatherResponse.weather.isEmpty()){
                weatherListAdapter.setBasedContent(weatherResponse.weatherLocation, weatherResponse.coord, weatherResponse.main);
                weatherListAdapter.setData(weatherResponse.weather);
                weatherListAdapter.notifyDataSetChanged();
            } else {

            }
        }
    }

    @Override
    public void showEmptyResult() {

    }

    @Override
    public void showLoading() {
        if(progressBar != null) progressBar.setVisibility(View.VISIBLE);
        if(recyclerView != null) recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void dismissLoading() {
        if(progressBar != null) progressBar.setVisibility(View.GONE);
        if(recyclerView != null) recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        Log.e(TAG, "showError: " + message);
    }

    @Override
    public void onLaunchMode() {
        if (isPresenterAvailable()) {
            getPresenter().fetchWeatherList();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent i = new Intent(getActivity(), WeatherDetailActivity.class);
        i.putExtra(WeatherDetailActivity.EXTRA_MODEL, weatherListAdapter.getData().get(position));
        i.putExtra(WeatherDetailActivity.EXTRA_TEMP, weatherListAdapter.getWeatherMainEntity().temp);
        i.putExtra(WeatherDetailActivity.EXTRA_LOCATION, weatherListAdapter.getLocation());
        getActivity().startActivity(i);
        getActivity().overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }

    @Override
    public void onLongItemClick(View v, int position) {

    }
}
