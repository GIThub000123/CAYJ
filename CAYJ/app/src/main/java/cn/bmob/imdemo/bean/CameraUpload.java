package cn.bmob.imdemo.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/4/3.
 */

public class CameraUpload  extends BmobObject {
    String avatar;
    BmobFile icon;
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
