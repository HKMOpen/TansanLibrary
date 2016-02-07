package com.wenoun.library.activity.v4;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wenoun.library.R;
import com.wenoun.library.image.ImageUtils;

import java.util.ArrayList;

/**
 * Created by jeyhoon on 16. 2. 7..
 */
public class FragmentTActivity extends FragmentActivity {
    protected Context ctx=null;
    protected String TAG="FragmentTactivity";
    public final int STYLE_TBLUE=0;
    public final int STYLE_BLACK=1;
    public final int STYLE_TRED=2;
    public final int STYLE_CUSTOM=3;
    private int mStyle=STYLE_TBLUE;



    private boolean isViewPager=false;
    private LinearLayout menuRoot=null;
    private LinearLayout mainRoot=null;

    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    private ArrayList<Fragment> fragments=new ArrayList<Fragment>();
    private ArrayList<TabItem> tabItems=new ArrayList<TabItem>();

    private TabSelectedListener tabSelectedListener=new TabSelectedListener() {
        @Override
        public void OnSelected(int pos) {
            for(int i=0; i<fragments.size();i++){
                ((TabView)findViewById(i)).setTabResource(tabItems.get(i).getUnselectBackID());
                ((TabView)findViewById(i)).setItemResource(tabItems.get(i).getUnselectItemID());
            }
            int bgId=R.drawable.tab_select_tblue;
            switch(mStyle){
                case STYLE_TBLUE:
                    bgId=R.drawable.tab_select_tblue;
                    break;
                case STYLE_BLACK:
                    bgId=R.drawable.tab_select_black;
                    break;
                case STYLE_TRED:
                    bgId=R.drawable.tab_select_tred;
                    break;
                case STYLE_CUSTOM:
                    bgId=tabItems.get(pos).getSelectBackID();
                    break;
            }
            ((TabView)findViewById(pos)).setTabResource(bgId);
            ((TabView)findViewById(pos)).setItemResource(tabItems.get(pos).getSelectItemID());
        }
    };

    public interface TabSelectedListener {
        public void OnSelected(int pos);
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        private int MAX_PAGE=3;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            this.MAX_PAGE=fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            // 해당하는 page의 Fragment를 생성합니다.
//            Fragment newFragment = null;

            if(position<0 || MAX_PAGE<=position)
                return null;
//            new TansanToast(ctx).showToast("position : "+position);

            return getFragment(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
//            new TansanToast(ctx).showToast("instatniateItem position : "+position);

            return super.instantiateItem(container, position);
        }


        @Override
        public int getCount() {
            return MAX_PAGE;  // 총 5개의 page를 보여줍니다.
        }

    }

    public class TabItem{
        private int unselectItemID=-1;
        private int selectItemID=-1;
        private int unselectBackID=-1;
        private int selectBackID=-1;

        public TabItem() {
            this(R.color.white,R.color.white,R.color.white,R.color.white);
        }

        public TabItem(int unselectItemID, int selectItemID) {
            this.unselectItemID = unselectItemID;
            this.selectItemID = selectItemID;
        }

        public TabItem(int unselectItemID, int selectItemID, int unselectBackID, int selectBackID) {
            this.unselectItemID = unselectItemID;
            this.selectItemID = selectItemID;
            this.unselectBackID = unselectBackID;
            this.selectBackID = selectBackID;
        }

        public int getUnselectItemID() {
            return unselectItemID;
        }

        public TabItem setUnselectItemID(int unselectItemID) {
            this.unselectItemID = unselectItemID;
            return this;
        }

        public int getSelectItemID() {
            return selectItemID;
        }

        public TabItem setSelectItemID(int selectItemID) {
            this.selectItemID = selectItemID;
            return this;
        }

        public int getUnselectBackID() {
            if(unselectBackID==-1){
                return R.drawable.tab_unselect;
            }else
                return unselectBackID;
        }

        public TabItem setUnselectBackID(int unselectBackID) {
            this.unselectBackID = unselectBackID;
            return this;
        }

        public int getSelectBackID() {
            if(selectBackID==-1){
                int bgId=R.drawable.tab_select_tblue;
                switch(mStyle){
                    case STYLE_TBLUE:
                        bgId=R.drawable.tab_select_tblue;
                        break;
                    case STYLE_BLACK:
                        bgId=R.drawable.tab_select_black;
                        break;
                    case STYLE_TRED:
                        bgId=R.drawable.tab_select_tred;
                        break;
                }
                return bgId;
            }else
                return selectBackID;
        }

        public TabItem setSelectBackID(int selectBackID) {
            this.selectBackID = selectBackID;
            return this;
        }

        public TabItem setItemIDs(int unselectItemID,int selectItemID){
            setUnselectItemID(unselectItemID);
            setSelectItemID(selectItemID);
            return this;
        }
        public TabItem setBackIDs(int unselectBackID,int selectBackID){
            setUnselectBackID(unselectBackID);
            setSelectBackID(selectBackID);
            return this;
        }
    }
    public class TabView extends LinearLayout{
        private int width=50;
        private int height=50;
        private int itemResID=-1;
//        private LinearLayout tabRoot=null;
        private ImageView tabItem=null;
        public TabView(Context context) {
            super(context);
        }
        public TabView(Context context,int itemResID) {
            super(context);
            this.itemResID=itemResID;
            init();
        }
        public TabView(Context context,int itemResID,int width, int height) {
            super(context);
            this.itemResID=itemResID;
            this.width=width;
            this.height=height;
            init();
        }

        public TabView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public TabView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
            init();
        }
        private void init(){
//            tabRoot=new LinearLayout(ctx);
            LinearLayout.LayoutParams itemRootParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            itemRootParams.weight=1;
            setLayoutParams(itemRootParams);
            setBackgroundResource(R.drawable.tab_unselect);
            setClickable(true);
            setOnClickListener(tabItemListener);
            setGravity(Gravity.CENTER);
            setId(fragments.size()-1);
            tabItem=new ImageView(ctx);
            tabItem.setLayoutParams(new LinearLayout.LayoutParams(ImageUtils.dpToPx(ctx,width),
                    ImageUtils.dpToPx(ctx,height)));
            tabItem.setImageResource(itemResID);
            addView(tabItem);
//            addView(tabRoot);
        }
        public void setItemResource(int itemResID){
            this.itemResID=itemResID;
            tabItem.setImageResource(itemResID);
        }
        public void setTabResource(int backResID){
            setBackgroundResource(backResID);
        }
    }
//    protected
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx=this;
        super.setContentView(R.layout.layout_fragmentv4_tactvity);
        menuRoot=(LinearLayout)findViewById(R.id.fragment_tactivity_menu_root);
        mainRoot=(LinearLayout)findViewById(R.id.fragment_tactivity_main_root);
        setStyle(STYLE_TBLUE);
    }

    @Override
    public void setContentView(int layoutResID) {
//        super.setContentView(layoutResID);
        View main=getLayoutInflater().inflate(layoutResID,null);
        try {
            ((LinearLayout.LayoutParams) main.getLayoutParams()).weight = 1F;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        mainRoot.addView(main);
    }

    protected FragmentTActivity addFragment(int idx, Fragment fragment){
        if(fragments.size()>=idx)
            fragments.add(idx,fragment);
        else
            fragments.add(fragment);
        return this;
    }
    protected FragmentTActivity addFragment(Fragment fragment){
        fragments.add(fragment);
        return this;
    }
    protected void setCurrentFragment(int pos){
        setCurrentMenu(pos);
    }

    public void fragmentReplace(Fragment fragment) {

//        Fragment newFragment = null;

//        Log.d(TAG, "fragmentReplace " + reqNewFragmentIndex);

//        newFragment = getFragment(reqNewFragmentIndex);

        // replace fragment
        final FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.fragment_tactivity_main_root, fragment);

        // Commit the transaction
        transaction.commit();

    }
    public Fragment getFragment(int position){
        return fragments.get(position);
    }

    public void setMenuBarHeightPx(int px){
        menuRoot.getLayoutParams().height= px;
    }
    public void setMenuBarHeightDip(int dip){
        setMenuBarHeightPx(ImageUtils.dpToPx(ctx,dip));
    }

    public void setMenuFragment(View v,TabSelectedListener listener){
        menuRoot.removeAllViews();
        menuRoot.addView(v);
        this.tabSelectedListener=listener;
    }
    public void setMenuFragment(int resID,TabSelectedListener listener){
        setMenuFragment(getLayoutInflater().inflate(resID,null),listener);
    }

    public void addMenuFragment(int iconResID, int widthDip, int heightDip, Fragment fragment){
        addMenuFragment(iconResID,widthDip,heightDip,fragment,new TabItem(iconResID,iconResID));
    }

    public void addMenuFragment(int iconResID, int widthDip, int heightDip, Fragment fragment,TabItem tabItem){
        addFragment(fragment);
        addMenuView(iconResID,widthDip,heightDip);
        tabItems.add(tabItem);
    }

    public void addMenuFragment(int iconResID,Fragment fragment){
        addMenuFragment(iconResID,fragment,new TabItem(iconResID,iconResID));
    }
    public void addMenuFragment(int iconResID,Fragment fragment,TabItem tabItem){
        addMenuFragment(iconResID,50,50,fragment,tabItem);
//        tabItems.add(tabItem);
    }
    public void addMenuFragment(TabItem tabItem, Fragment fragment){
        addMenuFragment(tabItem.getUnselectItemID(),fragment);
    }
    public void addMenuFragment(TabItem tabItem,int widthDip, int heightDip,Fragment fragment){
        addMenuFragment(tabItem.getUnselectItemID(),widthDip,heightDip,fragment,tabItem);
//        tabItems.add(tabItem);
    }
    private void addMenuView(int iconResID,int widthDip, int heightDip){
        TabView itemRoot=new TabView(ctx,iconResID,widthDip,heightDip);
        menuRoot.addView(itemRoot);
    }
    private void setMenuBg(int selectedId){
        tabSelectedListener.OnSelected(selectedId);
    }
    private View.OnClickListener tabItemListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id=v.getId();
            setMenuBg(id);
            if(isViewPager)
                mViewPager.setCurrentItem(id);
            else
                fragmentReplace(fragments.get(id));
        }
    };
    public FragmentTActivity setViewPager(boolean is){
        this.isViewPager=is;
        if(is){
            mViewPager=(ViewPager)findViewById(R.id.fragment_tactivity_pager);
            mViewPager.setVisibility(View.VISIBLE);
            mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
            mViewPager.setAdapter(mPagerAdapter);
            mViewPager.setCurrentItem(0);
            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    setMenuBg(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }else{
            findViewById(R.id.fragment_tactivity_pager).setVisibility(View.GONE);
        }
        return this;
    }
    public void setCurrentMenu(int pos){
        if(isViewPager)
            mViewPager.setCurrentItem(pos);
        else
            fragmentReplace(fragments.get(pos));
        setMenuBg(pos);
    }

    public void setStyle(int style){
        this.mStyle=style;
        setDivStyle(style);
    }

    public void setDivStyle(int style){
        int bgId=R.color.tblue;
        switch(mStyle){
            case STYLE_TBLUE:
                bgId=R.color.tblue;
                break;
            case STYLE_BLACK:
                bgId=R.color.gray;
                break;
            case STYLE_TRED:
                bgId=R.color.tred;
                break;
            default:
                bgId=R.color.white;
                break;
        }
        setDivResource(bgId);
    }
    public void setDivColor(int color){
        findViewById(R.id.fragment_tactivity_div).setBackgroundColor(color);
    }
    public void setDivHeight(int heightDip){
        findViewById(R.id.fragment_tactivity_div).getLayoutParams().height=ImageUtils.dpToPx(ctx,heightDip);
    }
    public void setDivResource(int resourceID){
        findViewById(R.id.fragment_tactivity_div).setBackgroundResource(resourceID);
    }
}
