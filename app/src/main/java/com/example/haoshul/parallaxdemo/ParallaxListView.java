package com.example.haoshul.parallaxdemo;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by haoshul on 2016/6/16.
 */
public class ParallaxListView extends ListView {
    public ParallaxListView(Context context) {
        super(context);
    }

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private ImageView imageView;
    private int maxHeight; //最大高度
    private int originHeight; //imageview的原始高度
    public void setImageView(final ImageView imageView){
        this.imageView = imageView;

        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                originHeight = imageView.getHeight();

                int drawableHeight = imageView.getDrawable().getIntrinsicHeight();
                if (originHeight >= drawableHeight){
                    maxHeight = (int) (originHeight *1.5);
                }else
                    maxHeight = drawableHeight;
            }
        });


    }
    /**
     * 当listview滑到两端时调用，在该方法中可以获得继续滑动的距离
     * @param deltaX
     * @param deltaY y 方向继续滑动的距离，正：底部到头； 负：顶部到头
     * @param scrollX
     * @param scrollY
     * @param scrollRangeX
     * @param scrollRangeY
     * @param maxOverScrollX
     * @param maxOverScrollY  到头后可以继续滑动的最大距离
     * @param isTouchEvent true为手动滑动到头，false为惯性滑动到头
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
                                   int scrollRangeX, int scrollRangeY, int maxOverScrollX,
                                   int maxOverScrollY, boolean isTouchEvent) {

        //如果顶部到头，并且手指拖动到头
        if (deltaY < 0  && isTouchEvent == true){
            int newHeight = imageView.getHeight() - deltaY/3;
            if (newHeight > maxHeight) newHeight = maxHeight;
            imageView.getLayoutParams().height = newHeight;
            imageView.requestLayout();
        }

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
                scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:
                final ValueAnimator animator = ValueAnimator.ofInt(imageView.getHeight(),originHeight);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        imageView.getLayoutParams().height = (int) animator.getAnimatedValue();
                        imageView.requestLayout();
                    }
                });
                animator.setInterpolator(new OvershootInterpolator());
                animator.setDuration(350);
                animator.start();
        }
        return super.onTouchEvent(ev);
    }
}
