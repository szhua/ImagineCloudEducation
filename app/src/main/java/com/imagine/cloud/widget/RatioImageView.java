package com.imagine.cloud.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.runer.liabary.util.Arith;

public class RatioImageView extends ImageView{


	private double ratio ;

	public void setRatio(double ratio) {
		this.ratio = ratio;
		postInvalidate();
	}

	public RatioImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		if(ratio!=0){
			int widthMode = MeasureSpec.getMode(widthMeasureSpec);
			int heightMode = MeasureSpec.getMode(heightMeasureSpec);
			int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft()
					- getPaddingRight();
			int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingBottom()
					- getPaddingTop();
			height = (int) (Arith.mul(width, ratio));
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
					MeasureSpec.EXACTLY);		
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}


}
