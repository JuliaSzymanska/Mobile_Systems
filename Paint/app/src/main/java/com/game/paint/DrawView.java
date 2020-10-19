package com.game.paint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawView extends View implements View.OnTouchListener {
    Paint paint;
    Bitmap bitmap;
    Canvas canvas;
    Path path;
    private MaskFilter blur = new BlurMaskFilter(10, BlurMaskFilter.Blur.NORMAL);
    int colour;
    boolean isEraser;

    public DrawView(Context context) {
        super(context);
        init();
    }

    /**
     * {@inheritDoc}
     */
    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * {@inheritDoc}
     */
    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * {@inheritDoc}
     */
    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {
        this.setOnTouchListener(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        isEraser = false;
        colour = Color.RED;
        paint = new Paint();
        paint.setColor(colour);
        paint.setStrokeWidth(30);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        path = new Path();
        setmBlurValue();
    }

    void setColour(int chosenColour){
        paint.setColor(chosenColour);
        colour = chosenColour;
    }

    private void drawPath(MotionEvent event) {
        this.path.lineTo(event.getX(), event.getY());
        this.canvas.drawPath(this.path, paint);
        if (this.paint.getMaskFilter() == this.blur) {
            this.path.reset();
            this.path.moveTo(event.getX(), event.getY());
        }
    }

    private void setmBlurValue() {
        boolean setMask = false;
        if (this.paint.getMaskFilter() == this.blur)
            setMask = true;
        int blursize = (int)(paint.getStrokeWidth() / 2);
        if (blursize <= 0)
            blursize = 1;
        blur = new BlurMaskFilter(blursize, BlurMaskFilter.Blur.NORMAL);
        if (setMask)
            this.paint.setMaskFilter(blur);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                this.drawPath(event);
                break;
            case MotionEvent.ACTION_UP:
                this.drawPath(event);
                this.path.reset();
                break;
        }
        invalidate();
        return true;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
    }



}



