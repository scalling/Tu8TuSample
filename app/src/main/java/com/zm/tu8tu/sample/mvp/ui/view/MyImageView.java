package com.zm.tu8tu.sample.mvp.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;


import com.zm.tu8tu.sample.mvp.model.api.bean.NewCaseDto;

import timber.log.Timber;


/**
 * @author : zengmei
 * @version : v1.7.0
 * @date : 2018/4/20
 * @description : 描述...
 */
public class MyImageView extends android.support.v7.widget.AppCompatImageView {
    public NewCaseDto.BoxBean mBoxDto;
    private int x;
    private int y;
    private ImageTouchInterface imageTouchInterface;

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setImageTouchInterface(ImageTouchInterface imageTouchInterface) {
        this.imageTouchInterface = imageTouchInterface;
    }

    public NewCaseDto.BoxBean getBox() {
        return this.mBoxDto;
    }


    public int getOriginalHeight() {
        return this.y;
    }

    public int getOriginalWith() {
        return this.x;
    }


    public boolean onTouchEvent(MotionEvent paramMotionEvent) {
        boolean i1 = false;
        switch (paramMotionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (this.mBoxDto != null) {
                    String str = this.mBoxDto.getType();

                    if ((str.equals("2"))) {
                        i1 = false;
                    } else {
                        BitmapDrawable localBitmapDrawable = (BitmapDrawable) getDrawable();
                        if (localBitmapDrawable != null) {
                            Bitmap localBitmap = localBitmapDrawable.getBitmap();
                            int i2 = (int) paramMotionEvent.getX();
                            int i3 = (int) paramMotionEvent.getY();
                            float f1 = Float.parseFloat(String.valueOf(getWidth())) / this.x;
                            float f2 = Float.parseFloat(String.valueOf(getHeight())) / this.y;
                            if ((i2 <= 0) || (localBitmap.getWidth() <= i2 / f1) || (i3 / f2 >= localBitmap.getHeight()) || (i3 <= 0) || (localBitmap.getPixel((int) (i2 / f1), (int) (i3 / f2)) != 0)) {
                                i1 = true;
                            }
                        } else {
                            i1 = true;
                        }
                    }
                } else {
                    i1 = true;
                }
                if (i1) {
                    if (imageTouchInterface != null) {
                        Timber.e(getBox().getBox_img());
                        imageTouchInterface.onImageTouch(paramMotionEvent, getBox().getLevel());
                    }


                }
                break;
        }
        return i1;
    }

    public void setBox(NewCaseDto.BoxBean boxDto) {
        this.mBoxDto = boxDto;
    }


    public void setMImageBitmap(Bitmap paramBitmap) {
        if (paramBitmap != null) {
            setImageBitmap(paramBitmap);

        }
    }

    public void setOriginalHeight(int paramInt) {
        this.y = paramInt;
    }

    public void setOriginalWith(int paramInt) {
        this.x = paramInt;
    }

    public interface ImageTouchInterface {
        void onImageTouch(MotionEvent paramMotionEvent, String level);
    }

}
