package com.zm.tu8tu.sample.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.zm.tu8tu.sample.mvp.contract.CaseDetailContract;
import com.zm.tu8tu.sample.mvp.model.CaseDetailModel;


@Module
public class CaseDetailModule {
    private CaseDetailContract.View view;

    /**
     * 构建CaseDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CaseDetailModule(CaseDetailContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CaseDetailContract.View provideCaseDetailView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CaseDetailContract.Model provideCaseDetailModel(CaseDetailModel model) {
        return model;
    }
}