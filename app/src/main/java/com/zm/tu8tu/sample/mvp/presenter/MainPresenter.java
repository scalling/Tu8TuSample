package com.zm.tu8tu.sample.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.zm.tu8tu.sample.mvp.contract.MainContract;
import com.zm.tu8tu.sample.mvp.ui.activity.CaseListActivity;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void navMain(int zoneId, String title) {
        Intent intent = new Intent(mApplication.getApplicationContext(), CaseListActivity.class);
        intent.putExtra("zoneId", zoneId);
        intent.putExtra("title", title);
        mRootView.launchActivity(intent);

    }
}
