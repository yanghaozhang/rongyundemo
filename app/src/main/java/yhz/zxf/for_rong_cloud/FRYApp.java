package yhz.zxf.for_rong_cloud;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.os.Process;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import io.rong.imkit.RongIM;
import yhz.zxf.for_rong_cloud.rypackage.Listener.FRYConnectionStatusListener;

/**
 * Created by YangHaoZhang on 2017/10/29.
 */

public class FRYApp extends Application {
    private static Context mContext;

    private static FRYApp mFRYApp;

    private FRYConnectionStatusListener mConnectionStatusListener;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        mFRYApp = this;
        initRongCloud();
    }

    private void initRongCloud() {
        if (!getPackageName().equals(getCurProcessName(getApplicationContext())) && !"io.rong.push".equals(getCurProcessName(getApplicationContext
                ())))
            return;
        RongIM.init(this);
//        if (mConnectionStatusListener == null) {
//            mConnectionStatusListener = new FRYConnectionStatusListener();
//        }
//        RongIM.setConnectionStatusListener(mConnectionStatusListener);
//        RYController.registerGroupConversationTemplate();
    }

    public static Application getApplication() {
        return mFRYApp;
    }

    public static boolean isInMainThread() {
        if (Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper()) {
            return true;
        }
        return false;
    }

    public static String getCurProcessName() {
        int pid = Process.myPid();
        ActivityManager am = (ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
            if (info.pid == pid) {
                return info.processName;
            }
        }
        return null;
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();

        if (processInfos != null) {
            for (ActivityManager.RunningAppProcessInfo info : processInfos) {
                if (info.pid == pid) {
                    return info.processName;
                }
            }
        } else {
            FileReader fileReader = null;
            BufferedReader bufferedReader = null;
            try {
                fileReader = new FileReader("/proc/" + pid + "/cmdline");
                bufferedReader = new BufferedReader(fileReader);
                return bufferedReader.readLine().trim();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception ignored) {
                    }
                }
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (Exception ignored) {
                    }
                }
            }
        }
        return null;
    }

    public static Context getContext() {
        return mContext;
    }
}
