package com.imagine.cloud.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imagine.cloud.R;

/**
 * Created by szhua on 2017/7/5/005.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * NormalInputEdittext
 */

public class NormalInputEditText extends LinearLayout {

    private String leftString ;
    private String rightHint ;
    private String inputType ;
    private int leftTextColor ;
    private int rightHintTextColor ;


    private TextView leftText ;
    private EditText rightEdit ;

    public static  final String DEFAULT_INPUT_TYPE ="text" ;
    public static  final String NUM_INPUT_TYPE ="num" ;
    public static  final String PASS_INPUT_TYPE ="pass" ;

    private boolean isEditable  =true;


    public NormalInputEditText(Context context) {
        this(context,null,0);
    }

    public NormalInputEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NormalInputEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.normal_input_edittext,this);
        leftText  = (TextView) findViewById(R.id.left_text);
        rightEdit = (EditText) findViewById(R.id.rigt_edit);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NormalInputEditText, defStyleAttr, 0);
        int count = ta.getIndexCount();
        leftTextColor = ContextCompat.getColor(getContext(),R.color.text_color_normal) ;
        rightHintTextColor =ContextCompat.getColor(getContext(),R.color.text_color_light) ;
        for (int i = 0; i < count; i++) {
            int attr = ta.getIndex(i);
            //如果引用成AndroidLib 资源都不是常量，无法使用switch case
            if (attr == R.styleable.NormalInputEditText_input_type) {
                inputType =ta.getString(attr);
            } else if (attr == R.styleable.NormalInputEditText_left_text) {
                leftString = ta.getString(attr);
            } else if (attr == R.styleable.NormalInputEditText_right_hint) {
                rightHint = ta.getString(attr);
            }else  if(attr==R.styleable.NormalInputEditText_left_text_color){
                leftTextColor =ta.getColor(attr, ContextCompat.getColor(getContext(),R.color.text_color_normal)) ;
            }else if(attr ==R.styleable.NormalInputEditText_right_hint_color){
                rightHintTextColor =ta.getColor(attr,ContextCompat.getColor(getContext(),R.color.text_color_light));
            }else if(attr==R.styleable.NormalInputEditText_editable){
                isEditable =ta.getBoolean(attr,true) ;
            }
        }
        ta.recycle();

        if(!TextUtils.isEmpty(leftString)){
            leftText.setText(leftString);
        }
        if(!TextUtils.isEmpty(rightHint)){
            rightEdit.setHint(rightHint);
        }

        //设置可否编辑
        rightEdit.setEnabled(isEditable);

        switch (inputType){
            case DEFAULT_INPUT_TYPE:
                rightEdit.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case PASS_INPUT_TYPE:
                rightEdit.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                break;
            case NUM_INPUT_TYPE:
                rightEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            default:
                rightEdit.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        leftText.setTextColor(leftTextColor);
        rightEdit.setHintTextColor(rightHintTextColor);
    }

    //获取输入的内容
    public String  getInputContent(){
        return  rightEdit.getText().toString();
    }

    public void setRightHint(String rihtHint){
        if(!TextUtils.isEmpty(rihtHint)){
            rightEdit.setHint(rihtHint);
        }else{
            rightEdit.setText("未填写");
        }
    }


    public void setRightText(String rightText){
        if(!TextUtils.isEmpty(rightText)){
            rightEdit.setText(rightText);
        }
    }
}
