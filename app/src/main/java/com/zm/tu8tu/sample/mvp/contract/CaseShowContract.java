package com.zm.tu8tu.sample.mvp.contract;

import android.widget.FrameLayout;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zm.tu8tu.sample.mvp.model.api.bean.NewCaseDto;
import com.zm.tu8tu.sample.mvp.model.api.bean.ResultDto;

import io.reactivex.Observable;


public interface CaseShowContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IBaseView {
        void showLoading(int color);


        /**
         * 初始化列表
         *
         * @param adapter
         */
        void initAdapter(DefaultAdapter adapter);

        /**
         * 添加子view
         *
         * @param frameLayout
         */
        void addFrameLayout(FrameLayout frameLayout);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<ResultDto<NewCaseDto>> getCaseDetail(int id);
    }
}
