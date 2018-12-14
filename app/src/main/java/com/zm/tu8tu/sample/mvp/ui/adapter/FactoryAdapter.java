package com.zm.tu8tu.sample.mvp.ui.adapter;

import android.view.View;
import android.widget.ImageView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.zm.tu8tu.sample.R;
import com.zm.tu8tu.sample.mvp.model.api.Api;
import com.zm.tu8tu.sample.mvp.model.api.bean.NewCaseDto;

import java.util.List;

import butterknife.BindView;

/**
 * @author : zengmei
 * @version : v1.7.0
 * @date : 2018/4/26
 * @description : 描述...
 */
public class FactoryAdapter extends DefaultAdapter<NewCaseDto.BoxReplacer.FactorysBean.ItemsBean> {
    public FactoryAdapter(List<NewCaseDto.BoxReplacer.FactorysBean.ItemsBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<NewCaseDto.BoxReplacer.FactorysBean.ItemsBean> getHolder(View v, int viewType) {
        return new ItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_factory;
    }

    static class ItemHolder extends BaseHolder<NewCaseDto.BoxReplacer.FactorysBean.ItemsBean> {
        @BindView(R.id.iv_img)
        ImageView ivImg;
        private AppComponent mAppComponent;
        private ImageLoader mImageLoader;

        public ItemHolder(View itemView) {
            super(itemView);
            mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
            mImageLoader = mAppComponent.imageLoader();
        }

        @Override
        public void setData(NewCaseDto.BoxReplacer.FactorysBean.ItemsBean data, int position) {
            mImageLoader.loadImage(itemView.getContext(),
                    ImageConfigImpl
                            .builder()
                            .url(Api.IMAGE_URL + data.getButton_img())
                            .imageView(ivImg)
                            .build());
        }


        @Override
        protected void onRelease() {
//            super.onRelease();
//            mImageLoader.clear(itemView.getContext(), ImageConfigImpl.builder()
//                    .imageViews(ivImg)
//                    .build());
        }
    }
}
