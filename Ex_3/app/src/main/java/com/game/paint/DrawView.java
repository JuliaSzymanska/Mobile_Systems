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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DrawView extends View implements View.OnTouchListener {
    private Paint paint;
    private Bitmap bitmap;
    private Canvas canvas;
    private Path path;
    private MaskFilter blurMaskFilter;
    private boolean isEraser;
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);

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
        bitmap = Bitmap.createBitmap(Resources.getSystem().getDisplayMetrics().widthPixels,
                Resources.getSystem().getDisplayMetrics().heightPixels,
                Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
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
    }

    float getStrokeWidth() {
        return paint.getStrokeWidth();
    }

    void setColour(int chosenColour) {
        turnOffEraseMode();
        paint.setColor(chosenColour);
    }

    void setCanvasColour(int chosenColour) {
        invalidate();
        canvas.drawColor(chosenColour);
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
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
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

    void saveImage() throws IOException {

        String fileName = "Paint " + formatter.format(new Date());
        OutputStream fos;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = getContext().getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName + ".png");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
        } else {
            String imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            File image = new File(imagesDir, fileName + ".png");
            fos = new FileOutputStream(image);
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        Objects.requireNonNull(fos).close();
    }
}



