package com.imagine.cloud.widget.adviewpager;

import java.util.List;

import com.imagine.cloud.R;
import com.imagine.cloud.widget.RatioImageView;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * Pcdd
 * Create   2017/3/15 13:30;
 * https://github.com/szhua
 *
 * @author sz.hua
 */
public final class BannerAdapter extends PagerAdapter  {

	double ratio ;
	private List<BannerBean> data;
	public void setRatio(double ratio){
		this.ratio =ratio ;
	}

public void setData(List<BannerBean> data){
	this.data=data;
	notifyDataSetChanged();
}

	private Context context;

	public BannerAdapter(Context context){
		this.context =context ;
	}

	@Override
	public int getCount() {
		return data==null?0:data.size();
	}
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {

		View view =View.inflate(container.getContext(), R.layout.banner_layout, null);
		RatioImageView imageView =(RatioImageView) view.findViewById(R.id.img);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		container.addView(view);
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

 			}
		});

		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
}
