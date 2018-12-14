package com.zm.tu8tu.sample.mvp.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zm.tu8tu.sample.di.component.DaggerCaseDetailComponent;
import com.zm.tu8tu.sample.di.module.CaseDetailModule;
import com.zm.tu8tu.sample.mvp.contract.CaseDetailContract;
import com.zm.tu8tu.sample.mvp.presenter.CaseDetailPresenter;

import com.zm.tu8tu.sample.R;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CaseDetailActivity extends BaseActivity<CaseDetailPresenter> implements CaseDetailContract.View {

    private RxPermissions rxPermissions;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        rxPermissions = new RxPermissions(this);
        DaggerCaseDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .caseDetailModule(new CaseDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(1);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_case_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        mPresenter.init(localDisplayMetrics.density, localDisplayMetrics.widthPixels, localDisplayMetrics.heightPixels, getIntent().getIntExtra("vrid", 0));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
    public void setShowCaseView(Fragment fragment) {
        FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
        localFragmentTransaction.replace(R.id.contenter, fragment);
        localFragmentTransaction.commit();
    }

    @Override
    public RxPermissions getRxPermissions() {
        return rxPermissions;
    }
}
