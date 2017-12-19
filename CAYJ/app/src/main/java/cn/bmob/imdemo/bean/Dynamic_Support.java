package cn.bmob.imdemo.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/5/31.
 */

public class Dynamic_Support extends BmobObject{
    String username;       //作者名字
    boolean isFriend;       //是否为好友
   // String obj;
    // 唯一标识符

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }
}
