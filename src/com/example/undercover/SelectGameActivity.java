package com.example.undercover;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectGameActivity extends BaseActivity {
	// viewPager 滑动
	private ViewPager viewPager;
	
	// 把需要滑动的页卡添加到这个list中
	private List<View> viewList;
	
	// 包裹小圆点的LinearLayout
    private ViewGroup pointContainer;
    
	// 包含滑动ViewPager的Layout
    private ViewGroup mainContainer;
    
	// 将小圆点ImageView放入该List
    private ImageView[] imageViews;
    
	private Button clickmeButton, circlemeButton, questionButton, weixinButton,
			appmakerbButton, btnStart;
    private ImageView startButton;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LayoutInflater mInflater = getLayoutInflater();
		
		// Welcome 的Layout
		View welcomeView = mInflater.inflate(R.layout.activity_welcome, null);
		// 帮助页面的layout
		View helpView = mInflater.inflate(R.layout.undercover_content, null);
		// 更多游戏的Layout
		View moreView = mInflater.inflate(R.layout.activity_more, null);
				
        viewList = new ArrayList<View>();
        viewList.add(helpView);
        viewList.add(welcomeView);
        viewList.add(moreView);
        
        mainContainer = (ViewGroup) mInflater.inflate(R.layout.activity_select_game, null);
        pointContainer = (ViewGroup) mainContainer.findViewById(R.id.pointGroup);
        viewPager = (ViewPager) mainContainer.findViewById(R.id.viewpager);
        
        imageViews = new ImageView[viewList.size()];
        Log.d("imageView length", String.valueOf(imageViews.length));
		// 将小圆点放到Layout中
		for (int i = 0; i < imageViews.length; i++) {
			ImageView image = new ImageView(SelectGameActivity.this);
			image.setLayoutParams(new LayoutParams(30, 30));
			image.setPadding(20, 20, 20, 20);
			// 默认为第一小圆点
			if (i==0) {
				image.setBackgroundResource(R.drawable.page_indicator_focused);
			}else{
				image.setBackgroundResource(R.drawable.page_indicator);
			}
			
			imageViews[i] = image;
			pointContainer.addView(image);
		}
        setContentView(mainContainer);
        
		// helpButton = (Button) helpView.findViewById(R.id.helpButton);
		// helpButton.setOnClickListener(new MyClickListener());
        startButton = (ImageView) welcomeView.findViewById(R.id.startButton);
        startButton.setOnClickListener(new MyClickListener());


		ScaleAnimation scaleAni = new ScaleAnimation(1.0f, 1.02f, 1.0f, 1.02f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scaleAni.setRepeatMode(Animation.REVERSE);
		scaleAni.setRepeatCount(-1);
		scaleAni.setDuration(1000);
		startButton.startAnimation(scaleAni);

		btnStart = (Button) welcomeView.findViewById(R.id.btnStart);
		btnStart.setOnClickListener(new MyClickListener());

		weixinButton = (Button) helpView.findViewById(R.id.Weixin);
        weixinButton.setOnClickListener(new MyClickListener());
		appmakerbButton = (Button) helpView.findViewById(R.id.appMaker);
        appmakerbButton.setOnClickListener(new MyClickListener());

        clickmeButton = (Button) moreView.findViewById(R.id.clickme);
        clickmeButton.setOnClickListener(new MyClickListener());
        circlemeButton = (Button) moreView.findViewById(R.id.circleme);
        circlemeButton.setOnClickListener(new MyClickListener());
        questionButton = (Button) moreView.findViewById(R.id.jumpQuestion);
        questionButton.setOnClickListener(new MyClickListener());
        
        viewPager.setAdapter(new MyPageAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        viewPager.setCurrentItem(1);
        
		TextView ruleText = (TextView) helpView.findViewById(R.id.ruleText);
		TextView winText = (TextView) helpView.findViewById(R.id.winText);
		ruleText.setText("游戏规则:\n1、选择参与人数与卧底人数开始游戏\n2、每人需记得自己的词语和编号"
				+ "\n3、依次描述自己的词语\n4、每轮描述结束后，投票选出卧底\n5、剩余玩家继续描述");
		winText.setText("胜利条件：\n1、卧底全部被指认出，平民胜利\n2、卧底人数大于等于平民数目时，卧底胜利");

	}
	
	private class MyClickListener implements android.view.View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent mIntent = new Intent();
			switch (v.getId()) {
			// case R.id.helpButton:
			// mIntent.setClass(SelectGameActivity.this,
			// UnderCoverContent.class);
			// break;
			case R.id.startButton:
				SoundPlayer.playball();
			case R.id.btnStart:
				mIntent.setClass(SelectGameActivity.this, Setting.class);
				break;
			case R.id.Weixin:
				uMengClick("click_weixin");
				mIntent.setClass(SelectGameActivity.this, weixin.class);
				break;
			case R.id.appMaker:
				mIntent.setClass(SelectGameActivity.this, MakeActivity.class);
				uMengClick("click_about");
				break;
			case R.id.clickme:
				mIntent.setClass(SelectGameActivity.this, random_50.class);
				uMengClick("game_click");
				break;
			case R.id.circleme:
				mIntent.setClass(SelectGameActivity.this,
						RotaryBottleActivity.class);
				uMengClick("game_bottle");
				break;
			case R.id.jumpQuestion:
				mIntent.setClass(SelectGameActivity.this, QuestionAnswer.class);
				uMengClick("game_ask");
				break;
			default:
				break;
			}
			
			startActivity(mIntent);
		}
		
	}
	
	// 滑动页面的的适配器啊，主要把ViewList中的View传给ViewPager
    private class MyPageAdapter extends PagerAdapter {  
  	  
        @Override  
        public int getCount() {  
            return viewList.size();  
        }  
  
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {  
            return arg0 == arg1;  
        }  
  
        @Override  
        public int getItemPosition(Object object) {  
            // TODO Auto-generated method stub  
            return super.getItemPosition(object);  
        }  
  
        @Override  
        public void destroyItem(View view, int position, Object object) {  
            // TODO Auto-generated method stub  
            ((ViewPager) view).removeView(viewList.get(position));  
        }  
  
        @Override  
        public Object instantiateItem(View view, int position) {  
            // TODO Auto-generated method stub  
            ((ViewPager) view).addView(viewList.get(position));  
            return viewList.get(position);  
        }  
  
        @Override  
        public void restoreState(Parcelable arg0, ClassLoader arg1) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public Parcelable saveState() {  
            // TODO Auto-generated method stub  
            return null;  
        }  
  
        @Override  
        public void startUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void finishUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
    } 
    
    
	// 监听页面切换，切换时把小圆点变更
    class MyPageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			for (int i = 0; i < imageViews.length; i++) {
				if (i!=position) {
					imageViews[i].setBackgroundResource(R.drawable.page_indicator);
				}
				imageViews[position].setBackgroundResource(R.drawable.page_indicator_focused);
			}
		}
    	
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_game, menu);
		return true;
	}

}
