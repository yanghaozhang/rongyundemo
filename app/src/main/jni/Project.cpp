#include <jni.h>
#include <stdio.h>
#include <string.h>
#include <android/log.h>
#define LOG_TAG "System.out"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)


#ifdef __cplusplus
extern "C"{
#endif

static jclass contextClass;
static jclass signatureClass;
static jclass packageNameClass;
static jclass packageInfoClass;

/**
    之前生成好的签名字符串
*/
const char* RELEASE_SIGN = "";


const char* Q8Zm0 = "12821981513131282198153681981";
const char* bxx = "5612815386812171783812818813315696310196761463198";
const char* sqhou = "158158158178881221521388128215214819215215882114106137846964661311814913210";
const char* i$Plk = "581512881386814191126322671081381581581781788128121012151";
const char* rhz = "33617619101761012151581288986812171783811818810315696310196761463121584128814868121717838615128191717171212815915108131215121012812612191281215121212191215121512151711151081312312101281261215121212151212121912151215121517111281210121515612815386812171783812818813315696310196761463198336176191017610121515812889868121717838118188103156963101967614631215841288148";
const char* ttt = "6812171783861512819171717121282151471288138681419112632267108138";
const char* RBz4zzzzzzzNQ = "3318126197134718191015191299104410181910107108111886619611144614171331317131";
const char* Sssq$sS = "111128779817312911141031773128149781187147128421481919151271731411142131091119391";
const char* O3HaJKA = "9191526101117144182618117311684101312182137151181019181429415121221781417272178347424181518715191117141518711141417219142171811182411127349141881913159141519321819102187477177191418198917341421513159419111824111071012101919141415101887671313111211871811931019624141391971711116414121891163151577211610111310861815715174154181194412410121519171111128123212";
const char* E000GD = "186171712613810214147151533171012171911431521414131281981281588815128813868141911263";
const char* P2L6MjuhJ = "119132188188910981147101111219106141912184171513181711910627151286319143310929176";
const char* zui = "8171061714817144179178141914111410613171013191944146193171814141441137103276151";
const char* NCU5as = "22671081381581581781788812215215881091512101312106921381518781312102291881517362711";
/*
    根据context对象,获取签名字符串
*/
const char* getSignString(JNIEnv *env,jobject contextObject) {
    jmethodID getPackageManagerId = (env)->GetMethodID(contextClass, "getPackageManager","()Landroid/content/pm/PackageManager;");
    jmethodID getPackageNameId = (env)->GetMethodID(contextClass, "getPackageName","()Ljava/lang/String;");
    jmethodID signToStringId = (env)->GetMethodID(signatureClass, "toCharsString","()Ljava/lang/String;");
    jmethodID getPackageInfoId = (env)->GetMethodID(packageNameClass, "getPackageInfo","(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jobject packageManagerObject =  (env)->CallObjectMethod(contextObject, getPackageManagerId);
    jstring packNameString =  (jstring)(env)->CallObjectMethod(contextObject, getPackageNameId);
    jobject packageInfoObject = (env)->CallObjectMethod(packageManagerObject, getPackageInfoId,packNameString, 64);
    jfieldID signaturefieldID =(env)->GetFieldID(packageInfoClass,"signatures", "[Landroid/content/pm/Signature;");
    jobjectArray signatureArray = (jobjectArray)(env)->GetObjectField(packageInfoObject, signaturefieldID);
    jobject signatureObject =  (env)->GetObjectArrayElement(signatureArray,0);
    return (env)->GetStringUTFChars((jstring)(env)->CallObjectMethod(signatureObject, signToStringId),0);
}

void iiiIIIooO(char* p,int z,const char* v){
    int  a = strlen(v);
    for(int i = 0 ;i<a ;i++){
        p[z]=v[i];
        z++;
    }
}

void gogogo(char* p){
    int l  = 0;
    iiiIIIooO(p,l,Q8Zm0);
    l+=strlen(Q8Zm0);
    iiiIIIooO(p,l,i$Plk);
    l+=strlen(i$Plk);
    iiiIIIooO(p,l,bxx);
    l+=strlen(bxx);
    iiiIIIooO(p,l,rhz);
    l+=strlen(rhz);
    iiiIIIooO(p,l,ttt);
    l+=strlen(ttt);
    iiiIIIooO(p,l,sqhou);
    l+=strlen(sqhou);
    iiiIIIooO(p,l,zui);
    l+=strlen(zui);
    iiiIIIooO(p,l,RBz4zzzzzzzNQ);
    l+=strlen(RBz4zzzzzzzNQ);
    iiiIIIooO(p,l,Sssq$sS);
    l+=strlen(Sssq$sS);
    iiiIIIooO(p,l,P2L6MjuhJ);
    l+=strlen(P2L6MjuhJ);
    iiiIIIooO(p,l,E000GD);
    l+=strlen(E000GD);
    iiiIIIooO(p,l,NCU5as);
    l+=strlen(NCU5as);
    iiiIIIooO(p,l,O3HaJKA);
    l+=strlen(O3HaJKA);
}

const char* getReal(){
    const char* R = "np84bg6f0e7a3d91x5c2";
    int temp_len  = strlen(Q8Zm0)+strlen(i$Plk)+strlen(bxx)+strlen(rhz)+strlen(ttt)+strlen(sqhou)+strlen(zui)+strlen(RBz4zzzzzzzNQ)+strlen(Sssq$sS)+strlen(P2L6MjuhJ)+strlen(E000GD)+strlen(NCU5as)+strlen(O3HaJKA);
    char* RELEASE_SIGN = new char[temp_len];
    gogogo(RELEASE_SIGN);
    int len = strlen(RELEASE_SIGN);
    char* p = new char[962 * sizeof(RELEASE_SIGN[0])];
    int i = 0;
    int k = 0;
    int j = 0;
    for( i=0; i<len; i++ ){
        j = RELEASE_SIGN[i]-48;
        if(j == 1){
            i++;
            j = RELEASE_SIGN[i]-48 + 10;
        }
        p[k] = R[j];
        k++;
    }
    return p;
}

jstring Java_encrypt_NativeGetKey_nativeGetKeyMethod(JNIEnv *env,jobject thiz,jobject contextObject) {

    const char* signStrng =  getSignString(env,contextObject);
    const char* b =getReal();
    LOGD("name=%s",b);
    LOGD("name=%s",signStrng);
    int m = strlen(signStrng);
    int n = strlen(b);
    if(strncmp(signStrng,b,m<n?m:n)==0)//签名一致  返回合法的 api key，否则返回错误
    {
       return (jstring)env->NewStringUTF("your key");
    }else
    {
       return (jstring)env->NewStringUTF("error");
    }
    //return charTojstring(env,b);
}


/**
    利用OnLoad钩子,初始化需要用到的Class类.
*/
JNIEXPORT jint JNICALL JNI_OnLoad (JavaVM* vm,void* reserved){

     JNIEnv* env = NULL;
     jint result=-1;
     if(vm->GetEnv((void**)&env, JNI_VERSION_1_4) != JNI_OK)
       return result;

     contextClass = (jclass)env->NewGlobalRef((env)->FindClass("android/content/Context"));
     signatureClass = (jclass)env->NewGlobalRef((env)->FindClass("android/content/pm/Signature"));
     packageNameClass = (jclass)env->NewGlobalRef((env)->FindClass("android/content/pm/PackageManager"));
     packageInfoClass = (jclass)env->NewGlobalRef((env)->FindClass("android/content/pm/PackageInfo"));

     return JNI_VERSION_1_4;
 }

#ifdef __cplusplus
}
#endif
