package com.game.paint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View implements View.OnTouchListener {
    public final Paint paint;
    private Bitmap bitmap;
    private Canvas canvas;

    public DrawView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
//        EmbossMaskFilter mEmboss = new EmbossMaskFilter(new float[]{1, 1, 1}, 0.5f, 0.6f, 2f);
//        paint.setMaskFilter(mEmboss);
        this.setOnTouchListener(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        canvas.drawCircle(event.getX(), event.getY(), 30, paint);
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

}
