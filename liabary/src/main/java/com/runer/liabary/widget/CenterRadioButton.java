package com.runer.liabary.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.widget.RadioButton;

/**
 * rcsd
 * Create   2017/5/24 11:27;
 * https://github.com/szhua
 * @author sz.hua
 */
public class CenterRadioButton extends AppCompatRadioButton {
    public CenterRadioButton(Context context) {
        super(context);
    }

    public CenterRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CenterRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //获取设置的图片
        Drawable[] drawables = getCompoundDrawables();
            if (drawables != null) {
                Drawable drawableRight = drawables[2];
                if (drawableRight != null) {
                    float textWidth = getPaint().measureText(getText().toString());
                    int drawablePadding = getCompoundDrawablePadding();
                    int drawableWidth = 0;
                    drawableWidth = drawableRight.getIntrinsicWidth();
                    float bodyWidth = textWidth + drawableWidth + drawablePadding;
                    setPadding(0, 0, (int)(getWidth() - bodyWidth), 0);
                    canvas.translate((getWidth() - bodyWidth) / 2, 0);
                }
        }
        super.onDraw(canvas);
    }
}
