package com.imagine.cloud.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.imagine.cloud.R;

/**
 * Created by szhua on 2017/7/6/006.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * OrderView
 */
public class OrderView extends LinearLayout {

    public OrderView(Context context) {
        this(context,null,0);
    }
    public OrderView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }
    public OrderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.information_order_layout,this);
    }

}
