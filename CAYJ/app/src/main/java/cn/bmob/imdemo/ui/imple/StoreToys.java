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
import cn.bmob.imdemo.bean.StoreFoodsListBean;
import cn.bmob.imdemo.bean.StoreToysListBean;

/**
 * Created by Administrator on 2017/3/29.
 */

public class StoreToys extends BaseImple {

    private Activity mActivity;

    public View myView;
    private ListView lv_StoreToys;

    private StoreToysAdapter storeToysAdapter;
    private List<StoreToysListBean> listStoreToysBean;
    private StoreToysListBean storeToysListBean;


    private StoreToysAdapter.ViewHolder viewHolder;

    public StoreToys(Activity activity) {
        mActivity = activity;
        myView = LinearLayout.inflate(mActivity, R.layout.f_storetoys, null);
        lv_StoreToys = (ListView) myView.findViewById(R.id.lv_storetoys);

        listStoreToysBean = new ArrayList<>();
        /////
        storeToysListBean = new StoreToysListBean();
        storeToysListBean.setAvatar(R.drawable.icon_head8);
        storeToysListBean.setTime("2017-3-28 15:30");
        storeToysListBean.setContent("玩具！");
        storeToysListBean.setContentImages(R.mipmap.ic_store_toy1);
        storeToysListBean.setGood("520");
        storeToysListBean.setTranslate("52");
        listStoreToysBean.add(storeToysListBean);
        ///
        storeToysListBean = new StoreToysListBean();
        storeToysListBean.setAvatar(R.drawable.icon_head8);
        storeToysListBean.setTime("2017-3-28 15:30");
        storeToysListBean.setContent("玩具！");
        storeToysListBean.setContentImages(R.mipmap.ic_store_toy2);
        storeToysListBean.setGood("520");
        storeToysListBean.setTranslate("52");
        listStoreToysBean.add(storeToysListBean);
        ///
        storeToysListBean = new StoreToysListBean();
        storeToysListBean.setAvatar(R.drawable.icon_head8);
        storeToysListBean.setTime("2017-3-28 15:30");
        storeToysListBean.setContent("玩具！");
        storeToysListBean.setContentImages(R.mipmap.ic_store_toy3);
        storeToysListBean.setGood("520");
        storeToysListBean.setTranslate("52");
        listStoreToysBean.add(storeToysListBean);
        //////
        for (int i = 0; i < 10; i++) {
            storeToysListBean = new StoreToysListBean();
            storeToysListBean.setAvatar(R.drawable.icon_head8);
            storeToysListBean.setTime("2017-3-28 15:30");
            storeToysListBean.setContent("玩具！");
            storeToysListBean.setContentImages(R.mipmap.ic_store_toy5);
            storeToysListBean.setGood("520");
            storeToysListBean.setTranslate("52");
            listStoreToysBean.add(storeToysListBean);
        }

        storeToysAdapter = new StoreToysAdapter();
        lv_StoreToys.setAdapter(storeToysAdapter);
    }


    class StoreToysAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listStoreToysBean.size();
        }

        @Override
        public Object getItem(int i) {
            return listStoreToysBean.get(i);
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
            StoreToysListBean petsFamilyListBeans = listStoreToysBean.get(i);
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
