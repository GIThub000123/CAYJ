package cn.bmob.imdemo.bean;


/**
 * Created by Administrator on 2017/3/28.
 */

public class PetsFamilyListBean {
    public String avatar;
    public String time;

    public String name;
    public String content;
    public String contentImages;
    public int good;
    public int translate;
    public int comment;

    public String  ObjcetId;

    public String getObjcetId() {
        return ObjcetId;
    }

    public void setObjcetId(String objcetId) {
        ObjcetId = objcetId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentImages() {
        return contentImages;
    }

    public void setContentImages(String contentImages) {
        this.contentImages = contentImages;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getTranslate() {
        return translate;
    }

    public void setTranslate(int translate) {
        this.translate = translate;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }
}
