#include <jni.h>
#include <algorithm>  // std::sort

extern "C"
JNIEXPORT void JNICALL
Java_com_pg_mos25_lab5_MainActivity_sortArray(JNIEnv *env, jobject /* this */, jintArray arr) {
    jint *array = env->GetIntArrayElements(arr, nullptr);
    jsize length = env->GetArrayLength(arr);

    std::sort(array, array + length);

    env->ReleaseIntArrayElements(arr, array, 0);
}
