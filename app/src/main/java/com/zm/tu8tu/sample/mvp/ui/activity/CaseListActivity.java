package com.zm.tu8tu.sample.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zm.tu8tu.sample.R;
import com.zm.tu8tu.sample.di.component.DaggerCaseListComponent;
import com.zm.tu8tu.sample.di.module.CaseListModule;
import com.zm.tu8tu.sample.mvp.contract.CaseListContract;
import com.zm.tu8tu.sample.mvp.presenter.CaseListPresenter;
import com.zm.tu8tu.sample.mvp.ui.adapter.CaseAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CaseListActivity extends BaseActivity<CaseListPresenter> implements CaseListContract.View {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.srl_layout)
    SmartRefreshLayout srlLayout;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    CaseAdapter mAdapter;
    @Inject
    RxPermissions mRxPermissions;

    private StatusLayoutManager slmLoading;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCaseListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .caseListModule(new CaseListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_case_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        int type = getIntent().getIntExtra("zoneId", 1);
        ArmsUtils.configRecycleView(rvList, mLayoutManager);
        rvList.setAdapter(mAdapter);
        srlLayout.setEnableLoadMore(false);
        setListener();
        mPresenter.initParams(type);
        //srlLayout.autoRefresh();//自动刷新

    }

    private void setListener() {
        mAdapter.setOnItemClickListener((view, viewType, data, position) -> mPresenter.navDetail(position));
        srlLayout.setOnRefreshListener(refreshLayout -> mPresenter.loadData(true));
        srlLayout.setOnLoadMoreListener(refreshLayout -> mPresenter.loadData(false));
        slmLoading = new StatusLayoutManager.Builder(srlLayout)

                // 设置重试事件监听器
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        mPresenter.loadData(true, true);
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        mPresenter.loadData(true, true);
                    }

                    @Override
                    public void onCustomerChildClick(View view) {


                    }
                })
                .build();

    }


    @Override
    public void showLoading() {
        slmLoading.showLoadingLayout();
    }

    @Override
    public void hideLoading() {
        slmLoading.showSuccessLayout();
        srlLayout.finishRefresh(true);
        srlLayout.finishLoadMore(true);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setEnableLoadMore(boolean enabled) {
        srlLayout.setEnableLoadMore(enabled);
    }


    @Override
    public void finishRefresh(boolean success) {
        srlLayout.finishRefresh(success);
    }

    @Override
    public void finishLoadMore(boolean success) {
        srlLayout.finishLoadMore(success);
    }

    @Override
    public void showEmpty() {
        slmLoading.showEmptyLayout();
    }

    @Override
    public void showError() {
        slmLoading.showErrorLayout();
    }


    @Override
    protected void onDestroy() {
        DefaultAdapter.releaseAllHolder(rvList);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mRxPermissions = null;
    }
}
