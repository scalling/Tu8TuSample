package com.zm.tu8tu.sample.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.zm.tu8tu.sample.R;
import com.zm.tu8tu.sample.di.component.DaggerCaseShowComponent;
import com.zm.tu8tu.sample.di.module.CaseShowModule;
import com.zm.tu8tu.sample.mvp.contract.CaseShowContract;
import com.zm.tu8tu.sample.mvp.presenter.CaseShowPresenter;

import butterknife.BindView;
import butterknife.OnClick;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class CaseShowFragment extends BaseFragment<CaseShowPresenter> implements CaseShowContract.View {

    @BindView(R.id.fl_container)
    FrameLayout ceptFramelayout;

    @BindView(R.id.rv_list)
    RecyclerView rvList;


    @BindView(R.id.tv_load)
    TextView tvLoad;

    @BindView(R.id.rl_parent)
    View rlParent;

    private StatusLayoutManager slmLoading;

    public static CaseShowFragment newInstance(int vrid, int screenWidth, int screenHeight) {
        CaseShowPresenter.mScreenWidth = screenWidth;
        CaseShowPresenter.mScreenHeight = screenHeight;
        CaseShowPresenter.vrid = vrid;
        return new CaseShowFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCaseShowComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .caseShowModule(new CaseShowModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_case_show, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        rvList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        slmLoading = new StatusLayoutManager.Builder(rlParent)

                // 设置重试事件监听器
                .setOnStatusChildClickListener(new OnStatusChildClickListener() {
                    @Override
                    public void onEmptyChildClick(View view) {
                        mPresenter.loadData();
                    }

                    @Override
                    public void onErrorChildClick(View view) {
                        mPresenter.loadData();
                    }

                    @Override
                    public void onCustomerChildClick(View view) {


                    }
                })
                .build();

        mPresenter.loadData();
    }

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {
    }

    @Override
    public void showLoading() {
        slmLoading.showLoadingLayout();
    }

    @Override
    public void hideLoading() {
        slmLoading.showSuccessLayout();
        tvLoad.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }

    @OnClick(R.id.btn_rotating)
    void onClick() {
        mPresenter.onClickRotating();
    }

    @OnClick(R.id.btn_all)
    void onAllClick() {
        mPresenter.onClickAllSubbox();
    }

    @OnClick(R.id.btn_refresh)
    void onRefreshClick() {
        mPresenter.onClickRefresh();
    }


    @Override
    public void initAdapter(DefaultAdapter adapter) {

        rvList.setAdapter(adapter);
    }

    @Override
    public void addFrameLayout(FrameLayout frameLayout) {
        ceptFramelayout.addView(frameLayout);
    }

    @Override
    public void showLoading(int color) {
        slmLoading.showLoadingLayout();
        tvLoad.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        slmLoading.showEmptyLayout();
    }

    @Override
    public void showError() {
        slmLoading.showErrorLayout();
    }

}
