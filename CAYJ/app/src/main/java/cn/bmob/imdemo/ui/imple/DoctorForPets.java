package cn.bmob.imdemo.ui.imple;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ImageLoaderFactory;
import cn.bmob.imdemo.bean.ConsultDoctorForPetsListBean;
import cn.bmob.imdemo.bean.petsbean.PetsDoctorDynamic;

import cn.bmob.imdemo.util.DensityUtil;
import cn.bmob.imdemo.view.RefreshListView;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/3/28.
 */

public class DoctorForPets extends BaseImple {

    private Activity mActivity;
    public View rootView;

    private RefreshListView lv_DoctorForPets;
    private DoctorForPetsAdapter doctorForPetsAdapter;

    private List<ConsultDoctorForPetsListBean> listConsultDoctorForPetsListBeen;
    private ConsultDoctorForPetsListBean consultDoctorForPetsListBean;

    private int img[] = {R.drawable.shengbing, R.drawable.shengbing2, R.drawable.shengbing3};
    private final String[] imageDescriptions = {
            "如何给宝宝一个健康的检查！",
            "宝宝怎么生病了？",
            "宝宝如何重获健康！"
    };

    private ArrayList<ImageView> mImg;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;

    private DoctorForPetsAdapter.ViewHolder viewHolder;

    private List<PetsDoctorDynamic> petsDoctorDynamics;

    private PetsDoctorDynamic petsDoctorDynamic;
    private PetsDoctorDynamic updatepetsDoctorDynamic;


    private LinearLayout llPointGroup;// 引导圆点的父控件
    private int mPointWidth;// 圆点间的距离
    private View viewRedPoint;// 小红点
    private TextView tv_description;

    private boolean isDragging = false;


    private int prePosition = 0;

    private int postPosition = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int item = viewPager.getCurrentItem() + 1;
            viewPager.setCurrentItem(item);

            //延迟发消息
            handler.sendEmptyMessageDelayed(0, 4000);
        }
    };

    public DoctorForPets(Activity activity) {
        mActivity = activity;
        rootView = LinearLayout.inflate(mActivity, R.layout.consult_doctorforpets, null);
        lv_DoctorForPets = (RefreshListView) rootView.findViewById(R.id.lv_consult_doctorforpets);

        //
        View view = LinearLayout.inflate(mActivity, R.layout.signalviewpager, null);
        viewPager = (ViewPager) view.findViewById(R.id.vp_singal);
        tv_description = (TextView) view.findViewById(R.id.tv_descri);

        llPointGroup = (LinearLayout) view.findViewById(R.id.ll);
        viewRedPoint = view.findViewById(R.id.view_red_point);

        mImg = new ArrayList<>();
        for (int i = 0; i < img.length; i++) {
            ImageView imageView = new ImageView(mActivity);
            imageView.setBackgroundResource(img[i]);
            mImg.add(imageView);
        }
        // 初始化引导页的小圆点
        for (int i = 0; i < img.length; i++) {
            View point = new View(mActivity);
            point.setBackgroundResource(R.drawable.shape_point_gray);// 设置引导页默认圆点

            int width = DensityUtil.dip2px(mActivity, 10);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    width, width);
            if (i > 0) {
                params.leftMargin = width;// 设置圆点间隔
            }

            point.setLayoutParams(params);// 设置圆点的大小

            llPointGroup.addView(point);// 将圆点添加给线性布局
        }
        // 获取视图树, 对layout结束事件进行监听
        llPointGroup.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    // 当layout执行结束后回调此方法
                    @Override
                    public void onGlobalLayout() {
                        System.out.println("layout 结束");
                        llPointGroup.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                        mPointWidth = llPointGroup.getChildAt(1).getLeft()
                                - llPointGroup.getChildAt(0).getLeft();
                        System.out.println("圆点距离:" + mPointWidth);
                    }
                });
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);

        viewPager.addOnPageChangeListener(new GuidePageListener());

        //设置中间位置
        int item = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mImg.size();//要保证imageViews的整数倍


        viewPager.setCurrentItem(item);

        tv_description.setText(imageDescriptions[prePosition]);

        //发消息
        handler.sendEmptyMessageDelayed(0, 3000);


        lv_DoctorForPets.addHeaderView(view);
        listConsultDoctorForPetsListBeen = new ArrayList<>();
        lv_DoctorForPets.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataFromServer();
            }

            @Override
            public void onLoadMore() {

            }
        });
        getDataFromServer();
        doctorForPetsAdapter = new DoctorForPetsAdapter();
        lv_DoctorForPets.setAdapter(doctorForPetsAdapter);

    }


    class GuidePageListener implements ViewPager.OnPageChangeListener {

        // 滑动事件
        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            // System.out.println("当前位置:" + position + ";百分比:" + positionOffset
            // + ";移动距离:" + positionOffsetPixels);
            int realPosition = position % mImg.size();

            int len = (int) (mPointWidth * positionOffset) + realPosition
                    * mPointWidth;
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) viewRedPoint
                    .getLayoutParams();// 获取当前红点的布局参数
            params.leftMargin = len;// 设置左边距

            viewRedPoint.setLayoutParams(params);// 重新给小红点设置布局参数
        }

        // 某个页面被选中
        @Override
        public void onPageSelected(int position) {
            int realPosition = position % mImg.size();
            //    Log.e(TAG, "onPageSelected==" + realPosition );
            //设置对应页面的文本信息
            tv_description.setText(imageDescriptions[realPosition]);

            prePosition = realPosition;
        }

        // 滑动状态发生变化
        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                isDragging = true;
                handler.removeCallbacksAndMessages(null);
                //Log.e(TAG,"SCROLL_STATE_DRAGGING-------------------");
            } else if (state == ViewPager.SCROLL_STATE_SETTLING) {
                // Log.e(TAG,"SCROLL_STATE_SETTLING-----------------");

            } else if (state == ViewPager.SCROLL_STATE_IDLE && isDragging) {
                isDragging = false;
                // Log.e(TAG,"SCROLL_STATE_IDLE------------");
                handler.removeCallbacksAndMessages(null);
                handler.sendEmptyMessageDelayed(0, 4000);
            }
        }

    }


    class DoctorForPetsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listConsultDoctorForPetsListBeen.size();
        }

        @Override
        public Object getItem(int i) {
            return listConsultDoctorForPetsListBeen.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }


        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            viewHolder = new ViewHolder();
            if (convertView == null) {
                convertView = View.inflate(mActivity, R.layout.fragment_pf_layout3, null);
                viewHolder.content = (TextView) convertView.findViewById(R.id.tv_content_pf);
                viewHolder.contentImages = (ImageView) convertView.findViewById(R.id.iv_content_pf);
                viewHolder.tv_Value = (TextView) convertView.findViewById(R.id.tv_value_pf);
                viewHolder.tv_PassValue = (TextView) convertView.findViewById(R.id.tv_pass_value_pf);
                viewHolder.iv_Buy = (ImageView) convertView.findViewById(R.id.iv_buy);

                viewHolder.tv_Titlepdd = (TextView) convertView.findViewById(R.id.tv_title_pdd);
                viewHolder.view = (View) convertView.findViewById(R.id.v);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            final ConsultDoctorForPetsListBean petsFamilyListBeans = listConsultDoctorForPetsListBeen.get(i);

            ImageLoaderFactory.getLoader().loadAvator(viewHolder.contentImages,
                    petsFamilyListBeans.getContentImages() != null ? petsFamilyListBeans.getContentImages() : null,
                    R.mipmap.head);

            viewHolder.content.setText(petsFamilyListBeans.getContent());
            viewHolder.tv_Value.setText("$ " + petsFamilyListBeans.getValue());
            viewHolder.tv_PassValue.setText("$ " + petsFamilyListBeans.getPassvalue());
            viewHolder.tv_PassValue.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

            if (i == 0) {
                viewHolder.tv_Titlepdd.setVisibility(View.VISIBLE);
                viewHolder.view.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tv_Titlepdd.setVisibility(View.GONE);
                viewHolder.view.setVisibility(View.GONE);
            }
            viewHolder.iv_Buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return convertView;
        }

        class ViewHolder {

            public TextView content;
            public ImageView contentImages;

            public TextView tv_Value;
            public TextView tv_PassValue;
            public ImageView iv_Buy;

            public TextView tv_Titlepdd;
            public View view;
        }
    }

    class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //  return mImg.size();
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            int realPosition = position % mImg.size();
            final ImageView imageV = mImg.get(realPosition);
            ViewGroup parent = (ViewGroup)imageV.getParent();
            if(parent!=null){
                parent.removeAllViews();
            }
            final ImageView imageView = mImg.get(realPosition);

            container.addView(imageView);

            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN://手指按下
                            //   Log.e(TAG,"onTouch==手指按下");
                            handler.removeCallbacksAndMessages(null);
                            break;

                        case MotionEvent.ACTION_MOVE://手指在这个控件上移动
                            break;
                        case MotionEvent.ACTION_CANCEL://手指在这个控件上移动
                            // Log.e(TAG,"onTouch==事件取消");
//                            handler.removeCallbacksAndMessages(null);
//                            handler.sendEmptyMessageDelayed(0,4000);
                            break;
                        case MotionEvent.ACTION_UP://手指离开
                            //  Log.e(TAG,"onTouch==手指离开");
                            handler.removeCallbacksAndMessages(null);
                            handler.sendEmptyMessageDelayed(0, 4000);
                            break;
                    }
                    return false;
                }
            });

            imageView.setTag(position);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Log.e(TAG,"点击事件");
                    int position = (int) v.getTag() % mImg.size();
                    String text = imageDescriptions[position];
                       Toast.makeText(mActivity, "text=="+text, Toast.LENGTH_SHORT).show();

                }
            });
            return imageView;
        }
    }

    private void getDataFromServer() {

        petsDoctorDynamic = new PetsDoctorDynamic();
        BmobQuery<PetsDoctorDynamic> bmobQuery = new BmobQuery<>();
        bmobQuery.setLimit(10);
        bmobQuery.order("createDAt");
        bmobQuery.findObjects(mActivity, new FindListener<PetsDoctorDynamic>() {
            @Override
            public void onSuccess(List<PetsDoctorDynamic> list) {
                petsDoctorDynamics = new ArrayList<>();
                petsDoctorDynamics.addAll(list);

                lv_DoctorForPets.onRefreshComplete(true);
                // lv_petsfamily_listview.isLoadingMore = true;
                //   Toast.makeText(mActivity, "query成功" + list.get(0).getAvatar(), Toast.LENGTH_LONG).show();
                Log.d("query", list.toString() + "");
                Collections.sort(list, new Comparator<PetsDoctorDynamic>() {
                    @Override
                    public int compare(PetsDoctorDynamic o1, PetsDoctorDynamic o2) {
                        return o1.getTime().compareTo(o2.getTime());
                    }
                });

                listConsultDoctorForPetsListBeen.clear();
                for (int i = list.size() - 1; i >= 0; i--) {
                    consultDoctorForPetsListBean = new ConsultDoctorForPetsListBean();
                    //头像，时间，名字
                    String avatar = list.get(i).getAvatar();
                    consultDoctorForPetsListBean.setAvatar(avatar);
                    String name = list.get(i).getUsername();
                    consultDoctorForPetsListBean.setName(name);
                    String time = list.get(i).getTime();
                    consultDoctorForPetsListBean.setTime(time);
                    //内容，图片
                    String content = list.get(i).getContent();
                    consultDoctorForPetsListBean.setContent(content);
                    String contentimage = list.get(i).getContentimage();
                    consultDoctorForPetsListBean.setContentImages(contentimage);

                    String value = list.get(i).getValue();
                    consultDoctorForPetsListBean.setValue(value);
                    String passvalue = list.get(i).getPass_value();
                    consultDoctorForPetsListBean.setPassvalue(passvalue);

                    consultDoctorForPetsListBean.setObjcetId(list.get(i).getObjectId());

                    listConsultDoctorForPetsListBeen.add(consultDoctorForPetsListBean);
                    lv_DoctorForPets.onRefreshComplete(true);
                }
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(mActivity, "query失败", Toast.LENGTH_LONG).show();
                lv_DoctorForPets.onRefreshComplete(false);
            }
        });
    }
}
