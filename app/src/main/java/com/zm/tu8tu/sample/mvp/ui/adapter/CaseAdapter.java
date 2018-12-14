package com.zm.tu8tu.sample.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.zm.tu8tu.sample.R;
import com.zm.tu8tu.sample.mvp.model.api.bean.ListBeanDto;

import java.util.List;

import butterknife.BindView;

/**
 * @author : zengmei
 * @version : v1.7.0
 * @date : 2018/5/3
 * @description : 描述...
 */
public class CaseAdapter extends DefaultAdapter<ListBeanDto.DataBean> {
    public CaseAdapter(List<ListBeanDto.DataBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<ListBeanDto.DataBean> getHolder(View v, int viewType) {
        return new ItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_case;
    }

    static class ItemHolder extends BaseHolder<ListBeanDto.DataBean> {

        @BindView(R.id.tv_title)
        TextView tvTitle;

        public ItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(ListBeanDto.DataBean data, int position) {
            tvTitle.setText(data.getTitle());
        }
    }
}
