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
            return JSON.parseObject(s, ResponMessage.class);
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
    public ResponMessage checkUserName(String user_Name) {
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
    public ResponMessage setUserNamePasswd(String user_Name, String passwd, String sessionid) {
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
    public ResponMessage uploadingUserHead(String user_Head, String sessionid) {
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
    public ResponMessage uploadingUserBackgroundUrl(String user_Background, String sessionid) {
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
    public ResponMessage updateUserNickName(String user_NickName, String sessionid) {
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
    public ResponMessage updateUserSex(String user_Sex, String sessionid) {
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
    public ResponMessage updatePasswd(String oldPasswd, String newPasswd, String sessionid) {
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
    public ResponMessage retrievePasswd(String newPasswd, String code, String sessionid) {
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
    public ResponMessage updateUserPhone(String user_Phone, String code, String sessionid) {
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
    public ResponMessage getEmailCode(String user_Email, String sessionid) {
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
    public ResponMessage bindUserEmail(String user_Email, String code, String sessionid) {
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
     * @return 请求状态码status，用户信息data.userVO
     */
    public ResponMessage updateUserEmail(String user_Email, String newCode, String oldCode, String sessionid) {
        Map<String, Object> data = new HashMap<>();
        data.put("user_Email", user_Email);
        data.put("newCode", newCode);
        data.put("oldCode", oldCode);
        RequestMessage requestMessage = new RequestMessage(data);
        return send("updateUserEmail.api", requestMessage, sessionid);
    }
}
