package wenoun.in.library.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import wenoun.in.library.R;
import wenoun.in.library.button.ArrMenuButton;
import wenoun.in.library.button.TTogleButton;
import wenoun.in.library.dialog.AlertDialog;
import wenoun.in.library.image.ImageUtils;
import wenoun.in.library.toast.TToast;
import wenoun.in.library.util.ImageUtil;

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
//    private LinearLayout rootLayout = null;
    private LinearLayout menuRoot = null;
    private View tmainRoot;
    private View backView=null;
    private boolean isMenuOpen = false;
    private boolean hasMenu = false;
    private TTogleButton togleBtn=null;
    public static final int LAYOUT_THEME_ARR_MENU = 0;
    public static final int LAYOUT_THEME_MENU_MENU = 1;
    public static final int LAYOUT_THEME_MORE_MENU = 2;
    public static final int BUTTON_STYLE_CUSTOM=-1;
    public static final int BUTTON_STYLE_ADD = 0;
    public static final int BUTTON_STYLE_MORE = 1;
    public static final int BUTTON_STYLE_SETTING = 2;
    public static final int BUTTON_STYLE_TOGGLE=3;

    public static final int MENU_TYPE_SLIDING=0;
    public static final int MENU_TYPE_CORVER=1;
    private int menuType=MENU_TYPE_SLIDING;
    private int LAYOUT_THEME = LAYOUT_THEME_ARR_MENU;
    private boolean isLayoutMain = true;
    private int menuWidth = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        TAG = this.getLocalClassName();
        aniShow = AnimationUtils.loadAnimation(this, R.anim.left_in);
        aniHide = AnimationUtils.loadAnimation(this, R.anim.left_out);
    }

    public void setMainlayout() {
        setLayoutStyle(true);
    }

    public void setBackLayout() {
        setLayoutStyle(false);
    }

    public void setLayoutStyle(boolean isLayoutMain) {
        this.isLayoutMain = isLayoutMain;
    }

    public boolean isLayoutMain() {
        return isLayoutMain;
    }

    private void setLayoutTheme() {
        if (isLayoutMain) {
            ArrMenuButton arrBtn = (ArrMenuButton) (mainRoot.findViewById(R.id.tmain_menu_arr_btn));
            switch (LAYOUT_THEME) {
                default:
                case LAYOUT_THEME_ARR_MENU:
                    arrBtn.setVisibility(View.VISIBLE);
                    mainRoot.findViewById(R.id.tmain_menu_menu_btn).setVisibility(View.GONE);
                    mainRoot.findViewById(R.id.tmain_menu_more_btn).setVisibility(View.GONE);
                    break;
                case LAYOUT_THEME_MENU_MENU:
                    arrBtn.setVisibility(View.GONE);
                    mainRoot.findViewById(R.id.tmain_menu_menu_btn).setVisibility(View.VISIBLE);
                    mainRoot.findViewById(R.id.tmain_menu_more_btn).setVisibility(View.GONE);
                    break;
                case LAYOUT_THEME_MORE_MENU:
                    arrBtn.setVisibility(View.GONE);
                    mainRoot.findViewById(R.id.tmain_menu_menu_btn).setVisibility(View.GONE);
                    mainRoot.findViewById(R.id.tmain_menu_more_btn).setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    public void setLayoutTheme(int layoutTheme) {
        if (layoutTheme < LAYOUT_THEME_ARR_MENU || layoutTheme > LAYOUT_THEME_MORE_MENU) {
            LAYOUT_THEME = LAYOUT_THEME_ARR_MENU;
        } else {
            LAYOUT_THEME = layoutTheme;
        }
    }
    public void setMenuType(int menuType){
        this.menuType=menuType;
    }

    public void setContentView(int menuLayoutResId, int mainLayoutResId, int layoutTheme, int _menuType) {
        setMenuType(_menuType);
        setLayoutTheme(layoutTheme);

        //setContentView(mainLayoutResId);


        menuLayout = new LinearLayout(ctx);
        LinearLayout.LayoutParams menuParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        menuParams.weight = 0;

        menuLayout.setLayoutParams(menuParams);
        menuLayout.setOrientation(LinearLayout.VERTICAL);
        menuLayout.setVisibility(View.GONE);

//        if (hasMenu) {
        tmainRoot =  getLayoutInflater().inflate(R.layout.layout_tmain, null);//
        backView=tmainRoot.findViewById(R.id.tmain_back);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
//                Log.d(TAG, "View Id : " + v.getId());
//                new TToast(ctx).showToast("View Id : " + v.getId());
            }
        });
        mainRoot=(LinearLayout)tmainRoot.findViewById(R.id.tmain_root);
        mainLayout = (LinearLayout) mainRoot.findViewById(R.id.tmain_main_root);
        if (isLayoutMain) {
            (menuRoot = (LinearLayout) mainRoot.findViewById(R.id.tmain_menu_root)).setVisibility(View.VISIBLE);
            (mainRoot.findViewById(R.id.tmain_menu_back_root)).setVisibility(View.GONE);
            setLayoutTheme();
        } else {
            (mainRoot.findViewById(R.id.tmain_menu_root)).setVisibility(View.GONE);
            (menuRoot = (LinearLayout) mainRoot.findViewById(R.id.tmain_menu_back_root)).setVisibility(View.VISIBLE);
        }
//        } else {
//            mainLayout = new LinearLayout(ctx);
//            LinearLayout.LayoutParams mainParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//            mainParams.weight = 1;
//            mainLayout.setLayoutParams(mainParams);
//            mainLayout.setOrientation(LinearLayout.VERTICAL);
//        }

        View main = getLayoutInflater().inflate(mainLayoutResId, null);
        mainLayout.addView(main);
        if (0 < menuLayoutResId) {
            hasMenu = true;
            setMenuLayout(menuLayoutResId);
        }
        if(menuType==MENU_TYPE_SLIDING) {
            LinearLayout rootLayout = new LinearLayout(ctx);
            LinearLayout.LayoutParams rootParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            rootLayout.setLayoutParams(rootParams);
            rootLayout.setOrientation(LinearLayout.HORIZONTAL);
            rootLayout.setBackgroundColor(0xffffffff);
            rootLayout.addView(menuLayout);
            rootLayout.addView(tmainRoot);
            super.setContentView(rootLayout);
        }else if(menuType==MENU_TYPE_CORVER){
            RelativeLayout rootLayout = new RelativeLayout(ctx);
            RelativeLayout.LayoutParams rootParams = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            rootLayout.setLayoutParams(rootParams);
            rootLayout.setBackgroundColor(0xffffffff);
//            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//            menuLayout.setLayoutParams(params);
            menuLayout.setClickable(true);
            menuLayout.setBackgroundColor(0xffffffff);
            rootLayout.addView(tmainRoot);
            rootLayout.addView(menuLayout);//,rootLayout.getChildCount());
            super.setContentView(rootLayout);
        }



    }

    public void setContentView(int menuLayoutResId, int mainLayoutResId) {
        setContentView(menuLayoutResId, mainLayoutResId, LAYOUT_THEME);
    }

    public void setContentView(int menuLayoutResId, int mainLayoutResId, int layoutTheme){
        setContentView(menuLayoutResId, mainLayoutResId, layoutTheme,menuType);
    }

    public void setContentView(int mainLayoutResId, boolean isLayoutMain) {
        setLayoutStyle(isLayoutMain);
        setContentView(0, mainLayoutResId);
    }


    @Override
    public void setContentView(int layoutResID) {
        setContentView(layoutResID, false);
    }

    public View addMenuBtn(int btnStyle, View.OnClickListener listener) {
        final int dip50 = ImageUtil.PxToDp(ctx, 50);
        final int dip5 = ImageUtil.PxToDp(ctx, 5);
        final int dip10 = ImageUtil.PxToDp(ctx, 10);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dip50, dip50);
        layoutParams.weight = 0;
        if(btnStyle!=BUTTON_STYLE_TOGGLE) {
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
        }else{

            return addTogleButton(listener);
        }
    }
    public View addMenuBtn(int btnStyle,int bgResId, View.OnClickListener listener) {
        final int dip50 = ImageUtil.PxToDp(ctx, 50);
        final int dip5 = ImageUtil.PxToDp(ctx, 5);
        final int dip10 = ImageUtil.PxToDp(ctx, 10);
        return addMenuBtn(btnStyle,bgResId,dip5,listener);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dip50, dip50);
//        layoutParams.weight = 0;
//        if(btnStyle!=BUTTON_STYLE_CUSTOM) {
//            return addMenuBtn(btnStyle,listener);
//        }else{
//            ImageView btn = new ImageView(ctx);
//            btn.setPadding(dip5, dip5, dip5, dip5);
//            btn.setClickable(true);
//            btn.setBackgroundResource(R.drawable.tr_white_selector);
//            btn.setOnClickListener(listener);
//            btn.setLayoutParams(layoutParams);
//            menuRoot.addView(btn);
//            return btn;
//        }
    }
    public View addMenuBtn(int btnStyle,int bgResId, int $paddingDip, View.OnClickListener listener) {
        final int dip50 = ImageUtil.PxToDp(ctx, 50);
        final int dip5 = ImageUtil.PxToDp(ctx, 5);
        final int dip10 = ImageUtil.PxToDp(ctx, 10);
        final int paddingDip= ImageUtil.PxToDp(ctx, $paddingDip);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dip50, dip50);
        layoutParams.weight = 0;
        if(btnStyle!=BUTTON_STYLE_CUSTOM) {
            return addMenuBtn(btnStyle,listener);
        }else{
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

    public TTogleButton addTogleButton(View.OnClickListener listener){
        return setTogleButton(listener,false);
    }
    public TTogleButton setTogleButton(View.OnClickListener listener,boolean isSet){
        final int dip50 = ImageUtil.PxToDp(ctx, 50);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dip50, dip50);
        layoutParams.weight = 0;
        togleBtn=new TTogleButton(ctx);
        togleBtn.setPadding(0,0,0,0);
        togleBtn.setLayoutParams(layoutParams);
        togleBtn.setOnClickListener(listener);
        togleBtn.setChecked(isSet);
        menuRoot.addView(togleBtn);
        return togleBtn;
    }
    public void setToggleButtonCheck(boolean is){
        if(null!=togleBtn)
            togleBtn.setChecked(is);
    }
    public boolean isToggleButtonSet(){
//        if(null!=togleBtn)
        return null!=togleBtn&& togleBtn.isChecked();
//        else
//            return false;
    }

    public void setMenuWidth(int widthDp) {
//        LinearLayout.LayoutParams menuParams = (LinearLayout.LayoutParams) menuLayout.getLayoutParams();

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
//        menuLayout.setLayoutParams(menuParams);

//        Log.d(TAG,"menuLayout Width : "+menuLayout.getWidth()+" WidthDp : "+widthDp);
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

    public void openMenu() {
        if (hasMenu) {
//            Log.d(TAG,"menuWidth : "+menuWidth);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mainLayout.getLayoutParams();
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            params.width = metrics.widthPixels;
            for (int i = 0; i < mainRoot.getChildCount(); i++) {
                mainRoot.getChildAt(i).getLayoutParams().width = metrics.widthPixels;
            }
//        mainLayout.setLayoutParams(params);
            menuLayout.setVisibility(View.VISIBLE);
//            Log.d(TAG, "menuLayout Width : " + menuLayout.getWidth());

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

//    public void closeMenu() {
//        if (hasMenu) {
//            menuLayout.startAnimation(aniHide);
//            menuLayout.setVisibility(View.GONE);
//            isMenuOpen = false;
//
//
//        }
//    }
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
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    public void setShowMenu() {
        if (!isMenuOpen) {
            openMenu();
        } else {
            closeMenu();
        }
    }

    public boolean isMenuOpen() {
        return isMenuOpen;
    }

    public boolean hasMenu() {
        return hasMenu;
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

    public void goBack(View v) {
        finish();
    }

    public void showMenu(View v) {
        setShowMenu();
    }

    @Override
    public void onBackPressed() {
        if (isMenuOpen) {
            closeMenu();
            if (LAYOUT_THEME == LAYOUT_THEME_ARR_MENU) {
                ((ArrMenuButton) mainRoot.findViewById(R.id.tmain_menu_arr_btn)).setShown(false);
            }
        } else {
            super.onBackPressed();
        }
    }

    public void startAct(Intent intent) {
        startAct(intent, false);
    }

    public void startActFinish(Intent intent) {
        startAct(intent, true);
    }

    public void startAct(Class<?> cls) {
        startAct(new Intent(ctx, cls));
    }

    public void startActFinish(Class<?> cls) {
        startActFinish(new Intent(ctx, cls));
    }

    public void startAct(Intent intent, boolean isFin) {
        startActivity(intent);
        if (isFin) {
            finish();
        }
    }
    public void showToast(String msg){
        new TToast(ctx).showToast(msg);
    }
    public void showAlertDialog(String title, String msg){
        new AlertDialog(ctx).setTitle(title).setMsg(msg).show();
    }
}
