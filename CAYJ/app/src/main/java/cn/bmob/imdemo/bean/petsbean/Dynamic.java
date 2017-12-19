package cn.bmob.imdemo.bean.petsbean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/5/31.
 */

public class Dynamic extends BmobObject {

   // String obj;
    //唯一标识符
    String avatar;          //作者头像
    String username;       //作者名字
    String time;          //时间
    String contentimage;  //内容图片
    String content;    //内容文字
    String location;     //地点
    BmobFile icon;       //上传的图片
    Integer comment;         //点赞评论转发
    Integer translate;
    Integer good;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContentimage() {
        return contentimage;
    }

    public void setContentimage(String contentimage) {
        this.contentimage = contentimage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String contenttet) {
        this.content = contenttet;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BmobFile getIcon() {
        return icon;
    }

    public void setIcon(BmobFile icon) {
        this.icon = icon;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getTranslate() {
        return translate;
    }

    public void setTranslate(int translate) {
        this.translate = translate;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }
}
