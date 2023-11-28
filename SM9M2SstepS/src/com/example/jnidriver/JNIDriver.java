package com.example.jnidriver;
//import com.exm.jnidriver.JNIListener;

public class JNIDriver {
	
	private boolean mConnectFlag;
	private boolean mConnectFlag2;
	private boolean mConnectFlag3;
	
	private boolean mConnectFlag4;
	/*
	private TranseThread mTranseThread;
	private JNIListener mMainActivity;
	 */
	
	
	static {
		System.loadLibrary("JNIDriver");
	}
	
	private native static int openDriver(String path);
	private native static void closeDriver();
	private native void setMotor(char led);
	private native static int openDriver2(String path);
	private native static void closeDriver2();
	
	private native static int openDriver3(String path);
	private native static void closeDriver3();
	
	private native static int openDriver4(String path);
	private native static void closeDriver4();
	
	private native float readTemp();
	private native float readHumi();
	/*
	private native void setBuzzer(char data);
	*/
	private native static void writeDriver(byte[] data, int length);
	private native static void fwriteDriver(byte[] data, int length);
	/*
	private native static int getInterrupt();
	*/
	
	public JNIDriver() {
		mConnectFlag = false;
		mConnectFlag2 = false;
		mConnectFlag3 = false;
		
		mConnectFlag4 = false;

	}
	/*
	@Override
	public void onReceive(int val) {
		if(mMainActivity != null) {
			mMainActivity.onReceive(val);
	
		}
	}
	public void setListener(JNIListener a) {
		mMainActivity = a;
	}
	*/
	public int open(String driver) {
		if(mConnectFlag) return -1;
		
		if(openDriver(driver)>0){
			mConnectFlag = true;
			return 1;
		} else {
			return -1;
		}
	}
	
	public int open2(String driver) {
		if(mConnectFlag2) return -1;
		
		if(openDriver2(driver)>0){
			mConnectFlag2 = true;
			return 1;
		} else {
			return -1;
		}
	}
	
	public int open3(String driver) {
		if(mConnectFlag3) return -1;
		
		if(openDriver3(driver)>0){
			mConnectFlag3 = true;
			return 1;
		} else {
			return -1;
		}
	}
	
	public int open4(String driver) {
		if(mConnectFlag4) return -1;
		
		if(openDriver4(driver)>0){
			mConnectFlag4 = true;
			return 1;
		} else {
			return -1;
		}
	}
	
	public void close() {
		if(!mConnectFlag) return;
		mConnectFlag = false;
		closeDriver();
	}
	
	public void close2() {
		if(!mConnectFlag2) return;
		mConnectFlag2 = false;
		closeDriver2();
	}
	
	public void close3() {
		if(!mConnectFlag3) return;
		mConnectFlag3 = false;
		closeDriver3();
	}
	
	public void close4() {
		if(!mConnectFlag4) return;
		mConnectFlag4 = false;
		closeDriver4();
	}
	
	protected void finalize() throws Throwable{
		close();
		close2();
		close3();
		
		close4();
		
		super.finalize();
		
	}
	public void setMotor(int val) {
		if(!mConnectFlag4) return;
				
		setMotor((char)val);
	}
	
	public float getTemp() {
		return readTemp();
	}
	public float getHumi() {
		return readHumi();
	}
	/*
	public void setBuzzer(int val){
		if(!mConnectFlag) return; //레지스터 파일이 이미 닫혀있으면 종료
		if(val < 1)
			val = 1; //최저음계 - 도
		if(val > 22)
			val = 22; //최고음계 - 도
		setBuzzer((char)val); //레지스터에 LED 작동을 위한 배열 값
	}
	*/
	public void write(byte[] data){
		if(!mConnectFlag4) return; //레지스터 파일이 이미 닫혀있으면 종료
		writeDriver(data, data.length); //레지스터에 LED 작동을 위한 배열 값
	}
	public void fwrite(byte[] data){
		if(!mConnectFlag2) return; //레지스터 파일이 이미 닫혀있으면 종료
		fwriteDriver(data, data.length); //레지스터에 LED 작동을 위한 배열 값
	}
	/*
	private class TranseThread extends Thread {
		@Override 
		public void run() {
			super.run();
			
			try {
				while(mConnectFlag4) {
					try {
						onReceive(getInterrupt());
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch(Exception e) {
		}
	}
  }
  */
}
