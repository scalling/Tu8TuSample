package com.zm.tu8tu.sample.mvp.ui.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;
import com.zm.tu8tu.sample.R;
import com.zm.tu8tu.sample.mvp.model.api.bean.ColorBean;

import java.util.List;

import butterknife.BindView;

/**
 * @author : zengmei
 * @version : v1.7.0
 * @date : 2018/4/27
 * @description : 描述...
 */
public class FactoryColorAdpter extends DefaultAdapter<ColorBean> {
    public FactoryColorAdpter(List<ColorBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<ColorBean> getHolder(View v, int viewType) {
        return new ItemHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_factory_color;
    }

    static class ItemHolder extends BaseHolder<ColorBean> {

        @BindView(R.id.tv_name)
        TextView tvName;

        public ItemHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(ColorBean data, int position) {
            tvName.setBackgroundColor(Color.parseColor(data.getColor()));
        }
    }
}
