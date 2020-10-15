package com.game.ex_3;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class ComputeDelta extends AppCompatActivity {
    private static DecimalFormat decimalFormat = new DecimalFormat("#.###");
    private int firstInputInt;
    private int secondInputInt;
    private int thirdInputInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute_delta);
        Button computeButton = (Button) findViewById(R.id.button);
        computeButton.setOnClickListener(computeButtonListener);

    }

    private View.OnClickListener computeButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText firstInput = (EditText) findViewById(R.id.first_input);
            EditText secondInput = (EditText) findViewById(R.id.second_input);
            EditText thirdInput = (EditText) findViewById(R.id.third_input);
            try {
                firstInputInt = Integer.parseInt(firstInput.getText().toString());
                secondInputInt = Integer.parseInt(secondInput.getText().toString());
                thirdInputInt = Integer.parseInt(thirdInput.getText().toString());
                calculateRoots();
            } catch (NumberFormatException e) {
                Toast.makeText(ComputeDelta.this, getString(R.string.enter_numbers), Toast.LENGTH_SHORT).show();
            }

        }
    };

    private int calculateDelta() {
        return secondInputInt * secondInputInt - 4 * firstInputInt * thirdInputInt;
    }

    private double calculateRoot(int minus, double delta) {
        return (-1 * secondInputInt + minus * Math.sqrt(delta)) / (2 * firstInputInt);
    }

    @SuppressLint("SetTextI18n")
    private void calculateRoots() {
        TextView output = (TextView) findViewById(R.id.output);
        if (firstInputInt == 0.0) {
            output.setText(getString(R.string.not_quadratic));
            return;
        }
        double delta = calculateDelta();
        if (delta < 0.0) {
            output.setText(getString(R.string.has_no_root) + getString(R.string.discriminant) + decimalFormat.format(delta));
            return;
        }
        if (delta == 0) {
            double first_root = calculateRoot(0, delta);
            output.setText(getString(R.string.root) + decimalFormat.format(first_root) + getString(R.string.discriminant) + decimalFormat.format(delta));
            return;
        }
        double first_root = calculateRoot(1, delta);
        double second_root = calculateRoot(-1, delta);
        output.setText(getString(R.string.first_root) + decimalFormat.format(first_root) + getString(R.string.second_root) + decimalFormat.format(second_root) + getString(R.string.discriminant) + decimalFormat.format(delta));
    }
}