package com.game.ex_3;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button secondActivityBtn = (Button) findViewById(R.id.secondActivityBtn);
        secondActivityBtn.setOnClickListener(secondActivityBtnListener);

        Button googleBtn = (Button) findViewById(R.id.googleBtn);
        googleBtn.setOnClickListener(googleBtnListener);

        Button mapBtn = (Button) findViewById(R.id.mapBtn);
        mapBtn.setOnClickListener(mapBtnListener);

        Button phoneBtn = (Button) findViewById(R.id.phoneBtn);
        phoneBtn.setOnClickListener(phoneBtnListener);

        Button computeDeltaBtn = (Button) findViewById(R.id.computeDelta);
        computeDeltaBtn.setOnClickListener(computeDeltaBtnListener);
    }

    private View.OnClickListener secondActivityBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent startIntend = new Intent(getApplicationContext(), SecondActivity.class);
            startActivity(startIntend);
        }
    };

    private View.OnClickListener googleBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String google = "http://www.google.com";
            Uri webadress = Uri.parse(google);
            Intent gotoGoogle = new Intent(Intent.ACTION_VIEW, webadress);
            if (gotoGoogle.resolveActivity(getPackageManager()) != null) {
                startActivity(gotoGoogle);
            }
        }
    };

    private View.OnClickListener mapBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Uri location = Uri.parse("geo:37.422219,-122.08364?z=14");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        }
    };

    private View.OnClickListener phoneBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Uri number = Uri.parse("tel:5551234");
            Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
            if (callIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(callIntent);
            }
        }
    };

    private View.OnClickListener computeDeltaBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent startIntend = new Intent(getApplicationContext(), ComputeDelta.class);
            startActivity(startIntend);
        }
    };

}


