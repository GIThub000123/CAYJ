package cn.bmob.imdemo.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;

import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;


import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ImageLoaderFactory;
import cn.bmob.imdemo.base.ParentWithNaviActivity;
import cn.bmob.imdemo.base.ParentWithNaviFragment;
import cn.bmob.imdemo.bean.petsbean.Dynamic;
import cn.bmob.imdemo.bean.PetsFamilyListBean;
import cn.bmob.imdemo.ui.add.AddDynamic;
import cn.bmob.imdemo.ui.MainActivity;
import cn.bmob.imdemo.util.GlideUtils;
import cn.bmob.imdemo.view.RefreshListView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2017/3/27.
 */

public class PetsFamilyFragemnt extends ParentWithNaviFragment {

    private RefreshListView lv_petsfamily_listview;
    private PetsFamilyAdapter petsFamilyAdapter;
    private List<PetsFamilyListBean> listPetsFamilyBeen;
    private List<Dynamic> listDynamic;

    private PetsFamilyListBean petsFamilyListBean;
    public View view;

    private PetsFamilyAdapter.ViewHolder viewHolder;

    private Dynamic dynamic;
    private Dynamic dynamicupdate;

    private int position;


    @Override
    protected String title() {
        return "宠物之家";
    }

    @Override
    public Object right() {
        return R.drawable.base_action_bar_add_bg_selector;
    }

    @Override
    public ParentWithNaviActivity.ToolBarListener setToolBarListener() {
        return new ParentWithNaviActivity.ToolBarListener() {
            @Override
            public void clickLeft() {

            }

            @Override
            public void clickRight() {
                startActivity(AddDynamic.class, null);
            }
        };
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_petsfamily, container, false);
        initNaviView();
        getDataFromServer();
        lv_petsfamily_listview = (RefreshListView) rootView.findViewById(R.id.lv_fragment_petsfamily);

        getDataFromServer();

        lv_petsfamily_listview.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }

            @Override
            public void onLoadMore() {
                //设置加载更多

            }
        });


        listPetsFamilyBeen = new ArrayList<>();

        petsFamilyAdapter = new PetsFamilyAdapter();
        lv_petsfamily_listview.setAdapter(petsFamilyAdapter);

        return rootView;
    }


    class PetsFamilyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listPetsFamilyBeen.size();
        }

        @Override
        public Object getItem(int i) {
            return listPetsFamilyBeen.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            viewHolder = new ViewHolder();
            position = i;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.fragment_pf_layout, null);
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
            final PetsFamilyListBean petsFamilyListBeans = listPetsFamilyBeen.get(i);

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
            // viewHolder.contentImages.setBackgroundResource(petsFamilyListBeans.getContentImages());

            GlideUtils.GlideCircleAvatar(mActivity,petsFamilyListBeans.getAvatar(),viewHolder.avatar);

            viewHolder.good.setText(petsFamilyListBeans.getGood() + "");
            viewHolder.translate.setText(petsFamilyListBeans.getTranslate() + "");
            viewHolder.comment.setText(petsFamilyListBeans.getComment() + "");

            viewHolder.good_pf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String translatenum = (String) viewHolder.good.getText();
                    int tn = Integer.parseInt(translatenum);
                    String obj = petsFamilyListBeans.getObjcetId();
                    dynamicupdate = new Dynamic();
                    final int tn2 = tn + 1;
                    dynamicupdate.increment("good");
                    dynamicupdate.update(mActivity, obj,
                            new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(mActivity, "translatesave成功" + petsFamilyListBeans.getGood() + "", Toast.LENGTH_LONG).show();
                                    viewHolder.good.setText(tn2 + "");
                                    petsFamilyAdapter.notifyDataSetChanged();
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
        dynamic = new Dynamic();
        BmobQuery<Dynamic> bmobQuery = new BmobQuery<>();
        bmobQuery.setLimit(10);
        bmobQuery.order("createDAt");
        bmobQuery.findObjects(mActivity, new FindListener<Dynamic>() {
            @Override
            public void onSuccess(List<Dynamic> list) {
                listDynamic = new ArrayList<>();
                listDynamic.addAll(list);

                lv_petsfamily_listview.onRefreshComplete(true);
                // lv_petsfamily_listview.isLoadingMore = true;

             //   Toast.makeText(mActivity, "query成功" + list.get(0).getAvatar(), Toast.LENGTH_LONG).show();
                Log.d("query", list.toString() + "");

                Collections.sort(list, new Comparator<Dynamic>() {
                    @Override
                    public int compare(Dynamic o1, Dynamic o2) {
                        return o1.getTime().compareTo(o2.getTime());
                    }
                });

                listPetsFamilyBeen.clear();
                for (int i = list.size() - 1; i >= 0; i--) {
                    petsFamilyListBean = new PetsFamilyListBean();
                    //头像，时间，名字
                    String avatar = list.get(i).getAvatar();
                    petsFamilyListBean.setAvatar(avatar);
                    String name = list.get(i).getUsername();
                    petsFamilyListBean.setName(name);
                    String time = list.get(i).getTime();
                    petsFamilyListBean.setTime(time);
                    //内容，图片
                    String content = list.get(i).getContent();
                    petsFamilyListBean.setContent(content);
                    String contentimage = list.get(i).getContentimage();
                    petsFamilyListBean.setContentImages(contentimage);
                    //赞评转
                    int good = list.get(i).getGood();
                    petsFamilyListBean.setGood(good);
                    int translate = list.get(i).getTranslate();
                    petsFamilyListBean.setTranslate(translate);
                    int comment = list.get(i).getComment();
                    petsFamilyListBean.setComment(comment);
                    petsFamilyListBean.setObjcetId(list.get(i).getObjectId());

                    listPetsFamilyBeen.add(petsFamilyListBean);
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(mActivity, "query失败", Toast.LENGTH_LONG).show();
                lv_petsfamily_listview.onRefreshComplete(false);

                // 请求不成功跳转到2页面
                MainActivity mAc = (MainActivity) mActivity;
                mAc.onTabIndex(1);
                isShowSelect = true;

            }
        });
    }
}
