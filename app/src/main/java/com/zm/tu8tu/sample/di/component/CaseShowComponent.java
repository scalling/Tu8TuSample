package com.zm.tu8tu.sample.di.component;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.zm.tu8tu.sample.di.module.CaseShowModule;

import com.zm.tu8tu.sample.mvp.ui.fragment.CaseShowFragment;

@ActivityScope
@Component(modules = CaseShowModule.class, dependencies = AppComponent.class)
public interface CaseShowComponent {
    void inject(CaseShowFragment fragment);
}