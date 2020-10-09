package com.game.calculate_delta;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private double firstInputDouble;
    private double secondInputDouble;
    private double thirdInputDouble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button computeButton = (Button) findViewById(R.id.button);
        computeButton.setOnClickListener(computeButtonListener);

    }

    private View.OnClickListener computeButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText firstInput = (EditText) findViewById(R.id.first_input);
            EditText secondInput = (EditText) findViewById(R.id.second_input);
            EditText thirdInput = (EditText) findViewById(R.id.third_input);
            firstInputDouble = Double.parseDouble(firstInput.getText().toString());
            secondInputDouble = Double.parseDouble(secondInput.getText().toString());
            thirdInputDouble = Double.parseDouble(thirdInput.getText().toString());
            calculateRoots();
        }
    };

    private Double calculateDelta(){
        return secondInputDouble * secondInputDouble - 4 * firstInputDouble * thirdInputDouble;
    }

    private double calculateRoot(int minus, double delta){
        return  (-1 * secondInputDouble + minus * Math.sqrt(delta)) / 2 * firstInputDouble;
    }

    @SuppressLint("SetTextI18n")
    private void calculateRoots(){
        TextView output = (TextView) findViewById(R.id.output);
        if(firstInputDouble == 0.0 ) {
            output.setText("It is not a quadratic equation.");
            return;
        }
        double delta = calculateDelta();
        if(delta < 0.0) {
            output.setText("The quadratic equation hasn't any roots. " + ". The square discriminant equals " + delta);
            return;
        }
        if (delta == 0) {
            double first_root = calculateRoot(0, delta);
            output.setText("The quadratic equation has one root equals " + first_root + ". The square discriminant equals " + delta);
            return;
        }
        double first_root = calculateRoot(1, delta);
        double second_root = calculateRoot(-1, delta);
        output.setText("The quadratic equation has two roots. First equals " + first_root + ", second equals " + second_root + ". The square discriminant equals " + delta);
        return;
    }
}