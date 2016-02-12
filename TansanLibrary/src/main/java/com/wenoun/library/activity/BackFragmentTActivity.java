package com.wenoun.library.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wenoun.library.R;
import com.wenoun.library.image.ImageUtils;

import java.util.ArrayList;

/**
 * Created by jeyhoon on 16. 2. 7..
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class BackFragmentTActivity extends Activity {
    protected Context ctx=null;

    private LinearLayout menuRoot=null;
    private LinearLayout mainRoot=null;

    private FragmentManager fragmentManager=null;

    private ArrayList<String> titleList=new ArrayList<String>();

    private int customButtonCnt=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx=this;
        super.setContentView(R.layout.layout_back_fragment_tactivity);
        fragmentManager = getFragmentManager();
        menuRoot=(LinearLayout)findViewById(R.id.back_fragment_menu_root);
        mainRoot=(LinearLayout)findViewById(R.id.back_fragment_main_root);
        setTitle("");
        findViewById(R.id.back_fragment_back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public void setFragment(Fragment fragment){
        setFragment(fragment,"");
    }
    public void setFragment(Fragment fragment,String title){
        addTitle(title);
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.back_fragment_container, fragment);
        fragmentTransaction.commit();
    }
    public void addFragment(Fragment fragment, String title){
        addTitle(title);
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.back_fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        removeCustomButton();
    }
    public void addFragment(Fragment fragment){
        addFragment(fragment,"");
    }

    public void removeCustomButton(){
        for(int i=2; i<customButtonCnt;i++){
            menuRoot.removeViewAt(i);
        }
        customButtonCnt=2;
    }

    public void addTitle(String title){
        titleList.add(title);
        setTitle(title);
    }
    public void setTitle(String title){
        ((TextView)findViewById(R.id.back_fragment_title)).setText(title);
    }

    public void addMenuButton(View v,View.OnClickListener listener){
        v.setOnClickListener(listener);
        addMenuButton(v);
    }

    public void addMenuButton(View v){
        if(null!=menuRoot){
            menuRoot.addView(v);
            customButtonCnt++;
        }
    }

    public void addMenuButton(String menu,float textSize,int textColor,View.OnClickListener listener){
        addMenuButton(getMenuButton(menu,textSize,textColor),listener);
    }

    public void addMenuButton(String menu,View.OnClickListener listener){
        addMenuButton(getMenuButton(menu),listener);
    }

    public View getMenuButton(String menu){
        return getMenuButton(menu,20, Color.WHITE);
    }
    public View getMenuButton(String menu,float textSize,int textColor){
        TextView item=new TextView(ctx);
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight=0;
        item.setLayoutParams(params);
        item.setTextSize(textSize);
        item.setTextColor(textColor);
        item.setText(menu);
        item.setGravity(Gravity.CENTER);
        final int dip10= ImageUtils.dpToPx(ctx,10);
        item.setPadding(dip10,0,dip10,0);
        return item;
    }

    @Override
    public void onBackPressed() {
        if(fragmentManager.getBackStackEntryCount()>0) {
            fragmentManager.popBackStack();
            try {
                titleList.remove(titleList.size() - 1);
                setTitle(titleList.get(titleList.size() - 1));
            }catch(Exception e){}
        }else
            super.onBackPressed();
    }

}
