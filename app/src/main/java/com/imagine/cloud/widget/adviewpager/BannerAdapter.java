package com.imagine.cloud.widget.adviewpager;

import java.util.List;

import com.imagine.cloud.R;
import com.imagine.cloud.base.BaseWebAcitivity;
import com.imagine.cloud.net.Requst;
import com.imagine.cloud.ui.activity.MeetingDetailActivity;
import com.imagine.cloud.ui.activity.ProjectDetailActivity;
import com.imagine.cloud.widget.RatioImageView;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * Pcdd
 * Create   2017/3/15 13:30;
 * https://github.com/szhua
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
              if("0".equals(data.get(position).getType())){
				  Bundle bundle  =new Bundle();
				  bundle.putString("id",data.get(position).getMp_id());
				  Intent intent =new Intent(v.getContext(), MeetingDetailActivity.class);
				  intent.putExtras(bundle);
				  v.getContext().startActivity(intent);
			  }else if("1".equals(data.get(position).getType())){
				  Bundle bundle  =new Bundle();
				  bundle.putString("id",data.get(position).getMp_id());
				  bundle.putString("title",data.get(position).getTitle());
				  Intent intent =new Intent(v.getContext(), ProjectDetailActivity.class);
				  intent.putExtras(bundle);
				  v.getContext().startActivity(intent);
			  }else if("2".equals(data.get(position).getType())){
				  Bundle bundle  =new Bundle();
				  bundle.putString(BaseWebAcitivity.WEB_URL,data.get(position).getUrl());
				  bundle.putString(BaseWebAcitivity.WEB_TITLE,data.get(position).getTitle());
				  Intent intent =new Intent(v.getContext(), BaseWebAcitivity.class);
				  intent.putExtras(bundle);
				  v.getContext().startActivity(intent);
			  }
 			}
		});
		Picasso.with(context).load(Requst.BASE_IMG_URL+data.get(position).getImg()).placeholder(R.drawable.start).into(imageView);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
}
