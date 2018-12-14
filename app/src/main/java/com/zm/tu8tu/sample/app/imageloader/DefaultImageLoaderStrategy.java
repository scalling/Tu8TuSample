package com.zm.tu8tu.sample.app.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jess.arms.http.imageloader.BaseImageLoaderStrategy;
import com.jess.arms.http.imageloader.glide.GlideArms;
import com.jess.arms.http.imageloader.glide.GlideRequest;
import com.jess.arms.http.imageloader.glide.GlideRequests;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shake on 2017/9/18.
 */

public class DefaultImageLoaderStrategy implements BaseImageLoaderStrategy<DefaultImageConfigImpl> {
    @Override
    public void loadImage(Context ctx, DefaultImageConfigImpl config) {
        if (ctx == null) throw new NullPointerException("Context is required");
        if (config == null) throw new NullPointerException("ImageConfigImpl is required");
        if (TextUtils.isEmpty(config.getUrl())&& TextUtils.isEmpty(config.getFileUrl())) throw new NullPointerException("Url is required");
        if (config.getImageView() == null) throw new NullPointerException("Imageview is required");



        GlideRequests requests;

        requests = GlideArms.with(ctx);//如果context是activity则自动使用Activity的生命周期

        GlideRequest<Drawable> glideRequest=null;
        if(!TextUtils.isEmpty(config.getUrl())){
            glideRequest= requests.load(config.getUrl());
        }
        if(!TextUtils.isEmpty(config.getFileUrl())){
            glideRequest= requests.load(new File(config.getFileUrl()));
        }
//        if(glideRequest==null) throw new NullPointerException("GlideRequests is null");
//        glideRequest.transition(DrawableTransitionOptions.withCrossFade())
//                .centerCrop();
        if(glideRequest==null) throw new NullPointerException("GlideRequests is null");
        if(config.isCenterCrop()){
//            glideRequest.transition(DrawableTransitionOptions.withCrossFade())
//                    .centerCrop();
            glideRequest
                    .centerCrop();
        }
        //else{
//            glideRequest.transition(DrawableTransitionOptions.withCrossFade());
//        }

        if(config.getThumbnail()>0){
            glideRequest.thumbnail(config.getThumbnail());
        }


        switch (config.getCacheStrategy()) {//缓存策略
            case 0:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case 1:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case 2:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                break;
            case 3:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA);
                break;
            case 4:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                break;
        }
        if (config.getTransformation() != null) {//glide用它来改变图形的形状
            glideRequest.transform(config.getTransformation());
        }


        if (config.getPlaceholder() != 0)//设置占位符
            glideRequest.placeholder(config.getPlaceholder());

        if (config.getErrorPic() != 0)//设置错误的图片
            glideRequest.error(config.getErrorPic());

        if (config.getFallback() != 0)//设置请求 url 为空图片
            glideRequest.fallback(config.getFallback());

        //glideRequest.dontAnimate();
        glideRequest.dontAnimate()
                .into(config.getImageView());
    }

    @Override
    public void clear(final Context ctx, DefaultImageConfigImpl config) {
        if (ctx == null) throw new NullPointerException("Context is required");
        if (config == null) throw new NullPointerException("ImageConfigImpl is required");

        if (config.getImageViews() != null && config.getImageViews().length > 0) {//取消在执行的任务并且释放资源
            for (ImageView imageView : config.getImageViews()) {
                GlideArms.get(ctx).getRequestManagerRetriever().get(ctx).clear(imageView);
            }
        }

        if (config.isClearDiskCache()) {//清除本地缓存
            Observable.just(0)
                    .observeOn(Schedulers.io())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(@NonNull Integer integer) throws Exception {
                            Glide.get(ctx).clearDiskCache();
                        }
                    });
        }

        if (config.isClearMemory()) {//清除内存缓存
            Observable.just(0)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(@NonNull Integer integer) throws Exception {
                            Glide.get(ctx).clearMemory();
                        }
                    });
        }
    }
}
