package cn.bmob.imdemo.ui.imple;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ImageLoaderFactory;
import cn.bmob.imdemo.bean.ConsultSeekHomeListBean;
import cn.bmob.imdemo.bean.petsbean.SeekHomeDynamic;
import cn.bmob.imdemo.util.GlideUtils;
import cn.bmob.imdemo.view.RefreshListView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2017/3/28.
 */

public class SeekHome extends BaseImple {

    private Activity mActivity;
    public View rootView;

    private RefreshListView lv_SeekHome;
    private SeekHomeAdapter seekHomeAdapter;
    private List<ConsultSeekHomeListBean> listSeekHomeBeen;
    private ConsultSeekHomeListBean consultSeekHomeListBean;


    private SeekHomeAdapter.ViewHolder viewHolder;

    private List<SeekHomeDynamic> seekHomeDynamics;

    private SeekHomeDynamic seekHomeDynamic;
    private SeekHomeDynamic updateseekHomeDynamic;


    public SeekHome(Activity activity) {
        mActivity = activity;

        rootView = LinearLayout.inflate(mActivity, R.layout.consult_seekhome, null);
        lv_SeekHome = (RefreshListView) rootView.findViewById(R.id.lv_consult_seekhome);
        listSeekHomeBeen = new ArrayList<>();

        lv_SeekHome.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }

            @Override
            public void onLoadMore() {
                //设置加载更多
            }
        });
        getDataFromServer();
        seekHomeAdapter = new SeekHomeAdapter();
        lv_SeekHome.setAdapter(seekHomeAdapter);
    }


    class SeekHomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listSeekHomeBeen.size();
        }

        @Override
        public Object getItem(int i) {
            return listSeekHomeBeen.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            viewHolder = new ViewHolder();
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.fragment_pf_layout, null);
                viewHolder.avatar = (ImageView) convertView.findViewById(R.id.iv_avatar_pf);
                viewHolder.time = (TextView) convertView.findViewById(R.id.tv_time_pf);
                viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name_pf);
                viewHolder.content = (TextView) convertView.findViewById(R.id.tv_content_pf);
                viewHolder.contentImages = (ImageView) convertView.findViewById(R.id.iv_content_pf);
                viewHolder.good = (TextView) convertView.findViewById(R.id.tv_good_pf);
                viewHolder.good_pf = (ImageView) convertView.findViewById(R.id.good_pf);
                viewHolder.translate = (TextView) convertView.findViewById(R.id.tv_translates_pf);
                viewHolder.comment = (TextView) convertView.findViewById(R.id.tv_comment_pf);
                viewHolder.translates_pf = (ImageView) convertView.findViewById(R.id.translates_pf);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final ConsultSeekHomeListBean petsFamilyListBeans = listSeekHomeBeen.get(i);

            if (TextUtils.isEmpty(petsFamilyListBeans.getContentImages())) {
                viewHolder.contentImages.setVisibility(View.GONE);
            } else {
                viewHolder.contentImages.setVisibility(View.VISIBLE);

                ImageLoaderFactory.getLoader().loadAvator(viewHolder.contentImages,
                        petsFamilyListBeans.getContentImages() != null ? petsFamilyListBeans.getContentImages() : null,
                        R.mipmap.head);
            }

         /*  Bitmap bitmap = ImageUtil.getHttpBitmap(petsFamilyListBeans.getAvatar());
            viewHolder.avatar.setImageBitmap(bitmap);*/

            viewHolder.name.setText(petsFamilyListBeans.getName());
            viewHolder.time.setText(petsFamilyListBeans.getTime());
            viewHolder.content.setText(petsFamilyListBeans.getContent());

            GlideUtils.GlideCircleAvatar(mActivity,petsFamilyListBeans.getAvatar(),viewHolder.avatar);

            viewHolder.good.setText(petsFamilyListBeans.getGood() + "");
            viewHolder.translate.setText(petsFamilyListBeans.getTranslate() + "");
            viewHolder.comment.setText(petsFamilyListBeans.getComment() + "");

            viewHolder.good_pf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*String translatenum = (String) viewHolder.good.getText();
                    int tn = Integer.parseInt(translatenum);*/
                    String obj = petsFamilyListBeans.getObjcetId();
                    updateseekHomeDynamic = new SeekHomeDynamic();
                  //  final int tn2 = tn + 1;
                    updateseekHomeDynamic.increment("good");
                    updateseekHomeDynamic.update(mActivity, obj,
                            new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(mActivity, "translatesave成功" + petsFamilyListBeans.getGood() + "", Toast.LENGTH_LONG).show();
                                   // viewHolder.good.setText(tn2 + "");
                                    seekHomeAdapter.notifyDataSetChanged();
                                }
                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(mActivity, "translatesave失败", Toast.LENGTH_LONG).show();

                                }
                            });
                    // viewHolder.translate.setText("5");
                    // petsFamilyAdapter.notifyDataSetChanged();
                    /*notifyDataSetChanged();
                    notifyDataSetInvalidated();*/
                }
            });

            return convertView;
        }

        class ViewHolder {
            public ImageView avatar;
            public TextView name;
            public TextView time;
            public TextView content;
            public ImageView contentImages;
            public TextView good;
            public TextView translate;
            public TextView comment;
            public ImageView translates_pf;
            public ImageView good_pf;
        }
    }


    private void getDataFromServer() {

        seekHomeDynamic = new SeekHomeDynamic();
        BmobQuery<SeekHomeDynamic> bmobQuery = new BmobQuery<>();
        bmobQuery.setLimit(10);
        bmobQuery.order("createDAt");
        bmobQuery.findObjects(mActivity, new FindListener<SeekHomeDynamic>() {
            @Override
            public void onSuccess(List<SeekHomeDynamic> list) {
                seekHomeDynamics = new ArrayList<>();
                seekHomeDynamics.addAll(list);

                lv_SeekHome.onRefreshComplete(true);
                // lv_petsfamily_listview.isLoadingMore = true;
//                Toast.makeText(mActivity, "query成功" + list.get(0).getAvatar(), Toast.LENGTH_LONG).show();
                Log.d("query", list.toString() + "");
                Collections.sort(list, new Comparator<SeekHomeDynamic>() {
                    @Override
                    public int compare(SeekHomeDynamic o1, SeekHomeDynamic o2) {
                        return o1.getTime().compareTo(o2.getTime());
                    }
                });

                listSeekHomeBeen.clear();
                for (int i = list.size() - 1; i >= 0; i--) {
                    consultSeekHomeListBean = new ConsultSeekHomeListBean();
                    //头像，时间，名字
                    String avatar = list.get(i).getAvatar();
                    consultSeekHomeListBean.setAvatar(avatar);
                    String name = list.get(i).getUsername();
                    consultSeekHomeListBean.setName(name);
                    String time = list.get(i).getTime();
                    consultSeekHomeListBean.setTime(time);
                    //内容，图片
                    String content = list.get(i).getContent();
                    consultSeekHomeListBean.setContent(content);
                    String contentimage = list.get(i).getContentimage();
                    consultSeekHomeListBean.setContentImages(contentimage);
                    //赞评转
                    int good = list.get(i).getGood();
                    consultSeekHomeListBean.setGood(good);
                    int translate = list.get(i).getTranslate();
                    consultSeekHomeListBean.setTranslate(translate);
                    int comment = list.get(i).getComment();
                    consultSeekHomeListBean.setComment(comment);
                    consultSeekHomeListBean.setObjcetId(list.get(i).getObjectId());

                    listSeekHomeBeen.add(consultSeekHomeListBean);

                    lv_SeekHome.onRefreshComplete(true);
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(mActivity, "query失败", Toast.LENGTH_LONG).show();

                lv_SeekHome.onRefreshComplete(false); }
        });
    }
}
