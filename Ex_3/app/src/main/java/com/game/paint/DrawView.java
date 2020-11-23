package com.game.paint;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
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
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Random;

public class DrawView extends View implements View.OnTouchListener {
    private Paint paint;
    private Bitmap bitmap;
    private Canvas canvas;
    private Path path;
    private MaskFilter blurMaskFilter;
    private boolean isEraser;
    private int canvasColour;

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
        canvasColour = Color.WHITE;
        bitmap = Bitmap.createBitmap(Resources.getSystem().getDisplayMetrics().widthPixels,
                Resources.getSystem().getDisplayMetrics().heightPixels,
                Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        isEraser = false;
        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        path = new Path();
        setBlurSettings();
    }

    private void draw(MotionEvent event) {
        path.lineTo(event.getX(), event.getY());
        canvas.drawPath(path, paint);
        if (paint.getMaskFilter() == blurMaskFilter) {
            drawInBlurMode(event);
        }
    }

    private void drawInBlurMode(MotionEvent event) {
        path.reset();
        path.moveTo(event.getX(), event.getY());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                draw(event);
                break;
            case MotionEvent.ACTION_UP:
                draw(event);
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
        setBackgroundColor(canvasColour);
    }

    float getStrokeWidth() {
        return paint.getStrokeWidth();
    }

    void setColour(int chosenColour) {
        turnOffEraseMode();
        paint.setColor(chosenColour);
    }

    void setCanvasColour(int chosenColour) {
        canvasColour = chosenColour;
        invalidate();
    }

    void setBlurSettings() {
        blurMaskFilter = new BlurMaskFilter(paint.getStrokeWidth(), BlurMaskFilter.Blur.NORMAL);
    }

    void setBlur() {
        setBlurSettings();
        turnOffEraseMode();
        paint.setMaskFilter(blurMaskFilter);
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

    void importImage(Bitmap bitmap) {
        clearCanvas();
        this.canvas.drawBitmap(bitmap, 0, 0, null);
    }

    void saveImage() {
        FileOutputStream fos = null;
        String path = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            path = String.valueOf(Environment.getExternalStorageDirectory()) +
                    "/" + java.time.Clock.systemUTC().instant() + ".jpg";
        }
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(new File(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



