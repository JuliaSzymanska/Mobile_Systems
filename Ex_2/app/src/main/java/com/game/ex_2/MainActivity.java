package com.game.ex_2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void secondActivityBtnListener(View v) {
        Intent secondActivityIntend = new Intent(this, SecondActivity.class);
        startActivity(secondActivityIntend);
    }

    public void googleBtnListener(View v) {
        Uri webadress = Uri.parse("http://www.google.com");
        Intent googleIntent = new Intent(Intent.ACTION_VIEW, webadress);
        if (googleIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(googleIntent);
        }
    }

    public void mapBtnListener(View v) {
        Uri location = Uri.parse("geo:37.422219,-122.08364?z=14");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    public void phoneBtnListener(View v) {
        Uri number = Uri.parse("tel:123456789");
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        if (callIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(callIntent);
        }
    }

    public void computeDeltaBtnListener(View v) {
        Intent calculateDeltaIntent = getPackageManager().getLaunchIntentForPackage("com.game.calculate_delta");
        if (calculateDeltaIntent != null) {
            startActivity(calculateDeltaIntent);
        }
    }
}


