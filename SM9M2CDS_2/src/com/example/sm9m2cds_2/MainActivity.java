package com.example.sm9m2cds_2;

import java.io.FileReader; // ���� ����� ����
import java.io.BufferedReader; // ���� ����� ����
import java.io.FileNotFoundException; // ���� �̹߰� ���� ����
import java.io.IOException; // ���� ����� ���� ó��
import java.io.RandomAccessFile;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	ReceiveThread mSegThread;
	boolean mThreadRun = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				mSegThread = new ReceiveThread();
				mSegThread.start();
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
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			TextView tv;
			FileReader in;
			try {
				in = new FileReader(
						"/sys/devices/12d10000.adc/iio:device0/in_voltage3_raw");
				BufferedReader br = new BufferedReader(in);
				String data = br.readLine();
				tv = (TextView) findViewById(R.id.textView1);
				tv.setText("CDS :" + data);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	};
	
}
