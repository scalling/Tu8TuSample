package com.zm.tu8tu.sample.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;

import java.io.FileInputStream;
import java.io.IOException;

import timber.log.Timber;

/**
 * @author : zengmei
 * @version : v1.7.0
 * @date : 2018/4/20
 * @description : 描述...
 */
public class BitmapUtil {
    public static Bitmap getImageFromAssetsFile(String fileName) {
        try {
            String filePath = FileUtil.getFilePath(fileName);
            Timber.i(filePath);
            FileInputStream fis = new FileInputStream(filePath);
            Bitmap image = BitmapFactory.decodeStream(fis);
            fis.close();
            return image;

        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }


    /**
     * 获取缩放比例
     *
     * @param paramOptions
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int getInSampleSize(BitmapFactory.Options paramOptions, int reqWidth, int reqHeight) {

        // 计算原始图像的高度和宽度
        final int height = paramOptions.outHeight;
        final int width = paramOptions.outWidth;
        int inSampleSize = 1;

//判定，当原始图像的高和宽大于所需高度和宽度时
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            //算出长宽比后去比例小的作为inSamplesize，保障最后imageview的dimension比request的大
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

//计算原始图片总像素
            final float totalPixels = width * height;
            // Anything more than 2x the requested pixels we'll sample down further

//所需总像素*2,长和宽的根号2倍
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

//如果遇到很长，或者是很宽的图片时，这个算法比较有用
            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    //改变图片背景颜色

    /**
     * @param paramBitmap
     * @param color       颜色值
     * @return
     */
    public static Bitmap changeBitmapBg(Bitmap paramBitmap, int color) {

        Bitmap localBitmap1 = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap1);
        Paint localPaint1 = new Paint(1);
        localPaint1.setColor(color);
        localCanvas.drawRect(new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight()), localPaint1);
        BitmapShader localBitmapShader = new BitmapShader(localBitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        int i = localCanvas.saveLayer(0.0F, 0.0F, paramBitmap.getWidth(), paramBitmap.getHeight(), null, 16);
        Paint localPaint2 = new Paint();
        localPaint2.setFilterBitmap(false);
        localPaint2.setStyle(Paint.Style.FILL);
        localPaint2.setShader(localBitmapShader);

        if (!paramBitmap.isRecycled()) {
            localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, localPaint2);
            localPaint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
            localCanvas.drawBitmap(localBitmap1, 0.0F, 0.0F, localPaint2);
            localPaint2.setXfermode(null);
            localCanvas.restoreToCount(i);
        }
        return localBitmap1;
    }

    public static Bitmap matrixScale(Bitmap paramBitmap, int paramInt1, int paramInt2) {
        try {
            int i = paramBitmap.getWidth();
            int j = paramBitmap.getHeight();
            Matrix localMatrix = new Matrix();
            localMatrix.postScale((float) paramInt1 / (float) i, (float) paramInt2 / (float) j);
            return Bitmap.createBitmap(paramBitmap, 0, 0, i, j, localMatrix, true);
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * 通过图片地址获取图片
     *
     * @param context
     * @param img     图片地址
     * @param width   宽度
     * @param height  高度
     * @return
     */
    public static Bitmap getBitmap(Context context, String img, int width, int height) {
        BitmapFactory.Options localOptions = new BitmapFactory.Options();
        localOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(img, localOptions);
        localOptions.inSampleSize = getInSampleSize(localOptions, width, width);
        localOptions.inJustDecodeBounds = false;
        localOptions.inPurgeable = true;
        localOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap image = null;
        // AssetManager am = context.getResources().getAssets();
        try {
            //InputStream is = am.open(img);
            FileInputStream fis = new FileInputStream(FileUtil.getFilePath(img));
            image = BitmapFactory.decodeStream(fis, null, localOptions);
            fis.close();
            return image;

        } catch (IOException e) {
            e.printStackTrace();


        }


        return null;
    }

    /**
     * 图片翻转
     *
     * @param paramBitmap
     * @param rotating
     * @return
     */
    public static Bitmap rotating(Bitmap paramBitmap, int rotating) {
        int i = paramBitmap.getWidth();
        int j = paramBitmap.getHeight();
        Bitmap localBitmap1 = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap1);
        Matrix localMatrix = new Matrix();
        localMatrix.postScale(-1.0F, 1.0F);
        localMatrix.postRotate(360.0F);
        Bitmap localBitmap2 = Bitmap.createBitmap(paramBitmap, 0, 0, i, j, localMatrix, true);
        localCanvas.drawBitmap(localBitmap2, new Rect(0, 0, localBitmap2.getWidth(), localBitmap2.getHeight()), new Rect(0, 0, i, j), null);
        paramBitmap.recycle();
        System.gc();
        return localBitmap1;
    }
}
