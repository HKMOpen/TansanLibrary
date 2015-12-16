package wenoun.in.library.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import wenoun.in.library.R;
import wenoun.in.library.util.ImageUtil;

/**
 * Created by jeyhoon on 15. 12. 16..
 */
public class TActivity extends Activity {
    private Animation aniShow, aniHide;
    protected Context ctx;
    protected String TAG="tag";
    private LinearLayout menuLayout=null;
    private LinearLayout mainLayout=null;
    private LinearLayout rootLayout=null;
    private boolean isMenuOpen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx=this;
        TAG=this.getLocalClassName();
        aniShow = AnimationUtils.loadAnimation(this, R.anim.left_in);
        aniHide = AnimationUtils.loadAnimation(this, R.anim.left_out);
    }

    public void setContentView(int menuLayoutResId,int mainLayoutResId){
        setContentView(mainLayoutResId);
        if(-1!=menuLayoutResId){
            setMenuLayout(menuLayoutResId);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        rootLayout=new LinearLayout(ctx);
        LinearLayout.LayoutParams rootParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        rootLayout.setLayoutParams(rootParams);
        rootLayout.setOrientation(LinearLayout.HORIZONTAL);

        menuLayout=new LinearLayout(ctx);
        LinearLayout.LayoutParams menuParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        menuParams.weight=0;
        menuLayout.setLayoutParams(menuParams);
        menuLayout.setOrientation(LinearLayout.VERTICAL);
        menuLayout.setVisibility(View.GONE);

        mainLayout=new LinearLayout(ctx);
        LinearLayout.LayoutParams mainParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mainParams.weight=1;
        mainLayout.setLayoutParams(mainParams);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        View main=getLayoutInflater().inflate(layoutResID,null);
        mainLayout.addView(main);

        rootLayout.addView(menuLayout);
        rootLayout.addView(mainLayout);
        super.setContentView(rootLayout);

    }

    public void setMenuWidth(int widthDp) {
        LinearLayout.LayoutParams menuParams = (LinearLayout.LayoutParams) menuLayout.getLayoutParams();
        if (widthDp == LinearLayout.LayoutParams.MATCH_PARENT) {
            menuParams.width=LinearLayout.LayoutParams.MATCH_PARENT;
        }else if(widthDp==LinearLayout.LayoutParams.WRAP_CONTENT){
            menuParams.width=LinearLayout.LayoutParams.WRAP_CONTENT;
        }else {
            menuParams.width = ImageUtil.PxToDp(ctx, widthDp);
        }
    }

    public void setMenuLayout(int layoutResId){
        menuLayout.addView(getLayoutInflater().inflate(layoutResId, null));
    }

    public void openMenu(){
        menuLayout.setVisibility(View.VISIBLE);
        menuLayout.startAnimation(aniShow);
        isMenuOpen = true;
    }
    public void closeMenu(){
        menuLayout.startAnimation(aniHide);
        menuLayout.setVisibility(View.GONE);
        isMenuOpen = false;
    }

    public void setShowMenu(){
        if(!isMenuOpen){
            openMenu();
        }else{
            closeMenu();
        }
    }
}
