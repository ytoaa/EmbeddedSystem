package com.example.jnidriver;

public class JNIDriver  {
	
	private boolean mConnectFlag;

	
	
	static {
		System.loadLibrary("JNIDriver");
	}
	
	private native static int openDriver(String path);
	private native static void closeDriver();

	private native static void writeDriver(byte[] data, int length);

	
	public JNIDriver() {
		mConnectFlag = false;

	}

	public int open(String driver) {
		if(mConnectFlag) return -1;
		
		if(openDriver(driver)>0){
			mConnectFlag = true;
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
	protected void finalize() throws Throwable{
		close();
		
		super.finalize();
		
	}
	public void write(byte[] data){
		if(!mConnectFlag) return; //레지스터 파일이 이미 닫혀있으면 종료
		writeDriver(data, data.length); //레지스터에 LED 작동을 위한 배열 값
	}
}
