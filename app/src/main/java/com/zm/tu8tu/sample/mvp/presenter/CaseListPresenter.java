package com.zm.tu8tu.sample.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;
import com.zm.tu8tu.sample.mvp.contract.CaseListContract;
import com.zm.tu8tu.sample.mvp.model.api.Api;
import com.zm.tu8tu.sample.mvp.model.api.bean.ListBeanDto;
import com.zm.tu8tu.sample.mvp.model.api.bean.ResultDto;
import com.zm.tu8tu.sample.mvp.ui.activity.CaseDetailActivity;
import com.zm.tu8tu.sample.mvp.ui.adapter.CaseAdapter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class CaseListPresenter extends BasePresenter<CaseListContract.Model, CaseListContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    @Inject
    List<ListBeanDto.DataBean> mDatas;
    @Inject
    CaseAdapter mAdapter;

    private int page = 1;
    private int type = 0;

    @Inject
    public CaseListPresenter(CaseListContract.Model model, CaseListContract.View rootView) {
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

    public void initParams(int type) {
        this.type = type;
        loadData(true);
    }

    public void loadData(boolean isRefresh) {
        loadData(true, isRefresh);
    }

    /**
     * 加载数据
     *
     * @param isShowLoading 是否显示loading
     * @param isRefresh     是否是刷新操作
     */
    public void loadData(boolean isShowLoading, boolean isRefresh) {
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
        if (isRefresh) {
            page = 1;
            mDatas.clear();
        }
        mModel.getCaseList(page, type)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(Api.MAX_RETRIES, Api.RETRY_DEALAY_SECOND))
                .doOnSubscribe(disposable -> {
                    if (mRootView != null && isShowLoading) {
                        mRootView.showLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())//在main线程里面
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<Object>(mErrorHandler) {
                    @Override
                    public void onNext(Object obj) {
                        if (mRootView != null) {
                            ResultDto<ListBeanDto> resultDto = (ResultDto<ListBeanDto>) obj;
                            ListBeanDto beanDto = resultDto.getContent();
                            if (resultDto.getStatus() == 1 && beanDto != null) {
                                if (beanDto.getData() != null) {
                                    mDatas.addAll(resultDto.getContent().getData());
                                }
                                if (beanDto.getCurrent_page() == beanDto.getTotal_pages()) {
                                    mRootView.setEnableLoadMore(false);
                                } else {
                                    page++;
                                    mRootView.setEnableLoadMore(true);
                                }
                                notifyDataSetChanged(isRefresh, true);
                            } else {
                                notifyDataSetChanged(isRefresh, false);
                            }

                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        if (mRootView != null) {
                            notifyDataSetChanged(isRefresh, false);
                        }
                    }
                });
    }


    /**
     * 刷新加载数据
     *
     * @param isRefresh 是否是加载
     * @param success   是否成功
     */
    private void notifyDataSetChanged(boolean isRefresh, boolean success) {
        if (isRefresh) {
            mRootView.finishRefresh(success);
        } else {
            mRootView.finishLoadMore(success);
        }
        if (mDatas.size() == 0) {
            if (success) {
                mRootView.showError();
            } else {
                mRootView.showEmpty();
            }
        } else {
            mRootView.hideLoading();
        }
        mAdapter.notifyDataSetChanged();
    }

    public void navDetail(int position) {
        Intent intent = new Intent(mApplication.getApplicationContext(), CaseDetailActivity.class);
        intent.putExtra("vrid", mDatas.get(position).getVrid());
        mRootView.launchActivity(intent);
    }
}
