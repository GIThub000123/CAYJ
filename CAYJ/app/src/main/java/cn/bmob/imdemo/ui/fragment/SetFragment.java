package cn.bmob.imdemo.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviFragment;
import cn.bmob.imdemo.bean.User;
import cn.bmob.imdemo.model.UserModel;
import cn.bmob.imdemo.ui.LoginActivity;
import cn.bmob.imdemo.ui.UserInfoActivity;
import cn.bmob.newim.BmobIM;
import cn.bmob.v3.BmobUser;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**设置
 * @author :smile
 * @project:SetFragment
 * @date :2016-01-25-18:23
 */
public class SetFragment extends ParentWithNaviFragment {

    public static TextView tv_set_name;

    @Bind(R.id.iv_avator)
    ImageView iv_avator;

    @Bind(R.id.layout_info)
    RelativeLayout layout_info;

    @Override
    protected String title() {
        return "个人资料";
    }

    public static SetFragment newInstance() {
        SetFragment fragment = new SetFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static void refreshName(){
        String username = UserModel.getInstance().getCurrentUser().getUsername();
        tv_set_name.setText(TextUtils.isEmpty(username)?"":username);
    }

    public SetFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_set,container, false);
        tv_set_name = (TextView)rootView.findViewById(R.id.tv_set_name);
        initNaviView();
        ButterKnife.bind(this, rootView);
        String username = UserModel.getInstance().getCurrentUser().getUsername();
        tv_set_name.setText(TextUtils.isEmpty(username)?"":username);

        String avatar = UserModel.getInstance().getCurrentUser().getAvatar();
        Glide.with(mActivity)
                .load(avatar)
                .bitmapTransform(new CropCircleTransformation(mActivity))
                .error(R.mipmap.head)
                .into(iv_avator);


        return rootView;
    }

    @OnClick(R.id.layout_info)
    public void onInfoClick(View view){
        Bundle bundle = new Bundle();
        bundle.putSerializable("u", BmobUser.getCurrentUser(getActivity(),User.class));
        startActivity(UserInfoActivity.class,bundle);
    }

    @OnClick(R.id.btn_logout)
    public void onLogoutClick(View view){
        UserModel.getInstance().logout();
        //可断开连接
        BmobIM.getInstance().disConnect();
        getActivity().finish();
        startActivity(LoginActivity.class,null);
    }
}
