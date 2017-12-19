package cn.bmob.imdemo.ui.imple;

import android.app.Activity;
import android.graphics.Color;
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
import cn.bmob.imdemo.bean.PetsFamilyListBean;
import cn.bmob.imdemo.bean.StoreFoodsListBean;

/**
 * Created by Administrator on 2017/3/29.
 */

public class StoreFoods extends BaseImple{

    private Activity mActivity;

    public View myView;
    private ListView lv_StoreFoods;

    private StoreFoodAdapter storeFoodAdapter;
    private List<StoreFoodsListBean> listStoreFoodsBean;
    private StoreFoodsListBean storeFoodsListBean;


    private StoreFoodAdapter.ViewHolder viewHolder;

    public StoreFoods(Activity activity) {
        mActivity = activity;
        myView = LinearLayout.inflate(mActivity, R.layout.f_storefoods, null);
           lv_StoreFoods = (ListView)myView.findViewById(R.id.lv_storefoods);

        listStoreFoodsBean = new ArrayList<>();

        /////
        storeFoodsListBean = new StoreFoodsListBean();
        storeFoodsListBean.setAvatar(R.drawable.icon_head4);
        storeFoodsListBean.setTime("2017-3-28 15:30");
        storeFoodsListBean.setContent("主食！");
        storeFoodsListBean.setContentImages(R.mipmap.ic_pets_toy1);
        storeFoodsListBean.setGood("520");
        storeFoodsListBean.setTranslate("52");
        listStoreFoodsBean.add(storeFoodsListBean);
        ///
        storeFoodsListBean = new StoreFoodsListBean();
        storeFoodsListBean.setAvatar(R.drawable.icon_head4);
        storeFoodsListBean.setTime("2017-3-28 15:30");
        storeFoodsListBean.setContent("主食！");
        storeFoodsListBean.setContentImages(R.mipmap.ic_pets_toy2);
        storeFoodsListBean.setGood("520");
        storeFoodsListBean.setTranslate("52");
        listStoreFoodsBean.add(storeFoodsListBean);
        ///
        storeFoodsListBean = new StoreFoodsListBean();
        storeFoodsListBean.setAvatar(R.drawable.icon_head3);
        storeFoodsListBean.setTime("2017-3-28 15:30");
        storeFoodsListBean.setContent("主食！");
        storeFoodsListBean.setContentImages(R.mipmap.ic_pets_toy3);
        storeFoodsListBean.setGood("520");
        storeFoodsListBean.setTranslate("52");
        listStoreFoodsBean.add(storeFoodsListBean);
        //////
        for (int i = 0; i < 10; i++) {
            storeFoodsListBean = new StoreFoodsListBean();
            storeFoodsListBean.setAvatar(R.drawable.icon_head7);
            storeFoodsListBean.setTime("2017-3-28 15:30");
            storeFoodsListBean.setContent("主食！");
            storeFoodsListBean.setContentImages(R.drawable.storefoods);
            storeFoodsListBean.setGood("520");
            storeFoodsListBean.setTranslate("52");
            listStoreFoodsBean.add(storeFoodsListBean);
        }

        storeFoodAdapter = new StoreFoodAdapter();
        lv_StoreFoods.setAdapter(storeFoodAdapter);
    }


    class StoreFoodAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listStoreFoodsBean.size();
        }

        @Override
        public Object getItem(int i) {
            return listStoreFoodsBean.get(i);
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
            StoreFoodsListBean petsFamilyListBeans = listStoreFoodsBean.get(i);
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
