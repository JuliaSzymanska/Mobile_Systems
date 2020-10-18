package com.game.paint;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import static android.view.Window.FEATURE_NO_TITLE;
import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class DrawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(FLAG_FULLSCREEN,FLAG_FULLSCREEN);
        requestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_draw);
        DrawView drawView = new DrawView(this);
        setContentView(drawView);
        drawView.requestFocus();

    }
}