package com.game.paint;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;


public class DrawActivity extends AppCompatActivity {

    private DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        drawView = findViewById(R.id.drawView);
        drawView.requestFocus();
    }

    public void normalButtonListener(View v) {
        drawView.setNormal();
    }

    public void blurButtonListener(View v) {
        this.drawView.setBlurSettings();
        this.drawView.setBlur();
    }

    public void embossButtonListener(View v) {
        this.drawView.setEmboss();
    }

    public void clearButtonListener(View v) {
        this.drawView.clearCanvas();
    }

    public void colourButtonListener(View v) {
        ColorPickerDialogBuilder
                .with(this)
                .setTitle("Choose color")
                .initialColor(Color.BLUE)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(18)
                .setPositiveButton(R.string.ok, (dialog, selectedColor, allColors) -> drawView.setColour(selectedColor))
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                })
                .build()
                .show();
    }

    public void sizeButtonListener(View v) {
        final AlertDialog.Builder d = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.number_picker_dialog, null);
        d.setTitle("Select size");
        d.setView(dialogView);
        final NumberPicker numberPicker = dialogView.findViewById(R.id.dialog_number_picker);
        numberPicker.setMaxValue(100);
        numberPicker.setMinValue(1);
        numberPicker.setValue((int) drawView.getStrokeWidth());
        numberPicker.setWrapSelectorWheel(false);
        d.setPositiveButton(R.string.ok, (dialogInterface, i) -> drawView.setStrokeWidth(numberPicker.getValue()));
        d.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
        });
        AlertDialog alertDialog = d.create();
        alertDialog.show();
    }

    public void eraseButtonListener(View v) {
        drawView.setErase();
    }

}