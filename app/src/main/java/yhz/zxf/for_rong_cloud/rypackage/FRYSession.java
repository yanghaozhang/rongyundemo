package yhz.zxf.for_rong_cloud.rypackage;

import java.util.ArrayList;
import java.util.HashMap;

import io.rong.imlib.model.UserInfo;

/**
 * Created by YangHaoZhang on 2017/10/29.
 */

public class FRYSession {
    private static FRYSession mInstance;

    private static ArrayList<UserInfo> mUserInfos;

    private static HashMap<String, String> mTokenMap;

    private UserInfo mUserInfo;

    private String token;

    static {
        mUserInfos = new ArrayList<>();
        mTokenMap = new HashMap<>();

        UserInfo userInfo = new UserInfo("abc1", "tttuu", null);
        String t = "jXJ4bDQN1D3d8D+X0VVpIPvOrhxU9W3lrJyHIzssuinYFDzGBr9EwldG0vtdhgBd9Uy7IsmYkcHxrstoS13Maw==";
        mUserInfos.add(userInfo);
        mTokenMap.put(userInfo.getUserId(), t);

        userInfo = new UserInfo("abc2", "eevvv", null);
        t = "2+T/zR+vqsmmQkEfgSWdPvvOrhxU9W3lrJyHIzssuinYFDzGBr9EwkGfOfGNsEgSuclXx+fXbeR/FT7WJBwAiw==";
        mUserInfos.add(userInfo);
        mTokenMap.put(userInfo.getUserId(), t);

        userInfo = new UserInfo("abc3", "qqqzzz", null);
        t = "+/1eAnqIMonafI5+sCWJMWdJllJyfgJS3lXsNmsiPAh8cN9ODx2+jrRSL6+vArp5hiF1HsXi9Rsf6lLziB8nxg==";
        mUserInfos.add(userInfo);
        mTokenMap.put(userInfo.getUserId(), t);
    }

    private FRYSession() {

    }

    public static FRYSession getInstance() {
        if (mInstance == null) {
            synchronized (FRYSession.class) {
                if (mInstance == null) {
                    mInstance = new FRYSession();
                }
            }
        }
        return mInstance;
    }

    public static UserInfo getUserInfo(int i) {
        if (i < 1 || i > mUserInfos.size()) {
            return null;
        }
        return mUserInfos.get(i - 1);
    }

    public void setUserInfo(UserInfo userInfo) {
        mUserInfo = userInfo;
        token = mTokenMap.get(userInfo.getUserId());
    }

    public UserInfo getUserInfo() {
        return mUserInfo;
    }

    public String getToken() {
        return token;
    }
}
