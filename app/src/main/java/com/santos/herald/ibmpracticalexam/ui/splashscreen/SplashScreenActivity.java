package com.santos.herald.ibmpracticalexam.ui.splashscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.santos.herald.ibmpracticalexam.BuildConfig;
import com.santos.herald.ibmpracticalexam.R;
import com.santos.herald.ibmpracticalexam.ui.main.MainActivity;
import com.santos.herald.ibmpracticalexam.utils.ActivityUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class SplashScreenActivity extends AppCompatActivity {

    @BindView(R.id.imgBackground)
    AppCompatImageView imgBackground;

    @BindView(R.id.tvTitle)
    AppCompatTextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        ButterKnife.bind(this);
        initViews();
        init();
    }

    private void init() {
        Disposable disposable = Observable.just(true)
                .delay(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean) {
                        ActivityUtils.startMainActivity(this, MainActivity.class, false);
                    }
                });

    }

    private void initViews() {
        if (BuildConfig.FLAVOR.equalsIgnoreCase("production")) {
            imgBackground.setBackgroundResource(R.drawable.background);
            tvTitle.setText("Weather Application (Production)");
        } else if (BuildConfig.FLAVOR.equalsIgnoreCase("dev")) {
            imgBackground.setBackgroundResource(R.drawable.background2);
            tvTitle.setText("Weather Application (Dev)");
        } else {
            imgBackground.setBackgroundResource(R.drawable.background2);
        }
    }
}
