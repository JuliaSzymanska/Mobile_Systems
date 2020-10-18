package com.game.paint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void drawBtnListener(View v) {
        Intent intent = new Intent(getApplicationContext(), com.game.paint.DrawActivity.class);
        startActivity(intent);
    }

}