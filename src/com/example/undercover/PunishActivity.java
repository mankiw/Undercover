package com.example.undercover;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.util.MathUtil;
import com.example.util.PunishProps;

public class PunishActivity extends BaseActivity {
	
	private Button trueBtn;
	private Button advenBtn;
	private Button changeBtn;
	private Button randomBtn;
	private Button punish_disc;
	private TextView punish_guize;
	private TextView punish_1;
	private TextView punish_2;
	private TextView punish_3;
	private TextView punish_4;
	private TextView punish_5;
	private TextView punish_6;
	private boolean flag;
	// 是否开始 随机数字的计算；
	private boolean isRandom;
	private long number;
	// 是否 点击过 随机按钮；
	private boolean isTouch;
	private TextView[] punish;
	private String num;
	private String PunishInTurn;
	private ImageView imagedice;
	private boolean discStart = false;
	private Random random = new Random();
	// 是否刚才按了摇骰子
	private int isShackOneMinit = 0;
	// 如果一直在摇，不断加时间
	private boolean isshacked = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.punish);
		initBtnBack(R.id.btnback);
		initShare(R.id.btnshare);
		// 注册摇动事件
		showShack = true;
		PunishInTurn = getResources().getString(R.string.PunishInTurn);
		num	= "7";
		isRandom	= false;
		isTouch	= false;
		flag	= false;
		trueBtn		= (Button)findViewById(R.id.trueBtn);
		advenBtn	= (Button)findViewById(R.id.advenBtn);
		changeBtn	= (Button)findViewById(R.id.changeBtn);
		randomBtn	= (Button)findViewById(R.id.punish_random);
		punish_disc = (Button) findViewById(R.id.punish_disc);
		punish_guize = (TextView) findViewById(R.id.punish_guize);
		punish_1	= (TextView)findViewById(R.id.punish_1);
		punish_2	= (TextView)findViewById(R.id.punish_2);
		punish_3	= (TextView)findViewById(R.id.punish_3);
		punish_4	= (TextView)findViewById(R.id.punish_4);
		punish_5	= (TextView)findViewById(R.id.punish_5);
		punish_6	= (TextView)findViewById(R.id.punish_6);
		punish		= new TextView[6];
		punish[0]	= punish_1;
		punish[1]	= punish_2;
		punish[2]	= punish_3;
		punish[3]	= punish_4;
		punish[4]	= punish_5;
		punish[5]	= punish_6;
		changeBtn.setVisibility(View.INVISIBLE);
		randomBtn.setVisibility(View.INVISIBLE);
		punish_disc.setVisibility(View.INVISIBLE);
		// 骰子动画
		imagedice = (ImageView) findViewById(R.id.imagedice);
		imagedice.setVisibility(View.INVISIBLE);
		// 用户选择真心话
		trueBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				SoundPlayer.playball();
				trueBtn.setVisibility(View.INVISIBLE);
				advenBtn.setVisibility(View.INVISIBLE);
				// 暂时，先隐藏 换题 按钮
//				changeBtn.setVisibility(View.VISIBLE);
				randomBtn.setVisibility(View.VISIBLE);
				punish_disc.setVisibility(View.VISIBLE);
				uMengClick("game_zhenxinhua");
				// 获取惩罚
				getTruePunish();
			}
		});
		// 用户选择大冒险
		advenBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				flag	= true;
				SoundPlayer.playball();
				trueBtn.setVisibility(View.INVISIBLE);
				advenBtn.setVisibility(View.INVISIBLE);
				// 暂时，先隐藏 换题 按钮
//				changeBtn.setVisibility(View.VISIBLE);
				randomBtn.setVisibility(View.VISIBLE);
				punish_disc.setVisibility(View.VISIBLE);
				uMengClick("game_damaoxian");
				// 获取惩罚
				getAdvenPunish();
			}
		});

		trueBtn.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// 更改为按下时的背景图片
					v.setBackgroundResource(R.drawable.btnzhenxin2);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// 改为抬起时的图片
					v.setBackgroundResource(R.drawable.btnzhenxin);
				}
				return false;
			}
		});

		advenBtn.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// 更改为按下时的背景图片
					v.setBackgroundResource(R.drawable.btn_maoxin2);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// 改为抬起时的图片
					v.setBackgroundResource(R.drawable.btn_maoxian);
				}
				return false;
			}
		});

		// 用户选择换题目
		changeBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(flag){
					getAdvenPunish();
				}else{
					getTruePunish();
				}
				changeBtn.setBackgroundResource(R.drawable.popogray72);
				changeBtn.setEnabled(false);
			}
		});
		
		
		// 用户点击 随机 按钮
		randomBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				return;
				// initTimer();
				// if (!isRandom) {
				// punish_disc.setClickable(false);
				// // punish_disc.setBackgroundResource(R.drawable.btnbggray);
				// } else {
				// punish_disc.setClickable(true);
				// // punish_disc.setBackgroundResource(R.drawable.btnbg);
				// SoundPlayer.playclaps();
				// }
				// isRandom = !isRandom;
			}
		});

		randomBtn.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// 更改为按下时的背景图片
					v.setBackgroundResource(R.drawable.btn_suiji2);
					randomBtnTouch();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// 改为抬起时的图片
					randomBtnTouch();
					v.setBackgroundResource(R.drawable.btn_suiji);
				}
				return false;
			}
		});

		imagedice.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// discstop();
			}
		});

		punish_disc.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (isRandom || isShackOneMinit > 0)
						return false;
					// 更改为按下时的背景图片
					v.setBackgroundResource(R.drawable.btnshaizi2);
					discstart();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					if (isRandom || isShackOneMinit > 0)
						return false;
					// 改为抬起时的图片
					v.setBackgroundResource(R.drawable.btnshaizi);
					discstop();
				}
				return false;
			}
		});

		punish_disc.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// shackAction();
			}
		});
	}// onCreat 方法结束

	private void randomBtnTouch() {
		initTimer();
		if (!isRandom) {
			punish_disc.setClickable(false);
			// punish_disc.setBackgroundResource(R.drawable.btnbggray);
		} else {
			punish_disc.setClickable(true);
			// punish_disc.setBackgroundResource(R.drawable.btnbg);
			SoundPlayer.playclaps();
		}
		isRandom = !isRandom;
	}
	
	// 初始化时间函数
	private void initTimer() {
		if (!isTouch) {
			Timer timer = new Timer();
			timer.schedule(timetask, 0, 68);
			isTouch = true;
		}
	}

	private void discstart() {
		randomBtn.setClickable(false);
		// randomBtn.setBackgroundResource(R.drawable.btnbggray);
		imagedice.setBackgroundResource(0);
		imagedice.setVisibility(View.VISIBLE);
		imagedice.clearAnimation();
		imagedice.setBackgroundResource(R.anim.dics);
		AnimationSet as=new AnimationSet(true);  
		TranslateAnimation al = new TranslateAnimation(0, 0, 0, 0, 0, -1000, 0,
				0);
		al.setDuration(1000);
		// al.setRepeatMode(Animation.REVERSE);
		// al.setRepeatCount(-1);
		as.addAnimation(al);
		imagedice.startAnimation(as);

		AnimationDrawable animDrawable = (AnimationDrawable) imagedice
				.getBackground();
		animDrawable.stop();
		animDrawable.start();
		discStart = true;
		SoundPlayer.playRolling();
		imagedice.setClickable(true);
	}

	private void discstop() {
		randomBtn.setClickable(true);
		// randomBtn.setBackgroundResource(R.drawable.btnbg);
		imagedice.clearAnimation();
		discStart = false;
		imagedice.setBackgroundResource(randomDisc());
		AnimationSet as = new AnimationSet(true);
		TranslateAnimation al = new TranslateAnimation(0, 0, 0, 0, 0, 0, 0,
				1000);
		al.setDuration(1000);
		al.setStartOffset(500);
		// al.setRepeatMode(Animation.REVERSE);
		// al.setRepeatCount(-1);
		as.addAnimation(al);
		as.setFillAfter(true);
		SoundPlayer.stopRolling();
		SoundPlayer.playclaps();
		imagedice.startAnimation(as);
		imagedice.setClickable(false);
	}
	private void getTruePunish(){
		int[] intArr = MathUtil.getInstance().check(190, 6);
		String[] str	= new String[6];
		for(int i=0;i<6;i++){
			str[i] = PunishProps.getQestionHard(intArr[i]);
		}
		setTextView(str);
	}
	
	private void getAdvenPunish(){
		int[] intArr = MathUtil.getInstance().check(93, 6);
		String[] str	= new String[6];
		for(int i=0;i<6;i++){
			str[i] = PunishProps.getPunish(intArr[i]);
		}
		setTextView(str);
	}
	
	private void setTextView(String[] str){
		punish_guize.setText(PunishInTurn);
		punish_1.setText("1、" + str[0]);
		punish_2.setText("2、" + str[1]);
		punish_3.setText("3、" + str[2]);
		punish_4.setText("4、" + str[3]);
		punish_5.setText("5、" + str[4]);
		punish_6.setText("6、" + str[5]);
	}
	
	// 接受时间
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if(isRandom){
				addTenMMS();
			}
			costMMS();
			super.handleMessage(msg);
		}
	};
	// 传递时间
	private TimerTask timetask = new TimerTask() {
		public void run() {
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}
	};
	
	private void costMMS() {
		if (isShackOneMinit > 0) {
			isShackOneMinit--;
		}
		if (isshacked == true && isShackOneMinit == 0) {
			discstop();
			isshacked = false;
		}
	}
	private void addTenMMS(){
		number = System.currentTimeMillis() % 6 + 1;
		// randomBtn.setText(number + "");
		num = MathUtil.getInstance().getRandomNum();
		// randomBtn.setText(num);
		returnColors(num);
	}
		
	private void returnColors(String num){
		punish_1.setTextColor(getResources().getColor(R.color.BLACK));
		punish_2.setTextColor(getResources().getColor(R.color.BLACK));
		punish_3.setTextColor(getResources().getColor(R.color.BLACK));
		punish_4.setTextColor(getResources().getColor(R.color.BLACK));
		punish_5.setTextColor(getResources().getColor(R.color.BLACK));
		punish_6.setTextColor(getResources().getColor(R.color.BLACK));
		// punish_1.setTextSize(14); //默认字体
//		punish_2.setTextSize(14);
//		punish_3.setTextSize(14);
//		punish_4.setTextSize(14);
//		punish_5.setTextSize(14);
//		punish_6.setTextSize(14);
		punish[Integer.valueOf(num)-1].setTextColor(getResources().getColor(R.color.RED));
//		punish[Integer.valueOf(num)-1].setTextSize(16);
	}

	// 重写摇动事件
	@Override
	public void shackAction() {
		initTimer();
		if (isRandom)
			return;
		if (isshacked == false) {
			discstart();
			isshacked = true;
		}
		isShackOneMinit = 10;
	}
	
	private int randomDisc() {
		String[] colorArr = { "dice1", "dice2", "dice3", "dice4", "dice5",
				"dice6" };
		int discIndex = Math.abs(random.nextInt()) % 6 + 1;
		returnColors("" + discIndex);
		return getResources().getIdentifier(
				"com.example.undercover:drawable/" + colorArr[discIndex - 1],
				null,
				null);
	}

	public void onPause() {
		super.onPause();
		SoundPlayer.stopRolling();
	}
}
