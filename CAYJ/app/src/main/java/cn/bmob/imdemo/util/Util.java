package cn.bmob.imdemo.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/** 判断sd卡是否可用&&返回地址
 * @author smile
 * @project Util
 * @date 2016-03-01-14:55
 */
public class Util {

    public static boolean checkSdCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }
    public static File filePath(){
        File path = Environment.getExternalStorageDirectory();
        return path;
    }
}
