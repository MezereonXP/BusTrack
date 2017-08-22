package com.example.mezereon.Home.Model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.mezereon.R;

/**
 * Created by Administrator on 2017-08-17.
 */

public class Myline extends View {

    private boolean isShowLine =true;

    private boolean isShowBigOne = false;
    public Myline(Context context) {
        super(context);
        setWillNotDraw(false);
    }

    public Myline(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public Myline(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
            p.setStrokeWidth(10);
            p.setColor(getResources().getColor(R.color.colormain));
            float x = getX();
            if (isShowLine) {
                canvas.drawLine(x + 30, 60, x + 30, 999999, p);
            }
            p.setStrokeWidth(5);
            p.setStyle(Paint.Style.STROKE);

            if (isShowBigOne) {
                p.setColor(Color.parseColor("#3A5FCD"));
                p.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawCircle(x + 30, 30, 28, p);
            }else{
                canvas.drawCircle(x + 30, 30, 28, p);
            }
            //canvas.drawLine(60, 60, 60,475 , p);
    }

    public boolean isShowLine() {
        return isShowLine;
    }

    public void setShowLine(boolean showLine) {
        isShowLine = showLine;
    }

    public boolean isShowBigOne() {
        return isShowBigOne;
    }

    public void setShowBigOne(boolean showBigOne) {
        isShowBigOne = showBigOne;
    }
}
