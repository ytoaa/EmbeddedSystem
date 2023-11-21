package com.example.sm9m2temphumis;

import com.exm.jnidriver.JNIDriver;
import com.exm.jnidriver.JNIListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements JNIListener  {
	//Message text2 = Message.obtain();
	int tempv = 0;
	TextView tv2;
	String str = "";
	private boolean isSoundPlaying = false;
	int value= (byte) 0x08; //소리 설정
	JNIDriver mDriver = new JNIDriver();
	ReceiveThread mSegThread;
	boolean mThreadRun = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDriver.setListener(this);
		if (mDriver.open4("/dev/sm9s5422_interrupt") < 0 ) {
			Toast.makeText(MainActivity.this,  "switch Driver Open Failed", Toast.LENGTH_SHORT).show();
		}
		tv2 = (TextView)findViewById(R.id.textView3);
		Button btn = (Button) findViewById(R.id.button1);
		Button stop = (Button) findViewById(R.id.button2);
		btn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				mSegThread = new ReceiveThread();
				mSegThread.start();
				isSoundPlaying = true; // 소리 중지됨을 표시
			}
			
		});
		stop.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				 // 소리 중지 코드 추가 (예: mDriver.stopSound())
                isSoundPlaying = false; // 소리 중지됨을 표시
			}
			
		});
	}
	private class ReceiveThread extends Thread {
		@Override
		public void run() {
			super.run();
			while (mThreadRun) {
				Message text = Message.obtain();
					
				handler.sendMessage(text);
				float temp = mDriver.getTemp();
				float humi = mDriver.getHumi();
				if (temp >= 30 || humi >= 80) {
                    if (isSoundPlaying) {
                        // Add code here to continue sound or start sound
                        mDriver.setBuzzer(value); // Replace someValue with the appropriate value for sound
                    } else {
                        // Add code here to stop sound (mDriver.stopSound() 또는 해당하는 중지 메서드 사용)
                    }
                }
				try {
					Thread.sleep(1000);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tempv = 1;
				if tempv == 1;
					Toast.makeText(MainActivity.this,  "up", Toast.LENGTH_SHORT).show();
				/*
				switch(tempv){
				case 1:
					Toast.makeText(MainActivity.this,  "up", Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(MainActivity.this,  "down", Toast.LENGTH_SHORT).show();
					break;
				case 3:
					Toast.makeText(MainActivity.this,  "Left", Toast.LENGTH_SHORT).show();
					break;
				case 4:
					Toast.makeText(MainActivity.this,  "right", Toast.LENGTH_SHORT).show();
					break;
				case 5:
					Toast.makeText(MainActivity.this,  "center", Toast.LENGTH_SHORT).show();
					break;
				}*/
			}
		}
	}
	
	public Handler handler = new Handler() {
		public void handleMessage (Message msg) {
			TextView tv;
			tv = (TextView) findViewById(R.id.textView1);
			tv.setText("Temp:" +  mDriver.getTemp());
			tv = (TextView) findViewById(R.id.textView2);
			tv.setText("Humi:" +  mDriver.getHumi());
		}
	};
	/*
	public Handler handler2 = new Handler() {
		public void handleMessage(Message msg) {
			switch(msg.arg1){
			case 1:
				tv2.setText("up");
				break;
			case 2:
				tv2.setText("Down");
				break;
			case 3:
				tv2.setText("Left");
				break;
			case 4:
				tv2.setText("Right");
				break;
			case 5:
				tv2.setText("Center");
				break;
			}
		}
	};
	*/
	
	@Override
	public void onReceive(int val) {
		//Message text2 = Message.obtain();
		//text2.arg1=val;
		tempv = val;
		//handler2.sendMessage(text2);
	}
	@Override
	protected void onPause() {
		mDriver.close();
		mDriver.close2();
		mDriver.close3();
		mDriver.close4();
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		if (mDriver.open("/dev/sm9s5422_sht20") < 0 ) {
			Toast.makeText(MainActivity.this,  "temp Driver Open Failed", Toast.LENGTH_SHORT).show();
		}
		if (mDriver.open2("/dev/sm9s5422_piezo") < 0 ) {
			Toast.makeText(MainActivity.this,  "piezo Driver Open Failed", Toast.LENGTH_SHORT).show();
		}
		if (mDriver.open3("/dev/sm9s5422_led") < 0 ) {
			Toast.makeText(MainActivity.this,  "Led Driver Open Failed", Toast.LENGTH_SHORT).show();
		}
		
		super.onResume();
	}
}
