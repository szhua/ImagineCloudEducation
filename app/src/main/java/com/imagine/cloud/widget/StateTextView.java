package com.imagine.cloud.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import com.imagine.cloud.R;
import com.orhanobut.logger.Logger;

/**
 * Created by szhua on 2017/7/10/010.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * StateTextView
 *
 * 可更换状态的textView;
 */

public class StateTextView extends AppCompatTextView {
    private  int selectedTextColor;
    private  int unSelectedTextColor;
    private  int  leftSelectIcon;
    private  int  leftUnSelectIcon;

    public StateTextView(Context context) {
        this(context,null,0);
    }
    public StateTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }
    public StateTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StateTextView, defStyleAttr, 0);

         selectedTextColor = ta.getColor(R.styleable.StateTextView_text_selected_color,getResources().getColor(R.color.colorAccent));
         unSelectedTextColor = ta.getColor(R.styleable.StateTextView_text_un_selected_color,getResources().getColor(R.color.text_color_gray));

         leftSelectIcon =ta.getResourceId(R.styleable.StateTextView_left_selected_icon,R.drawable.zan);
         leftUnSelectIcon =ta.getResourceId(R.styleable.StateTextView_left_un_selected_icon,R.drawable.zan_un);
        //设置默认的属性
        this.setTextColor(unSelectedTextColor);
/// 这一步必须要做,否则不会显示.
        Drawable drawable =getResources().getDrawable(leftUnSelectIcon);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        this.setCompoundDrawables(drawable,null,null,null);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
       if(selected){

           this.setTextColor(selectedTextColor);
/// 这一步必须要做,否则不会显示.
           Drawable drawable =getResources().getDrawable(leftSelectIcon);
           drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
           this.setCompoundDrawables(drawable,null,null,null);

       }else{

           this.setTextColor(unSelectedTextColor);
          /// 这一步必须要做,否则不会显示.
           Drawable drawable =getResources().getDrawable(leftUnSelectIcon);
           drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
           this.setCompoundDrawables(drawable,null,null,null);

       }
    }



}
