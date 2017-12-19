package cn.bmob.imdemo.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.InputStream;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ImageLoaderFactory;

/**
 * Created by Administrator on 2017/4/2.
 */

public class DragImageActivity extends Activity {
    private int window_width, window_height;// 控件宽度
    private DragImageView dragImageView;// 自定义控件
    private int state_height;// 状态栏的高度

    private ViewTreeObserver viewTreeObserver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        /** 获取可見区域高度 **/
        WindowManager manager = getWindowManager();
        window_width = manager.getDefaultDisplay().getWidth();
        window_height = manager.getDefaultDisplay().getHeight();
        dragImageView = (DragImageView) findViewById(R.id.div_main);
        String avatar = (String) getBundle().getSerializable("avatar");
        Log.d("666", avatar);
        Bitmap bmp;
       /* if (BitmapUtil.downImage(avatar) != null) {
            bmp = BitmapUtil.downImage(avatar);
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("666", "avatar == null");
        } else {
            bmp = BitmapUtil.ReadBitmapById(this, R.drawable.ic_yy_head,
                    window_width, window_height);
        }*/
        dragImageView.setmActivity(this);//注入Activity
        // Toast.makeText(DragImageActivity.this, avatar+"**", Toast.LENGTH_SHORT).show();
        ImageLoader.getInstance().displayImage(avatar,dragImageView);
       // ImageLoaderFactory.getLoader().loadAvator(dragImageView,avatar!= null ? avatar : null,R.mipmap.head);
        // Bitmap bmp = BitmapUtil.ReadBitmapById(this, R.drawable.ic_yy_head,window_width, window_height);
        // 设置图片
        // dragImageView.setImageBitmap(bmp);
        /** 测量状态栏高度 **/
        viewTreeObserver = dragImageView.getViewTreeObserver();
        viewTreeObserver
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        if (state_height == 0) {
                            // 获取状况栏高度
                            Rect frame = new Rect();
                            getWindow().getDecorView()
                                    .getWindowVisibleDisplayFrame(frame);
                            state_height = frame.top;
                            dragImageView.setScreen_H(window_height - state_height);
                            dragImageView.setScreen_W(window_width);
                        }
                        dragImageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });

    }

    public Bundle getBundle() {
        if (getIntent() != null && getIntent().hasExtra(getPackageName()))
            return getIntent().getBundleExtra(getPackageName());
        else
            return null;
    }

    /**
     * 读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap ReadBitmapById(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
}
