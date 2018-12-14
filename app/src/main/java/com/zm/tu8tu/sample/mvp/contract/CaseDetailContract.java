package com.zm.tu8tu.sample.mvp.contract;

import android.support.v4.app.Fragment;
import android.widget.FrameLayout;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zm.tu8tu.sample.mvp.model.api.bean.NewCaseDto;
import com.zm.tu8tu.sample.mvp.model.api.bean.ResultDto;

import io.reactivex.Observable;


public interface CaseDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setShowCaseView(Fragment fragment);

        RxPermissions getRxPermissions();


    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<ResultDto<NewCaseDto>> getCaseDetail(int id);
    }
}
