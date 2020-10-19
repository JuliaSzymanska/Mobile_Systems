package com.game.paint;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;


public class DrawActivity extends AppCompatActivity {

    private DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        drawView = findViewById(R.id.drawView);
        drawView.requestFocus();
    }

    public void blurButtonListener(View v) {
        this.drawView.paint.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL));
//        this.drawView.eraser();
    }

    public void embossButtonListener(View v) {
        EmbossMaskFilter mEmboss = new EmbossMaskFilter(new float[]{1, 1, 1}, 0.5f, 0.6f, 2f);
        this.drawView.paint.setMaskFilter(mEmboss);
    }

    public void normalButtonListener(View v) {
        this.drawView.paint.setMaskFilter(null);
    }

    public void clearButtonListener(View v) {
//        this.drawView.paint.setMaskFilter(null);
        this.drawView.canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }

}