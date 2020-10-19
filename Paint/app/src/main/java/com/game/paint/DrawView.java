package com.game.paint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawView extends View implements View.OnTouchListener {
    private Paint paint;
    private Bitmap bitmap;
    private Canvas canvas;
    private Path path;
    private MaskFilter blurMaskFilter;
    private int colour;
    private boolean isEraser;

    public DrawView(Context context) {
        super(context);
        init();
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
        setBlurSettings();
    }

    private void drawPath(MotionEvent event) {
        this.path.lineTo(event.getX(), event.getY());
        this.canvas.drawPath(this.path, paint);
        if (this.paint.getMaskFilter() == this.blurMaskFilter) {
            this.path.reset();
            this.path.moveTo(event.getX(), event.getY());
        }
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

    float getStrokeWidth(){
        return paint.getStrokeWidth();
    }

    void setColour(int chosenColour) {
        turnOffEraseMode();
        paint.setColor(chosenColour);
        colour = chosenColour;
    }

    void setBlurSettings() {
        int size = (int) (paint.getStrokeWidth() / 2);
        blurMaskFilter = new BlurMaskFilter((size <= 0) ? 1 : size, BlurMaskFilter.Blur.NORMAL);
    }

    void setBlur() {
        turnOffEraseMode();
        paint.setMaskFilter(blurMaskFilter);
    }

    void setEmboss() {
        turnOffEraseMode();
        EmbossMaskFilter mEmboss = new EmbossMaskFilter(new float[]{1, 1, 1}, 0.5f, 0.6f, 2f);
        paint.setMaskFilter(mEmboss);
    }

    void setNormal() {
        turnOffEraseMode();
        paint.setMaskFilter(null);
    }

    void clearCanvas() {
        turnOffEraseMode();
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
    }

    void setStrokeWidth(int width) {
        paint.setStrokeWidth(width);
    }

    void setErase() {
        if (!isEraser) {
            paint.setColor(Color.WHITE);
            isEraser = true;
        } else {
            turnOffEraseMode();
        }
    }

    private void turnOffEraseMode() {
        paint.setColor(colour);
        isEraser = false;
    }

}



