package cn.bmob.imdemo.ui;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/3/29.
 */

public class PassWordChange extends BmobObject {
    private String name;
    private String pws;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPws() {
        return pws;
    }

    public void setPws(String pws) {
        this.pws = pws;
    }
}
