package com.zm.tu8tu.sample.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;
import com.zm.tu8tu.sample.mvp.contract.CaseDetailContract;
import com.zm.tu8tu.sample.mvp.ui.fragment.CaseShowFragment;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;


@ActivityScope
public class CaseDetailPresenter extends BasePresenter<CaseDetailContract.Model, CaseDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    public int screenheight;
    public int screenwith;
    public float density;

    @Inject
    public CaseDetailPresenter(CaseDetailContract.Model model, CaseDetailContract.View rootView) {
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

    public void init(Float density, int screenwith, int screenheight, int vrid) {
        this.density = density;
        this.screenwith = screenwith;
        this.screenheight = screenheight;
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {

            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {

            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {

            }
        }, mRootView.getRxPermissions(), mErrorHandler);
        mRootView.setShowCaseView(CaseShowFragment.newInstance(vrid, screenwith, screenheight));
    }
}