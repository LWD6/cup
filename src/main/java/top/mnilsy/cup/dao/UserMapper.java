package top.mnilsy.cup.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import top.mnilsy.cup.VO.UserVO;
import top.mnilsy.cup.pojo.PasswdPojo;
import top.mnilsy.cup.pojo.UserPojo;


/**
 * Created by mnilsy on 19-4-20 上午12:22.
 */
@Repository("userMapper")
@Mapper
public interface UserMapper {
    /**
     * 根据user_Id获取用户基本信息
     *
     * @param user_Id 用户账号id
     * @return 用户基本资料
     */
    @Select("select * from user where user_Id = #{user_Id}")
    UserVO getUserByIdInfo(String user_Id);

    /**
     * 根据user_Name获取用户基本信息
     *
     * @param user_Name 用户名
     * @return 用户基本资料
     */
    @Select("select * from user where user_Name = #{user_Name }")
    UserVO getUserByNameInfo(String user_Name);

    /**
     * 根据user_Name\\user_Phone\\user_email查询用户信息
     *
     * @param user 用户名||用户手机号码||用户电子邮箱
     * @return 用户基本资料
     */
    @Select("select user_Id from user where user_Name = #{user} or user_Phone = #{user} or user_email = #{user}")
    UserPojo getUserByNamePhoneEmail (String user);

    /**
     * 根据user_Id获取用户密码
     *
     * @param user_Id 用户账号id
     * @return passwd_Normal
     */
    @Select("select passwd_Normal from passwd where user_Id = #{user_Id}")
    PasswdPojo getPasswdById (String user_Id);

    /**
     * 根据user_Name查询是否重名
     *
     * @param user_Name 用户名
     * @return 用户名
     */
    @Select("select user_Name from user where user_Name = #{user_Name}")
    UserVO getUserNameByUserName (String user_Name);


}
