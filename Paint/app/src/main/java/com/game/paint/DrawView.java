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
import android.graphics.PorterDuffXfermode;
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
        colour = Color.BLUE;
        paint = new Paint();
        paint.setColor(colour);
        paint.setStrokeWidth(25);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        path = new Path();
        setBlurSettings();
    }

    private void drawPath(MotionEvent event) {
        path.lineTo(event.getX(), event.getY());
        canvas.drawPath(path, paint);
        if (paint.getMaskFilter() == blurMaskFilter) {
            path.reset();
            path.moveTo(event.getX(), event.getY());
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath(event);
                break;
            case MotionEvent.ACTION_UP:
                drawPath(event);
                path.reset();
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
    }

    float getStrokeWidth() {
        return paint.getStrokeWidth();
    }

    void setColour(int chosenColour) {
        turnOffEraseMode();
        paint.setColor(chosenColour);
        colour = chosenColour;
    }

    void setBlurSettings() {
        blurMaskFilter = new BlurMaskFilter(paint.getStrokeWidth(), BlurMaskFilter.Blur.NORMAL);
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
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

    void setStrokeWidth(int width) {
        paint.setStrokeWidth(width);
    }

    void setErase() {
        if (!isEraser) {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            isEraser = true;
        } else {
            turnOffEraseMode();
        }
    }

    private void turnOffEraseMode() {
        paint.setXfermode(null);
        isEraser = false;
    }

}



