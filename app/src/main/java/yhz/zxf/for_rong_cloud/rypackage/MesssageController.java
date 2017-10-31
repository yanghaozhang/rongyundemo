package yhz.zxf.for_rong_cloud.rypackage;

import android.net.Uri;

import java.io.File;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;
import io.rong.message.RichContentMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * Created by YangHaoZhang on 2017/10/29.
 *
 * 控制消息的发送/接收
 */

public class MesssageController {

    public static void sendTextMessagePrivate(String userId, String text, IRongCallback.ISendMessageCallback callback) {
        // 构造 TextMessage 实例
        TextMessage myTextMessage = TextMessage.obtain(text);
        /* 生成 Message 对象。
        * "7127" 为目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
        * Conversation.ConversationType.PRIVATE 为私聊会话类型，根据需要，也可以传入其它会话类型，如群组，讨论组等。
        */
        Message myMessage = Message.obtain(userId, Conversation.ConversationType.PRIVATE, myTextMessage);
        /**
         * <p>发送消息。
         * 通过 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}
         * 中的方法回调发送的消息状态及消息体。</p>
         *
         * @param message 将要发送的消息体。
         * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
         * 如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
         * 如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
         * @param pushData push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessa
        ge#getPushData()} 方法获取。
         * @param callback 发送消息的回调，参考 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}。
         */
        RongIM.getInstance().sendMessage(myMessage, null, null, callback);
    }

    public static void sendImageMessage(String userId, Uri uri, Uri largeUri, RongIMClient.SendImageMessageCallback callback) {
        sendImageMessage(userId, uri, largeUri, false, callback);
    }

    public static void sendImageMessage(String userId, Uri uri, Uri largeUri, boolean isFull, RongIMClient.SendImageMessageCallback callback) {
        /**
         * 生成ImageMessage对象。
         *
         * @param thumUri 缩略图地址。
         * @param localUri 大图地址。
         * @param isFull 是否发送原图。
         * @return ImageMessage对象实例。
         */
        ImageMessage imgMsg = ImageMessage.obtain(uri, largeUri, isFull);

        /**
         * <p>根据会话类型，发送图片消息。</p>
         *
         * @param type 会话类型。
         * @param targetId 目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
         * @param content 消息内容，例如 {@link TextMessage}, {@link ImageMessage}。
         * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
         * 如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
         * 如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
         * @param pushData push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessa
        ge#getPushData()} 方法获取。
         * @param callback 发送消息的回调。
         */
        RongIM.getInstance().sendImageMessage(Conversation.ConversationType.PRIVATE, userId, imgMsg, null, null, callback);
    }

    public static void sendImageMessageAndUpload(String userId, Uri uri, Uri largeUri, RongIMClient.SendImageMessageCallback callback) {
        ImageMessage imageMessage = ImageMessage.obtain(uri, largeUri);
        Message message = Message.obtain(userId, Conversation.ConversationType.PRIVATE, imageMessage);
        /**
         * <p>发送图片消息，可以使用该方法将图片上传到自己的服务器发送，同时更新图片状态。</p>
         * <p>使用该方法在上传图片时，会回调 {@link io.rong.imlib.RongIMClient.SendImageMessageWithUploadListenerCallback}
         * 此回调中会携带 {@link RongIMClient.UploadImageStatusListener} 对象，使用者只需要调用其中的
         * {@link RongIMClient.UploadImageStatusListener#update(int)} 更新进度
         * {@link RongIMClient.UploadImageStatusListener#success(Uri)} 更新成功状态，并告知上传成功后的图片地址
         * {@link RongIMClient.UploadImageStatusListener#error()} 更新失败状态 </p>
         *
         * @param message 发送消息的实体。
         * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
         * 如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
         * 如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
         * @param pushData push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessa
        ge#getPushData()} 方法获取。
         * @param callback 发送消息的回调，回调中携带 {@link RongIMClient.UploadImageStatusListener} 对象，用户调用该对象中的方法更新状态。
         * {@link #sendImageMessage(Message, String, String, RongIMClient.SendImageMessageCallback)}
         */
        RongIM.getInstance().sendImageMessage(message, null, null, new RongIMClient.SendImageMessageWithUploadListenerCallback() {
            @Override
            public void onAttached(Message message, final RongIMClient.UploadImageStatusListener uploadImageStatusListener) {
                /*上传图片到自己的服务器，并回调融云的监听*/
               /* uploadImg(imgMsg.getPicFilePath(), new UploadListener() {
                    @Override
                    public void onSuccess(String url) {
                        // 上传成功，回调 SDK 的 success 方法，传递回图片的远端地址
                        uploadImageStatusListener.success(Uri.parse(url));
                    }

                    @Override
                    public void onProgress(float progress) {
                        //刷新上传进度
                        uploadImageStatusListener.update((int) progress);
                    }

                    @Override
                    public void onFail() {
                        // 上传图片失败，回调 error 方法。
                        uploadImageStatusListener.error();
                    }
                });*/
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                //发送失败
            }

            @Override
            public void onSuccess(Message message) {
                //发送成功
            }

            @Override
            public void onProgress(Message message, int progress) {
                //发送进度
            }
        });
    }

    public static void sendVoiceMessage(String userId, File fileName, IRongCallback.ISendMessageCallback callback) {
        /**
         * 获取语音消息实体。
         *
         * @param Uri 语音 Uri 。
         * @param duration 语音时长（单位：秒）。
         */
        VoiceMessage vocMsg = VoiceMessage.obtain(Uri.fromFile(fileName), 10);
        Message message = Message.obtain(userId, Conversation.ConversationType.PRIVATE, vocMsg);
        /**
         * <p>发送消息。
         * 通过 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}
         * 中的方法回调发送的消息状态及消息体。</p>
         *
         * @param message 将要发送的消息体。
         * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
         * 如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
         * 如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
         * @param pushData push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessa
        ge#getPushData()} 方法获取。
         * @param callback 发送消息的回调，参考 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}。
         */
        RongIM.getInstance().sendMessage(message, null, null, callback);
    }

    public static void sendImageWithTextMessage(String userId, String title, String content, String imageUrl, IRongCallback.ISendMessageCallback
            callback) {
        /**
         * 生成RichContentMessage对象。
         *
         * @param title 消息标题。
         * @param content 消息内容。
         * @param imageUrl 消息图片url.
         * @return 生成RichContentMessage对象。
         */
        RichContentMessage richContentMessage = RichContentMessage.obtain(title, content, imageUrl);
        //"9517" 为目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
        //Conversation.ConversationType.PRIVATE 为会话类型。
        Message myMessage = Message.obtain(userId, Conversation.ConversationType.PRIVATE, richContentMessage);
        /**
         * <p>发送消息。
         * 通过 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}
         * 中的方法回调发送的消息状态及消息体。</p>
         *
         * @param message 将要发送的消息体。
         * @param pushContent 当下发 push 消息时，在通知栏里会显示这个字段。
         * 如果发送的是自定义消息，该字段必须填写，否则无法收到 push 消息。
         * 如果发送 sdk 中默认的消息类型，例如 RC:TxtMsg, RC:VcMsg, RC:ImgMsg，则不需要填写，默认已经指定。
         * @param pushData push 附加信息。如果设置该字段，用户在收到 push 消息时，能通过 {@link io.rong.push.notification.PushNotificationMessa
        ge#getPushData()} 方法获取。
         * @param callback 发送消息的回调，参考 {@link io.rong.imlib.IRongCallback.ISendMessageCallback}。
         */
        RongIM.getInstance().sendMessage(myMessage, null, null, callback);
    }
}
