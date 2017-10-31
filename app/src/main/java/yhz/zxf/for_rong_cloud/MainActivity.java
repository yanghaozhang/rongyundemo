package yhz.zxf.for_rong_cloud;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import encrypt.NativeGetKey;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import yhz.zxf.for_rong_cloud.rypackage.FRYSession;
import yhz.zxf.for_rong_cloud.rypackage.RYController;

public class MainActivity extends Activity {

    private static int mTokenErrorReconnectTime = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String s = NativeGetKey.nativeGetKeyMethod(MainActivity.this);
                System.out.println(s);
            }
        }).start();

//        ((EditText) findViewById(R.id.tv_main_count)).setHint(s + "");
//        getSignInfo();
//        getR();
    }

    private void getR() {
        String s = "np84bg6f0e7a3d91x5c2";
        String t =
                "308201dd30820146020101300d06092a864886f70d010105050030373116301406035504030c0d416e64726f69642044656275673110300e060355040a0c07416e64726f6964310b3009060355040613025553301e170d3137303632303133323131315a170d3437303631333133323131315a30373116301406035504030c0d416e64726f69642044656275673110300e060355040a0c07416e64726f6964310b300906035504061302555330819f300d06092a864886f70d010101050003818d00308189028181008ab76df0b6e6b66da09ed87057659059b5e50929a976d57d22bb96245c999ba4f748f61d4c362fdbfc27123ee7bb7c277f70ac06626a9b695d4d5daa30ffe0543ea9745f4309ef0a0f9f30b8902213f549a98d7ea24ea2d8c0c0e7e0abf7aa8276923cb51dc5ae768f1306429447e8e56c65536d07899f114457352ab41899d30203010001300d06092a864886f70d0101050500038181007e137d376e8d01cf0d3788ec015468fae21867a59bc86caf4a60b7d3c8df1a072c98eb133850958f8504bfb8bc1cf12a591cfa99582985cac8ba3f4be9c02d1e91248c278cfbff5f29c20e54b981d1eb2ac8ba7f737229917c0f6fdda3a0fcae47268b9de2f5aa6b93cea6411ff8a67ad706c1f15b1bcaebb3b73125aa303483";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            int i1 = s.indexOf(c);
            sb.append(i1);
        }
        System.out.println(sb.toString());
        Log.e("System.out:",sb.toString());
    }

    public void getSignInfo() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            System.out.println(sign.toCharsString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void go(View view) {
        EditText tv = (EditText) findViewById(R.id.tv_main_count);
        String trim = tv.getText().toString().trim();
        if (trim == null || trim.length() == 0) {
            defaultUserInfo();
            return;
        }
        int i = 0;
        try {
            i = Integer.valueOf(trim);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            defaultUserInfo();
            return;
        }
        UserInfo userInfo = FRYSession.getUserInfo(i);
        if (userInfo == null) {
            defaultUserInfo();
        } else {
            chooseUserInfo(userInfo);
        }
    }

    private void chooseUserInfo(UserInfo userInfo) {
        FRYSession.getInstance().setUserInfo(userInfo);
        goConversationList();
    }

    private void defaultUserInfo() {
        FRYSession.getInstance().setUserInfo(FRYSession.getUserInfo(1));
        goConversationList();
    }

    private void goConversationList() {
        if (mTokenErrorReconnectTime <= 0) {
            Toast.makeText(this, "用户数据有误", Toast.LENGTH_SHORT).show();
            Log.e("ANDLOG", "Class:MainActivity-----Method:goConversationList\ntoken error");
            return;
        }
        mTokenErrorReconnectTime--;
        RYController.connect(FRYSession.getInstance().getToken(), new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                goConversationList();
            }

            @Override
            public void onSuccess(String s) {
                RYController.initDefaultGlobalListener();
                RYController.startConversationList(MainActivity.this);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }


}
