package com.kishan.dynamicisland.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VisualizerView extends View {
    private static final int LINE_WIDTH = 3; // width of visualizer lines
    private static final int LINE_SCALE = 700; // scales visualizer lines
    private List<Float> amplitudes; // amplitudes for line lengths
    private int width; // width of this View
    private int height; // height of this View
    private Paint linePaint; // specifies line drawing characteristics

    // constructor
    public VisualizerView(Context context, AttributeSet attrs) {
        super(context, attrs); // call superclass constructor
        linePaint = new Paint(); // create Paint for lines
        linePaint.setColor(Color.GREEN); // set color to green
        linePaint.setStrokeWidth(LINE_WIDTH); // set stroke width
    }

    // called when the dimensions of the View change
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w; // new width of this View
        height = h; // new height of this View
        amplitudes = new ArrayList<>(width / LINE_WIDTH);
    }

    // clear all amplitudes to prepare for a new visualization
    public void clear() {
        amplitudes.clear();
    }

    // add the given amplitude to the amplitudes ArrayList
    public void addAmplitude(float amplitude) {
        amplitudes.add(amplitude); // add newest to the amplitudes ArrayList
        // if the power lines completely fill the VisualizerView
        if (amplitudes.size() * LINE_WIDTH >= width) {
            //amplitudes.remove(0); // remove oldest power value
            amplitudes.clear(); // remove oldest power value
            linePaint.setColor(getRandomColor());
        }
    }



    /**
     * min and max are to be understood inclusively
     */
    public int getRandomColor() {
        int min =0;
        int max = 6;
        int random = (new Random()).nextInt((max - min) + 1) + min;
        switch (random) {
            case 0:
                return Color.GREEN;
            case 1:
                return Color.RED;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.WHITE;
            case 4:
                return Color.YELLOW;
            case 5:
                return Color.MAGENTA;
            default:
                return Color.GRAY;
        }
    }

    public void addColor(int color) {
        //linePaint.setColor(color);
    }

    // draw the visualizer with scaled lines representing the amplitudes
    @Override
    public void onDraw(@NonNull Canvas canvas) {
        int middle = height / 2; // get the middle of the View

        canvas.drawLine(0, middle, width, middle , linePaint);
        float curX = 0; // start curX at zero
        // for each item in the amplitudes ArrayList
        for (float power : amplitudes) {
            float scaledHeight = power / LINE_SCALE; // scale the power
            curX += LINE_WIDTH; // increase X by LINE_WIDTH
            // draw a line representing this item in the amplitudes ArrayList
            canvas.drawLine(curX, middle + scaledHeight / 2, curX, middle - scaledHeight / 2, linePaint);
        }


        Log.e("onDraw", "here");
    }
}
