package cn.bmob.imdemo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviActivity;
import cn.bmob.imdemo.event.FinishEvent;
import cn.bmob.imdemo.model.BaseModel;
import cn.bmob.imdemo.model.UserModel;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

/**注册界面
 * @author :smile
 * @project:RegisterActivity
 * @date :2016-01-15-18:23
 */
public class RegisterActivity extends ParentWithNaviActivity {

    PassWordChange passWordChange;
    @Bind(R.id.et_username)
    EditText et_username;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.et_password_again)
    EditText et_password_again;

    @Bind(R.id.btn_register)
    Button btn_register;

    @Override
    protected String title() {
        return "注册";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initNaviView();
    }

    @OnClick(R.id.btn_register)
    public void onRegisterClick(View view){
        UserModel.getInstance().register(et_username.getText().toString(),
                et_password.getText().toString(),et_password_again.getText().toString(),
                new LogInListener() {
            @Override
            public void done(Object o, BmobException e) {
                if(e==null){
                    passWordChange = new PassWordChange();
                    String queryname = et_username.getText().toString();
                    String querycontent = et_password_again.getText().toString();
                    passWordChange.setName( queryname );
                    passWordChange.setPws( querycontent );
                    passWordChange.save(RegisterActivity.this,new SaveListener(){
                                @Override
                                public void postOnFailure(int code, String msg) {
                                    super.postOnFailure(code, msg);
                                }

                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onFailure(int i, String s) {

                                }
                            });
                    EventBus.getDefault().post(new FinishEvent());
                    startActivity(MainActivity.class, null, true);
                }else{

                    if(e.getErrorCode()== BaseModel.CODE_NOT_EQUAL){
                        et_password_again.setText("");
                    }
                    toast(e.getMessage()+"("+e.getErrorCode()+")");
                }
            }
        });
    }

}
