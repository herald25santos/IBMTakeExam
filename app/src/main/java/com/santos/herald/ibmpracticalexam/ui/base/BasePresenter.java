package com.santos.herald.ibmpracticalexam.ui.base;

import android.content.Context;
import android.support.annotation.NonNull;

public abstract class BasePresenter<V extends BaseView> {
    private V view;

    protected abstract void onViewAttached(Context context, @NonNull V view);

    protected abstract void onViewDetached();

    public boolean isViewAttached() {
        return this.view != null;
    }

    public void attachView(Context context, @NonNull V view) {
        this.view = view;
        onViewAttached(context, view);
    }

    public void detachView() {
        this.view = null;
        onViewDetached();
    }

    public V getView() {
        return view;
    }
}