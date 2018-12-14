package com.zm.tu8tu.sample.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.zm.tu8tu.sample.R;
import com.zm.tu8tu.sample.mvp.model.api.bean.NewCaseDto;

import java.util.List;

import butterknife.BindView;

/**
 * @author : zengmei
 * @version : v1.7.0
 * @date : 2018/4/26
 * @description : 描述...
 */
public class SubboxAllAdapter extends DefaultAdapter<NewCaseDto.BoxBean.SubboxBean> {
    public SubboxAllAdapter(List<NewCaseDto.BoxBean.SubboxBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<NewCaseDto.BoxBean.SubboxBean> getHolder(View v, int viewType) {
        return new ItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_subbox_all;
    }

    static class ItemHolder extends BaseHolder<NewCaseDto.BoxBean.SubboxBean> {

        @BindView(R.id.tv_name)
        TextView tvName;
        private AppComponent mAppComponent;
        private ImageLoader mImageLoader;

        public ItemHolder(View itemView) {
            super(itemView);
            mAppComponent = ArmsUtils.obtainAppComponentFromContext(itemView.getContext());
            mImageLoader = mAppComponent.imageLoader();
        }

        @Override
        public void setData(NewCaseDto.BoxBean.SubboxBean data, int position) {
            tvName.setText(data.getName());

        }

        @Override
        protected void onRelease() {
        }
    }

}
