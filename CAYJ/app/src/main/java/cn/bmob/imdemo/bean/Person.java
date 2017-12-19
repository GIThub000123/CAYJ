package cn.bmob.imdemo.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/2/11.
 */

public class Person extends BmobObject {
    String avatar;
    String username;
    BmobFile icon;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setIcon(BmobFile icon) {
        this.icon = icon;
    }

    public BmobFile getIcon() {

        return icon;
    }
}
