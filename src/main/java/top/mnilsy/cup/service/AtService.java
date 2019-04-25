package top.mnilsy.cup.service;


import top.mnilsy.cup.BO.AtBO;
import top.mnilsy.cup.pojo.AtPojo;

import java.util.List;

/**
 * Created by mnilsy on 19-4-25 上午1:20.
 */
public interface AtService {
    /**
     * 推文中@用户
     *
     * @param at_From_Id 艾特的推文id
     * @param user_Id    艾特的目标用户名
     * @return 是否艾特成功
     */
    boolean tweetAt(String at_From_Id, String user_Id);

    /**
     * 推文评论通知
     *
     * @param at_From_Id 推文的id
     * @return
     */
    boolean discussAt(String at_From_Id);

    /**
     * 评论回复通知
     *
     * @param at_From_Id 评论的id
     * @param user_Id    通知目标用户的id
     * @return
     */
    boolean writebackAt(String at_From_Id, String user_Id);

    /**
     * 公告通知
     *
     * @param at_From_Id 公告id
     * @return
     */
    boolean proclamationAt(String at_From_Id);

    /**
     * 点赞通知
     *
     * @param at_From_Id 推文id
     * @param user_Id    点赞的用户id
     * @return
     */
    boolean likeAt(String at_From_Id, String user_Id);

    /**
     * 艾特签收
     *
     * @param at_Id 艾特的id
     * @return 是否签收成功
     */
    boolean signfor(String at_Id);

    /**
     * 获取用户还没签收的艾特
     *
     * @param user_Name 用户名
     * @return 该用户所有还没签收的艾特
     */
    List<AtBO> getNotSignfor(String user_Name);

    /**
     * 发送艾特给客户端
     *
     * @param atBO 艾特包
     * @return
     */
    boolean sendAt(AtBO atBO);

    /**
     * 根据用户名将发送艾特给客户端
     *
     * @param atBO      艾特包
     * @param user_Name 用户名
     * @return
     */
    boolean sendAt(AtBO atBO, String user_Name);
}
