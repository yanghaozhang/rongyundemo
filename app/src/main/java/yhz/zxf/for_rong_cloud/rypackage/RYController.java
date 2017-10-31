package yhz.zxf.for_rong_cloud.rypackage;

import android.app.Activity;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import yhz.zxf.for_rong_cloud.FRYApp;
import yhz.zxf.for_rong_cloud.rypackage.Listener.FRYConversationListBehaviorListener;
import yhz.zxf.for_rong_cloud.rypackage.Listener.FRYReceiveMessageListener;
import yhz.zxf.for_rong_cloud.rypackage.Listener.FRYSendMessageListener;
import yhz.zxf.for_rong_cloud.rypackage.UI.FRYGroupConversationProvider;

/**
 * Created by YangHaoZhang on 2017/10/29.
 * <p>
 * 控制融云的设置
 */

public class RYController {

    private static FRYSendMessageListener mSendMessageListener;

    private static FRYReceiveMessageListener mReceiveMessageListener;

    private static FRYConversationListBehaviorListener mConversationListBehaviorListener;

    /**
     * -----------------------<<<<<必须连接服务器后才能调用其他聊天功能
     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {RongIM.init(Context)} 之后调用。</p>
     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
     *
     * @param token    从服务端获取的用户身份令牌（Token）。
     * @param callback 连接回调。
     * @return RongIM 客户端核心类的实例。
     */
    public static void connect(String token, RongIMClient.ConnectCallback callback) {
        if (FRYApp.getApplication().getApplicationInfo().packageName.equals(FRYApp.getCurProcessName())) {
            RongIM.connect(token, callback);
        }
    }

    /**
     * 自定义群组绘画模板
     */
    public static void registerGroupConversationTemplate() {
        RongIM.getInstance().registerConversationTemplate(new FRYGroupConversationProvider());
    }

    /**
     * 自定义群组绘画模板
     */
    public static void initDefaultGlobalListener() {
        setRongCloudGetUserInfoListener();
        setConversationListBehaviorListener();
        setSendMessageListener();
        setOnReceiveMessageListener();
    }

    /**
     * <p>启动会话界面。</p>
     * <p>使用时，可以传入多种会话类型 {@link io.rong.imlib.model.Conversation.ConversationType} 对应不同的会话类型，开启不同的会话界面。
     * 如果传入的是 {@link io.rong.imlib.model.Conversation.ConversationType#CHATROOM}，sdk 会默认调用
     * {@link RongIMClient#joinChatRoom(String, int, RongIMClient.OperationCallback)} 加入聊天室。
     * 如果你的逻辑是，只允许加入已存在的聊天室，请使用接口 {startChatRoomChat(Context, String, boolean)} 并且第三个参数为 true</p>
     *
     * @param context          应用上下文。
     * @param conversationType 会话类型。
     * @param targetId         根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
     * @param title            聊天的标题，开发者可以在聊天界面通过 intent.getData().getQueryParameter("title") 获取该值, 再手动设置为标题。
     */
    public static void startConversation(Context context, Conversation.ConversationType conversationType, String targetId, String title) {
        RongIM.getInstance().startConversation(context, conversationType, targetId, title);
    }

    /**
     * 启动会话列表界面。
     *
     * @param context               应用上下文。
     * @param supportedConversation 定义会话列表支持显示的会话类型，及对应的会话类型是否聚合显示。
     *                              例如：supportedConversation.put(Conversation.ConversationType.PRIVATE.getName(), false) 非聚合式显示 private 类型的会
     *                              话。
     */
    public static void startConversationList(Context context) {
        Map<String, Boolean> supportedConversation = new HashMap<>();
        supportedConversation.put(Conversation.ConversationType.PRIVATE.getName(), true);
        supportedConversation.put(Conversation.ConversationType.GROUP.getName(), true);
        RongIM.getInstance().startConversationList(context, supportedConversation);
    }

    public static void startConversationList(Context context, Map<String, Boolean> supportedConversation) {
        RongIM.getInstance().startConversationList(context, supportedConversation);
    }

    /**
     * <p>断开与融云服务器的连接。当调用此接口断开连接后，仍然可以接收 Push 消息。</p>
     * <p>若想断开连接后不接受 Push 消息，可以调用{@link #logout()}</p>
     */
    public void disconnect() {
        RongIM.getInstance().disconnect();
    }

    /**
     * <p>断开与融云服务器的连接，并且不再接收 Push 消息。</p>
     * <p>若想断开连接后仍然接受 Push 消息，可以调用 {@link #disconnect()}</p>
     */
    public void logout() {
        RongIM.getInstance().logout();
        unRegisterAndClearUserInfo();

    }

    private void unRegisterAndClearUserInfo() {
        mSendMessageListener = null;
        mReceiveMessageListener = null;
        mConversationListBehaviorListener = null;
    }

    public static void setRongCloudGetUserInfoListener() {
        /**
         * 设置用户信息的提供者，供 RongIM 调用获取用户名称和头像信息。
         *
         * @param userInfoProvider 用户信息提供者。
         * @param isCacheUserInfo 设置是否由 IMKit 来缓存用户信息。<br>
         * 如果 App 提供的 UserInfoProvider
         * 每次都需要通过网络请求用户数据，而不是将用户数据缓存到本地内存，会影响用户信息的加载速度；<br>
         * 此时最好将本参数设置为 true，由 IMKit 将用户信息缓存到本地内存中。
         * @see UserInfoProvider
         */
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String userId) {
                getUserInfoFormAppServer(userId);
                return null;//根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
            }
        }, true);
    }

    private static void getUserInfoFormAppServer(String userId) {
        //请求并在成功回调中调用以下方法
        /**
         * 刷新用户缓存数据。
         *
         * @param userInfo 需要更新的用户缓存数据。
         */
//        RongIM.getInstance().refreshUserInfoCache(new UserInfo(userId, name, uri));
    }

    public static void startPrivateChat(Context context, String userId, String title) {
        /**
         * 启动单聊界面。
         *
         * @param context 应用上下文。
         * @param targetUserId 要与之聊天的用户 Id。
         * @param title 聊天的标题，开发者需要在聊天界面通过 intent.getData().getQueryParameter("title")
         * 获取该值, 再手动设置为聊天界面的标题。
         */
        RongIM.getInstance().startPrivateChat(context, userId, title);
    }

    public static void setSendMessageListener() {
        if (mSendMessageListener == null) {
            mSendMessageListener = new FRYSendMessageListener();
        }
        RongIM.getInstance().setSendMessageListener(mSendMessageListener);
    }

    public static void setOnReceiveMessageListener() {
        if (mReceiveMessageListener == null) {
            mReceiveMessageListener = new FRYReceiveMessageListener();
        }
        RongIM.setOnReceiveMessageListener(mReceiveMessageListener);
    }

    public static void setConversationListBehaviorListener() {
        /**
         * 设置会话列表界面操作的监听器。
         */
        if (mConversationListBehaviorListener == null) {
            mConversationListBehaviorListener = new FRYConversationListBehaviorListener();
        }
        RongIM.setConversationListBehaviorListener(mConversationListBehaviorListener);
    }

    /**
     * 设置未读消息数变化监听器。
     * 注意:如果是在 activity 中设置,那么要在 activity 销毁时,调用 {removeUnReadMessageCountChangedObserver(IUnReadMessageObserver)}
     * 否则会造成内存泄漏。
     *
     * @param observer          接收未读消息消息的监听器。
     * @param conversationTypes 接收未读消息的会话类型。
     */
    public static void addUnReadMessageCountChangedObserver(final IUnReadMessageObserver observer, Conversation.ConversationType... conversationTypes) {
        RongIM.getInstance().addUnReadMessageCountChangedObserver(observer, conversationTypes);
    }

    /**
     * 注销已注册的未读消息数变化监听器。
     *
     * @param observer 接收未读消息消息的监听器。
     */
    public static void removeUnReadMessageCountChangedObserver(IUnReadMessageObserver observer) {
        RongIM.getInstance().removeUnReadMessageCountChangedObserver(observer);
    }

}
