package com.santos.herald.ibmpracticalexam.ui.main;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.santos.herald.ibmpracticalexam.R;
import com.santos.herald.ibmpracticalexam.ui.weatherlist.WeatherListFragment;
import com.santos.herald.ibmpracticalexam.ui.weatherlist.WeatherRefreshFragment;
import com.santos.herald.ibmpracticalexam.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvToolbar)
    TextView tvToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ActivityUtils.replaceFragmentButton(WeatherRefreshFragment.newInstance(), getSupportFragmentManager());
        ActivityUtils.replaceFragment(WeatherListFragment.newInstance("Weather List"), getSupportFragmentManager());
    }

    public void setToolbarTitle(String title) {
        if(tvToolbar != null) tvToolbar.setText(title);
    }

}
