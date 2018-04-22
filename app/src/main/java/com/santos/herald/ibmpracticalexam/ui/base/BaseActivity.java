package com.santos.herald.ibmpracticalexam.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.santos.herald.ibmpracticalexam.App;
import com.santos.herald.ibmpracticalexam.R;
import com.santos.herald.ibmpracticalexam.di.components.ApplicationComponent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<P extends BasePresenter<V>, V extends BaseView> extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private static final int LOADER_ID = 201;

    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvToolbar)
    TextView tvToolbar;

    protected P presenter;

    private Unbinder unbinder;

    private LoaderManager.LoaderCallbacks<P> loaderCallbacks
            = new LoaderManager.LoaderCallbacks<P>() {

        @Override
        public Loader<P> onCreateLoader(int id, Bundle args) {
            return PresenterLoader.newInstance(getApplicationContext(), createPresenterFactory());
        }

        @Override
        public void onLoadFinished(Loader<P> loader, P basePresenter) {
            final V presenterView = getPresenterView();
            presenter = basePresenter;
            if (null != presenterView) {
                presenter.attachView(BaseActivity.this, presenterView);
            } else {
                Log.d(TAG, "View can't be attached because you don't implement it in your activity.");
            }
            onPresenterCreated(presenter);
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        unbinder = ButterKnife.bind(this);
        onViewReady(savedInstanceState, getIntent());
        if (getSupportLoaderManager().getLoader(LOADER_ID) == null) {
            getSupportLoaderManager().initLoader(LOADER_ID, null, loaderCallbacks);
        } else {
            getSupportLoaderManager().restartLoader(LOADER_ID, null, loaderCallbacks);
        }
    }

    @CallSuper
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        onInjectDaggerDependency();
        //To be used by child activities
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    public void onBackPressed(Boolean isHaveAnimation){
        super.onBackPressed();
        if(isHaveAnimation) overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null) unbinder.unbind();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        onPresenterStateRestore(presenter, savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        onPresenterStateSave(presenter, outState);
        super.onSaveInstanceState(outState);
    }

    protected abstract int getContentView();

    protected abstract void onInjectDaggerDependency();

    @NonNull
    protected abstract PresenterFactory<P> createPresenterFactory();

    protected abstract void onPresenterCreated(@NonNull P presenter);

    protected abstract void onPresenterStateRestore(@NonNull final P presenter, @Nullable final Bundle savedInstanceState);

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
            Log.e(TAG, "You should implement your view class in the activity.", ex.getCause());
        }

        return view;
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((App) getApplication()).getApplicationComponent();
    }

    public void initToolbar() {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    public void setToolbarTitle(String title) {
        if(tvToolbar != null) tvToolbar.setText(title);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void clearToolbarScrollFlags() {
        if (toolbar == null)
            return;
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(0);

    }

    public void addToolbarScrollFlags() {
        if (toolbar == null)
            return;
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
    }

}