
#include <jni.h>
#include <fcntl.h>

int fd = 0;
int fd3 = 0;
int fd2 = 0;
int fd4 = 0;

JNIEXPORT jint JNICALL Java_com_example_jnidriver_JNIDriver_openDriver
(JNIEnv * env, jclass class, jstring path){
	jboolean iscopy;
	const char *path_utf = (*env)->GetStringUTFChars(env, path, &iscopy);
	fd = open(path_utf, O_WRONLY);
	(*env)->ReleaseStringUTFChars(env,path,path_utf);

	if(fd<0) return -1;
	else return 1;
}

JNIEXPORT void JNICALL Java_com_example_jnidriver_JNIDriver_closeDriver
(JNIEnv * env, jobject obj){
	if(fd>0) close(fd);
}
JNIEXPORT jint JNICALL Java_com_example_jnidriver_JNIDriver_openDriver2
  (JNIEnv * env, jclass class, jstring path){
	jboolean iscopy;
	const char *path_utf = (*env)->GetStringUTFChars(env, path, &iscopy);
	fd2 = open(path_utf, O_WRONLY);
	(*env)->ReleaseStringUTFChars(env, path, path_utf );

	if(fd2<0) return -1;
	else return 1;
}
JNIEXPORT void JNICALL Java_com_example_jnidriver_JNIDriver_closeDriver2
  (JNIEnv * env, jobject obj){
	if(fd2>0) close(fd2);
}
JNIEXPORT jint JNICALL Java_com_example_jnidriver_JNIDriver_openDriver3
(JNIEnv * env, jclass class, jstring path){
	jboolean iscopy;
	const char *path_utf = (*env)->GetStringUTFChars(env, path, &iscopy);
	fd3 = open(path_utf, O_RDONLY);
	(*env)->ReleaseStringUTFChars(env,path,path_utf);

	if(fd3<0) return -1;
	else return 1;
}

JNIEXPORT void JNICALL Java_com_example_jnidriver_JNIDriver_closeDriver3
(JNIEnv * env, jobject obj){
	if(fd3>0) close(fd3);
}
JNIEXPORT jint JNICALL Java_com_example_jnidriver_JNIDriver_openDriver4
  (JNIEnv * env, jclass class, jstring path){
	jboolean iscopy;
	const char *path_utf = (*env)->GetStringUTFChars(env, path, &iscopy);
	fd4 = open(path_utf, O_WRONLY);
	(*env)->ReleaseStringUTFChars(env, path, path_utf );

	if(fd4<0) return -1;
	else return 1;
}

JNIEXPORT void JNICALL Java_com_example_jnidriver_JNIDriver_closeDriver4
  (JNIEnv * env, jobject obj){
	if(fd4>0) close(fd4);
}
JNIEXPORT void JNICALL Java_com_example_jnidriver_JNIDriver_writeDriver(JNIEnv * env, jobject obj, jbyteArray arr,
jint count){
	jbyte* chars = (*env)->GetByteArrayElements(env, arr, 0);
	if(fd4 > 0) write(fd4, (unsigned char*)chars, count);
	(*env)->ReleaseByteArrayElements(env, arr, chars, 0);
}
JNIEXPORT void JNICALL Java_com_example_jnidriver_JNIDriver_fwriteDriver(JNIEnv * env, jobject obj, jbyteArray arr,
jint count){
	jbyte* chars = (*env)->GetByteArrayElements(env, arr, 0);
	if(fd2 > 0) write(fd2, (unsigned char*)chars, count);
	(*env)->ReleaseByteArrayElements(env, arr, chars, 0);
}
JNIEXPORT jfloat JNICALL Java_com_example_jnidriver_JNIDriver_readTemp
  (JNIEnv * env, jobject obj){
	int iResult=0;
	float t=0, t_C=0;

	if(fd3>0) {
		iResult = ioctl(fd3, 0 , NULL);
		t = (float)iResult;
		t_C = -46.85+175.72/65536*t;
		return t_C;
	}
	return 0;
}

JNIEXPORT jfloat JNICALL Java_com_example_jnidriver_JNIDriver_readHumi
  (JNIEnv * env, jobject obj){
	int iResult=0;
		float t=0, t_C=0;

		if(fd3>0) {
			iResult = ioctl(fd3, 1 , NULL);
			t = (float)iResult;
			t_C = -6.0+125.0/65536*t;
			return t_C;
		}
		return 0;
}

JNIEXPORT void JNICALL Java_com_example_jnidriver_JNIDriver_setMotor
  (JNIEnv * env, jobject o, jchar c){
	int i = (int)c;

	char temp[4] = {1,1,1,1};

	if(i==0)
		write(fd,temp,4);
	else if(i==1)
	{
		temp[0] = 0;
		write(fd,temp,4);
		usleep(15000);

		temp[0] = 1;
		temp[1] = 0;
		write(fd,temp,4);
		usleep(15000);

		temp[1] = 1;
		temp[2] = 0;
		write(fd,temp,4);
		usleep(15000);

		temp[2] = 1;
		temp[3] = 0;
		write(fd,temp,4);
		usleep(15000);
		temp[3] = 1;

	}
	else if(i ==2 ) {
		temp[3] = 0;
		write(fd,temp,4);
		usleep(15000);

		temp[3] = 1;
		temp[2] = 0;
		write(fd,temp,4);
		usleep(15000);

		temp[2] = 1;
		temp[1] = 0;
		write(fd,temp,4);
		usleep(15000);

		temp[1] = 1;
		temp[0] = 0;
		write(fd,temp,4);
		usleep(15000);
		temp[0] = 1;
	}
	else if(i ==3 ) {
			temp[0] = 0;
			temp[1] = 0;
			write(fd,temp,4);
			usleep(4500);

			temp[0] = 1;
			temp[2] = 0;
			write(fd,temp,4);
			usleep(4500);

			temp[1] = 1;
			temp[3] = 0;
			write(fd,temp,4);
			usleep(4500);

			temp[2] = 1;
			temp[0] = 0;
			write(fd,temp,4);
			usleep(4500);
			temp[3] = 1;
		}
	else if(i ==4 ) {
			temp[0] = 0;
			write(fd,temp,4);
			usleep(10000);

			temp[1] = 0;
			write(fd,temp,4);
			usleep(10000);

			temp[0] = 1;
			write(fd,temp,4);
			usleep(10000);

			temp[2] = 0;
			write(fd,temp,4);
			usleep(10000);

			temp[1] = 1;
			write(fd,temp,4);
			usleep(10000);

			temp[3] = 0;
			write(fd,temp,4);
			usleep(10000);

			temp[2] = 1;
			write(fd,temp,4);
			usleep(10000);

			temp[0] = 0;
			write(fd,temp,4);
			usleep(10000);
			temp[3] = 1;
		}

}

