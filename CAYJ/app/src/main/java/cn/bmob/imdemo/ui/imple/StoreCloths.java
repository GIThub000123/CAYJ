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
import cn.bmob.imdemo.bean.StoreClothesListBean;
import cn.bmob.imdemo.bean.StoreFoodsListBean;

/**
 * Created by Administrator on 2017/3/29.
 */

public class StoreCloths extends BaseImple{

    private Activity mActivity;

    public View myView;
    private ListView lv_StoreCloths;

    private StoreClothesAdapter storeClothesAdapter;
    private List<StoreClothesListBean> listStoreClothesListBean;
    private StoreClothesListBean storeClothesListBean;


    private StoreClothesAdapter.ViewHolder viewHolder;

    public StoreCloths(Activity activity) {
        mActivity = activity;
        myView = LinearLayout.inflate(mActivity, R.layout.f_storeclothes, null);
        lv_StoreCloths = (ListView)myView.findViewById(R.id.lv_storecloths);

        listStoreClothesListBean = new ArrayList<>();
        /////
        storeClothesListBean = new StoreClothesListBean();
        storeClothesListBean.setAvatar(R.drawable.icon_head2);
        storeClothesListBean.setTime("2017-3-28 15:30");
        storeClothesListBean.setContent("衣服！");
        storeClothesListBean.setContentImages(R.mipmap.ic_store_clothes1);
        storeClothesListBean.setGood("520");
        storeClothesListBean.setTranslate("52");
        listStoreClothesListBean.add(storeClothesListBean);
        ///
        storeClothesListBean = new StoreClothesListBean();
        storeClothesListBean.setAvatar(R.drawable.icon_head3);
        storeClothesListBean.setTime("2017-3-28 15:30");
        storeClothesListBean.setContent("衣服！");
        storeClothesListBean.setContentImages(R.mipmap.ic_store_clothes2);
        storeClothesListBean.setGood("520");
        storeClothesListBean.setTranslate("52");
        listStoreClothesListBean.add(storeClothesListBean);
        ///
        storeClothesListBean = new StoreClothesListBean();
        storeClothesListBean.setAvatar(R.drawable.icon_head5);
        storeClothesListBean.setTime("2017-3-28 15:30");
        storeClothesListBean.setContent("衣服！");
        storeClothesListBean.setContentImages(R.mipmap.ic_store_clothes3);
        storeClothesListBean.setGood("520");
        storeClothesListBean.setTranslate("52");
        listStoreClothesListBean.add(storeClothesListBean);
        //////
        for (int i = 0; i < 10; i++) {
            storeClothesListBean = new StoreClothesListBean();
            storeClothesListBean.setAvatar(R.drawable.icon_head6);
            storeClothesListBean.setTime("2017-3-28 15:30");
            storeClothesListBean.setContent("衣服！");
            storeClothesListBean.setContentImages(R.mipmap.ic_store_clothes4);
            storeClothesListBean.setGood("520");
            storeClothesListBean.setTranslate("52");
            listStoreClothesListBean.add(storeClothesListBean);
        }

        storeClothesAdapter = new StoreClothesAdapter();
        lv_StoreCloths.setAdapter(storeClothesAdapter);
    }


    class StoreClothesAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listStoreClothesListBean.size();
        }

        @Override
        public Object getItem(int i) {
            return listStoreClothesListBean.get(i);
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
            StoreClothesListBean petsFamilyListBeans = listStoreClothesListBean.get(i);
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
