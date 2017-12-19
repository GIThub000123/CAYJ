package cn.bmob.imdemo.ui;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;


/**
 * Created by Administrator on 2017/2/26.
 */
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.bmob.imdemo.bean.User;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;


public class copyTry extends RecyclerView.ViewHolder{

    public int layoutId;
    public final SparseArray<View> mViews;

    public copyTry(View itemView, int layoutId, SparseArray<View> mViews) {
        super(itemView);
        this.layoutId = layoutId;
        this.mViews = mViews;
    }

    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if(view == null){

            view = itemView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }
}
