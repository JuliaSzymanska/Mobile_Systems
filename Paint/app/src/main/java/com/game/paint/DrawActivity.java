package com.game.paint;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.BlurMaskFilter;
import android.os.Bundle;
import android.view.View;


public class DrawActivity extends AppCompatActivity {

    private DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        drawView.requestFocus();
        drawView = (DrawView) findViewById(R.id.drawView);
    }

    public void blurButtonListener(View v) {
            this.drawView.paint.setMaskFilter(new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL));
    };

}