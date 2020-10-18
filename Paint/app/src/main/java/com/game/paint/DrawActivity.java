package com.game.paint;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class DrawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawView drawView = new DrawView(this);
        setContentView(drawView);
        drawView.requestFocus();
    }
}