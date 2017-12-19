package cn.bmob.imdemo.ui.add;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviActivity;
import cn.bmob.imdemo.bean.DynamicImageAlong;
import cn.bmob.imdemo.bean.User;
import cn.bmob.imdemo.bean.petsbean.PetsDoctorDynamic;
import cn.bmob.imdemo.bean.petsbean.SeekHomeDynamic;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2017/5/30.
 */

public class AddDoctorForPetsDynamic extends ParentWithNaviActivity {

    String url;
    private String img_url;

    DynamicImageAlong dynamicImageAlong;

    PetsDoctorDynamic dynamic;


    private EditText tv_Dynamic_Content;
    private EditText tv_PassVaule;
    private EditText tv_Value;
    private ImageView iv_Dynamic_image;

    @Override
    protected String title() {
        return "";
    }

    @Override
    public Object right() {
        return "发表";
    }

    @Override
    public ToolBarListener setToolBarListener() {
        return new ToolBarListener() {
            @Override
            public void clickLeft() {

            }

            @Override
            public void clickRight() {
                if (img_url != null) {
                    upload(img_url);
                } else {
                    upload();
                }
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_petsmainntain_dynamic_itme);
        initNaviView();
        tv_Dynamic_Content = (EditText) findViewById(R.id.et_dynamic_content);
        tv_Value = (EditText) findViewById(R.id.et_value);
        tv_PassVaule = (EditText) findViewById(R.id.et_passvalue);
        iv_Dynamic_image = (ImageView) findViewById(R.id.iv_dynamic_image);

        iv_Dynamic_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
                galleryIntent.setType("image/*");//图片
                startActivityForResult(galleryIntent, 0);
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
                            .managedQuery(uri, images, null, null, null);
                    int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    img_url = cursor.getString(index);
                    Log.d("uri", "*****" + img_url + "******");
                    //  upload(img_url);

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
/*        View view = LinearLayout.inflate(AddDynamic.this, R.layout.add_dynamic_itme, null);
        ImageView ivHead = (ImageView) view.findViewById(R.id.bmob_update_id_cancel);*/
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            //   ivHead.setImageDrawable(drawable);
            iv_Dynamic_image.setImageDrawable(drawable);
        }
    }


    private void upload() {
        String avatar = BmobUser.getCurrentUser(this, User.class).getAvatar();
        String name = BmobUser.getCurrentUser(this, User.class).getUsername();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        String content = tv_Dynamic_Content.getText().toString();

        String location = "河南理工大学计算机科学与技术学院";

        String value = tv_Value.getText().toString();
        String passvalue = tv_Value.getText().toString();

        dynamic = new PetsDoctorDynamic();
        dynamic.setAvatar(avatar);
        dynamic.setUsername(name);
        dynamic.setTime(time);
        dynamic.setContent(content);
        dynamic.setLocation(location);
        dynamic.setValue(value+"");
        dynamic.setPass_value(passvalue+"");
        dynamic.setContentimage(url);
        dynamic.save(getApplicationContext(), new SaveListener() {

            @Override
            public void onSuccess() {
                Toast.makeText(AddDoctorForPetsDynamic.this, "上传成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(AddDoctorForPetsDynamic.this, "上传失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void upload(String imgpath) {

        Log.d("imgpath", imgpath + "******");
        final BmobFile icon = new BmobFile(new File(imgpath));
        String imgpath2 = "sdcard/love.jpg";
        final BmobFile icon2 = new BmobFile(new File(imgpath));
        icon.upload(AddDoctorForPetsDynamic.this, new UploadFileListener() {
            @Override
            public void onSuccess() {
                dynamicImageAlong = new DynamicImageAlong();
                dynamicImageAlong.setIcon(icon);
                dynamicImageAlong.save(getApplicationContext(), new SaveListener() {

                    @Override
                    public void onSuccess() {
                        Toast.makeText(AddDoctorForPetsDynamic.this, "图片上传成功", Toast.LENGTH_SHORT).show();

                        url = icon.getFileUrl(getApplicationContext());

                        //  url = icon.getUrl();
                        //   uploadother(url);
                        Message message = Message.obtain();
                        message.arg1 = 1;
                        handler.sendMessage(message);

                        //  handler.sendEmptyMessage(0);
                        Log.d("url11", url + "*****");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(AddDoctorForPetsDynamic.this, "图片上传shibai1" + s.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(AddDoctorForPetsDynamic.this, "图片上传shibai2", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            //  可变的Sessiontoken c05870a2402f1f2680fad43b62b8c176
           /* SharedPreferences sp = getSharedPreferences("data", Context.MODE_PRIVATE);
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
            });*/

            upload();
        }
    };

}
