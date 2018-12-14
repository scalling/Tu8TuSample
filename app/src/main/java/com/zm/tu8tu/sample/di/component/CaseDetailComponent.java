package com.zm.tu8tu.sample.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zm.tu8tu.sample.di.module.CaseDetailModule;

import com.zm.tu8tu.sample.mvp.ui.activity.CaseDetailActivity;

@ActivityScope
@Component(modules = CaseDetailModule.class, dependencies = AppComponent.class)
public interface CaseDetailComponent {
    void inject(CaseDetailActivity activity);
}