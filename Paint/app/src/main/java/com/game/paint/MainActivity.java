package com.game.paint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setSupportActionBar(findViewById(R.id.toolbar));
        Button DrawActivity = (Button) findViewById(R.id.Draw_Btn);
        DrawActivity.setOnClickListener(drawBtnListener);
    }

    private final View.OnClickListener drawBtnListener = v -> {
        Intent intent = new Intent(getApplicationContext(), com.game.paint.DrawActivity.class);
        startActivity(intent);
    };

}