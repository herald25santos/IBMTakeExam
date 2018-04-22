package com.santos.herald.ibmpracticalexam.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santos.herald.ibmpracticalexam.App;
import com.santos.herald.ibmpracticalexam.di.components.ApplicationComponent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.santos.herald.ibmpracticalexam.App.getApplication;


public abstract class BaseFragment<P extends BasePresenter<V>, V extends BaseView> extends Fragment {

    private static final String TAG = BaseFragment.class.getSimpleName();

    private static final int LOADER_ID = 201;

    protected P presenter;

    private Bundle mBundle;

    private Unbinder unbinder;

    private LoaderManager.LoaderCallbacks<P> loaderCallbacks
            = new LoaderManager.LoaderCallbacks<P>() {

        @Override
        public Loader<P> onCreateLoader(int id, Bundle args) {
            return PresenterLoader.newInstance(getContext(), createPresenterFactory());
        }

        @Override
        public void onLoadFinished(Loader<P> loader, P basePresenter) {
            final V presenterView = getPresenterView();
            presenter = basePresenter;

            if (null != presenterView) {
                presenter.attachView(getContext(), presenterView);
            } else {
                Log.d(TAG, "View can't be attached because you don't implement it in your fragment.");
            }
            onPresenterCreated(presenter, mBundle);
        }

        @Override
        public void onLoaderReset(Loader<P> loader) {
            if (isPresenterAvailable()) {
                presenter.detachView();
                presenter = null;
            }

            onPresenterDestroyed();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBundle = savedInstanceState;
        onViewReady(savedInstanceState);
        if (getLoaderManager().getLoader(LOADER_ID) == null) {
            getLoaderManager().initLoader(LOADER_ID, null, loaderCallbacks);
            Log.d(TAG, "initLoader");
        } else {
            getLoaderManager().restartLoader(LOADER_ID, null, loaderCallbacks);
            Log.d(TAG, "restartLoader");
        }
    }

    @CallSuper
    protected void onViewReady(Bundle savedInstanceState) {
        onInjectDaggerDependency();
        //To be used by child activities
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        onPresenterStateSave(presenter, outState);
        super.onSaveInstanceState(outState);
    }

//    @LayoutRes
//    protected abstract int getLayout();

    protected abstract int getContentView();

    protected abstract void onInjectDaggerDependency();

    @NonNull
    protected abstract PresenterFactory<P> createPresenterFactory();

    protected abstract void onPresenterCreated(@NonNull final P presenter, @Nullable final Bundle savedInstanceState);

    protected abstract void onPresenterStateSave(@NonNull final P presenter, @NonNull final Bundle outState);

    protected abstract void onPresenterDestroyed();

    protected P getPresenter() {
        return presenter;
    }

    protected boolean isPresenterAvailable() {
        return presenter != null;
    }

    @SuppressWarnings("all")
    protected V getPresenterView() {
        V view = null;

        try {
            view = (V) this;
        } catch (final ClassCastException ex) {
            Log.e(TAG, "You should implement your view class in the fragment.", ex.getCause());
        }

        return view;
    }

    @NonNull
    protected Context getApplicationContext() {
        return getContext().getApplicationContext();
    }

    @NonNull
    protected ApplicationComponent getApplicationComponent() {
        return ((App) getApplication()).getApplicationComponent();
    }

}