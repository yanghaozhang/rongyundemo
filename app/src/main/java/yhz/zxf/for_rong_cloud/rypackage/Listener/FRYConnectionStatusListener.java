package yhz.zxf.for_rong_cloud.rypackage.Listener;

import io.rong.imlib.RongIMClient;

/**
 * Created by YangHaoZhang on 2017/10/29.
 * 监听融云服务端的连接状态
 */

public class FRYConnectionStatusListener implements RongIMClient.ConnectionStatusListener {
    @Override
    public void onChanged(ConnectionStatus connectionStatus) {
        switch (connectionStatus) {
            case CONNECTED://连接成功。
                break;
            case DISCONNECTED://断开连接。
                break;
            case CONNECTING://连接中。
                break;
            case NETWORK_UNAVAILABLE://网络不可用。
                break;
            case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
                break;
        }
    }
}
