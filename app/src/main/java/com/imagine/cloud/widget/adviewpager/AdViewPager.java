package com.imagine.cloud.widget.adviewpager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.imagine.cloud.R;
import com.runer.liabary.indicator.CircleIndicator;
import com.runer.liabary.util.Arith;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdViewPager extends LinearLayout {

	private final BannerAdapter  bannerAdapter;
	private ViewPager viewPager ;
	private CircleIndicator indicator;
	private int currentItem = 0;
	private TextView adTitle ;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				viewPager.setCurrentItem(currentItem);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	private Timer timer;
	private List<BannerBean> datas;
	public AdViewPager(Context context) {
		this(context,null,0);
	}
	public AdViewPager(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}
	public AdViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		LayoutInflater.from(context).inflate(R.layout.ad_viewpager_layout, this) ;
		viewPager =(ViewPager) findViewById(R.id.ad_viewpager);
		indicator =(CircleIndicator) findViewById(R.id.indicator);
		adTitle = (TextView) findViewById(R.id.ad_title);
		bannerAdapter =new BannerAdapter(getContext());
		viewPager.setAdapter(bannerAdapter);
		indicator.setViewPager(viewPager);
		viewPager.addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				currentItem =arg0;
				if(datas!=null||!datas.isEmpty())
				adTitle.setText(datas.get(arg0).getTitle());
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	private class ScrollTask extends TimerTask {
		public void run() {
			synchronized (viewPager) {
				if(datas!=null&&!datas.isEmpty()){
					currentItem = (currentItem + 1) %4;
					handler.obtainMessage().sendToTarget();
				}
			}
		}
	}
	public void setIndicatorGone(){
		if(indicator!=null){
			indicator.setVisibility(View.GONE);
		}
	}
	public View getViewPager(){
		return viewPager ;
	}
	double ratio ;
	public void setViewPagerHeight(final double ratio){
		this.ratio =ratio ;
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		double windowWidth = wm.getDefaultDisplay().getWidth();
		double viewPageHeight =Arith.mul(windowWidth, ratio);
		bannerAdapter.setRatio(ratio);
		postInvalidate();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if(ratio!=0){
			int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft()
					- getPaddingRight();
			int height = (int) (Arith.mul(width, ratio) + 0.5f);
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
					MeasureSpec.EXACTLY);		
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public void setBannerFirstTitlte(String title){
		adTitle.setText(title);
	}
	//设置数据开始轮播
	public void setBannerEntities(List<BannerBean> data) {
		if(bannerAdapter!=null){
			datas=data;
			bannerAdapter.setData(data);
			viewPager.setAdapter(bannerAdapter);
			indicator.setViewPager(viewPager);
			if(timer==null){
				timer=new Timer() ;
			}else{
				timer.cancel();
				timer =null ;
				timer =new Timer();
			}
			timer.schedule(new ScrollTask(),3000,3000);
		}
	}








}
