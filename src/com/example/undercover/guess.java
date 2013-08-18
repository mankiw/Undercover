package com.example.undercover;

import java.util.Random;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class guess extends BaseActivity {
	private TableLayout contentTable;

	// 卧底人数
	private int soncount;
	// 平民人数
	private int fathercount;
	// 卧底的词语
	private String son;
	// 0-n人数的词语数组
	private String[] content;
	private TextView txtTitle;
	private Button punishBtn;
	private Button startBtn;
	private int totalcount;
	private boolean isOver;
	private boolean flag;
	private boolean isGetRestart;
	private boolean isShow;
	private int temindex;
	private Random random = new Random();
	private TextView txtLong;
	private String[] overString = { "没找到全部卧底，游戏继续", "卧底隐藏很深噢！", "快去把卧底给投出来吧~",
			"平民可要小心噢!", "卧底这是要逆天了吗？", "投错一个就少一个战友~", "卧底卧底，你在哪里！", "卧底，你是想怎样~" };

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guess);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		isOver = false;
		flag = false;
		isGetRestart = false;
		contentTable = (TableLayout) findViewById(R.id.tableContent);
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		punishBtn = new Button(this);
		startBtn = new Button(this);
		Bundle bundle = this.getIntent().getExtras();
		isShow	= bundle.getBoolean("isShow");
		son = bundle.getString("son");
		soncount = bundle.getInt("sonCount");
		content = bundle.getStringArray("content");
		fathercount = content.length - soncount;
		totalcount = content.length;
		txtTitle.setText("快去猜猜谁是卧底吧~");
		temindex = 0;
		for (int i = 0; i < Math.ceil((float) content.length / 4); i++) {
			TableRow newrow = new TableRow(this);
			for (int m = 0; m < 4; m++) {
				temindex++;
				if (temindex > content.length) {
					break;
				}
				FrameLayout fl = new FrameLayout(this);
				ImageView select = new ImageView(this);
				final TextView text	= new TextView(this);
				text.setText("" + temindex);
				text.setGravity(Gravity.CENTER);
				text.setTextSize(30);
				select.setTag(temindex);
				select.setBackgroundResource(R.drawable.popo72);
				select.setOnLongClickListener(new Button.OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						tapIndex((Integer) v.getTag());
						v.setClickable(false);
						ImageView tt = (ImageView) v;
						tt.setBackgroundResource(R.drawable.popogray72);
						SoundPlayer.playball();
						if(isShow){
							text.setTextSize(20);
							if (content[(Integer) v.getTag() - 1].equals(son)) {
								text.setText("卧底");
								text.setTextColor(getResources().getColor(R.color.RED));
							} else {
								if (content[(Integer) v.getTag() - 1]
										.equals("空白")) {
									text.setText("空白");
									text.setTextColor(getResources().getColor(R.color.BLUE));
								}else{
									text.setText("冤死");
								}
							}
						}
						// tt.setText("*");
						return true;
					}
				});
				fl.addView(select);
				fl.addView(text);
				newrow.addView(fl, disWidth / 4, disWidth / 4);
			}
			contentTable.addView(newrow);
		}
		txtLong = new TextView(this);
		txtLong.setText("长按选择");
		txtLong.setTag(100099);
		contentTable.addView(txtLong);
	}

	protected void setAllButton(boolean useable) {
		for (int i = 1; i <= content.length; i++) {
			ImageView tem = (ImageView) contentTable.findViewWithTag(i);
			tem.setClickable(useable);
		}
	}
	protected void tapIndex(int tag) {
		txtLong.setVisibility(View.INVISIBLE);
		if (soncount + fathercount == totalcount) {
			uMengClick("click_guess_first");
		}
		if (content[tag - 1].equals(son)) {
			soncount--;
		} else {
			fathercount--;
		}
		if (!isOver) {
			if (soncount <= 0) {
				Log("任务完成");
				txtTitle.setText("完成任务，卧底为【" + son + "】");
				isOver = true;
				uMengClick("click_guess_last");
				SoundPlayer.playclaps();
				refash();
				setAllButton(false);
			} else if (fathercount <= soncount) {
				Log("卧底胜利");
				txtTitle.setText("卧底胜利，卧底为【" + son + "】");
				isOver = true;
				uMengClick("click_guess_last");
				refash();
				setAllButton(false);
			} else {
				int stringcount = overString.length;
				int stringindex = Math.abs(random.nextInt()) % stringcount;
				txtTitle.setText(overString[stringindex]);
				Log("还有" + soncount + "个");
			}
		}
	}

	protected void Log(String string) {
		Log.v("tag", string);
	}

	private void refash() {
		//Button punishBtn = new Button(this);
		punishBtn.setText("开始惩罚");
		punishBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				 Intent goMain = new Intent();
				 goMain.setClass(guess.this, PunishActivity.class);
				 startActivity(goMain);
				 finish();
			}
		});
		contentTable.addView(punishBtn);
		startBtn.setText("重新开始");
		startBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent goMain = new Intent();
				goMain.setClass(guess.this, Setting.class);
				startActivity(goMain);
				finish();
			}
		});
		contentTable.addView(startBtn);
	}

	/**
	 * 获取惩罚内容数组
	 * 
	 * @return
	 */
//	private void getPunish() {
//		if (!flag) {
//			flag 			= true;
//			int arr[]		= MathUtil.getInstance().check(73,6);
//			TextView text 	= null;
//			String temp 	= null;
//			for (int i = 0; i < 6; i++) {
//				text 		= new TextView(this);
//				temp 		= PunishProps.getPunish(arr[i]);
//				if (null == temp) {
	// temp = "请执行第一条";
//				}
	// text.setText((i + 1) + "、" + temp);
//				contentTable.addView(text);
//			}
//		}
//	}
	
	private void getRestartButton(){
		Button restartBtn = new Button(this);
		restartBtn.setText("重新开始");
		restartBtn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent goMain = new Intent();
				uMengClick("game_undercover_resert");
				goMain.setClass(guess.this, Setting.class);
				startActivity(goMain);
				finish();
			}
		});
		contentTable.addView(restartBtn);
	}
//	private void frozenBtn()
//	{
//		for(int i = 1 ;i < temindex+1 ; i++)
//		{
//			Button btn = (Button)contentTable.findViewWithTag(i);
//			if(btn == null)
//			{
//			    continue ;	
//			}
//			
//			btn.setClickable(false);
//		}
//	}

}
