package com.wenoun.library.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wenoun.library.R;
import com.wenoun.library.button.ArrMenuButton;
import com.wenoun.library.button.TTogleButton;
import com.wenoun.library.dialog.AlertDialog;
import com.wenoun.library.image.ImageUtils;
import com.wenoun.library.toast.TToast;
import com.wenoun.library.util.ImageUtil;

import java.util.ArrayList;

//import wenoun.in.library.R;

/**
 * Created by jeyhoon on 15. 12. 19..
 */
public class TActivity extends Activity {
    private Animation aniShow, aniHide;
    protected Context ctx;
    protected String TAG = "tag";
    private LinearLayout menuLayout = null;
    private LinearLayout mainRoot = null;
    private LinearLayout mainLayout = null;
    private View root = null;
    //    private LinearLayout rootLayout = null;
    private LinearLayout menuRoot = null;
    private LinearLayout moreDialogRoot = null;
    private View tmainRoot;
    private View backView = null;
    private View moreBackView = null;
    private boolean isMenuOpen = false;
    private boolean hasMenu = false;
    private TTogleButton togleBtn = null;
    private ArrMenuButton arrBtn = null;
    public static final int LAYOUT_THEME_ARR_MENU = 0;
    public static final int LAYOUT_THEME_MENU_MENU = 1;
    public static final int LAYOUT_THEME_MORE_MENU = 2;
    public static final int LAYOUT_THEME_APP_ICON = 3;
    public static final int BUTTON_STYLE_CUSTOM = -1;
    public static final int BUTTON_STYLE_ADD = 0;
    public static final int BUTTON_STYLE_MORE = 1;
    public static final int BUTTON_STYLE_SETTING = 2;
    public static final int BUTTON_STYLE_TOGGLE = 3;

    public static final int MENU_TYPE_SLIDING = 0;
    public static final int MENU_TYPE_CORVER = 1;
    private int menuType = MENU_TYPE_SLIDING;
    private int LAYOUT_THEME = LAYOUT_THEME_ARR_MENU;
    private boolean isLayoutMain = true;
    private int menuWidth = 0;

    private boolean showMenuBar = true;

    public boolean isRunning = false;
    public static boolean isAppRunning=false;
    private boolean isTwoPressBack = false;
    private boolean mIsBackButtonTouched = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                mIsBackButtonTouched = false;
            }
        }
    };

    private Dialog progressDialog;

    private float downX = 0;
    private float downY = 0;

    private boolean menuBarScrolled = true;


    /*
    Overriding Function
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        TAG = this.getLocalClassName();
        aniShow = AnimationUtils.loadAnimation(this, R.anim.left_in);
        aniHide = AnimationUtils.loadAnimation(this, R.anim.left_out);
    }

    @Override
    public void onBackPressed() {
        if (isMenuOpen) {
            closeMenu();
            if (LAYOUT_THEME == LAYOUT_THEME_ARR_MENU) {
                ((ArrMenuButton) mainRoot.findViewById(R.id.tmain_menu_arr_btn)).setShown(false);
            }
        } else if (null != moreDialogRoot && moreDialogRoot.isShown()) {
            closeMoreMenu();
        } else {
            if (isTwoPressBack) {
                if (mIsBackButtonTouched == false) {
                    mIsBackButtonTouched = true;
                    showToast(getString(R.string.back_pressed_quit));
                    mHandler.sendEmptyMessageDelayed(0, 2000);
                } else {
                    superOnBackPressed();
                }
            } else {
                superOnBackPressed();
            }
        }

    }

    public void superOnBackPressed() {
        dismissProgress();
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        isRunning = true;
        isAppRunning=true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isAppRunning=false;
        super.onPause();
    }
    /*
    Set Press Button Count Default : false-One Press.
     */

    public void setTwoPressBack(boolean is) {
        isTwoPressBack = is;
    }

    public boolean isTwoPressBack() {
        return isTwoPressBack;
    }

    public void setTwoPressBack() {
        setTwoPressBack(true);
    }

    public void setOnePressBack() {
        setTwoPressBack(false);
    }

    /*
    Set Scroll MenuBar
     */

    public AbsListView.OnScrollListener getScrollListener(final View v){
        return new AbsListView.OnScrollListener() {
            private float downY=0F;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                Log.d(TAG,"visibleItemCount : "+visibleItemCount+" totalItemCount : "+totalItemCount);
                if (visibleItemCount < totalItemCount) {

                    v.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
//                            Log.d(TAG, "listView.getLastVisiblePosition() : " + listView.getLastVisiblePosition() + " listView.getChildCount() : " + listView.getChildCount());
                            int action = event.getAction();
                            if (action == MotionEvent.ACTION_DOWN) {
                                downY = event.getY();
                            } else if (action == MotionEvent.ACTION_MOVE) {
                                final float upY = event.getY();
                                final float min = (downY - upY);
                                final float dip50 = ImageUtils.pxToDp(ctx, 50);
                                if (min > 0) {
                                    setScrollUpMenuBar(min);
                                } else if (min < 0) {
                                    setScrollDownMenuBar(min);
                                }
                            }
                            return false;
                        }
                    });
                } else {
                    v.setOnTouchListener(null);
                }
            }
        };
    }

    public boolean isScrollMenuBar() {
        return menuBarScrolled;
    }

    public void setScrollMenuBar(boolean is) {
        menuBarScrolled = is;
        if (is)
            setScrollMenuBar();
    }

    public boolean isShownMenuBar(){
        return menuRoot.isShown();
    }
    private float menuRootY=0;
    @TargetApi(11)
    public void setScrollUpMenuBar(float up){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float y=menuRoot.getMeasuredHeight()-Math.abs(up);
        if(y<=0){
            ViewGroup.LayoutParams params=menuRoot.getLayoutParams();
            params.height=0;
            menuRoot.setLayoutParams(params);
            isShowMenuBar=false;
        }else{

            ViewGroup.LayoutParams params=menuRoot.getLayoutParams();
            params.height=(int)y;
            menuRoot.setLayoutParams(params);

        }
    }
    public boolean isShowMenuBar=true;
    private int orgMenuRootHeight;
    @TargetApi(11)
    public void setScrollDownMenuBar(float down){
        final float dip50 = ImageUtils.dpToPx(ctx, 50);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float y=menuRoot.getMeasuredHeight()+Math.abs(down);
        if(y>=dip50){
            ViewGroup.LayoutParams params=menuRoot.getLayoutParams();
            params.height=(int)dip50;
            menuRoot.setLayoutParams(params);
            isShowMenuBar=true;
        }else{
            ViewGroup.LayoutParams params=menuRoot.getLayoutParams();
            params.height=(int)y;
            menuRoot.setLayoutParams(params);

        }
    }
    @TargetApi(11)
    public void setScrollMenuBar(float y){
        float min=y;
        if(min>0){
            setScrollUpMenuBar(min);
        }else if(min<0){
            setScrollDownMenuBar(min);
        }
        return;
    }

    @TargetApi(11)
    private void setScrollMenuBar() {
        if (null != menuRoot && menuBarScrolled && isShowMenuBar()) {
            root.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    int action = event.getAction();

                    float upX = 0;
                    float upY = 0;
                    final float dip50 = (float) ImageUtils.dpToPx(ctx, 50);
                    if (action == MotionEvent.ACTION_DOWN) {
                        downX = event.getX();
                        downY = event.getY();
                    } else if (action == MotionEvent.ACTION_UP) {

                    } else if (action == MotionEvent.ACTION_MOVE) {
                        upX = event.getX();
                        upY = event.getY();
                        final float min = (downY - upY);
                        if(min>0){
                            setScrollUpMenuBar(min);
                        }else if(min<0){
                            setScrollDownMenuBar(min);
                        }

                    }


                    return true;

                }
            });
        }
    }
    public AbsListView.OnScrollListener getScrollMenuBarScrollListener(){

        return new AbsListView.OnScrollListener() {
            private float downY = 0;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d(TAG,"visibleItemCount : "+visibleItemCount+" totalItemCount : "+totalItemCount);
                if (visibleItemCount < totalItemCount) {
                    view.setOnTouchListener(new View.OnTouchListener() {


                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            int lastPos = ((ListView) v).getLastVisiblePosition();
                            int childCount = ((ListView) v).getChildCount() - 1;
                            Log.d(TAG, "lp : " + lastPos + " cC : " + childCount);
                            if (lastPos < childCount) {
                                int action = event.getAction();
                                if (action == MotionEvent.ACTION_DOWN) {
                                    downY = event.getY();
                                } else if (action == MotionEvent.ACTION_MOVE) {
                                    final float upY = event.getY();
                                    final float min = (downY - upY);
                                    Log.d(TAG,"min : "+min);
                                    final float dip50 = ImageUtils.pxToDp(ctx, 50);
                                    if (min > 0) {
                                        setScrollUpMenuBar(min);
//                                        if(isShowMenuBar)
//                                            return true;

                                    } else if (min < 0) {
                                        setScrollDownMenuBar(min);
//                                        if(!isShowMenuBar)
//                                            return true;

                                    }
//                                    return true;


                                }
                            }
                            return false;
                        }
                    }                );

                } else {
                    view.setOnTouchListener(null);
                }
            }
        };
    }



    /*
    Set show MenuBar
     */

    public boolean isShowMenuBar() {
        return showMenuBar;
    }

    public void closeMenuBar() {
        showMenuBar(false);
    }

    public void showMenuBar() {
        showMenuBar(true);
    }

    public void showMenuBar(boolean is) {
        showMenuBar = is;
        if (null != menuRoot)
            if (showMenuBar) {
                menuRoot.setVisibility(View.VISIBLE);
            } else {
                menuRoot.setVisibility(View.GONE);
            }
    }

    /*
    Set Main or Back
     */

    public void setMainlayout() {
        setLayoutStyle(true);
        setTwoPressBack();
    }

    public void setBackLayout() {
        setLayoutStyle(false);
    }

    public void setLayoutStyle(boolean isLayoutMain) {
        this.isLayoutMain = isLayoutMain;
    }

    /*
    Set Menu Layout Theme
     */

    private void setLayoutTheme() {
        if (isLayoutMain) {
            arrBtn = (ArrMenuButton) (mainRoot.findViewById(R.id.tmain_menu_arr_btn));
            switch (LAYOUT_THEME) {
                default:
                case LAYOUT_THEME_ARR_MENU:
                    arrBtn.setVisibility(View.VISIBLE);
                    mainRoot.findViewById(R.id.tmain_menu_menu_btn).setVisibility(View.GONE);
                    mainRoot.findViewById(R.id.tmain_menu_more_btn).setVisibility(View.GONE);
                    mainRoot.findViewById(R.id.tmain_menu_app_icon).setVisibility(View.GONE);
                    break;
                case LAYOUT_THEME_MENU_MENU:
                    arrBtn.setVisibility(View.GONE);
                    mainRoot.findViewById(R.id.tmain_menu_menu_btn).setVisibility(View.VISIBLE);
                    mainRoot.findViewById(R.id.tmain_menu_more_btn).setVisibility(View.GONE);
                    mainRoot.findViewById(R.id.tmain_menu_app_icon).setVisibility(View.GONE);
                    break;
                case LAYOUT_THEME_MORE_MENU:
                    arrBtn.setVisibility(View.GONE);
                    mainRoot.findViewById(R.id.tmain_menu_menu_btn).setVisibility(View.GONE);
                    mainRoot.findViewById(R.id.tmain_menu_more_btn).setVisibility(View.VISIBLE);
                    mainRoot.findViewById(R.id.tmain_menu_app_icon).setVisibility(View.GONE);
                    break;
                case LAYOUT_THEME_APP_ICON:
                    arrBtn.setVisibility(View.GONE);
                    mainRoot.findViewById(R.id.tmain_menu_menu_btn).setVisibility(View.GONE);
                    mainRoot.findViewById(R.id.tmain_menu_more_btn).setVisibility(View.GONE);
                    mainRoot.findViewById(R.id.tmain_menu_app_icon).setVisibility(View.VISIBLE);
                    ((ImageView) mainRoot.findViewById(R.id.tmain_menu_app_icon)).setImageDrawable(ctx.getApplicationInfo().loadIcon(ctx.getPackageManager()));
                    break;
            }
        }
    }

    public void setLayoutTheme(int layoutTheme) {
        if (layoutTheme < LAYOUT_THEME_ARR_MENU || layoutTheme > LAYOUT_THEME_APP_ICON) {
            LAYOUT_THEME = LAYOUT_THEME_ARR_MENU;
        } else {
            LAYOUT_THEME = layoutTheme;
        }
    }

    /*
    Set Menu Open Type(Corver or Sliding)
     */
    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    /*
    SetContentView
     */

    @Override
    public void setContentView(int layoutResID) {
        setContentView(layoutResID, false);
    }

    public void setContentView(int mainLayoutResId, boolean isLayoutMain) {
        setLayoutStyle(isLayoutMain);
        setContentView(0, mainLayoutResId);
    }

    public void setContentView(int menuLayoutResId, int mainLayoutResId) {
        setContentView(menuLayoutResId, mainLayoutResId, LAYOUT_THEME);
    }

    public void setContentView(int menuLayoutResId, int mainLayoutResId, int layoutTheme) {
        setContentView(menuLayoutResId, mainLayoutResId, layoutTheme, menuType);
    }

    public void setContentView(int menuLayoutResId, int mainLayoutResId, int layoutTheme, int _menuType) {
        setMenuType(_menuType);
        setLayoutTheme(layoutTheme);

        menuLayout = new LinearLayout(ctx);
        LinearLayout.LayoutParams menuParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        menuParams.weight = 0;

        menuLayout.setLayoutParams(menuParams);
        menuLayout.setOrientation(LinearLayout.VERTICAL);
        menuLayout.setVisibility(View.GONE);

        tmainRoot = getLayoutInflater().inflate(R.layout.layout_tmain, null);//
        backView = tmainRoot.findViewById(R.id.tmain_back);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
        mainRoot = (LinearLayout) tmainRoot.findViewById(R.id.tmain_root);
        mainLayout = (LinearLayout) mainRoot.findViewById(R.id.tmain_main_root);
        if (isLayoutMain) {
            (menuRoot = (LinearLayout) mainRoot.findViewById(R.id.tmain_menu_root)).setVisibility(View.VISIBLE);
            (mainRoot.findViewById(R.id.tmain_menu_back_root)).setVisibility(View.GONE);
            setLayoutTheme();
        } else {
            (mainRoot.findViewById(R.id.tmain_menu_root)).setVisibility(View.GONE);
            (menuRoot = (LinearLayout) mainRoot.findViewById(R.id.tmain_menu_back_root)).setVisibility(View.VISIBLE);
        }
        if (showMenuBar)
            menuRoot.setVisibility(View.VISIBLE);
        else
            menuRoot.setVisibility(View.GONE);
        moreBackView = tmainRoot.findViewById(R.id.tmain_more_back);
        moreBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                closeMoreMenu();
            }
        });

        moreDialogRoot = (LinearLayout) tmainRoot.findViewById(R.id.tmain_menu_more_dialog);

        LinearLayout.LayoutParams mainViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mainViewParams.weight = 1;
        View main = getLayoutInflater().inflate(mainLayoutResId, null);
        try {
            ((LinearLayout.LayoutParams) main.getLayoutParams()).weight = 1F;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        main.setLayoutParams(mainViewParams);
        mainLayout.addView(main);
        if (0 < menuLayoutResId) {
            hasMenu = true;
            setMenuLayout(menuLayoutResId);
        }

        if (menuType == MENU_TYPE_SLIDING) {
            LinearLayout rootLayout = new LinearLayout(ctx);
            LinearLayout.LayoutParams rootParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            rootLayout.setLayoutParams(rootParams);
            rootLayout.setOrientation(LinearLayout.HORIZONTAL);
            rootLayout.setBackgroundColor(0xffffffff);
            rootLayout.addView(menuLayout);
            rootLayout.addView(tmainRoot);
            root = rootLayout;
            super.setContentView(rootLayout);
        } else if (menuType == MENU_TYPE_CORVER) {
            RelativeLayout rootLayout = new RelativeLayout(ctx);
            RelativeLayout.LayoutParams rootParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            rootLayout.setLayoutParams(rootParams);
            rootLayout.setBackgroundColor(0xffffffff);
            menuLayout.setClickable(true);
            menuLayout.setBackgroundColor(0xffffffff);
            rootLayout.addView(tmainRoot);
            rootLayout.addView(menuLayout);//,rootLayout.getChildCount());
            root = rootLayout;
            super.setContentView(rootLayout);
        }
        orgMenuRootHeight=menuRoot.getMeasuredHeight();
        setScrollMenuBar();


    }

    /*
    Add Menu Button
     */

    public Button addMenuBtn(String txt, View.OnClickListener listener) {
        return addMenuBtn(0xFF000000, txt, listener);
    }

    public View addMenuBtn(int btnStyle, int bgResId, View.OnClickListener listener) {
        final int dip50 = ImageUtil.PxToDp(ctx, 50);
        final int dip5 = ImageUtil.PxToDp(ctx, 5);
        final int dip10 = ImageUtil.PxToDp(ctx, 10);
        return addMenuBtn(btnStyle, bgResId, dip5, listener);
    }

    public Button addMenuBtn(int textColor, String txt, View.OnClickListener listener) {
        final int dip50 = ImageUtil.PxToDp(ctx, 50);
        final int dip5 = ImageUtil.PxToDp(ctx, 5);
        final int dip10 = ImageUtil.PxToDp(ctx, 10);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, dip50);
        layoutParams.weight = 0;
        Button btn = new Button(ctx);
        btn.setBackgroundResource(R.drawable.tr_white_selector);
        btn.setTextSize(16);
        btn.setPadding(dip10, 0, dip10, 0);
        btn.setPaintFlags(btn.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
        btn.setTextColor(textColor);
        btn.setLayoutParams(layoutParams);
        btn.setText(txt);
        btn.setOnClickListener(listener);
        menuRoot.addView(btn);
        return btn;
    }

    public View addMenuBtn(int btnStyle, int bgResId, int $paddingDip, View.OnClickListener listener) {
        final int dip50 = ImageUtil.PxToDp(ctx, 50);
        final int dip5 = ImageUtil.PxToDp(ctx, 5);
        final int dip10 = ImageUtil.PxToDp(ctx, 10);
        final int paddingDip = ImageUtil.PxToDp(ctx, $paddingDip);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dip50, dip50);
        layoutParams.weight = 0;
        if (btnStyle != BUTTON_STYLE_CUSTOM) {
            return addMenuBtn(btnStyle, listener);
        } else {
            ImageView btn = new ImageView(ctx);
            btn.setPadding(paddingDip, paddingDip, paddingDip, paddingDip);
            btn.setClickable(true);
            btn.setBackgroundResource(R.drawable.tr_white_selector);
            btn.setOnClickListener(listener);
            btn.setLayoutParams(layoutParams);
            btn.setImageResource(bgResId);
            menuRoot.addView(btn);
            return btn;
        }
    }

    public View addMenuBtn(int btnStyle, View.OnClickListener listener) {
        final int dip50 = ImageUtil.PxToDp(ctx, 50);
        final int dip5 = ImageUtil.PxToDp(ctx, 5);
        final int dip10 = ImageUtil.PxToDp(ctx, 10);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dip50, dip50);
        layoutParams.weight = 0;
        if (btnStyle != BUTTON_STYLE_TOGGLE) {
            ImageView btn = new ImageView(ctx);
            btn.setPadding(dip5, dip5, dip5, dip5);
            btn.setClickable(true);
            btn.setBackgroundResource(R.drawable.tr_white_selector);
            btn.setOnClickListener(listener);
            switch (btnStyle) {
                case BUTTON_STYLE_ADD:
                    btn.setImageResource(R.drawable.btn_add_white);
                    btn.setPadding(dip10, dip10, dip10, dip10);
                    break;
                case BUTTON_STYLE_MORE:
                    btn.setImageResource(R.drawable.more_white);
                    break;
                case BUTTON_STYLE_SETTING:
                    btn.setImageResource(R.drawable.btn_set_white);
                    btn.setPadding(dip10, dip10, dip10, dip10);
                    break;
            }
            btn.setLayoutParams(layoutParams);
            menuRoot.addView(btn);
            return btn;
        } else {

            return addTogleButton(listener);
        }
    }

    /*
    Menu Togle
     */

    public TTogleButton addTogleButton(View.OnClickListener listener) {
        return setTogleButton(listener, false);
    }

    public TTogleButton setTogleButton(View.OnClickListener listener, boolean isSet) {
        final int dip50 = ImageUtil.PxToDp(ctx, 50);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dip50, dip50);
        layoutParams.weight = 0;
        togleBtn = new TTogleButton(ctx);
        togleBtn.setPadding(0, 0, 0, 0);
        togleBtn.setLayoutParams(layoutParams);
        togleBtn.setOnClickListener(listener);
        togleBtn.setChecked(isSet);
        menuRoot.addView(togleBtn);
        return togleBtn;
    }

    public void setTogleButtonCheck(boolean is) {
        if (null != togleBtn)
            togleBtn.setChecked(is);
    }

    public boolean isTogleButtonSet() {
        return null != togleBtn && togleBtn.isChecked();
    }



    /*
    Set Menu...
     */

    public void setMenuWidth(int widthDp) {
        if (widthDp == LinearLayout.LayoutParams.MATCH_PARENT) {
            menuLayout.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
            menuWidth = widthDp;
        } else if (widthDp == LinearLayout.LayoutParams.WRAP_CONTENT) {
            menuLayout.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
            menuWidth = widthDp;
        } else {
            menuLayout.getLayoutParams().width = ImageUtil.PxToDp(ctx, widthDp);
            menuWidth = ImageUtils.dpToPx(ctx, widthDp);

        }
    }

    public void setMenuLayout(int layoutResId) {
        if (layoutResId > 0) {
            View menu = getLayoutInflater().inflate(layoutResId, null);
            menuLayout.addView(menu);
            hasMenu = true;
        } else {
            hasMenu = false;
        }
    }

    public void setShowMenu() {
        if (!isMenuOpen) {
            openMenu();
        } else {
            closeMenu();
        }
    }

    public void openMenu() {
        if (hasMenu) {
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mainLayout.getLayoutParams();
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            params.width = metrics.widthPixels;
            for (int i = 0; i < mainRoot.getChildCount(); i++) {
                mainRoot.getChildAt(i).getLayoutParams().width = metrics.widthPixels;
            }
            menuLayout.setVisibility(View.VISIBLE);

            if (menuWidth <= metrics.widthPixels * 0.5) {
                menuLayout.getLayoutParams().width = (int) (metrics.widthPixels * 0.5);
            } else if (menuWidth >= metrics.widthPixels * 0.75) {
                menuLayout.getLayoutParams().width = (int) (metrics.widthPixels * 0.75);
            } else {
                menuLayout.getLayoutParams().width = menuWidth;
            }

            menuLayout.startAnimation(aniShow);
            isMenuOpen = true;
            backView.setVisibility(View.VISIBLE);

        }

    }

    public void closeMenu() {
        if (hasMenu) {
            menuLayout.startAnimation(aniHide);
            aniHide.setAnimationListener(closeList);

        }
    }

    Animation.AnimationListener closeList = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            menuLayout.setVisibility(View.GONE);
            isMenuOpen = false;
            backView.setVisibility(View.GONE);
            if (null != arrBtn)
                arrBtn.setShown(false);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    /*
    Getter And Setter.
     */

    public boolean isLayoutMain() {
        return isLayoutMain;
    }

    public boolean isMenuOpen() {
        return isMenuOpen;
    }

    public boolean hasMenu() {
        return hasMenu;
    }

    public boolean isMoreDialogOpen() {
        return null != moreDialogRoot && moreDialogRoot.isShown();
    }


    public String getTTitle() {
        if (isLayoutMain) {
            if (null != mainRoot) {
                return ((TextView) mainRoot.findViewById(R.id.tmain_menu_title)).getText().toString();
            } else {
                return "";
            }
        } else {
            if (null != mainRoot) {
                return ((TextView) mainRoot.findViewById(R.id.tmain_menu_back_title)).getText().toString();
            } else {
                return "";
            }
        }
    }

    public void setTitle(String titleStr) {
        if (isLayoutMain) {
            if (null != mainRoot) {
                ((TextView) mainRoot.findViewById(R.id.tmain_menu_title)).setText(titleStr);
            }
        } else {
            if (null != mainRoot) {
                ((TextView) mainRoot.findViewById(R.id.tmain_menu_back_title)).setText(titleStr);
            }

        }
    }

    /*
    * More Dialog...
    */

    private ArrayList<String> moreMenuList=new ArrayList<String>();
//    private ArrayList<View.OnClickListener> moreMenuLisList=new ArrayList<View.OnClickListener>();
    private OnMenuClickListener onMenuClickListener=null;
    public interface OnMenuClickListener {
        public void onClick(View parent, View v, int position);
    }

    public void addMoreMenu(String menu){
        moreMenuList.add(menu);
        setMoreMenuView();
//        moreMenuLisList.add(listener);
    }
    public void addMoreMenu(ArrayList<String> menu){
        moreMenuList.addAll(menu);
//        moreMenuLisList.addAll(listener);
        setMoreMenuView();
    }
    public void setMoreMenuClickListener(OnMenuClickListener listener){
        onMenuClickListener=listener;
    }
    public void setMoreMenu(ArrayList<String> menu,OnMenuClickListener listener){
        moreMenuList=menu;
        onMenuClickListener=listener;
        setMoreMenuView();
    }
    public void setMoreMenu(String[] menu,OnMenuClickListener listener){
        ArrayList<String> menuList=new ArrayList<String>();
        for(int i=0; i<menu.length;i++){
            menuList.add(menu[i]);
        }
        setMoreMenu(menuList,listener);

    }
    private void setMoreMenuView(){
        if(null==moreDialogRoot)
            return;
        for(int i=moreDialogRoot.getChildCount(); i<moreMenuList.size();i++){
            final int pos=i;
            View v=getMoreMenuItem(moreMenuList.get(pos));
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null!=onMenuClickListener)
                        onMenuClickListener.onClick(moreDialogRoot,v,pos);
                }
            });
            moreDialogRoot.addView(v);
        }
    }
    private View getMoreMenuItem(String menu){
        final int dip20= ImageUtils.dpToPx(ctx, 20);
        final int dip10= ImageUtils.dpToPx(ctx, 10);
        TextView menuView=new TextView(ctx);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity= Gravity.CENTER_VERTICAL;
        menuView.setLayoutParams(params);
        menuView.setText(menu);
        menuView.setTextColor(Color.BLACK);
        menuView.setTextSize(18);
        menuView.setPadding(dip20, dip10, dip20, dip10);
        menuView.setClickable(true);
        menuView.setBackgroundResource(R.drawable.tr_selector);
        menuView.setMinEms(5);

        return menuView;
    }

    public void showMoreMenu() {
        if (null != moreDialogRoot)
            showMoreMenu(!moreDialogRoot.isShown());
    }

    public void showMoreMenu(boolean is) {
        if (is) openMoreMenu();
        else closeMoreMenu();
    }

    public void openMoreMenu() {
        if (null != moreBackView)
            moreBackView.setVisibility(View.VISIBLE);
        if (null != moreDialogRoot)
            moreDialogRoot.setVisibility(View.VISIBLE);
    }

    public void closeMoreMenu() {
        if (null != moreBackView)
            moreBackView.setVisibility(View.GONE);
        if (null != moreDialogRoot)
            moreDialogRoot.setVisibility(View.GONE);
    }

    /*
    * OnClick Function..
    * */

    public void goBack(View v) {
        finish();
    }

    public void showMenu(View v) {
        setShowMenu();
    }



    /*
    StartActivity Function...
     */

    public void startAct(Intent intent) {
        startAct(intent, false, false);
    }

    public void startActRun(Intent intent) {
        startAct(intent, false, true);
    }

    public void startActFinish(Intent intent) {
        startAct(intent, true, false);
    }

    public void startAct(Class<?> cls) {
        startAct(new Intent(ctx, cls));
    }

    public void startActFinish(Class<?> cls) {
        startActFinish(new Intent(ctx, cls));
    }

    public void startAct(Intent intent, boolean isFin, boolean isRunning) {
        startActivity(intent);
        if (isFin) {
            finish();
        }
        this.isRunning = isRunning;
    }

    /*
    Show Toast, AlertDialog, Log, Progress Function...
     */
    public void showToast(String msg) {
        new TToast(ctx).showToast(msg);
    }

    public void showAlertDialog(String title, String msg) {
        new AlertDialog(ctx).setTitle(title).setMsg(msg).show();
    }

    public void Log(String msg) {
        Log.d(this.getClass().getSimpleName(), msg);
    }

    public Dialog getProgressDialog() {
        return getProgressDialog(true);
    }

    public Dialog getProgressDialog(boolean isCancel) {
        Dialog dialog = new Dialog(this, R.style.dialog);
        dialog.setContentView(R.layout.dialog_progress);
        dialog.setCanceledOnTouchOutside(isCancel);
        return dialog;
    }

    public void showProgress() {
        showProgress(true);
    }

    public void showProgress(boolean isCancel) {
        progressDialog = getProgressDialog(isCancel);
        if (null != progressDialog)
            progressDialog.show();
    }

    public void dismissProgress() {
        if (null != progressDialog)
            progressDialog.dismiss();
    }

    /*
    Show Other Apps(Made By WeNoun)
     */

    public void showOtherApps(){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=5954317455002110606")));
    }
}
