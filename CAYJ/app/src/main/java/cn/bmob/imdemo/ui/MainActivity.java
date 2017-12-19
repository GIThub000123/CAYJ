package cn.bmob.imdemo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.BaseActivity;
import cn.bmob.imdemo.bean.User;
import cn.bmob.imdemo.db.NewFriendManager;
import cn.bmob.imdemo.event.RefreshEvent;
import cn.bmob.imdemo.ui.fragment.ConsultAppealFragemnt;
import cn.bmob.imdemo.ui.fragment.ContactFragment;
import cn.bmob.imdemo.ui.fragment.ConversationFragment;
import cn.bmob.imdemo.ui.fragment.PetsCritttersFragment;
import cn.bmob.imdemo.ui.fragment.PetsFamilyFragemnt;
import cn.bmob.imdemo.ui.fragment.SetFragment;
import cn.bmob.imdemo.ui.fragment.StoreFragemnt;
import cn.bmob.imdemo.util.IMMLeaks;
import cn.bmob.newim.BmobIM;
import cn.bmob.newim.core.ConnectionStatus;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.newim.event.OfflineMessageEvent;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.newim.listener.ConnectStatusChangeListener;
import cn.bmob.newim.listener.ObseverListener;
import cn.bmob.newim.notification.BmobNotificationManager;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

import static cn.bmob.v3.c.darkness.log;

/**
 * @author :smile
 * @project:MainActivity
 * @date :2016-01-15-18:23
 */
public class MainActivity extends SlidingFragmentActivity implements ObseverListener {

    @Bind(R.id.btn_petsfamlity)
    Button btn_petsfamlity;     //1

    @Bind(R.id.btn_consultappeal)
    Button btn_consultappeal;       //2

    @Bind(R.id.btn_conversation)
    Button btn_conversation;        //3

    @Bind(R.id.btn_contact)
    Button btn_storefamily;         //4

    @Bind(R.id.btn_set)
    Button btn_petscrittersfragmet;     //5

    @Bind(R.id.iv_conversation_tips)
    ImageView iv_conversation_tips;

    @Bind(R.id.iv_contact_tips)
    ImageView iv_contact_tips;

    private Button[] mTabs;
    private PetsFamilyFragemnt petsFamilyFragemnt; //1
    private ConsultAppealFragemnt consultAppealFragemnt; //2
    private ConversationFragment conversationFragment;   //3
    private StoreFragemnt storeFragemnt;             //4
    private PetsCritttersFragment petsCritttersFragment;  //5

    private SetFragment setFragment;
    private ContactFragment contactFragment;

    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // SlidingMenu
        Bmob.initialize(this, "3d86a961f57e881009e774ce40ab47db");// 初始化Bmob
        //connect server
        User user = BmobUser.getCurrentUser(this, User.class);
        BmobIM.connect(user.getObjectId(), new ConnectListener() {
            @Override
            public void done(String uid, BmobException e) {
                if (e == null) {
                    Logger.i("connect success");
                    //服务器连接成功就发送一个更新事件，同步更新会话及主页的小红点
                    EventBus.getDefault().post(new RefreshEvent());
                } else {
                    Logger.e(e.getErrorCode() + "/" + e.getMessage());
                }
            }
        });
        //监听连接状态，也可通过BmobIM.getInstance().getCurrentStatus()来获取当前的长连接状态
        BmobIM.getInstance().setOnConnectStatusChangeListener(new ConnectStatusChangeListener() {
            @Override
            public void onChange(ConnectionStatus status) {
                toast("" + status.getMsg());
            }
        });
        //解决leancanary提示InputMethodManager内存泄露的问题
        IMMLeaks.fixFocusedViewLeak(getApplication());
        initView();
    }


    protected void initView() {
        mTabs = new Button[7];
        mTabs[0] = btn_petsfamlity;
        mTabs[1] = btn_consultappeal;
        mTabs[2] = btn_conversation;
        mTabs[3] = btn_storefamily;
        mTabs[4] = btn_petscrittersfragmet;
        mTabs[0].setSelected(true);
        initTab();
    }

    private void initTab() {
        setBehindContentView(R.layout.fragmentleftmenu);

        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        slidingMenu.setSecondaryMenu(R.layout.fragmentrightmenu);
        slidingMenu.setBehindOffset(255);

        petsFamilyFragemnt = new PetsFamilyFragemnt();
        consultAppealFragemnt = new ConsultAppealFragemnt();
        conversationFragment = new ConversationFragment();
        storeFragemnt = new StoreFragemnt();
        petsCritttersFragment = new PetsCritttersFragment();

        setFragment = new SetFragment();
        contactFragment = new ContactFragment();

        fragments = new Fragment[]{petsFamilyFragemnt, consultAppealFragemnt, conversationFragment,
                storeFragemnt,petsCritttersFragment,
                contactFragment, setFragment};
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, petsFamilyFragemnt)
                .add(R.id.fragment_container, consultAppealFragemnt)
                .add(R.id.fragment_container, conversationFragment)
                .add(R.id.fragment_container, storeFragemnt)
                .add(R.id.fragment_container, petsCritttersFragment)
                //    .replace(R.id.fragment_container, contactFragment)
                .replace(R.id.fl_leftmenu, setFragment)
                .replace(R.id.fl_rightmenu, contactFragment)
                //   .hide(contactFragment)
                .hide(consultAppealFragemnt)
                .hide(conversationFragment)
                .hide(storeFragemnt)
                .hide(petsCritttersFragment)
                .show(petsFamilyFragemnt).commit();
    }

    public void onTabSelect(View view) {
        switch (view.getId()) {
            case R.id.btn_petsfamlity:
                index = 0;
                break;
            case R.id.btn_consultappeal:
                index = 1;
                break;
            case R.id.btn_conversation:
                index = 2;
                break;
            case R.id.btn_contact:
                index = 3;
                break;
            case R.id.btn_set:
                index = 4;
                break;
        }
        onTabIndex(index);
    }

    public void onTabIndex(int index) {
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
            mTabs[currentTabIndex].setSelected(false);
            mTabs[index].setSelected(true);
            currentTabIndex = index;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //显示小红点
        checkRedPoint();
        //进入应用后，通知栏应取消
        BmobNotificationManager.getInstance(this).cancelNotification();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        //清理导致内存泄露的资源
        BmobIM.getInstance().clear();
    }

    /**
     * 注册消息接收事件
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        checkRedPoint();
    }

    /**
     * 注册离线消息接收事件
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(OfflineMessageEvent event) {
        checkRedPoint();
    }

    /**
     * 注册自定义消息接收事件
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(RefreshEvent event) {
        log("---主页接收到自定义消息---");
        checkRedPoint();
    }

    //消息提示小圆点
    private void checkRedPoint() {
        int count = (int) BmobIM.getInstance().getAllUnReadCount();
        if (count > 0) {
            iv_conversation_tips.setVisibility(View.VISIBLE);
        } else {
            iv_conversation_tips.setVisibility(View.GONE);
        }
        //是否有好友添加的请求
        if (NewFriendManager.getInstance(this).hasNewFriendInvitation()) {
            iv_contact_tips.setVisibility(View.VISIBLE);
        } else {
            iv_contact_tips.setVisibility(View.GONE);
        }
    }

    public SetFragment getSetFragment() {
        return setFragment;
    }

    protected final static String NULL = "";
    private Toast toast;

    public void toast(final Object obj) {
        try {
            runOnMain(new Runnable() {

                @Override
                public void run() {
                    if (toast == null)
                        toast = Toast.makeText(MainActivity.this,
                                NULL, Toast.LENGTH_SHORT);
                    toast.setText(obj.toString());
                    //toast.setView();
                    toast.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void runOnMain(Runnable runnable) {
        runOnUiThread(runnable);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

}
