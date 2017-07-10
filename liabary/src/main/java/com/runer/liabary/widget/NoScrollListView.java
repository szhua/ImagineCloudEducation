/**
 * @(#) MyListView.java Created on 2013-3-19
 *
 * Copyright (c) 2013 Aspire. All Rights Reserved
 */
package com.runer.liabary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ListView;

/**
 * The class <code>MyListView</code>
 * 
 * @author likai
 * @version 1.0
 */
public class NoScrollListView extends ListView {

    /**
     * Constructor
     * 
     * @param context
     */
    public NoScrollListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor
     * @param context
     * @param attrs
     * @param defStyle
     */
    public NoScrollListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor
     * @param context
     * @param attrs
     */
    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
