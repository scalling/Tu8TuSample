package com.zm.tu8tu.sample.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

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
public class SubboxAdapter extends DefaultAdapter<NewCaseDto.BoxReplacer.FactorysBean> {
    public SubboxAdapter(List<NewCaseDto.BoxReplacer.FactorysBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<NewCaseDto.BoxReplacer.FactorysBean> getHolder(View v, int viewType) {
        return new ItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_subbox;
    }

    static class ItemHolder extends BaseHolder<NewCaseDto.BoxReplacer.FactorysBean> {
        @BindView(R.id.tv_name)
        TextView tvName;

        public ItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(NewCaseDto.BoxReplacer.FactorysBean data, int position) {
            tvName.setText(data.getFactory_name());
        }
    }
}
