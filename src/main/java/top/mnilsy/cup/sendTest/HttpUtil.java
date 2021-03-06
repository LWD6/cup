package top.mnilsy.cup.sendTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import okhttp3.*;
import top.mnilsy.cup.enums.UrlEnum;
import top.mnilsy.cup.utils.RequestMessage;
import top.mnilsy.cup.utils.ResponMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mnilsy on 19-5-9 上午12:54.
 */
public class HttpUtil {
    /**
     * 通用发送请求
     *
     * @param url            请求地址
     * @param requestMessage 请求数据包
     * @return 服务器返回数据
     * @author mnilsy
     */
    private static ResponMessage send(String url, RequestMessage requestMessage, String sessionId) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), JSON.toJSONString(requestMessage));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().addHeader("cookie", "JSESSIONID=" + sessionId).url(UrlEnum.HTTPSERVER.vlue + url).post(requestBody).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.body() == null) return null;
            String s = response.body().string();
            ResponMessage responMessage = null;
            try {
                responMessage = JSON.parseObject(s, ResponMessage.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return responMessage;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 密码登录
     *
     * @param user   用户名||手机号码||电子邮箱
     * @param passwd 登录密码
     * @return 请求状态码status，失败信息message，用户信息userVO
     * @author mnilsy
     */
    public static ResponMessage passwdLogin(String user, String passwd) {
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        data.put("passwd", passwd);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("open/passwdLogin.api", requestMessage, null);
    }


    /**
     * 请求手机验证码,用于登录时sessionid传null
     *
     * @param user_Phone 手机号码
     * @return 请求状态码status，失败信息message，会话sessionid
     * @author mnilsy
     */
    public static ResponMessage getPhoneCode(String user_Phone, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_Phone", user_Phone);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("open/getPhoneCode.api", requestMessage, sessionid);
    }


    /**
     * 验证码登录
     *
     * @param user_Phone 手机号码
     * @param code       验证码
     * @return 请求状态码status，失败信息message，用户信息userVO
     * @author mnilsy
     */
    public static ResponMessage codeLogin(String user_Phone, String code, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_Phone", user_Phone);
        data.put("code", code);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("open/codeLogin.api", requestMessage, sessionid);
    }

    /**
     * 登出
     *
     * @return 请求状态码status，失败信息message
     * @author mnilsy
     */
    public static ResponMessage logout(String sessionid) {
        return send("logout.api", null, sessionid);
    }

    /**
     * 账号注册
     *
     * @param user_Phone 手机号码
     * @param code       验证码
     * @return 请求状态码status，失败信息message
     * @author mnilsy
     */
    public static ResponMessage register(String user_Phone, String code, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_Phone", user_Phone);
        data.put("code", code);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("open/register.api", requestMessage, sessionid);
    }


    /**
     * 检测用户名是否唯一
     *
     * @param user_Name 用户名
     * @return 请求状态码status，失败信息message
     * @author mnilsy
     */
    public static ResponMessage checkUserName(String user_Name) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_Name", user_Name);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("open/checkUserName.api", requestMessage, null);
    }


    /**
     * 设置用户名和密码
     *
     * @param user_Name 用户名
     * @param passwd    密码
     * @return 请求状态码status, 失败信息message，用户信息userVO
     * @author mnilsy
     */
    public static ResponMessage setUserNamePasswd(String user_Name, String passwd, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_Name", user_Name);
        data.put("passwd", passwd);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("setUserNamePasswd.api", requestMessage, sessionid);
    }

    /**
     * 上传头像
     *
     * @param user_Head 头像base64编码
     * @return 请求状态码status, 失败信息message，用户信息userVO
     * @author mnilsy
     */
    public static ResponMessage uploadingUserHead(String user_Head, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_Head", user_Head);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("uploadingUserHead.api", requestMessage, sessionid);
    }

    /**
     * 上传背景图
     *
     * @param user_Background 背景base64编码
     * @return 请求状态码status, 失败信息message，用户信息userVO
     * @author mnilsy
     */
    public static ResponMessage uploadingUserBackgroundUrl(String user_Background, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_Background", user_Background);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("uploadingUserBackgroundUrl.api", requestMessage, sessionid);
    }

    /**
     * 修改昵称
     *
     * @param user_NickName 用户昵称
     * @return 请求状态码status, 失败信息message，用户信息userVO
     * @author mnilsy
     */
    public static ResponMessage updateUserNickName(String user_NickName, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_NickName", user_NickName);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("updateUserNickName.api", requestMessage, sessionid);
    }

    /**
     * 修改性别
     *
     * @param user_Sex 用户性别
     * @return 请求状态码status, 失败信息message，用户信息userVO
     * @author mnilsy
     */
    public static ResponMessage updateUserSex(String user_Sex, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_Sex", user_Sex);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("updateUserSex.api", requestMessage, sessionid);
    }

    /**
     * 修改密码
     *
     * @param oldPasswd 用户旧密码
     * @param newPasswd 用户新密码
     * @return 请求状态码status, 失败信息message
     * @author mnilsy
     */
    public static ResponMessage updatePasswd(String oldPasswd, String newPasswd, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("oldPasswd", oldPasswd);
        data.put("newPasswd", newPasswd);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("updatePasswd.api", requestMessage, sessionid);
    }

    /**
     * 找回密码
     *
     * @param newPasswd 用户新密码data.get("newPasswd")，手机验证码data.get("code")
     * @return 请求状态码status, 失败信息message
     * @author mnilsy
     */
    public static ResponMessage retrievePasswd(String newPasswd, String code, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("newPasswd", newPasswd);
        data.put("code", code);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("open/retrievePasswd.api", requestMessage, sessionid);
    }

    /**
     * 修改手机号码
     *
     * @param user_Phone 用户新手机号码
     * @param code       手机验证码
     * @return 请求状态码status, 失败信息message，用户信息userVO
     * @author mnilsy
     */
    public static ResponMessage updateUserPhone(String user_Phone, String code, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_Phone", user_Phone);
        data.put("code", code);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("updateUserPhone.api", requestMessage, sessionid);
    }

    /**
     * 请求电子邮箱验证码
     *
     * @param user_Email 用户电子邮箱
     * @return 请求状态码status, 失败信息message
     * @author mnilsy
     */
    public static ResponMessage getEmailCode(String user_Email, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_Email", user_Email);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("open/getEmailCode.api", requestMessage, sessionid);
    }

    /**
     * 绑定电子邮箱
     *
     * @param user_Email 用户邮箱
     * @param code       邮箱验证码
     * @return 请求状态码status, 失败信息message，用户信息userVO
     * @author mnilsy
     */
    public static ResponMessage bindUserEmail(String user_Email, String code, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_Email", user_Email);
        data.put("code", code);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("bindUserEmail.api", requestMessage, sessionid);
    }

    /**
     * 修改电子邮箱
     *
     * @param user_Email 用户新邮箱
     * @param newCode    新邮箱验证码
     * @param oldCode    旧邮箱验证码
     * @return 请求状态码status, 失败信息message，用户信息userVO
     */
    public static ResponMessage updateUserEmail(String user_Email, String newCode, String oldCode, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_Email", user_Email);
        data.put("newCode", newCode);
        data.put("oldCode", oldCode);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("updateUserEmail.api", requestMessage, sessionid);
    }

    /**
     * 心跳，判断是否在线
     *
     * @return 请求状态码status
     * @author mnilsy
     */
    public static ResponMessage onlin(String sessionid) {
        return send("pingpong.api", null, sessionid);
    }

    /**
     * 发布推文
     *
     * @param tweet_Type 推文类型
     * @param tweet_Text 推文文字
     * @param accessory  推文附件
     * @param user_Name  @的用户
     * @return 请求状态码status, 失败信息message
     * @author mnilsy
     */
    public static ResponMessage putTweet(String tweet_Type, String tweet_Text, String[] accessory, String[] user_Name, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("tweet_Type", tweet_Type);
        data.put("tweet_Text", tweet_Text);
        data.put("accessory", accessory);
        data.put("user_Name", user_Name);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("putTweet.api", requestMessage, sessionid);
    }

    /**
     * 获取用户关注的人的推文，按时间降序
     *
     * @param conut 获取次数
     * @return 请求状态码 status, 失败信息message 关注的人的推文List<TweetVO>
     * @author mnilsy
     */
    public static ResponMessage getFollowTweet(String conut, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("conut", conut);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("getFollowTweet.api", requestMessage, sessionid);
    }

    /**
     * 查看推文，即点开推文
     *
     * @param tweet_Id 推文id
     * @return 请求状态码status, 失败信息message
     * @author mnilsy
     */
    public static ResponMessage openTweet(String tweet_Id) {
        Map<String, Object> data = new HashMap<>();
        data.put("tweet_Id", tweet_Id);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("open/openTweet.api", requestMessage, null);
    }

    /**
     * 获取指定推文的更多评论
     *
     * @param tweet_Id 推文id
     * @param count    获取次数
     * @return 请求状态码status，失败信息message，推文评论List<discussVO>
     * @author mnilsy
     */
    public static ResponMessage getMoreDiscuss(String tweet_Id, String count) {
        Map<String, Object> data = new HashMap<>();
        data.put("tweet_Id", tweet_Id);
        data.put("count", count);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("open/getMoreDiscuss.api", requestMessage, null);
    }

    /**
     * 评论推文
     *
     * @param discuss_Vlue 评论内容
     * @param tweet_Id     推文id
     * @return 请求状态码status，失败信息message
     * @author mnilsy
     */
    public static ResponMessage putDiscuss(String discuss_Vlue, String tweet_Id, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("discuss_Vlue", discuss_Vlue);
        data.put("tweet_Id", tweet_Id);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("putDiscuss.api", requestMessage, sessionid);
    }

    /**
     * 回复评论
     *
     * @param writeBack_Vlue      回复内容
     * @param discuss_Id          评论的id
     * @param writeBack_User_Name 回复的用户名
     * @return 请求状态码status，失败信息message
     * @author mnilsy
     */
    public static ResponMessage putWriteBack(String writeBack_Vlue, String discuss_Id, String writeBack_User_Name, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("writeBack_Vlue", writeBack_Vlue);
        data.put("discuss_Id", discuss_Id);
        data.put("writeBack_User_Name", writeBack_User_Name);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("putWriteBack.api", requestMessage, sessionid);
    }

    /**
     * 点赞
     *
     * @param tweet_Id 推文id
     * @return 请求状态码status，失败信息message
     * @author mnilsy
     */
    public static ResponMessage putLike(String tweet_Id, String sessionid) {
        return send("putLike" + tweet_Id + ".api", null, sessionid);
    }

    /**
     * 删除推文
     *
     * @param tweet_Id 推文id
     * @return 请求状态码status，失败信息message
     * @author mnilsy
     */
    public static ResponMessage deleteTweet(String tweet_Id, String sessionid) {
        return send("deleteTweet" + tweet_Id + ".api", null, sessionid);
    }

    /**
     * 删除评论
     *
     * @param discuss_Id 评论的id
     * @return 请求状态码status
     * @author mnilsy
     */
    public static ResponMessage deleteDiscuss(String discuss_Id, String sessionid) {
        return send("deleteDiscuss" + discuss_Id + ".api", null, sessionid);
    }

    /**
     * 删除评论回复
     *
     * @param writeBack_Id 评论回复的id
     * @return 请求状态码status
     * @author mnilsy
     */
    public static ResponMessage deleteWriteBack(String writeBack_Id, String sessionid) {
        return send("deleteWriteBack" + writeBack_Id + ".api", null, sessionid);
    }

    /**
     * 关注用户
     *
     * @param user_Name 用户名
     * @return 请求状态码status，失败信息 message
     * @author mnilsy
     */
    public static ResponMessage follow(String user_Name, String sessionid) {
        return send("follow" + user_Name + ".api", null, sessionid);
    }


    /**
     * 获取关注的人列表
     *
     * @param count 请求次数
     * @return 请求状态码status，失败信息 message，关注人列表List<UserListVO>
     * @author mnilsy
     */
    public static ResponMessage getFollowList(String count, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("count", count);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("getFollowList.api", requestMessage, sessionid);
    }

    /**
     * 获取粉丝列表
     *
     * @param count 请求次数
     * @return 请求状态码status，失败信息 message，关注你的人列表List<UserListVO>
     * @author mnilsy
     */
    public static ResponMessage getFans(String count, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("count", count);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("getFans.api", requestMessage, sessionid);
    }

    /**
     * 查看黑名单
     *
     * @param count 请求次数
     * @return 请求状态码status，失败信息 message，关注你的人列表List<UserListVO>
     * @author mnilsy
     */
    public static ResponMessage getBlackList(String count, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("count", count);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("getBlackList.api", requestMessage, sessionid);
    }

    /**
     * 加入黑名单
     *
     * @param user_Name 目标用户名
     * @return 请求状态码status，失败信息 message
     * @author mnilsy
     */
    public static ResponMessage setBlackList(String user_Name, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_Name", user_Name);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("setBlackList.api", requestMessage, sessionid);
    }

    /**
     * 上传定位
     *
     * @param location_Y 定位经度
     * @param location_X 定位纬度
     * @return 请求状态码status，失败信息 message
     * @author mnilsy
     */
    public static ResponMessage setUserLocation(String location_Y, String location_X, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("location_Y", location_Y);
        data.put("location_X", location_X);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("setUserLocation.api", requestMessage, sessionid);
    }

    /**
     * 清除定位
     *
     * @return 请求状态码status，失败信息 message
     * @author mnilsy
     */
    public static ResponMessage clearUserLocation(String sessionid) {
        return send("clearUserLocation.api", null, sessionid);
    }

    /**
     * 已登录用户获取附近的人的最新推文
     *
     * @param count 获取次数
     * @return 请求状态码status，失败信息 message，推文内容List<tweetVO>
     * @author mnilsy
     */
    public static ResponMessage getLocationTweet(String count, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("count", count);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("open/getLocationTweet.api", requestMessage, sessionid);
    }

    /**
     * 未登录用户获取附近的人的最新推文
     *
     * @param x     纬度
     * @param y     经度
     * @param count 获取次数
     * @return 请求状态码status，失败信息 message，推文内容List<tweetVO>
     * @author mnilsy
     */
    public static ResponMessage getLocationTweet(String x, String y, String count) {
        Map<String, Object> data = new HashMap<>();
        data.put("x", x);
        data.put("y", y);
        data.put("count", count);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("open/getLocationTweet.api", requestMessage, null);
    }

}
