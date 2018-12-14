package com.zm.tu8tu.sample.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zm.tu8tu.sample.mvp.contract.CaseDetailContract;
import com.zm.tu8tu.sample.mvp.model.api.bean.NewCaseDto;
import com.zm.tu8tu.sample.mvp.model.api.bean.ResultDto;
import com.zm.tu8tu.sample.mvp.model.api.service.CaseService;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


@ActivityScope
public class CaseDetailModel extends BaseModel implements CaseDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public CaseDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<ResultDto<NewCaseDto>> getCaseDetail(int id) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(CaseService.class)
                .getDetail("box", "list", id))
                .flatMap((Function<Observable<ResultDto<NewCaseDto>>, ObservableSource<ResultDto<NewCaseDto>>>) resultDtoObservable -> resultDtoObservable);
    }
}