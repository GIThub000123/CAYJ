package cn.bmob.imdemo.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cn.bmob.imdemo.R;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2017/6/2.
 */

public class GlideUtils {
    public static void GlideCircleAvatar(Context context,String url, ImageView iv){
        Glide.with(context)
                .load(url)
                .override(50,50)
                .bitmapTransform(new CropCircleTransformation(context))
                .error(R.mipmap.head)
                .into(iv);
    }
}
