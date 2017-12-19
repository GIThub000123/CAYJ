package cn.bmob.imdemo.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviActivity;
import cn.bmob.imdemo.bean.User;
import cn.bmob.imdemo.ui.fragment.SetFragment;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/3/27.
 */

public class setUserInfoActivity extends ParentWithNaviActivity {
    @Bind(R.id.et_change_name)
    EditText et_change_name;
    @Bind(R.id.et_change_psw)
    EditText et_change_psw;

    private User user;

    @Override
    protected String title() {
        return "修改账号";
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

             /*   MainActivity mainActivity = (MainActivity) getApplicationContext();
                SetFragment setFragment = mainActivity.getSetFragment();
                setFragment.refreshName();*/
                finish();
            }

            @Override
            public void clickRight() {
                String change_name = et_change_name.getText().toString().trim();
                String change_psw = et_change_psw.getText().toString().trim();

                SharedPreferences sp = getSharedPreferences("data", Context.MODE_PRIVATE);
                String token = sp.getString("local_token", "");
                user.setSessionToken(token);
                if (TextUtils.isEmpty(change_name)) {
                    user.setUsername(change_name);
                }
                if (TextUtils.isEmpty(change_psw)) {
                    user.setPassword(change_psw);
                }
                user.update(getApplicationContext(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        //startActivityForresult 处理姓名不刷新问题
                        Toast.makeText(setUserInfoActivity.this, "changenameonsuccess", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(setUserInfoActivity.this, "changenameonFailure" + i + "***" + s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_updateinfo);
        initNaviView();
        user = (User) getBundle().getSerializable("u");
    }
}
