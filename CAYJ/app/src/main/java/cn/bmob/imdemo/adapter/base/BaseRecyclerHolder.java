package cn.bmob.imdemo.adapter.base;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bmob.imdemo.base.ImageLoaderFactory;

/**  ：设置View与Image ViewHolder实现数据绑定  ViewHolder这个类有点特殊
 * 与BaseRecyclerAdapter一起使用
 *
 */
public class BaseRecyclerHolder extends RecyclerView.ViewHolder {
    //android系统建议我们使用SparseArray<E>来代替HashMap<Integer,E>,这样会带来更好的性能
    private final SparseArray<View> mViews;
    public  int layoutId;

    public BaseRecyclerHolder(int layoutId,View itemView) {
        super(itemView); //调用父类方法
        this.layoutId =layoutId; //自己独立的View -- R.layout.XXX
        this.mViews = new SparseArray<>(8);
    }

    public SparseArray<View> getAllView() {
        return mViews;
    }

    /** 描述：返回任意类型的view
     * @param viewId
     * @return
     */
    protected <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * @param viewId
     * @param text
     * @return
     */
    public BaseRecyclerHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 设置Enabled
     * @param viewId
     * @param enable
     * @return
     */
    public BaseRecyclerHolder setEnabled(int viewId,boolean enable){
        View v = getView(viewId);
        v.setEnabled(enable);
        return this;
    }

    /**
     * 点击事件
     * @param viewId
     * @param listener
     * @return
     */
    public BaseRecyclerHolder setOnClickListener(int viewId,
                                                 View.OnClickListener listener){
        View v = getView(viewId);
        v.setOnClickListener(listener);
        return this;
    }

    /**
     * @param viewId
     * @param visibility
     * @return
     */
    public BaseRecyclerHolder setVisible(int viewId,int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    /**
     * @param viewId
     * @param drawableId
     * @return
     */
    public BaseRecyclerHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    /**
     * @param viewId
     * @param bm
     * @return
     */
    public BaseRecyclerHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**描述：返回Image类型的数据 avatar头像
     * @param avatar
     * @param defaultRes
     * @param viewId
     * @return
     */
    public BaseRecyclerHolder setImageView(String avatar,
                                           int defaultRes,
                                           int viewId) {
        ImageView iv = getView(viewId);
        ImageLoaderFactory.getLoader().loadAvator(iv,avatar, defaultRes);
        return this;
    }

}
