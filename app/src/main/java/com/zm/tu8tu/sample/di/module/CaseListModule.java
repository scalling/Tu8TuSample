package com.zm.tu8tu.sample.di.module;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zm.tu8tu.sample.mvp.contract.CaseListContract;
import com.zm.tu8tu.sample.mvp.model.CaseListModel;
import com.zm.tu8tu.sample.mvp.model.api.bean.ListBeanDto;
import com.zm.tu8tu.sample.mvp.ui.adapter.CaseAdapter;

import java.util.ArrayList;
import java.util.List;


@Module
public class CaseListModule {
    private CaseListContract.View view;

    /**
     * 构建CaseListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CaseListModule(CaseListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CaseListContract.View provideCaseListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CaseListContract.Model provideCaseListModel(CaseListModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RxPermissions provideRxPermissions() {
        return new RxPermissions(view.getActivity());
    }


    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity(),LinearLayoutManager.VERTICAL,false);
    }

    @ActivityScope
    @Provides
    List<ListBeanDto.DataBean> provideUserList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    CaseAdapter provideUserAdapter(List<ListBeanDto.DataBean> list){
        return new CaseAdapter(list);
    }
}