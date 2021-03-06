package cn.bmob.imdemo.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.BaseActivity;
import cn.bmob.imdemo.bean.User;
import cn.bmob.imdemo.event.FinishEvent;
import cn.bmob.imdemo.model.UserModel;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**登陆界面
 * @author :smile
 * @project:LoginActivity
 * @date :2016-01-15-18:23
 */
public class LoginActivity extends BaseActivity {

    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.btn_login)
    Button btn_login;
    @Bind(R.id.tv_register)
    TextView tv_register;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         sp = getSharedPreferences("data", Context.MODE_PRIVATE);
        String un = sp.getString("username", "");
        String up = sp.getString("userpassword", "");

        if(un!=null&up!=null){
            et_username.setText(un);
            et_password.setText(up);
        }
    }

    @OnClick(R.id.btn_login)
    public void onLoginClick(View view){

        UserModel.getInstance().login(et_username.getText().toString(),
                et_password.getText().toString(),
                new LogInListener() {

            @Override
            public void done(Object o, BmobException e) {
                if (e == null) {
                    User user =(User)o;
                    BmobIM.getInstance().updateUserInfo(new BmobIMUserInfo(user.getObjectId(),
                            user.getUsername(), user.getAvatar()));

                    String local_token = ((User) o).getSessionToken();
                    Log.d("fail",local_token);

                    sp.edit().putString("local_token",local_token).commit();
                    sp.edit().putString("username",et_username.getText().toString()).commit();
                    sp.edit().putString("userpassword",et_password.getText().toString()).commit();

                    startActivity(MainActivity.class, null, true);
                } else {
                    toast(e.getMessage() + "(" + e.getErrorCode() + ")");
                }
            }
        });
    }

    @OnClick(R.id.tv_register)
    public void onRegisterClick(View view){
        startActivity(RegisterActivity.class, null, false);
    }

    @Subscribe
    public void onEventMainThread(FinishEvent event){
        finish();
    }
}
