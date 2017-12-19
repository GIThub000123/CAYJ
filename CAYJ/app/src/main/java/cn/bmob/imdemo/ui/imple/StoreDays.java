package cn.bmob.imdemo.ui.imple;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.bean.StoreDaysListBean;
import cn.bmob.imdemo.bean.StoreFoodsListBean;

/**
 * Created by Administrator on 2017/3/29.
 */

public class StoreDays extends BaseImple{

    private Activity mActivity;

    public View myView;
    private ListView lv_StoreFoods;

    private StoreDaysAdapter storeDaysAdapter;
    private List<StoreDaysListBean> listStoreDaysBean;
    private StoreDaysListBean storeDaysListBean;


    private StoreDaysAdapter.ViewHolder viewHolder;

    public StoreDays(Activity activity) {
        mActivity = activity;
        myView = LinearLayout.inflate(mActivity, R.layout.f_storedays, null);
           lv_StoreFoods = (ListView)myView.findViewById(R.id.lv_storedays);

        listStoreDaysBean = new ArrayList<>();
        /////
        storeDaysListBean = new StoreDaysListBean();
        storeDaysListBean.setAvatar(R.drawable.icon_head3);
        storeDaysListBean.setTime("2017-3-28 15:30");
        storeDaysListBean.setContent("日用！");
        storeDaysListBean.setContentImages(R.mipmap.ic_store_day1);
        storeDaysListBean.setGood("520");
        storeDaysListBean.setTranslate("52");
        listStoreDaysBean.add(storeDaysListBean);
        //
        storeDaysListBean = new StoreDaysListBean();
        storeDaysListBean.setAvatar(R.drawable.icon_head5);
        storeDaysListBean.setTime("2017-3-28 15:30");
        storeDaysListBean.setContent("日用！");
        storeDaysListBean.setContentImages(R.mipmap.ic_store_day2);
        storeDaysListBean.setGood("520");
        storeDaysListBean.setTranslate("52");
        listStoreDaysBean.add(storeDaysListBean);
        ///
        storeDaysListBean = new StoreDaysListBean();
        storeDaysListBean.setAvatar(R.drawable.icon_head7);
        storeDaysListBean.setTime("2017-3-28 15:30");
        storeDaysListBean.setContent("日用！");
        storeDaysListBean.setContentImages(R.mipmap.ic_store_day3);
        storeDaysListBean.setGood("520");
        storeDaysListBean.setTranslate("52");
        listStoreDaysBean.add(storeDaysListBean);
        //////
        for (int i = 0; i < 10; i++) {
            storeDaysListBean = new StoreDaysListBean();
            storeDaysListBean.setAvatar(R.drawable.icon_head5);
            storeDaysListBean.setTime("2017-3-28 15:30");
            storeDaysListBean.setContent("日用！");
            storeDaysListBean.setContentImages(R.drawable.storefoods);
            storeDaysListBean.setGood("520");
            storeDaysListBean.setTranslate("52");
            listStoreDaysBean.add(storeDaysListBean);
        }

        storeDaysAdapter = new StoreDaysAdapter();
        lv_StoreFoods.setAdapter(storeDaysAdapter);
    }


    class StoreDaysAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listStoreDaysBean.size();
        }

        @Override
        public Object getItem(int i) {
            return listStoreDaysBean.get(i);
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
                viewHolder.content = (TextView) convertView.findViewById(R.id.tv_content_pf);
                viewHolder.contentImages = (ImageView) convertView.findViewById(R.id.iv_content_pf);
                viewHolder.good = (TextView) convertView.findViewById(R.id.tv_good_pf);
                viewHolder.translate = (TextView) convertView.findViewById(R.id.tv_translates_pf);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            StoreDaysListBean petsFamilyListBeans = listStoreDaysBean.get(i);
            viewHolder.avatar.setBackgroundResource(petsFamilyListBeans.getAvatar());
            viewHolder.time.setText(petsFamilyListBeans.getTime());
            viewHolder.content.setText(petsFamilyListBeans.getContent());
            viewHolder.contentImages.setBackgroundResource(petsFamilyListBeans.getContentImages());
            viewHolder.good.setText(petsFamilyListBeans.getGood());
            viewHolder.translate.setText(petsFamilyListBeans.getTranslate());
            return convertView;
        }

        class ViewHolder {
            public ImageView avatar;
            public TextView time;
            public TextView content;
            public ImageView contentImages;
            public TextView good;
            public TextView translate;
        }
    }

}
