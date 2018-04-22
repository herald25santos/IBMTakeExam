package com.santos.herald.ibmpracticalexam.ui.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.util.Log;


public final class PresenterLoader<P extends BasePresenter> extends Loader<P> {

    @NonNull
    private final PresenterFactory<P> factory;

    @Nullable
    private P presenter;

    protected PresenterLoader(@NonNull Context context, @NonNull PresenterFactory<P> factory) {
        super(context);
        this.factory = factory;
        Log.e("PresenterLoader", "conductor");
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.e("PresenterLoader", "onStartLoading");
        if (null != presenter) {
            deliverResult(presenter);
        }

        forceLoad();
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        Log.e("PresenterLoader", "onForceLoad");
        if(presenter == null){
            presenter = factory.create();
            deliverResult(presenter);
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        Log.e("PresenterLoader", "onReset");
        if (null != presenter) {
            presenter.detachView();
            presenter = null;
        }
    }

    public static <P extends BasePresenter> PresenterLoader<P> newInstance(@NonNull Context context, @NonNull PresenterFactory<P> factory) {
        return new PresenterLoader<>(context, factory);
    }
}