package cn.bmob.imdemo.ui.imple;

import android.app.Activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ImageLoaderFactory;
import cn.bmob.imdemo.bean.ConsultPetsMaintainListBean;
import cn.bmob.imdemo.bean.ConsultSeekHomeListBean;
import cn.bmob.imdemo.bean.PetsFamilyListBean;
import cn.bmob.imdemo.bean.petsbean.PetsMaintainDynamic;
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

public class PetsMaintain extends BaseImple {

    private Activity mActivity;
    public View rootView;

    private RefreshListView lv_PetsMain;
    private PetsMaintainAdapter petsMaintainAdapter;
    private List<ConsultPetsMaintainListBean> listPetsMaintainBeen;
    private ConsultPetsMaintainListBean consultPetsMaintainListBean;

    private PetsMaintainAdapter.ViewHolder viewHolder;
    private List<PetsMaintainDynamic> petsMaintainDynamics;


    private PetsMaintainDynamic petsMaintainDynamic;

    private PetsMaintainDynamic updatepetsMaintainDynamic;
    //HeaderView


    private GridView gvProducts;

    private PetsMaintainGridViewAdapter petsMaintainGridViewAdapter;

    private PetsMaintainGridViewAdapter.GridViewViewHolder gridViewViewHolder;

    private List<ConsultPetsMaintainListBean> listPetsMaintainBeen2;
    private ConsultPetsMaintainListBean consultPetsMaintainListBean2;


    public PetsMaintain(Activity activity) {
        mActivity = activity;

        rootView = LinearLayout.inflate(mActivity, R.layout.consult_petsmaintain, null);
        lv_PetsMain = (RefreshListView) rootView.findViewById(R.id.lv_consult_petsmaintain);

        petsMaintainDynamics = new ArrayList<>();

        listPetsMaintainBeen = new ArrayList<>();

        listPetsMaintainBeen2 = new ArrayList<>();

      //  View view = LinearLayout.inflate(mActivity, R.layout.inflate_petsmaintain, null);
        View v = LayoutInflater.from(mActivity).inflate(R.layout.inflate_petsmaintain,lv_PetsMain,false);

        gvProducts = (GridView) v.findViewById(R.id.gv_products);

        petsMaintainGridViewAdapter = new PetsMaintainGridViewAdapter();

      //  gvProducts.setAdapter(petsMaintainGridViewAdapter);

        lv_PetsMain.addHeaderView(v);
        lv_PetsMain.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
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

        petsMaintainAdapter = new PetsMaintainAdapter();
        lv_PetsMain.setAdapter(petsMaintainAdapter);
    }

    class PetsMaintainGridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listPetsMaintainBeen.size();
        }

        @Override
        public Object getItem(int i) {
            return listPetsMaintainBeen.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            gridViewViewHolder = new GridViewViewHolder();
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.fragment_pf_layout4, null);

                gridViewViewHolder.content = (TextView) convertView.findViewById(R.id.tv_content_pf);
                gridViewViewHolder.contentImages = (ImageView) convertView.findViewById(R.id.iv_content_pf);
                gridViewViewHolder.tvValue = (TextView) convertView.findViewById(R.id.tv_value);
                gridViewViewHolder.tvPassValue = (TextView) convertView.findViewById(R.id.tv_pass_value);

                gridViewViewHolder.good = (TextView) convertView.findViewById(R.id.tv_good_pf);
                gridViewViewHolder.good_pf = (ImageView) convertView.findViewById(R.id.good_pf);
                gridViewViewHolder.comment = (TextView) convertView.findViewById(R.id.tv_comment_pf);
                convertView.setTag(gridViewViewHolder);
            } else {
                gridViewViewHolder = (GridViewViewHolder) convertView.getTag();
            }

            final ConsultPetsMaintainListBean petsFamilyListBeans = listPetsMaintainBeen.get(i);

            if (!TextUtils.isEmpty(petsFamilyListBeans.getContentImages())) {
                ImageLoaderFactory.getLoader().loadAvator(gridViewViewHolder.contentImages,
                        petsFamilyListBeans.getContentImages() != null ? petsFamilyListBeans.getContentImages() : null,
                        R.mipmap.head);
            }
            gridViewViewHolder.content.setText(petsFamilyListBeans.getContent());
            gridViewViewHolder.tvValue.setText("$ "+petsFamilyListBeans.getValue());
            gridViewViewHolder.tvPassValue.setText("$ "+petsFamilyListBeans.getPassvalue());
            gridViewViewHolder.good.setText(petsFamilyListBeans.getGood() + "");
            gridViewViewHolder.comment.setText(petsFamilyListBeans.getComment() + "");

            gridViewViewHolder.good_pf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String obj = petsFamilyListBeans.getObjcetId();
                    updatepetsMaintainDynamic = new PetsMaintainDynamic();
                    //  final int tn2 = tn + 1;
                    updatepetsMaintainDynamic.increment("good");
                    updatepetsMaintainDynamic.update(mActivity, obj,
                            new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(mActivity, "translatesave成功" + petsFamilyListBeans.getGood() + "", Toast.LENGTH_LONG).show();
                                    // viewHolder.good.setText(tn2 + "");
                                    petsMaintainGridViewAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(mActivity, "translatesave失败", Toast.LENGTH_LONG).show();

                                }
                            });
                }
            });

            return convertView;
        }

        class GridViewViewHolder {

            public TextView content;
            public ImageView contentImages;
            public TextView tvValue;
            public TextView tvPassValue;

            public TextView comment;
            public TextView good;
            public ImageView good_pf;
            public ImageView comment_pf;
        }
    }


    class PetsMaintainAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listPetsMaintainBeen.size();
        }

        @Override
        public Object getItem(int i) {
            return listPetsMaintainBeen.get(i);
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
                viewHolder.comment = (TextView) convertView.findViewById(R.id.tv_comment_pf);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final ConsultPetsMaintainListBean petsFamilyListBeans = listPetsMaintainBeen.get(i);

            if (TextUtils.isEmpty(petsFamilyListBeans.getContentImages())) {
                viewHolder.contentImages.setVisibility(View.GONE);
            } else {
                viewHolder.contentImages.setVisibility(View.VISIBLE);

                ImageLoaderFactory.getLoader().loadAvator(viewHolder.contentImages,
                        petsFamilyListBeans.getContentImages() != null ? petsFamilyListBeans.getContentImages() : null,
                        R.mipmap.head);
            }

            viewHolder.name.setText(petsFamilyListBeans.getName());
            viewHolder.time.setText(petsFamilyListBeans.getTime());
            viewHolder.content.setText(petsFamilyListBeans.getContent());

            GlideUtils.GlideCircleAvatar(mActivity, petsFamilyListBeans.getAvatar(), viewHolder.avatar);


            viewHolder.good.setText(petsFamilyListBeans.getGood() + "");
            viewHolder.comment.setText(petsFamilyListBeans.getComment() + "");

            viewHolder.good_pf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*String translatenum = (String) viewHolder.good.getText();
                    int tn = Integer.parseInt(translatenum);*/
                    String obj = petsFamilyListBeans.getObjcetId();
                    updatepetsMaintainDynamic = new PetsMaintainDynamic();
                    //  final int tn2 = tn + 1;
                    updatepetsMaintainDynamic.increment("good");
                    updatepetsMaintainDynamic.update(mActivity, obj,
                            new UpdateListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(mActivity, "translatesave成功" + petsFamilyListBeans.getGood() + "", Toast.LENGTH_LONG).show();
                                    // viewHolder.good.setText(tn2 + "");
                                    petsMaintainAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(int i, String s) {
                                    Toast.makeText(mActivity, "translatesave失败", Toast.LENGTH_LONG).show();

                                }
                            });
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
            public TextView comment;
            public ImageView good_pf;
        }
    }

    private void getDataFromServer() {

        BmobQuery<PetsMaintainDynamic> bmobQuery = new BmobQuery<>();
        bmobQuery.setLimit(10);
        bmobQuery.order("createDAt");
        bmobQuery.findObjects(mActivity, new FindListener<PetsMaintainDynamic>() {
            @Override
            public void onSuccess(List<PetsMaintainDynamic> list) {
                petsMaintainDynamics.addAll(list);
                Toast.makeText(mActivity, "query成功" + list.size(), Toast.LENGTH_LONG).show();
                Collections.sort(list, new Comparator<PetsMaintainDynamic>() {
                    @Override
                    public int compare(PetsMaintainDynamic o1, PetsMaintainDynamic o2) {
                        return o1.getTime().compareTo(o2.getTime());
                    }
                });

                listPetsMaintainBeen.clear();

                for (int i = list.size() - 1; i >= 0; i--) {
                    consultPetsMaintainListBean = new ConsultPetsMaintainListBean();
                    //头像，时间，名字
                    String avatar = list.get(i).getAvatar();
                    consultPetsMaintainListBean.setAvatar(avatar);
                    String name = list.get(i).getUsername();
                    consultPetsMaintainListBean.setName(name);
                    String time = list.get(i).getTime();
                    consultPetsMaintainListBean.setTime(time);
                    //内容，图片
                    String content = list.get(i).getContent();
                    consultPetsMaintainListBean.setContent(content);
                    String contentimage = list.get(i).getContentimage();
                    consultPetsMaintainListBean.setContentImages(contentimage);
                    //赞评转
                    int good = list.get(i).getGood();
                    consultPetsMaintainListBean.setGood(good);

                    String value = list.get(i).getValue();
                    consultPetsMaintainListBean.setValue(value);
                    String passvalue = list.get(i).getPassvalue();
                    consultPetsMaintainListBean.setPassvalue(passvalue);

                    consultPetsMaintainListBean.setObjcetId(list.get(i).getObjectId());
                    listPetsMaintainBeen.add(consultPetsMaintainListBean);
                }
                lv_PetsMain.onRefreshComplete(true);
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(mActivity, "query失败", Toast.LENGTH_LONG).show();
                lv_PetsMain.onRefreshComplete(false);
            }
        });
    }
}
