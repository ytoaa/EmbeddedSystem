
#include <jni.h>
#include <fcntl.h>

int fd = 0;
int fd2 = 0;
int fd3 = 0;
int fd4 = 0;

JNIEXPORT jint JNICALL Java_com_exm_jnidriver_JNIDriver_openDriver
  (JNIEnv * env, jclass class, jstring path){
	jboolean iscopy;
	const char *path_utf = (*env)->GetStringUTFChars(env, path, &iscopy);
	fd = open(path_utf, O_WRONLY);
	(*env)->ReleaseStringUTFChars(env,path,path_utf);

	if(fd<0) return -1;
	else return 1;
}

JNIEXPORT void JNICALL Java_com_exm_jnidriver_JNIDriver_closeDriver
  (JNIEnv * env, jobject obj){
	if(fd>0) close(fd);
}

JNIEXPORT jint JNICALL Java_com_exm_jnidriver_JNIDriver_openDriver2
  (JNIEnv * env, jclass class, jstring path){
	jboolean iscopy;
	const char *path_utf = (*env)->GetStringUTFChars(env, path, &iscopy);
	fd2 = open(path_utf, O_WRONLY);
	(*env)->ReleaseStringUTFChars(env,path,path_utf);

	if(fd2<0) return -1;
	else return 1;
}

JNIEXPORT void JNICALL Java_com_exm_jnidriver_JNIDriver_closeDriver2
  (JNIEnv * env, jobject obj){
	if(fd2>0) close(fd2);
}
JNIEXPORT jint JNICALL Java_com_exm_jnidriver_JNIDriver_openDriver3
  (JNIEnv * env, jclass class, jstring path){
	jboolean iscopy;
	const char *path_utf = (*env)->GetStringUTFChars(env, path, &iscopy);
	fd3 = open(path_utf, O_WRONLY);
	(*env)->ReleaseStringUTFChars(env,path,path_utf);

	if(fd3<0) return -1;
	else return 1;
}

JNIEXPORT void JNICALL Java_com_exm_jnidriver_JNIDriver_closeDriver3
  (JNIEnv * env, jobject obj){
	if(fd3>0) close(fd3);
}
JNIEXPORT jint JNICALL Java_com_exm_jnidriver_JNIDriver_openDriver4
  (JNIEnv * env, jclass class, jstring path){
	jboolean iscopy;
	const char *path_utf = (*env)->GetStringUTFChars(env, path, &iscopy);
	fd4 = open(path_utf, O_RDONLY);
	(*env)->ReleaseStringUTFChars(env, path, path_utf );

	if(fd4<0) return -1;
	else return 1;
}

JNIEXPORT void JNICALL Java_com_exm_jnidriver_JNIDriver_closeDriver4
  (JNIEnv * env, jobject obj){
	if(fd4>0) close(fd4);
}
JNIEXPORT jfloat JNICALL Java_com_exm_jnidriver_JNIDriver_readTemp
  (JNIEnv * env, jobject obj){
	int iResult=0;
	float t=0, t_C=0;

	if(fd>0) {
		iResult = ioctl(fd, 0 , NULL);
		t = (float)iResult;
		t_C = -46.85+175.72/65536*t;
		return t_C;
	}
	return 0;
}

JNIEXPORT jfloat JNICALL Java_com_exm_jnidriver_JNIDriver_readHumi
  (JNIEnv * env, jobject obj){
	int iResult=0;
		float t=0, t_C=0;

		if(fd>0) {
			iResult = ioctl(fd, 1 , NULL);
			t = (float)iResult;
			t_C = -6.0+125.0/65536*t;
			return t_C;
		}
		return 0;
}

JNIEXPORT jint JNICALL Java_com_exm_jnidriver_JNIDriver_setBuzzer(JNIEnv * env, jobject obj, jchar c){
	int G = (int) c;
	write(fd2,&G, sizeof(G));
}
JNIEXPORT void JNICALL Java_com_exm_jnidriver_JNIDriver_writeDriver(JNIEnv * env, jobject obj, jbyteArray arr,
jint count){
	jbyte* chars = (*env)->GetByteArrayElements(env, arr, 0);
	if(fd3 > 0) write(fd3, (unsigned char*)chars, count);
	(*env)->ReleaseByteArrayElements(env, arr, chars, 0);
}
JNIEXPORT jint JNICALL Java_com_exm_jnidriver_JNIDriver_getInterrupt
  (JNIEnv * env, jobject obj){
	int ret = 0;
		char value[100];

		char* ch1 = "Up";
		char* ch2 = "Down";
		char* ch3 = "Left";
		char* ch4 = "Right";
		char* ch5 = "Center";
		ret = read(fd4, &value, 100);

		if(ret<0)
			return -1;
		else {
			if(strcmp(ch1,value) == 0)
				return 1;
			else if (strcmp(ch2,value) == 0)
				return 2;
			else if (strcmp(ch3,value) == 0)
				return 3;
			else if (strcmp(ch4,value) == 0)
				return 4;
			else if (strcmp(ch5,value) == 0)
				return 5;
		}
}

#undef com_exm_jnidriver_JNIDriver_TranseThread_MIN_PRIORITY
#define com_exm_jnidriver_JNIDriver_TranseThread_MIN_PRIORITY 1L
#undef com_exm_jnidriver_JNIDriver_TranseThread_NORM_PRIORITY
#define com_exm_jnidriver_JNIDriver_TranseThread_NORM_PRIORITY 5L
#undef com_exm_jnidriver_JNIDriver_TranseThread_MAX_PRIORITY
#define com_exm_jnidriver_JNIDriver_TranseThread_MAX_PRIORITY 10L
