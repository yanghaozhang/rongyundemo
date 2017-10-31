package encrypt;

import android.content.Context;

public class NativeGetKey {
   static {
      System.loadLibrary("project");
   }

   public static native String nativeGetKeyMethod(Context context);

}