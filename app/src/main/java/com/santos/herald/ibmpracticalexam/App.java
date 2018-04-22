package com.santos.herald.ibmpracticalexam;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.bumptech.glide.Glide;
import com.santos.herald.ibmpracticalexam.di.components.ApplicationComponent;
import com.santos.herald.ibmpracticalexam.di.components.DaggerApplicationComponent;
import com.santos.herald.ibmpracticalexam.di.modules.ApplicationModule;
import com.santos.herald.ibmpracticalexam.di.modules.NetModule;
import com.squareup.leakcanary.LeakCanary;

public class App extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Application app;
    private ApplicationComponent mApplicationComponent;

    public static Application getApplication() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        initializeApplicationComponent();
        if (BuildConfig.DEBUG) {
            initLeakDetection();
        }
    }

    private void initializeApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .netModule(new NetModule("http://samples.openweathermap.org/"))
                .build();
    }

    private void initLeakDetection() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Glide.get(this).clearMemory();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}