package com.zm.tu8tu.sample.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zm.tu8tu.sample.di.module.CaseListModule;

import com.zm.tu8tu.sample.mvp.ui.activity.CaseListActivity;

@ActivityScope
@Component(modules = CaseListModule.class, dependencies = AppComponent.class)
public interface CaseListComponent {
    void inject(CaseListActivity activity);
}