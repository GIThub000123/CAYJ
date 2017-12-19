package cn.bmob.imdemo.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviFragment;
import cn.bmob.imdemo.bean.GridViewInfoBean;
import cn.bmob.imdemo.ui.imple.BaseImple;
import cn.bmob.imdemo.ui.imple.StoreCloths;
import cn.bmob.imdemo.ui.imple.StoreDays;
import cn.bmob.imdemo.ui.imple.StoreFoods;
import cn.bmob.imdemo.ui.imple.StorePetsDoctor;
import cn.bmob.imdemo.ui.imple.StoreToys;

/**
 * Created by Administrator on 2017/3/27.
 */

//宠物商场
public class StoreFragemnt extends ParentWithNaviFragment {

    private List<BaseImple> mListStoreFoods;
    private StoreDays storeDays;
    private StoreFoods storeFoods;
    private StoreToys storeToys;
    private StoreCloths storeCloths;
    private StorePetsDoctor storePetsDoctor;


    private RadioGroup rgPets;
    public RadioButton rg_Pets_All;
    public RadioButton rb_Store_Pets_Day;
    public RadioButton rb_Store_Pets_Food;
    public RadioButton rb_Store_Pets_Toys;
    public RadioButton rb_Store_Pets_Docteor;

    private MyEnjoyPager myEnjoyPager;
    private ViewPager vp_StoreFragmentViewPager;


    @Override
    protected String title() {
        return "宠物商场";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.frament_store, container, false);
        initNaviView();
        vp_StoreFragmentViewPager = (ViewPager) rootView.findViewById(R.id.vp_store_fragment);
        rgPets = (RadioGroup) rootView.findViewById(R.id.rg_pets_all);
        rb_Store_Pets_Day = (RadioButton) rootView.findViewById(R.id.rb_store_pets_day);

        mListStoreFoods = new ArrayList<>();
        //初始化数据
        /*for (int i = 0; i < 5; i++) {
            storeFoods = new StoreFoods(mActivity);
            mListStoreFoods.add(storeFoods);
        }*/
        storeDays = new StoreDays(mActivity);
        mListStoreFoods.add(storeDays);
        storeFoods = new StoreFoods(mActivity);
        mListStoreFoods.add(storeFoods);
        storeToys = new StoreToys(mActivity);
        mListStoreFoods.add(storeToys);
        storeCloths = new StoreCloths(mActivity);
        mListStoreFoods.add(storeCloths);
        storePetsDoctor = new StorePetsDoctor(mActivity);
        mListStoreFoods.add(storePetsDoctor);


        myEnjoyPager = new MyEnjoyPager();
        vp_StoreFragmentViewPager.setAdapter(myEnjoyPager);

        rgPets.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_store_pets_day:
                        vp_StoreFragmentViewPager.setCurrentItem(0, false);
                        break;
                    case R.id.rb_store_pets_food:
                        vp_StoreFragmentViewPager.setCurrentItem(1, false);
                        break;
                    case R.id.rb_store_pets_toys:
                        vp_StoreFragmentViewPager.setCurrentItem(2, false);
                        break;
                    case R.id.rb_store_pets_clothes:
                        vp_StoreFragmentViewPager.setCurrentItem(3, false);
                        break;
                    case R.id.rb_store_pets_docteor:
                        vp_StoreFragmentViewPager.setCurrentItem(4, false);
                        break;
                    default:
                        break;
                }
            }
        });
        rb_Store_Pets_Day.setChecked(true);
        return rootView;
    }

    class MyEnjoyPager extends PagerAdapter {

        @Override
        public int getCount() {
            return mListStoreFoods.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (position == 0) {
                StoreDays storeDays1 = (StoreDays) mListStoreFoods.get(0);
                container.addView(storeDays1.myView);
                return storeDays1.myView;

            } else if (position == 1) {
                StoreFoods storeFoodss1 = (StoreFoods) mListStoreFoods.get(1);
                container.addView(storeFoodss1.myView);
                return storeFoodss1.myView;

            } else if (position == 2) {
                StoreToys storeToys1 = (StoreToys) mListStoreFoods.get(2);
                container.addView(storeToys1.myView);
                return storeToys1.myView;

            } else if (position == 3) {
                StoreCloths storeCloths1 = (StoreCloths)mListStoreFoods.get(3);
                container.addView(storeCloths1.myView);
                return storeCloths1.myView;

            } else{
                StorePetsDoctor storePetsDoctor1 = (StorePetsDoctor)mListStoreFoods.get(4);
                container.addView(storePetsDoctor1.myView);
                return storePetsDoctor1.myView;
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
