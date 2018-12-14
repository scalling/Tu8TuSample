package com.zm.tu8tu.sample.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import com.zm.tu8tu.sample.mvp.contract.CaseShowContract;
import com.zm.tu8tu.sample.mvp.model.CaseShowModel;


@Module
public class CaseShowModule {
    private CaseShowContract.View view;

    /**
     * 构建CaseShowModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CaseShowModule(CaseShowContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CaseShowContract.View provideCaseShowView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CaseShowContract.Model provideCaseShowModel(CaseShowModel model) {
        return model;
    }
}