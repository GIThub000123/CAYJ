package cn.bmob.imdemo.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ImageLoaderFactory;
import cn.bmob.imdemo.base.ParentWithNaviActivity;
import cn.bmob.imdemo.bean.AddFriendMessage;
import cn.bmob.imdemo.bean.Friend;
import cn.bmob.imdemo.bean.Person;
import cn.bmob.imdemo.bean.User;
import cn.bmob.imdemo.db.NewFriend;
import cn.bmob.imdemo.db.NewFriendManager;
import cn.bmob.imdemo.model.UserModel;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.core.BmobIMClient;
import cn.bmob.newim.listener.MessageSendListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * 用户资料
 */
public class UserInfoActivity extends ParentWithNaviActivity {

    @Bind(R.id.iv_avator)
    ImageView iv_avator;
    @Bind(R.id.tv_name)
    TextView tv_name;

    @Bind(R.id.btn_add_friend)
    Button btn_add_friend;
    @Bind(R.id.btn_chat)
    Button btn_chat;

    @Bind(R.id.layout_name)
    RelativeLayout rl;

    User user;
    BmobIMUserInfo info;
    Person p;
    String url;
    String objectIs;

    @Override
    protected String title() {
        return "个人资料";
    }

    @Override
    public Object right() {
        return "编辑";
    }

    @Override
    public ParentWithNaviActivity.ToolBarListener setToolBarListener() {
        return new ParentWithNaviActivity.ToolBarListener() {
            @Override
            public void clickLeft() {

            }

            @Override
            public void clickRight() {

            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initNaviView();
        user = (User) getBundle().getSerializable("u");


//        校验是否是自己
        if (user.getObjectId().equals(getCurrentUid())) {
            btn_add_friend.setVisibility(View.GONE);
            btn_chat.setVisibility(View.GONE);
            tv_name.setClickable(true);
        } else {
            btn_add_friend.setVisibility(View.VISIBLE);
            tv_name.setClickable(false);
            btn_chat.setVisibility(View.VISIBLE);
          /*  NewFriend local = NewFriendManager.getInstance(getApplicationContext()).
                    getNewFriend(user.getObjectId());
            if (local != null) {
                // 校验添加好友中是否已是好友的情况
                btn_add_friend.setEnabled(false);
                btn_add_friend.setText("已添加好友");
            }*/
        }
        //  构造聊天方的用户信息:传入用户id、用户名和用户头像三个参数
        info = new BmobIMUserInfo(user.getObjectId(), user.getUsername(), user.getAvatar());
        ImageLoaderFactory.getLoader().loadAvator(iv_avator, user.getAvatar(), R.mipmap.head);
        tv_name.setText(user.getUsername());
        //修改姓名
      /*  btn_ChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("data", Context.MODE_PRIVATE);
                String token = sp.getString("local_token", "");
                user.setSessionToken(token);
                user.setUsername("gengqing");
                user.setPassword("123");

                user.update(getApplicationContext(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(UserInfoActivity.this, "changenameonsuccess", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        // Log.d("fail",i+"****"+s);
                        Toast.makeText(UserInfoActivity.this, "changenameonFailure" + i + "***" + s, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/
        /*tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("data", Context.MODE_PRIVATE);
                String token = sp.getString("local_token","");
                user.setSessionToken(token);
                user.setUsername("gengqing");
            }
        });*/
        iv_avator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                做判断，只有登陆用户自己才能点击上传自己的头像
                if (user.getObjectId().equals(getCurrentUid())) {
                    Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                    galleryIntent.setType("image/*");//图片
                    startActivityForResult(galleryIntent, 0);   //跳转，传递打开相册请求码
                }
            }
        });
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("u", BmobUser.getCurrentUser(getApplicationContext(), User.class));
                startActivity(setUserInfoActivity.class, bundle);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        } else {
            switch (requestCode) {
                case 0:
                    Uri uri = data.getData();
                    Log.d("uri", uri + "******");
                    resizeImage(uri);
//                     将获取到的uri转换为String型
                    String[] images = {MediaStore.Images.Media.DATA};// 将图片URI转换成存储路径
                  //  String sortOrder = uri.getEncodedQuery();
                    Cursor cursor = this
                            .managedQuery(uri, images, null, null,null);
                    int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    String img_url = cursor.getString(index);
                    Log.d("uri", "*****" + img_url + "******");
                    upload(img_url);

                    break;
                case 1:
                    if (data != null) {
                        showResizeImage(data);
                    }
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.btn_add_friend)
    public void onAddClick(View view) {
        sendAddFriendMessage();
    }

    /**
     * 发送添加好友的请求
     */
    private void sendAddFriendMessage() {
        // 启动一个会话，如果isTransient设置为true,则不会创建在本地会话表中创建记录，
        //  设置isTransient设置为false,则会在本地数据库的会话列表中先创建（如果没有）与该用户的会话信息，
        //   且将用户信息存储到本地的用户表中
        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info, true, null);
        //  这个obtain方法才是真正创建一个管理消息发送的会话
        BmobIMConversation conversation = BmobIMConversation.obtain(BmobIMClient.getInstance(), c);
        AddFriendMessage msg = new AddFriendMessage();
        User currentUser = BmobUser.getCurrentUser(this, User.class);
        msg.setContent("很高兴认识你，可以加个好友吗?");//给对方的一个留言信息
        Map<String, Object> map = new HashMap<>();
        map.put("name", currentUser.getUsername());//发送者姓名，这里只是举个例子，
        // 其实可以不需要传发送者的信息过去
        map.put("avatar", currentUser.getAvatar());//发送者的头像
        map.put("uid", currentUser.getObjectId());//发送者的uid
        msg.setExtraMap(map);
        conversation.sendMessage(msg, new MessageSendListener() {
            @Override
            public void done(BmobIMMessage msg, BmobException e) {
                if (e == null) {//发送成功
                    toast("好友请求发送成功，等待验证");
                    btn_add_friend.setEnabled(false);
                    btn_add_friend.setText("已发送");

                } else {//发送失败
                    toast("发送失败:" + e.getMessage());
                    btn_add_friend.setEnabled(true);
                    btn_add_friend.setText("添加好友");
                }
            }
        });
    }

    //     重塑图片大小
    public void resizeImage(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");// 可以裁剪
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 1);// 跳转，传递调整大小请求码
    }

    //    显示图片
    private void showResizeImage(Intent data) {
        View view = LinearLayout.inflate(UserInfoActivity.this, R.layout.activity_main, null);
        ImageView ivHead = (ImageView) view.findViewById(R.id.bmob_update_id_cancel);
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            // ivHead.setImageDrawable(drawable);
        }
    }

    //     图片上传
    private void upload(String imgpath) {
        final BmobFile icon = new BmobFile(new File(imgpath));
        String imgpath2 = "sdcard/love.jpg";
        final BmobFile icon2 = new BmobFile(new File(imgpath));
        icon.upload(UserInfoActivity.this, new UploadFileListener() {
            @Override
            public void onSuccess() {
                p = new Person();
                p.setIcon(icon);
                p.setAvatar("hh");
                p.setUsername(user.getUsername());
                p.save(getApplicationContext(), new SaveListener() {

                    @Override
                    public void onSuccess() {
                        Toast.makeText(UserInfoActivity.this, "图片上传成功", Toast.LENGTH_SHORT).show();

                        url = icon.getFileUrl(getApplicationContext());
                        //   uploadother(url);
                        Message message = Message.obtain();
                        message.arg1 = 1;
                        handler.sendMessage(message);
                        Log.d("url11", url + "*****");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(UserInfoActivity.this, "图片上传shibai1" + s.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(UserInfoActivity.this, "图片上传shibai2", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @OnClick(R.id.btn_chat)
    public void onChatClick(View view) {
        // 启动一个会话，设置isTransient设置为false,则会在本地数据库的会话列表中先创建（如果没有）
        // 与该用户的会话信息，且将用户信息存储到本地的用户表中
        BmobIMConversation c = BmobIM.getInstance().startPrivateConversation(info, false, null);
        Bundle bundle = new Bundle();
        bundle.putSerializable("c", c);
        startActivity(ChatActivity.class, bundle, false);
    }

    public Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            //  可变的Sessiontoken c05870a2402f1f2680fad43b62b8c176
            SharedPreferences sp = getSharedPreferences("data", Context.MODE_PRIVATE);
            String token = sp.getString("local_token", "");
            user.setSessionToken(token);
            user.setAvatar(url);

            user.update(getApplicationContext(), new UpdateListener() {
                @Override
                public void onSuccess() {
                    Toast.makeText(UserInfoActivity.this, "添加URL数据成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int i, String s) {
                    Log.d("fail", i + "****" + s);
                    Toast.makeText(UserInfoActivity.this, "添加URL数据失败" + i + "***" + s, Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}
