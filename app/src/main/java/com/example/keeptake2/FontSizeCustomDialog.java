package com.example.keeptake2;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.RequiresApi;

public class FontSizeCustomDialog extends Dialog implements View.OnClickListener {
    public Activity c;
    public Dialog d;
    public static SeekBar fontSizeSeekBar;
    public Button resetFontSizeBtn;
    public static int fontSizeNewNote=20;


    public FontSizeCustomDialog(Activity a) {
        super(a);
        this.c = a;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.font_size_custom_dialog);
        resetFontSizeBtn=findViewById(R.id.resetBtn);
        fontSizeSeekBar=findViewById(R.id.fontSizeSeekBar);
        fontSizeSeekBar.setMin(12);
        fontSizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                noteActivity.changeFontSize(i);
                fontSizeNewNote=i;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        resetFontSizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteActivity.changeFontSize(20);
                fontSizeSeekBar.setProgress(20);
            }
        });


    }


    @Override
    public void onClick(View view) {}
}
