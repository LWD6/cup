package top.mnilsy.cup.VO;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mnilsy on 19-4-21 下午3:21.
 */
public class TweetVO {
    private String user_HeadUrl_min;
    private String user_Name;
    private String tweet_Id;
    private String tweet_Time;
    private String tweet_Text;
    private int tweet_LikeCount;
    private int tweet_DiscussCount;
    private List<String> accessory;

    public TweetVO() {
    }

    public TweetVO(String user_HeadUrl_min, String user_Name, String tweet_Id, String tweet_Time, String tweet_Text, int tweet_LikeCount, int tweet_DiscussCount, List<String> accessory) {
        this.user_HeadUrl_min = user_HeadUrl_min;
        this.user_Name = user_Name;
        this.tweet_Id = tweet_Id;
        this.tweet_Time = tweet_Time;
        this.tweet_Text = tweet_Text;
        this.tweet_LikeCount = tweet_LikeCount;
        this.tweet_DiscussCount = tweet_DiscussCount;
        this.accessory = accessory;
    }

    public String getUser_HeadUrl_min() {
        return user_HeadUrl_min;
    }

    public void setUser_HeadUrl_min(String user_HeadUrl_min) {
        this.user_HeadUrl_min = user_HeadUrl_min;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public void setUser_Name(String user_Name) {
        this.user_Name = user_Name;
    }

    public String getTweet_Id() {
        return tweet_Id;
    }

    public void setTweet_Id(String tweet_Id) {
        this.tweet_Id = tweet_Id;
    }

    public String getTweet_Time() {
        return tweet_Time;
    }

    public void setTweet_Time(String tweet_Time) {
        this.tweet_Time = tweet_Time;
    }

    public String getTweet_Text() {
        return tweet_Text;
    }

    public void setTweet_Text(String tweet_Text) {
        this.tweet_Text = tweet_Text;
    }

    public int getTweet_LikeCount() {
        return tweet_LikeCount;
    }

    public void setTweet_LikeCount(int tweet_LikeCount) {
        this.tweet_LikeCount = tweet_LikeCount;
    }

    public int getTweet_DiscussCount() {
        return tweet_DiscussCount;
    }

    public void setTweet_DiscussCount(int tweet_DiscussCount) {
        this.tweet_DiscussCount = tweet_DiscussCount;
    }

    public List<String> getAccessory() {
        return accessory;
    }

    public void setAccessory(List<String> accessory) {
        this.accessory = accessory;
    }

    @Override
    public String toString() {
        return "TweetVO{" +
                "user_HeadUrl_min='" + user_HeadUrl_min + '\'' +
                ", user_Name='" + user_Name + '\'' +
                ", tweet_Id='" + tweet_Id + '\'' +
                ", tweet_Time='" + tweet_Time + '\'' +
                ", tweet_Text='" + tweet_Text + '\'' +
                ", tweet_LikeCount=" + tweet_LikeCount +
                ", tweet_DiscussCount=" + tweet_DiscussCount +
                ", accessory=" + Arrays.toString(accessory.toArray()) +
                '}';
    }
}
