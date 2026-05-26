#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_rohan_moonlightmodelstudent_SplashScreen_getApikey(JNIEnv *env, jobject instance) {

return (*env)-> NewStringUTF(env, "TmF0aXZlNWVjcmV0UEBzc3cwcmQx");
}

JNIEXPORT jstring JNICALL
Java_com_rohan_moonlightmodelstudent_SplashScreen_getApiurl(JNIEnv *env, jobject instance) {
    return (*env)-> NewStringUTF(env, "https://dummyjson.com/products/1");
}

JNIEXPORT jstring JNICALL
Java_com_rohan_moonlightmodelstudent_api_RetrofitInstance_getApiurl(JNIEnv *env, jobject instance) {
    return (*env)-> NewStringUTF(env, "http://192.168.100.231/project/moonlight_api/");
}

JNIEXPORT jstring JNICALL
Java_com_rohan_moonlightmodelstudent_api_LoginInstance_getApiurl(JNIEnv *env, jobject instance) {
    return (*env)-> NewStringUTF(env, "http://192.168.100.231/project/moonlight_api/");
}

JNIEXPORT jstring JNICALL
Java_com_rohan_moonlightmodelstudent_api_NetworkConnectionInterceptor_getApiname(JNIEnv *env, jobject instance) {
    return (*env)-> NewStringUTF(env, "moonlightmodel.com.np");
}

JNIEXPORT jstring JNICALL
Java_com_rohan_moonlightmodelstudent_api_ResponseInterceptor_getApiname(JNIEnv *env, jobject instance) {
    return (*env)-> NewStringUTF(env, "moonlightmodel.com.np");
}

JNIEXPORT jstring JNICALL
Java_com_rohan_moonlightmodelstudent_Scanner_getsecretkey(JNIEnv *env, jobject instance) {
    return (*env)-> NewStringUTF(env, "2tse6kfjhdgs9yfhdjsdgwehbcdfjjjsdfdn8gsd");
}

JNIEXPORT jstring JNICALL
Java_com_rohan_moonlightmodelstudent_Scanner_getsecretiv(JNIEnv *env, jobject instance) {
    return (*env)-> NewStringUTF(env, "347ujfbvnl0d3yhj");
}