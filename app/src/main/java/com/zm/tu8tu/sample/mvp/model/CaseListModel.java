package com.zm.tu8tu.sample.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zm.tu8tu.sample.mvp.contract.CaseListContract;
import com.zm.tu8tu.sample.mvp.model.api.bean.ListBeanDto;
import com.zm.tu8tu.sample.mvp.model.api.bean.ResultDto;
import com.zm.tu8tu.sample.mvp.model.api.service.CaseService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


@ActivityScope
public class CaseListModel extends BaseModel implements CaseListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CaseListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<ResultDto<ListBeanDto>> getCaseList(int page, int id) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CaseService.class)
                .getList("vrcase", "list", 1, 0, page, 25, 0, 0, id))
                .flatMap((Function<Observable<ResultDto<ListBeanDto>>, ObservableSource<ResultDto<ListBeanDto>>>) resultDtoObservable -> resultDtoObservable);
    }
}