package com.zm.tu8tu.sample.app.imageloader;

import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.jess.arms.http.imageloader.ImageConfig;

/**
 * Created by shake on 2017/9/18.
 */

public class DefaultImageConfigImpl extends ImageConfig {
    private int cacheStrategy=0;//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
    private int fallback; //请求 url 为空,则使用此图片作为占位符
    private BitmapTransformation transformation;//glide用它来改变图形的形状
    private ImageView[] imageViews;
    private boolean isClearMemory;//清理内存缓存
    private boolean isClearDiskCache;//清理本地缓存
    private String fileUrl;
    private float thumbnail;//缩略
    private boolean centerCrop = true;

    private DefaultImageConfigImpl(DefaultImageConfigImpl.Builder builder) {
        this.url = builder.url;
        this.imageView = builder.imageView;
        this.placeholder = builder.placeholder;
        this.errorPic = builder.errorPic;
        this.fallback = builder.fallback;
        this.cacheStrategy = builder.cacheStrategy;
        this.transformation = builder.transformation;
        this.imageViews = builder.imageViews;
        this.isClearMemory = builder.isClearMemory;
        this.isClearDiskCache = builder.isClearDiskCache;
        this.fileUrl = builder.fileUrl;
        this.thumbnail=builder.thumbnail;
        this.centerCrop =builder.centerCrop;
    }

    public boolean isCenterCrop() {
        return centerCrop;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public float getThumbnail() {
        return thumbnail;
    }

    public int getCacheStrategy() {
        return cacheStrategy;
    }

    public BitmapTransformation getTransformation() {
        return transformation;
    }

    public ImageView[] getImageViews() {
        return imageViews;
    }

    public boolean isClearMemory() {
        return isClearMemory;
    }

    public boolean isClearDiskCache() {
        return isClearDiskCache;
    }

    public int getFallback() {
        return fallback;
    }

    public static DefaultImageConfigImpl.Builder builder() {
        return new DefaultImageConfigImpl.Builder();
    }


    public static final class Builder {
        private String url;
        private ImageView imageView;
        private int placeholder;
        private int errorPic;
        private int fallback; //请求 url 为空,则使用此图片作为占位符
        private int cacheStrategy;//0对应DiskCacheStrategy.all,1对应DiskCacheStrategy.NONE,2对应DiskCacheStrategy.SOURCE,3对应DiskCacheStrategy.RESULT
        private BitmapTransformation transformation;//glide用它来改变图形的形状
        private ImageView[] imageViews;
        private boolean isClearMemory;//清理内存缓存
        private boolean isClearDiskCache;//清理本地缓存
        private float thumbnail;//缩略
        private String fileUrl;//本地文件地址
        private boolean centerCrop = true;//是否中间裁剪

        private Builder() {
        }
        public DefaultImageConfigImpl.Builder fileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
            return this;
        }
        public DefaultImageConfigImpl.Builder thumbnail(float thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }
        public DefaultImageConfigImpl.Builder url(String url) {
            this.url = url;
            return this;
        }

        public boolean isCenterCrop() {
            return centerCrop;
        }

        public DefaultImageConfigImpl.Builder setCenterCrop(boolean centerCrop) {
            this.centerCrop = centerCrop;
            return this;
        }

        public DefaultImageConfigImpl.Builder placeholder(int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public DefaultImageConfigImpl.Builder errorPic(int errorPic) {
            this.errorPic = errorPic;
            return this;
        }

        public DefaultImageConfigImpl.Builder fallback(int fallback) {
            this.fallback = fallback;
            return this;
        }

        public DefaultImageConfigImpl.Builder imageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public DefaultImageConfigImpl.Builder cacheStrategy(int cacheStrategy) {
            this.cacheStrategy = cacheStrategy;
            return this;
        }

        public DefaultImageConfigImpl.Builder transformation(BitmapTransformation transformation) {
            this.transformation = transformation;
            return this;
        }

        public DefaultImageConfigImpl.Builder imageViews(ImageView... imageViews) {
            this.imageViews = imageViews;
            return this;
        }

        public DefaultImageConfigImpl.Builder isClearMemory(boolean isClearMemory) {
            this.isClearMemory = isClearMemory;
            return this;
        }

        public DefaultImageConfigImpl.Builder isClearDiskCache(boolean isClearDiskCache) {
            this.isClearDiskCache = isClearDiskCache;
            return this;
        }


        public DefaultImageConfigImpl build() {
            return new DefaultImageConfigImpl(this);
        }
    }
}
