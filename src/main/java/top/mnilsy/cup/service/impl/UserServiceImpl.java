package top.mnilsy.cup.service.impl;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.http.util.TextUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import top.mnilsy.cup.VO.UserVO;
import top.mnilsy.cup.dao.UserMapper;
import top.mnilsy.cup.enums.NettyActionEnum;
import top.mnilsy.cup.enums.UrlEnum;
import top.mnilsy.cup.netty.ChatHandler;
import top.mnilsy.cup.netty.DataContent;
import top.mnilsy.cup.netty.UserChannelRel;
import top.mnilsy.cup.pojo.PasswdPojo;
import top.mnilsy.cup.pojo.UserPojo;

import top.mnilsy.cup.service.UserService;
import top.mnilsy.cup.utils.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.*;

/**
 * Created by mnilsy on 19-4-20 下午7:28.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource(name = "userMapper")
    private UserMapper userMapper;

    @Resource(name = "sendMailUtils")
    private SendMailUtil sendMailUtil;

    /**
     * 获取验证码
     */
    @Override
    public String getPhoneCode(String user_Phone) {
        String code = (int) (Math.random() * 10) + "" + (int) (Math.random() * 10) + "" + (int) (Math.random() * 10) + "" + (int) (Math.random() * 10) + "" + (int) (Math.random() * 10) + "" + (int) (Math.random() * 10);
        System.out.println(code);
        if (SendSMSUtil.send(user_Phone, code)) return code;
        return null;
    }

    /**
     * 获取邮箱验证码
     */
    @Override
    public String getEmailCode(String user_Email) {
        String code = "TestEcode";
        sendMailUtil.send(user_Email, code);
        return code;
    }

    /**
     * 密码登录
     *
     * @author Jason_Jane
     */
    @Override
    public UserPojo getPasswdLogin(String user, String passwd) {
        String passwdConvertMD5 = MD5Util.MD5Encode(passwd, "utf8");
        return userMapper.getUserByNamePhoneEmail(user, passwdConvertMD5);
    }

    /**
     * 根据手机获取用户
     *
     * @author Jason_Jane
     */
    @Override
    public UserPojo getUserByPhone(String user_Phone) {
        UserPojo userPojo = userMapper.getUserByPhoneInfo(user_Phone);
        if (userPojo != null) {
            return userPojo;
        }
        return null;
    }

    /**
     * 验证码登录
     *
     * @author Jason_Jane
     */
    @Override
    public UserPojo codeLogin(String user_Phone) {
        UserPojo userPojo = userMapper.getUserByPhoneInfo(user_Phone);
        if (userPojo != null) {
            return userPojo;
        }
        return null;
    }

    /**
     * 检测用户名是否唯一
     *
     * @author Jason_Jane
     */
    @Override
    public String checkUserName(String user_Name) {
        UserPojo userPojo = userMapper.getUserByUserName(user_Name);
        if (userPojo != null) {
            return null;
        }
        return "用户名可用";
    }

    /**
     * 账号注册
     *
     * @author Jason_Jane
     */
    @Override
    public int register(String user_Phone) {
        UserPojo userPojo1 = userMapper.getUserByPhoneInfo(user_Phone);
        if (userPojo1 == null) {
            UserPojo userPojo = new UserPojo(user_Phone);
            int status = userMapper.addUserByPhoneInfo(userPojo);
            if (status == 1) {
                return 2;
            }
            return 1;
        }
        return 0;
    }

    /**
     * 设置用户名和密码
     *
     * @author Jason_Jane
     */
    @Override
    public UserVO setUserNamePasswd(String user_Name, String passwd, UserPojo userPojo) {
        String user_Id = userPojo.getUser_Id();
        String passwdConvertMD5 = MD5Util.MD5Encode(passwd, "utf8");
        int name = userMapper.setUserNameById(user_Name, user_Id);
        int pass = userMapper.setPasswd(passwdConvertMD5, user_Id);
        if (name == 1 && pass == 1) {
            UserVO userVO = userMapper.getUserByName(user_Name);
            return userVO;
        }
        return null;
    }

    /**
     * 上传头像
     *
     * @author Jason_Jane
     */
    @Override
    public UserVO uploadingUserHead(String user_Head, UserPojo userPojo) {
        String user_Name = userPojo.getUser_Name();
        String url = UrlEnum.HEADMAX.vlue + userPojo.getUser_Id() + ".jpg";
        if (!FileUtil.base64ToFile(user_Head, url)) return null;
        if (!FileUtil.headMin(url)) return null;
        String minUrl = UrlEnum.HEADMIN.vlue + userPojo.getUser_Id() + ".jpg";
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userPojo, userVO);
        userVO.setUser_HeadUrl_max(url);
        userVO.setUser_HeadUrl_min(minUrl);
        int status = userMapper.updateUserHead(url, minUrl, user_Name);
        if (status == 1) {
            return userVO;
        }
        return null;
    }

    /**
     * 上传背景图
     *
     * @author Jason_Jane
     */
    @Override
    public UserVO uploadingBackground(String user_Background, UserPojo userPojo) {
        String user_Name = userPojo.getUser_Name();
        String url = UrlEnum.BACKGROUN.vlue + userPojo.getUser_Id() + ".jpg";
        if (!FileUtil.base64ToFile(user_Background, url)) return null;
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userPojo, userVO);
        userVO.setUser_BackgroundUrl(url);
        int status = userMapper.updateBackground(url, user_Name);
        if (status == 1) {
            return userVO;
        }
        return null;
    }

    /**
     * 修改性别
     *
     * @author Jason_Jane
     */
    @Override
    public UserVO updateUserSex(String user_Sex, UserPojo userPojo) {
        String user_Id = userPojo.getUser_Id();
        int sex = userMapper.updateUserSex(user_Sex, user_Id);
        if (sex == 1) {
            String user_Name = userPojo.getUser_Name();
            UserVO userVO = userMapper.getUserByName(user_Name);
            return userVO;
        }
        return null;
    }

    /**
     * 修改密码
     *
     * @author Jason_Jane
     */
    @Override
    public int updatePasswd(String oldPasswd, String newPasswd, UserPojo userPojo) {
        String user_Id = userPojo.getUser_Id();
        PasswdPojo passwdPojo = userMapper.getPasswdById(user_Id);
        String oldPasswdConvertMD5 = MD5Util.MD5Encode(oldPasswd, "utf8");
        String newPasswdConvertMD5 = MD5Util.MD5Encode(newPasswd, "utf8");
        if (oldPasswdConvertMD5.equals(passwdPojo.getPasswd_Normal())) {
            String passwd_Old3 = passwdPojo.getPasswd_Old2();
            String passwd_Old2 = passwdPojo.getPasswd_Old1();
            String passwd_Old1 = oldPasswdConvertMD5;
            String passwd_Normal = newPasswdConvertMD5;
            System.out.println(passwd_Normal);
            System.out.println(passwd_Old1);
            System.out.println(passwd_Old2);
            System.out.println(passwd_Old3);
            int updatePasswd = userMapper.updatePasswd(passwd_Normal, passwd_Old1, passwd_Old2, passwd_Old3, user_Id);
            if (updatePasswd == 1) {
                return 2;
            }
            return 1;
        }
        return 0;
    }

    /**
     * 找回密码
     *
     * @author Jason_Jane
     */
    @Override
    public int retrievePasswd(String newPasswd, String user_Phone) {
        UserPojo userPojo = userMapper.getUserByPhoneInfo(user_Phone);
        if (userPojo != null) {
            String user_Id = userPojo.getUser_Id();
            PasswdPojo passwdPojo = userMapper.getPasswdById(user_Id);
            String passwdConvertMD5 = MD5Util.MD5Encode(newPasswd, "utf8");
            if (!passwdConvertMD5.equals(passwdPojo.getPasswd_Normal())) {
                String passwd_Old3 = passwdPojo.getPasswd_Old2();
                String passwd_Old2 = passwdPojo.getPasswd_Old1();
                String passwd_Old1 = passwdPojo.getPasswd_Normal();
                String passwd_Normal = passwdConvertMD5;
                int status = userMapper.findPasswd(passwd_Normal, passwd_Old1, passwd_Old2, passwd_Old3, user_Id);
                if (status == 1) {
                    return 3;
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }

    /**
     * 修改手机号
     *
     * @author Jason_Jane
     */
    @Override
    public UserVO updateUserPhone(String user_Phone, UserPojo userPojo) {
        String user_Id = userPojo.getUser_Id();
        int status = userMapper.updatePhone(user_Phone, user_Id);
        if (status == 1) {
            String user_Name = userPojo.getUser_Name();
            UserVO userVO = userMapper.getUserByName(user_Name);
            return userVO;
        }
        return null;
    }

    /**
     * 绑定邮箱
     *
     * @author Jason_Jane
     */
    @Override
    public UserVO bindUserEmail(String user_Email, UserPojo userPojo) {
        String user_Id = userPojo.getUser_Id();
        int status = userMapper.bindUserEmail(user_Email, user_Id);
        if (status == 1) {
            String user_Name = userPojo.getUser_Name();
            UserVO userVO = userMapper.getUserByName(user_Name);
            return userVO;
        }
        return null;
    }

    /**
     * 修改邮箱
     *
     * @author Jason_Jane
     */
    @Override
    public UserVO updateUserEmail(String user_Email, UserPojo userPojo) {
        String user_Id = userPojo.getUser_Id();
        int status = userMapper.updateUserEmail(user_Email, user_Id);
        if (status == 1) {
            String user_Name = userPojo.getUser_Name();
            UserVO userVO = userMapper.getUserByName(user_Name);
            return userVO;
        }
        return null;
    }

    /**
     * 修改昵称
     *
     * @author Jason_Jane
     */
    @Override
    public UserVO updateUserNickName(String user_NickName, UserPojo userPojo) {
        int nickName = userMapper.updateUserNickName(user_NickName, userPojo.getUser_Id());
        if (nickName == 1) {
            String user_Name = userPojo.getUser_Name();
            UserVO userVO = userMapper.getUserByName(user_Name);
            return userVO;
        }
        return null;
    }

    @Override
    public void RedundanceLogin(String user_Name) {
        ChannelId channelId = UserChannelRel.get(user_Name);
        if (channelId == null) return;
        Channel channel = ChatHandler.users.find(channelId);
        if (channel == null) return;
        channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(new DataContent(NettyActionEnum.LOGOUT.vule, null, null))));
    }

    @Override
    public List<UserVO> getSelectUser(String user_Name) {
        List<UserPojo> pojoList = userMapper.selectUser("%" + user_Name + "%");
        if (pojoList.isEmpty()) return null;
        List<UserVO> voList = new ArrayList<>();
        for (UserPojo userPojo : pojoList) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(userPojo, userVO);
            voList.add(userVO);
        }
        return voList;
    }
}
