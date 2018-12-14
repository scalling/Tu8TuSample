package com.zm.tu8tu.sample.mvp.presenter;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadLargeFileListener;
import com.zm.tu8tu.sample.app.utils.BitmapUtil;
import com.zm.tu8tu.sample.app.utils.DownLoadUtil;
import com.zm.tu8tu.sample.app.utils.FileUtil;
import com.zm.tu8tu.sample.mvp.contract.CaseShowContract;
import com.zm.tu8tu.sample.mvp.model.api.Api;
import com.zm.tu8tu.sample.mvp.model.api.bean.ColorBean;
import com.zm.tu8tu.sample.mvp.model.api.bean.NewCaseDto;
import com.zm.tu8tu.sample.mvp.model.api.bean.ResultDto;
import com.zm.tu8tu.sample.mvp.ui.adapter.FactoryAdapter;
import com.zm.tu8tu.sample.mvp.ui.adapter.FactoryColorAdpter;
import com.zm.tu8tu.sample.mvp.ui.adapter.SubboxAdapter;
import com.zm.tu8tu.sample.mvp.ui.adapter.SubboxAllAdapter;
import com.zm.tu8tu.sample.mvp.ui.view.MyImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import timber.log.Timber;


@ActivityScope
public class CaseShowPresenter extends BasePresenter<CaseShowContract.Model, CaseShowContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    public static NewCaseDto mCaseDtos;
    public static int mScreenHeight;
    public static int mScreenWidth;
    public static int vrid = 0;
    private List<FrameLayout> mFrameLayouts;
    private float k;
    private int o;
    private int mWidth;
    private int mHeight;

    private DefaultAdapter mAdapter;
    private int mPosition;//当前点击的位置
    private boolean mRoted;//是否翻转
    private List<NewCaseDto.BoxBean.SubboxBean> subboxs = new ArrayList<>();

    private Map<String, List<NewCaseDto.BoxBean.SubboxBean>> map = new HashMap<>();


    private List<ColorBean> colors;
    private int mDownLoadCount = 0;
    @Inject
    public CaseShowPresenter(CaseShowContract.Model model, CaseShowContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }


    /**
     * 根据分辨率和设计模型计算宽高
     *
     * @param modelWidth  模型设计宽度
     * @param modelHeight 模型设计高度
     */
    public void initWidthHeight(int modelWidth, int modelHeight) {
        this.k = (Float.parseFloat(mScreenHeight + "") / modelHeight);
        if ((int) (this.k * modelWidth) > mScreenWidth)
            this.k = (Float.parseFloat(mScreenWidth + "") / modelWidth);
        this.mWidth = (int) (modelWidth * this.k);
        this.mHeight = (int) (modelHeight * this.k);
        this.o = ((mScreenWidth - this.mWidth) / 2);
    }

    /**
     * 初始化数据
     */
    public void initData() {
        this.mFrameLayouts = new ArrayList();
        initWidthHeight(1136, 757);
        if (mRootView != null) {
            for (NewCaseDto.BoxBean box : mCaseDtos.getBox()) {
                FrameLayout localFrameLayout = new FrameLayout(mApplication.getApplicationContext());
                FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(mWidth, mHeight);
                LinearLayout localLinearLayout = new LinearLayout(mApplication.getApplicationContext());
                localLinearLayout.setLayoutParams(localLayoutParams);
                MyImageView localb5 = new MyImageView(mApplication.getApplicationContext());
                localb5.setScaleType(ImageView.ScaleType.CENTER);
                localLinearLayout.addView(localb5);
                localFrameLayout.addView(localLinearLayout);
                localFrameLayout.setLayoutParams(localLayoutParams);
                mFrameLayouts.add(localFrameLayout);
                mRootView.addFrameLayout(localFrameLayout);
                localb5.setImageTouchInterface(imageTouchInterface);
                if (box.getSubbox_count() != 0) {
                    for (NewCaseDto.BoxBean.SubboxBean subboxBean : box.getSubbox()) {
                        subboxBean.setLevel(box.getLevel());
                        if (TextUtils.isEmpty(subboxBean.getName())) {
                            subboxBean.setName(box.getName());
                        }
                        subboxs.add(subboxBean);
                    }
                    if (box.getSubbox_count() > 1) {
                        map.put(box.getLevel(), box.getSubbox());
                    }
                }
                localb5.setBox(box);
                //加载数据
                int i8 = Integer.parseInt(box.getLevel());
                if (box.getPoint_x() != 0.0F) {
                    changeImg(i8, box.getBox_img(), box.getScale(), box.getWscale(), box.getHscale(), box.getPoint_x(), box.getPoint_y());
                } else {
                    changeBg(i8, box.getBox_img());
                }


            }
        }
        mRootView.hideLoading();

    }


    ////赋值point_x 为0的图片

    /**
     * 赋值图片 当point_x 为0的时候
     *
     * @param level  层级
     * @param boxImg 数据类
     */
    private void changeBg(int level, String boxImg) {
        MyImageView imageView = (MyImageView) ((LinearLayout) (LinearLayout) ((FrameLayout) mFrameLayouts.get(level)).getChildAt(0)).getChildAt(0);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(mWidth, mHeight));
        Bitmap localBitmap7 = BitmapUtil.getBitmap(mApplication.getApplicationContext(), boxImg, mWidth, mHeight);
        Bitmap localBitmap8 = null;
        if (mRoted) {
            localBitmap8 = BitmapUtil.rotating(BitmapUtil.matrixScale(localBitmap7, mWidth, mHeight), 360);
        } else {
            localBitmap8 = BitmapUtil.matrixScale(localBitmap7, mWidth, mHeight);
        }
        imageView.setOriginalHeight(localBitmap8.getHeight());
        imageView.setOriginalWith(localBitmap8.getWidth());
        imageView.setMImageBitmap(localBitmap8);
    }


    /**
     * 设置墙漆颜色
     *
     * @param level
     * @param color 颜色值
     */
    private void changeBgColor(int level, int color) {
        MyImageView imageView = (MyImageView) ((LinearLayout) (LinearLayout) ((FrameLayout) mFrameLayouts.get(level)).getChildAt(0)).getChildAt(0);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(mWidth, mHeight));
        Bitmap localBitmap7 = BitmapUtil.getBitmap(mApplication.getApplicationContext(), mCaseDtos.getBox().get(level).getBox_img(), mWidth, mHeight);
        Bitmap localBitmap8;
        if (mRoted) {
            localBitmap8 = BitmapUtil.rotating(BitmapUtil.matrixScale(localBitmap7, mWidth, mHeight), 360);
        } else {
            localBitmap8 = BitmapUtil.matrixScale(localBitmap7, mWidth, mHeight);
        }
        imageView.setImageBitmap(BitmapUtil.changeBitmapBg(localBitmap8, color));
    }


    /**
     * 赋值图片 当point_x 不为0的时候
     *
     * @param level   层级
     * @param boxImg
     * @param scala
     * @param wScalae
     * @param hScale
     * @param pointX
     * @param pointY
     */
    private void changeImg(int level, String boxImg, float scala, float wScalae, float hScale, float pointX, float pointY) {
        MyImageView imageView = (MyImageView) ((LinearLayout) (LinearLayout) ((FrameLayout) mFrameLayouts.get(level)).getChildAt(0)).getChildAt(0);
        Bitmap localBitmap5 = BitmapUtil.getImageFromAssetsFile(boxImg);
        Matrix localMatrix2 = new Matrix();
        localMatrix2.postScale(wScalae * scala * k, hScale * scala * k);
        Bitmap localBitmap6 = Bitmap.createBitmap(localBitmap5, 0, 0, localBitmap5.getWidth(), localBitmap5.getHeight(), localMatrix2, true);
        LinearLayout.LayoutParams localLayoutParams3 = new LinearLayout.LayoutParams(-2, -2);
        int i9 = (int) (pointY * k + localBitmap6.getHeight() / 2);
        int i10 = (int) (pointX * k + localBitmap6.getWidth() / 2);
        int i11 = (int) (pointY * k - localBitmap6.getHeight() / 2);
        int i12 = (int) ((pointX) * k - localBitmap6.getWidth() / 2);
        if (i9 > mHeight)
            localLayoutParams3.height = localBitmap6.getHeight();
        if (i10 > mWidth)
            localLayoutParams3.width = localBitmap6.getWidth();
        localLayoutParams3.setMargins(i12, i11, 0, 0);
        imageView.setOriginalHeight(localBitmap6.getHeight());
        imageView.setOriginalWith(localBitmap6.getWidth());
        imageView.setLayoutParams(localLayoutParams3);
        if (mRoted) {
            imageView.setMImageBitmap(BitmapUtil.rotating(localBitmap6, 360));
        } else {
            imageView.setMImageBitmap(localBitmap6);
        }
        imageView.setClickable(true);
        if (mRoted) {
            LinearLayout.LayoutParams localLayoutParams4 = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            localLayoutParams4.leftMargin = mWidth - i12 - localBitmap6.getWidth();
            localLayoutParams4.width = localBitmap6.getWidth();
            localLayoutParams4.height = localBitmap6.getHeight();
            imageView.setLayoutParams(localLayoutParams4);
        }
    }


    /**
     * 视角转换 旋转
     */
    public void onClickRotating() {
        mRoted = !mRoted;
        for (FrameLayout frameLayout : mFrameLayouts) {
            MyImageView localImageView = (MyImageView) ((LinearLayout) (LinearLayout) (frameLayout).getChildAt(0)).getChildAt(0);
            BitmapDrawable localBitmapDrawable = (BitmapDrawable) localImageView.getDrawable();
            if (localBitmapDrawable != null) {
                Bitmap localBitmap = localBitmapDrawable.getBitmap();
                if (localBitmap != null) {
                    if (localImageView.getBox().getPoint_x() != 0) {
                        LinearLayout.LayoutParams localLayoutParams4 = (LinearLayout.LayoutParams) localImageView.getLayoutParams();
                        if (mRoted) {
                            localLayoutParams4.leftMargin = mWidth - localImageView.getLeft() - localImageView.getWidth();
                        } else {
                            localLayoutParams4.leftMargin = (int) ((localImageView.getBox().getPoint_x()) * k - localBitmap.getWidth() / 2);
                        }
                        localLayoutParams4.width = localBitmap.getWidth();
                        localLayoutParams4.height = localBitmap.getHeight();
                        localImageView.setLayoutParams(localLayoutParams4);
                    }
                    localImageView.setMImageBitmap(BitmapUtil.rotating(localBitmap, 360));
                }
            }
        }
    }

    private MyImageView.ImageTouchInterface imageTouchInterface = new MyImageView.ImageTouchInterface() {
        @Override
        public void onImageTouch(MotionEvent paramMotionEvent, String level) {
            /**
             * 触发点击事件
             *
             * @param paramMotionEvent
             * @param level            层级（当前点击的img）
             */
            if (paramMotionEvent.getAction() != MotionEvent.ACTION_DOWN || mCaseDtos == null) {
                return;
            }
            mPosition = Integer.parseInt(level);
            NewCaseDto.BoxBean boxBean = mCaseDtos.getBox().get(mPosition);
            if (boxBean.getSubbox_count() == 0 || boxBean.getSubbox() == null || boxBean.getSubbox().size() == 0)
                return;
            if (boxBean.getSubbox_count() > 1 && map.get(boxBean.getLevel()) != null) {
                initAllBubbox(map.get(boxBean.getLevel()));
            } else {
                int id = boxBean.getSubbox().get(0).getId();
                setSubboxAdapter(id);
            }


        }
    };


    private List<NewCaseDto.BoxReplacer.FactorysBean> factorysBeans = null;
    private List<NewCaseDto.BoxReplacer.FactorysBean.ItemsBean> itemsBeans = null;

    private void setSubboxAdapter(int id) {
        NewCaseDto.BoxBean boxBean = mCaseDtos.getBox().get(mPosition);
        for (NewCaseDto.BoxReplacer factorysBean : mCaseDtos.getSubbox()) {
            if (id == factorysBean.getId()) {
                factorysBeans = factorysBean.getFactorys();
                break;
            }
        }
        if (factorysBeans == null || factorysBeans.size() == 0) {
            return;
        }
        mAdapter = new SubboxAdapter(factorysBeans);
        mRootView.initAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                if (boxBean.getType().equals("0") && boxBean.getSubbox_count() > 0) {
                    initFactoryAdapterColor();
                } else {
                    initFactoryAdapter(factorysBeans.get(position).getItems());
                }
            }
        });
    }

    private void initFactoryAdapter(List<NewCaseDto.BoxReplacer.FactorysBean.ItemsBean> itemsBeans) {
        mAdapter = new FactoryAdapter(itemsBeans);
        mRootView.initAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                NewCaseDto.BoxBean box = mCaseDtos.getBox().get(mPosition);

                box.setSelect_img(itemsBeans.get(position).getImg());
                downSingle(box);

            }
        });
    }

    /**
     * 墙漆颜色列表
     */
    private void initFactoryAdapterColor() {
        if (colors == null) {
            colors = new ArrayList<>();
            colors.add(new ColorBean("#990033"));
            colors.add(new ColorBean("#CC6699"));
        }
        mAdapter = new FactoryColorAdpter(colors);
        mRootView.initAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                String color = colors.get(position).getColor();
                mCaseDtos.getBox().get(mPosition).setSelect_color(color);
                changeBgColor(mPosition, Color.parseColor(color));
            }
        });

    }

    /**
     * 加载右边选项
     */
    private void initAllBubbox(List<NewCaseDto.BoxBean.SubboxBean> subboxs) {
        mAdapter = new SubboxAllAdapter(subboxs);
        mRootView.initAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new DefaultAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {
                mPosition = Integer.parseInt(subboxs.get(position).getLevel());
                int id = subboxs.get(position).getId();
                setSubboxAdapter(id);
            }
        });
    }


    /**
     * 加载右边全部选项
     */
    public void onClickAllSubbox() {
        initAllBubbox(subboxs);
    }


    /**
     * 重置数据
     */
    public void onClickRefresh() {
        mRoted = false;
        for (NewCaseDto.BoxBean box : mCaseDtos.getBox()) {
            int i8 = Integer.parseInt(box.getLevel());
            if (box.getPoint_x() != 0.0F) {
                changeImg(i8, box.getBox_img(), box.getScale(), box.getWscale(), box.getHscale(), box.getPoint_x(), box.getPoint_y());
            } else {
                changeBg(i8, box.getBox_img());
            }
        }
    }


    public void loadData() {
        mRootView.showLoading(Color.WHITE);
        result(mModel.getCaseDetail(vrid), new ErrorHandleSubscriber<Object>(mErrorHandler) {
            @Override
            public void onNext(Object o) {
                ResultDto<NewCaseDto> resultDto = (ResultDto<NewCaseDto>) o;
                if (resultDto.getStatus() == 1) {
                    mCaseDtos = resultDto.getContent();
                    downMultit();
                } else {
                    mRootView.showError();
                    mRootView.showMessage(resultDto.getTitle());
                }

            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                mRootView.showError();
            }
        });


    }

    private <T> void result(Observable<T> obj, ErrorHandleSubscriber<Object> handler) {
        obj.subscribeOn(Schedulers.io())//在io线程里面
                .retryWhen(new RetryWithDelay(Api.MAX_RETRIES, Api.RETRY_DEALAY_SECOND))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .subscribeOn(AndroidSchedulers.mainThread())//在main线程里面
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {

                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(handler);
    }


    private void downMultit() {
        Map<String, String> urls = new HashMap<>();
        for (NewCaseDto.BoxBean box : mCaseDtos.getBox()) {
            String filePath = FileUtil.getFilePath(box.getBox_img());
            File file = new File(filePath);
            if (!file.exists()) {
                urls.put(box.getBox_img(), filePath);
            }
        }
        if (urls.size() == 0) {
            initData();
            return;
        }
        mDownLoadCount = 0;
        DownLoadUtil.downMultit(Api.IMAGE_URL, urls, new FileDownloadLargeFileListener() {
            @Override
            protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {

            }

            @Override
            protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                Timber.i("正在下载");
            }

            @Override
            protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {

            }

            @Override
            protected void completed(BaseDownloadTask task) {
                Timber.i("下载成功");
                if (mRootView != null) {
                    mDownLoadCount++;
                    if (mDownLoadCount == urls.size()) {
                        initData();
                    }
                }


            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                Timber.e("下载失败");
                if (mRootView != null) {
                    mDownLoadCount++;
                    if (mDownLoadCount == urls.size()) {
                        mRootView.hideLoading();
                    }
                }

            }

            @Override
            protected void warn(BaseDownloadTask task) {

            }
        });
    }


    private void downSingle(NewCaseDto.BoxBean box) {
        String filePath = FileUtil.getFilePath(box.getSelect_img());
        File file = new File(filePath);
        Timber.e("1" + filePath);
        if (file.exists()) {
            changeDownImg(box);
            return;
        }
        mRootView.showLoading(Color.TRANSPARENT);
        DownLoadUtil.downSingle(Api.IMAGE_URL, box.getSelect_img(), filePath, new FileDownloadLargeFileListener() {
            @Override
            protected void pending(BaseDownloadTask task, long soFarBytes, long totalBytes) {

            }

            @Override
            protected void progress(BaseDownloadTask task, long soFarBytes, long totalBytes) {
                Timber.i("正在下载");
            }

            @Override
            protected void paused(BaseDownloadTask task, long soFarBytes, long totalBytes) {

            }

            @Override
            protected void completed(BaseDownloadTask task) {
                Timber.i("下载成功");
                if (mRootView != null) {
                    mRootView.hideLoading();
                    changeDownImg(box);
                }

            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                Timber.e("下载失败");
                if (mRootView != null) {
                    mRootView.hideLoading();
                }


            }

            @Override
            protected void warn(BaseDownloadTask task) {

            }
        });
    }

    private void changeDownImg(NewCaseDto.BoxBean box) {
        if (box.getPoint_x() == 0) {
            changeBg(mPosition, box.getSelect_img());
        } else {
            changeImg(mPosition, box.getSelect_img(), box.getScale(), box.getWscale(), box.getHscale(), box.getPoint_x(), box.getPoint_y());
        }
    }
}
