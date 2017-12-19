package cn.bmob.imdemo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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

import java.util.ArrayList;
import java.util.List;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviFragment;
import cn.bmob.imdemo.bean.PetsCritttersFragmentListBean;
import cn.bmob.imdemo.bean.PetsFamilyListBean;

/**
 * Created by Administrator on 2017/3/28.
 */
//宠物笑话

public class PetsCritttersFragment extends ParentWithNaviFragment {


    private List<PetsCritttersFragmentListBean> listPetsFamilyBeen;
    private PetsCritttersFragmentListBean petsFamilyListBean;
    private RecyclerView lv_listPetsCrittters;

    private MyRecycleViewAdapter myRecycleViewAdapter;


    private SwipeRefreshLayout swipeRefreshLayout;

    private static final int[] mImags = {R.drawable.ic_yy_head, R.drawable.ic_yy_head,
            R.drawable.ic_yy_head, R.drawable.ic_yy_head,
            R.drawable.ic_yy_head, R.drawable.ic_yy_head};
    private static final String[] foodStr = {"圆圆", "2=2", "水晶恋人", "长江尾", "一人心", "粉色迷醉"};
    private static final String[] foodStrContent = {"山有木兮木有枝，心悦君兮君不知", "坐车下错站，上课进错班",
            "落花人独立，微风燕子斜", "我住长江头，君住长江尾", "愿得一人心，白首不相离", "惺忪庭院前，梅花轻吻风"};

    @Override
    protected String title() {
        return "宠物乐趣";
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_petscrittters, container, false);
        initNaviView();
        lv_listPetsCrittters = (RecyclerView) rootView.findViewById(R.id.lv_fragment_petscrittters);

                initData();

     /*   swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });*/

        lv_listPetsCrittters.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        myRecycleViewAdapter = new MyRecycleViewAdapter(mActivity);
        lv_listPetsCrittters.setAdapter(myRecycleViewAdapter);

        return rootView;

    }

    class MyRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private LayoutInflater mLayoutImflater;

        public MyRecycleViewAdapter(Context context) {
            mLayoutImflater = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            // View view = View.inflate(getActivity(), R.layout.fragment_pf_layout2, null);
            View v = mLayoutImflater.inflate(R.layout.fragment_pf_layout2, parent, false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((MyViewHolder) holder).bindData(listPetsFamilyBeen.get(position));
        }

        @Override
        public int getItemCount() {
            return listPetsFamilyBeen.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView time;
        private TextView content;
        private ImageView contentImages;
        private TextView good;
        private TextView translate;

        public MyViewHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.iv_avatar_pf);
            time = (TextView) itemView.findViewById(R.id.tv_time_pf);
            content = (TextView) itemView.findViewById(R.id.tv_content_pf);
            contentImages = (ImageView) itemView.findViewById(R.id.iv_content_pf);
            good = (TextView) itemView.findViewById(R.id.tv_good_pf);
            translate = (TextView) itemView.findViewById(R.id.tv_translate_pf);
        }

        public void bindData(PetsCritttersFragmentListBean petsCritttersFragmentListBean) {

            avatar.setBackgroundResource(petsCritttersFragmentListBean.getAvatar());
            time.setText(petsCritttersFragmentListBean.getTime());
            content.setText(petsCritttersFragmentListBean.getContent()+"");
            contentImages.setBackgroundResource(petsCritttersFragmentListBean.getContentImages());
            good.setText(petsCritttersFragmentListBean.getGood());
            translate.setText(petsCritttersFragmentListBean.getTranslate());

        }
    }

    private void initData(){
        listPetsFamilyBeen = new ArrayList<>();
        //////
        petsFamilyListBean = new PetsCritttersFragmentListBean();
        petsFamilyListBean.setAvatar(R.drawable.icon_head6);
        petsFamilyListBean.setTime("2017-3-28 15:30");
        petsFamilyListBean.setContent("不要打豆豆了，都无法直视了！");
        petsFamilyListBean.setContentImages(R.mipmap.ic_critter1);
        petsFamilyListBean.setGood("520");
        petsFamilyListBean.setTranslate("52");
        listPetsFamilyBeen.add(petsFamilyListBean);
        //////
        petsFamilyListBean = new PetsCritttersFragmentListBean();
        petsFamilyListBean.setAvatar(R.drawable.icon_head7);
        petsFamilyListBean.setTime("2017-3-28 15:30");
        petsFamilyListBean.setContent("有一个人养了一百只宠物狗，" +
                "有一天，他问其中的一只狗说：“你一天中最喜欢做那三件事？”，" +
                "它回答说：“吃饭，睡觉，打豆豆”。他问了99只狗同样的问题，" +
                "99只狗回答了同样的答案。最后，他问第100只狗同样的问题，" +
                "它回答说：“吃饭，睡觉”。“你为什么不打豆豆呢？”" +
                "他问。狗回答说：“因为我就是豆豆”。 ：）");
        petsFamilyListBean.setContentImages(R.mipmap.ic_critter2);
        petsFamilyListBean.setGood("520");
        petsFamilyListBean.setTranslate("52");
        listPetsFamilyBeen.add(petsFamilyListBean);
        //////
        petsFamilyListBean = new PetsCritttersFragmentListBean();
        petsFamilyListBean.setAvatar(R.drawable.icon_head10);
        petsFamilyListBean.setTime("2017-3-28 15:30");
        petsFamilyListBean.setContent("有一个人养了一百只宠物狗，" +
                "有一天，他问其中的一只狗说：“你一天中最喜欢做那三件事？”，" +
                "它回答说：“吃饭，睡觉，打豆豆”。他问了99只狗同样的问题，" +
                "99只狗回答了同样的答案。最后，他问第100只狗同样的问题，" +
                "它回答说：“吃饭，睡觉”。“你为什么不打豆豆呢？”" +
                "他问。狗回答说：“因为我就是豆豆”。 ：）");
        petsFamilyListBean.setContentImages(R.mipmap.ic_critter3);
        petsFamilyListBean.setGood("520");
        petsFamilyListBean.setTranslate("52");
        listPetsFamilyBeen.add(petsFamilyListBean);
        //////
        petsFamilyListBean = new PetsCritttersFragmentListBean();
        petsFamilyListBean.setAvatar(R.drawable.icon_head5);
        petsFamilyListBean.setTime("2017-3-28 15:30");
        petsFamilyListBean.setContent("有一个人养了一百只宠物狗，" +
                "有一天，他问其中的一只狗说：“你一天中最喜欢做那三件事？”，" +
                "它回答说：“吃饭，睡觉，打豆豆”。他问了99只狗同样的问题，" +
                "99只狗回答了同样的答案。最后，他问第100只狗同样的问题，" +
                "它回答说：“吃饭，睡觉”。“你为什么不打豆豆呢？”" +
                "他问。狗回答说：“因为我就是豆豆”。 ：）");
        petsFamilyListBean.setContentImages(R.mipmap.ic_critter4);
        petsFamilyListBean.setGood("520");
        petsFamilyListBean.setTranslate("52");
        listPetsFamilyBeen.add(petsFamilyListBean);
        //////
        petsFamilyListBean = new PetsCritttersFragmentListBean();
        petsFamilyListBean.setAvatar(R.drawable.ic_yy_head);
        petsFamilyListBean.setTime("2017-3-28 15:30");
        petsFamilyListBean.setContent("有一个人养了一百只宠物狗，" +
                "有一天，他问其中的一只狗说：“你一天中最喜欢做那三件事？”，" +
                "它回答说：“吃饭，睡觉，打豆豆”。他问了99只狗同样的问题，" +
                "99只狗回答了同样的答案。最后，他问第100只狗同样的问题，" +
                "它回答说：“吃饭，睡觉”。“你为什么不打豆豆呢？”" +
                "他问。狗回答说：“因为我就是豆豆”。 ：）");
        petsFamilyListBean.setContentImages(R.mipmap.ic_critter5);
        petsFamilyListBean.setGood("520");
        petsFamilyListBean.setTranslate("52");
        listPetsFamilyBeen.add(petsFamilyListBean);
        ///////
        for (int i = 0; i < 3; i++) {
            //////
            petsFamilyListBean = new PetsCritttersFragmentListBean();
            petsFamilyListBean.setAvatar(R.drawable.ic_yy_head);
            petsFamilyListBean.setTime("2017-3-28 15:30");
            petsFamilyListBean.setContent("有一个人养了一百只宠物狗，" +
                    "有一天，他问其中的一只狗说：“你一天中最喜欢做那三件事？”，" +
                    "它回答说：“吃饭，睡觉，打豆豆”。他问了99只狗同样的问题，" +
                    "99只狗回答了同样的答案。最后，他问第100只狗同样的问题，" +
                    "它回答说：“吃饭，睡觉”。“你为什么不打豆豆呢？”" +
                    "他问。狗回答说：“因为我就是豆豆”。 ：）");
            petsFamilyListBean.setContentImages(R.drawable.dog);
            petsFamilyListBean.setGood("520");
            petsFamilyListBean.setTranslate("52");
            listPetsFamilyBeen.add(petsFamilyListBean);
        }

      //  swipeRefreshLayout.setRefreshing(false);
    }
}
