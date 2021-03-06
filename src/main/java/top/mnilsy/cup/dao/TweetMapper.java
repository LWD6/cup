package top.mnilsy.cup.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.mnilsy.cup.VO.TweetVO;
import top.mnilsy.cup.pojo.TweetPojo;

import java.util.List;

/**
 * Created by mnilsy on 19-4-25 下午1:18.
 */
@Repository("tweetMapper")
@Mapper
public interface TweetMapper {
    /**
     * 获取一条可显示推文
     *
     * @param tweet_Id 推文id
     * @return 推文的VO包
     * @author mnilsy
     */
    @Select("select u.user_HeadUrl_min," +
            "u.user_Name," +
            "u.user_NickName," +
            "t.tweet_Id," +
            "t.tweet_Time," +
            "t.tweet_Text," +
            "t.tweet_Type," +
            "(select count(*) from `like` where tweet_Id = #{tweet_Id}) as tweet_LikeCount," +
            "(select count(*) from discuss where tweet_Id = #{tweet_Id}) as tweet_DiscussCount " +
            "from user u join tweet t on u.user_Id = t.user_Id where t.tweet_Id=#{tweet_Id}")
    @Results({
            @Result(property = "accessory", column = "tweet_Id",
                    many = @Many(select = "top.mnilsy.cup.dao.AccessoryMapper.getAccessoryListUrl")
            ),
            @Result(property = "accessory_Id", column = "tweet_Id",
                    many = @Many(select = "top.mnilsy.cup.dao.AccessoryMapper.getAccessory_Id")
            )
    })
    TweetVO getTweetVO(String tweet_Id);


    /**
     * 获取一组被关注者的可显示推文
     *
     * @param user_Id 用户id
     * @param count 获取次数
     * @return 推文的VO包
     * @author mnilsy
     */
    @Select("select u.user_HeadUrl_min," +
            "u.user_Name," +
            "u.user_NickName," +
            "t.tweet_Id," +
            "t.tweet_Time," +
            "t.tweet_Text," +
            "t.tweet_Type," +
            "(select count(*) from `like` where tweet_Id = #{tweet_Id}) as tweet_LikeCount," +
            "(select count(*) from discuss where tweet_Id = #{tweet_Id}) as tweet_DiscussCount " +
            "from user u join tweet t on u.user_Id = t.user_Id where t.user_Id int " +
            "(select secondParty_User_Id from fans where firstParty_User_Id=#{user_Id}) order by desc limit #{count},10")
    @Results({
            @Result(property = "accessory", column = "tweet_Id",
                    many = @Many(select = "top.mnilsy.cup.dao.AccessoryMapper.getAccessoryListUrl")
            ),
            @Result(property = "accessory_Id", column = "tweet_Id",
                    many = @Many(select = "top.mnilsy.cup.dao.AccessoryMapper.getAccessory_Id")
            )
    })
    List<TweetVO> getTweetVO_secondParty_User_Id(String user_Id,int count);

    /**
     * 增加一条推文记录
     *
     * @param tweetPojo 推文pojo包
     * @return 增加条数
     * @author mnilsy
     */
    @Insert("insert into tweet(tweet_Id,tweet_Type,tweet_Text,user_Id) values (#{tweet_Id},#{tweet_Type},#{tweet_Text},#{user_Id})")
    int insertTweet(TweetPojo tweetPojo);

    /**
     * 更改推文记录的状态tweet_Condition
     *
     * @param tweet_Id 推文id
     * @param user_Id  用户id
     * @return 改变的条数
     * @author mnilsy
     */
    @Update("update tweet set tweet_Condition='1' where tweet_Id=#{tweet_Id} and user_Id=#{user_Id}")
    int updateCondition(@Param("tweet_Id") String tweet_Id, @Param("user_Id") String user_Id);

}
