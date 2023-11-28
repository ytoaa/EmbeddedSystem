package com.example.sm9m2ssteps;

import java.io.FileReader; // 파일 입출력 제어
import java.io.BufferedReader; // 버퍼 입출력 제어
import java.io.FileNotFoundException; // 파일 미발견 에러 제어
import java.io.IOException; // 파일 입출력 예외 처리
import java.io.RandomAccessFile;

import com.example.jnidriver.JNIDriver;
//import com.example.sm9m2temphumis.MainActivity;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends Activity {
	byte[] data = {0, 0, 0, 0, 0, 0, 0, 0};
	TextView tv;
	int mCurCount;
	int istempstop = 0;
	ReceiveThread mSegThread;
	boolean mThreadRun = true;
	JNIDriver mDriver = new JNIDriver();
	int direction = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn1 = (Button) findViewById(R.id.button1);
		Button btn2 = (Button) findViewById(R.id.button2);
		Button btn3 = (Button) findViewById(R.id.button3);
		Button btn4 = (Button) findViewById(R.id.button4);
		Button btn5 = (Button) findViewById(R.id.button5);
		btn1.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				direction = 1;
				istempstop = 1;
				data[0] = 0;
				data[1] = 0;
				data[2] = 0;
				data[3] = 1;
				mCurCount = 3;
				mDriver.write(data);
				if (mSegThread == null) {
					
					mSegThread = new ReceiveThread();
					mSegThread.start();
					
				}
			}
		});
		btn2.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				direction = 3;
				istempstop = 1;
				data[0] = 0;
				data[1] = 0;
				data[2] = 1;
				data[3] = 0;
				mCurCount = 4;
				mDriver.write(data);
	
				if (mSegThread == null) {
					
					mSegThread = new ReceiveThread();
					mSegThread.start();
					
				}
			}
		});
		btn3.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				direction = 4;
				istempstop = 1;
				data[0] = 0;
				data[1] = 1;
				data[2] = 0;
				data[3] = 0;
				mCurCount = 2;
				mDriver.write(data);

				if (mSegThread == null) {
					
					mSegThread = new ReceiveThread();
					mSegThread.start();
					
				}
			}
		});
		btn4.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				direction = 5;
				istempstop = 1;
				data[0] = 0;
				data[1] = 1;
				data[2] = 0;
				data[3] = 0;
				mCurCount = 1;
				mDriver.write(data);
				if (mSegThread == null) {
					
					mSegThread = new ReceiveThread();
					mSegThread.start();
					
				}
			}
		});
		btn5.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				direction = 0;
				istempstop = 0;
				data[0] = 0;
				data[1] = 0;
				data[2] = 0;
				data[3] = 0;
				mCurCount = 0;
				mDriver.write(data);

				if (mSegThread != null) {
					mSegThread = null;
				}
				
				mDriver.setMotor(0);
			}
		});
	}
	
	private class ReceiveThread extends Thread {
		@Override
		public void run() {
			super.run();

			while (mThreadRun) {
				if (istempstop == 1) {
					Message text = Message.obtain();
					handler.sendMessage(text);
				}
				byte[] n = {0,0,0,0,0,0,0};
				n[0] = (byte) (mCurCount / 100000);
				n[1] = (byte) (mCurCount % 100000/10000);
				n[2] = (byte) (mCurCount % 10000/1000);
				n[3] = (byte) (mCurCount % 1000/100);
				n[4] = (byte) (mCurCount % 100/10);
				n[5] = (byte) (mCurCount % 10);
				mDriver.fwrite(n);
				float temp = mDriver.getTemp();
				if (temp >= 25 ) {
					mDriver.setMotor(3);
                }
				else if (direction == 5 ) {
					long startTime = System.currentTimeMillis();
				    long duration = 5000; // 5초

				    // mDriver.setMotor(1) 실행
				    mDriver.setMotor(1);

				    // 5초 동안 대기
				    while (System.currentTimeMillis() - startTime < duration) {
				        // 대기
				    }

				    // mDriver.setMotor(2) 실행
				    mDriver.setMotor(2);
				}
				else 
					mDriver.setMotor(direction);
				
				
			}
		}
	}
	public Handler handler = new Handler() {
		public void handleMessage (Message msg) {
			TextView tv;
			tv = (TextView) findViewById(R.id.textView1);
			tv.setText("Temp:" +  mDriver.getTemp());
		}
	};
	@Override
	protected void onPause() {
		mDriver.close();
		mDriver.close2();
		mDriver.close4();
		mDriver.close3();
		super.onPause();
	}

	@Override
	protected void onResume() {
		if (mDriver.open("/dev/sm9s5422_step") < 0 ) {
			Toast.makeText(MainActivity.this,  " Driver Open Failed", Toast.LENGTH_SHORT).show();
		}
		if (mDriver.open4("/dev/sm9s5422_led") < 0 ) {
			Toast.makeText(MainActivity.this,  "Led Driver Open Failed", Toast.LENGTH_SHORT).show();
		}
		if (mDriver.open2("/dev/sm9s5422_segment") < 0 ) {
			Toast.makeText(MainActivity.this,  "Led Driver Open Failed", Toast.LENGTH_SHORT).show();
		}
		if (mDriver.open3("/dev/sm9s5422_sht20") < 0 ) {
			Toast.makeText(MainActivity.this,  "temp Driver Open Failed", Toast.LENGTH_SHORT).show();
		}
		super.onResume();
	}
	
}

