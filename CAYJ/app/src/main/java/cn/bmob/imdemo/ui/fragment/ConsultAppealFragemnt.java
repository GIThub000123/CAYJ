package cn.bmob.imdemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviActivity;
import cn.bmob.imdemo.base.ParentWithNaviFragment;
import cn.bmob.imdemo.ui.add.AddDoctorForPetsDynamic;
import cn.bmob.imdemo.ui.add.AddDynamic;
import cn.bmob.imdemo.ui.MainActivity;
import cn.bmob.imdemo.ui.add.AddPetsMaintainDynamic;
import cn.bmob.imdemo.ui.add.AddSeekHomeDynamic;
import cn.bmob.imdemo.ui.imple.BaseImple;
import cn.bmob.imdemo.ui.imple.DoctorForPets;
import cn.bmob.imdemo.ui.imple.PetsMaintain;
import cn.bmob.imdemo.ui.imple.SeekHome;

/**
 * Created by Administrator on 2017/3/27.
 */

public class ConsultAppealFragemnt extends ParentWithNaviFragment {
    private ArrayList<BaseImple> mPagerList;
    private RadioGroup rgGroup;
    private ViewPager vp_ConsultAppeal;
    private ConsultAppealAdapter consultAppealAdapter;

    private int position = 0;

    public RadioButton rb_DoctorForPets;
    public RadioButton rb_SeekHome;
    public RadioButton rb_PetsMaintain;


    @Override
    protected String title() {
        return "咨询求助";
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
                if(position==0){
                    startActivity(AddSeekHomeDynamic.class, null);
                }else if(position==1){
                    startActivity(AddDoctorForPetsDynamic.class, null);
                } else if(position==2){
                    startActivity(AddPetsMaintainDynamic.class, null);
                }

            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         //防止1页面PetsFamilyFragment发生错误！
        if (isShowSelect == true) {
            MainActivity mAc = (MainActivity) mActivity;
           // mAc.onTabIndex(1);
            isShowSelect = false;
        }

        rootView = inflater.inflate(R.layout.fragment_consultappeal, container, false);
        initNaviView();
        vp_ConsultAppeal = (ViewPager) rootView.findViewById(R.id.vp_consultappeal);

        rgGroup = (RadioGroup) rootView.findViewById(R.id.rg_group);
        rb_DoctorForPets = (RadioButton) rootView.findViewById(R.id.rb_doctor_for_pets);
        rb_SeekHome = (RadioButton) rootView.findViewById(R.id.rb_seek_home);
        rb_PetsMaintain = (RadioButton) rootView.findViewById(R.id.rb_pets_maintain);
        initData();
        return rootView;
    }

    private void initData() {
        rgGroup.check(R.id.rb_seek_home);// 默认勾选首页

        // 初始化3个子页面
        mPagerList = new ArrayList<>();
        mPagerList.add(new SeekHome(mActivity));
        mPagerList.add(new DoctorForPets(mActivity));
        mPagerList.add(new PetsMaintain(mActivity));

        consultAppealAdapter = new ConsultAppealAdapter();
        vp_ConsultAppeal.setAdapter(consultAppealAdapter);


        ////
        vp_ConsultAppeal.addOnPageChangeListener(new ConsultAppealAdapterListener());
        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_seek_home:
                        vp_ConsultAppeal.setCurrentItem(0, false);
                        break;
                    case R.id.rb_doctor_for_pets:
                        vp_ConsultAppeal.setCurrentItem(1, false);
                        break;
                    case R.id.rb_pets_maintain:
                        vp_ConsultAppeal.setCurrentItem(2, false);
                        break;
                    default:
                        break;
                }
            }
        });
        rb_SeekHome.setChecked(true);

    }

    class ConsultAppealAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagerList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (position == 0) {
                SeekHome seekHome1 = (SeekHome) mPagerList.get(0);
                container.addView(seekHome1.rootView);
                return seekHome1.rootView;

            } else if (position == 1) {
                DoctorForPets doctorForPett1 = (DoctorForPets) mPagerList.get(1);
                container.addView(doctorForPett1.rootView);
                return doctorForPett1.rootView;

            } else {
                PetsMaintain petsMaintain1 = (PetsMaintain) mPagerList.get(2);
                container.addView(petsMaintain1.rootView);
                return petsMaintain1.rootView;
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class ConsultAppealAdapterListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float positionOffset, int positionOffsetPixels) {
            if (positionOffset == 0) {
                position = i;
                if (position == 0) {
                    rb_SeekHome.setChecked(true);
                    rb_DoctorForPets.setChecked(false);
                } else if (position == 1) {
                    rb_SeekHome.setChecked(false);
                    rb_DoctorForPets.setChecked(true);
                    rb_PetsMaintain.setChecked(false);
                } else if (position == 2) {
                    rb_DoctorForPets.setChecked(false);
                    rb_PetsMaintain.setChecked(true);
                }
            }
        }

        @Override
        public void onPageSelected(int i) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
