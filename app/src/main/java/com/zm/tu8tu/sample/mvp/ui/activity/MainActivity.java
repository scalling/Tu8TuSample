package com.zm.tu8tu.sample.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.zm.tu8tu.sample.di.component.DaggerMainComponent;
import com.zm.tu8tu.sample.di.module.MainModule;
import com.zm.tu8tu.sample.mvp.contract.MainContract;
import com.zm.tu8tu.sample.mvp.presenter.MainPresenter;

import com.zm.tu8tu.sample.R;


import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

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


    @OnClick(R.id.tv_ke_ting)
    public void keTing() {
         mPresenter.navMain(1, "客厅");
    }

    @OnClick(R.id.tv_wo_shi)
    public void woShi() {
        mPresenter.navMain(2, "卧室");
    }

    @OnClick(R.id.tv_can_ting)
    public void canTing() {
        mPresenter.navMain(3, "餐厅");
    }

    @OnClick(R.id.tv_shu_fang)
    public void shuFang() {
        mPresenter.navMain(7, "书房");
    }
}
