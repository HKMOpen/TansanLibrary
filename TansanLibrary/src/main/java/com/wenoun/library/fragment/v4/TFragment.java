package com.wenoun.library.fragment.v4;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.wenoun.library.R;
import com.wenoun.library.activity.v4.BackFragmentTActivity;
import com.wenoun.library.activity.v4.FragmentTActivity;
import com.wenoun.library.fragment.TFragmentInterface;
import com.wenoun.library.intent.v4.TIntent;

/**
 * Created by jeyhoon on 16. 2. 7..
 */

public class TFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRoot(R.layout.fragment_base,inflater,container);
        return root;
    }
    protected View root;
    public void setRoot(int resId,LayoutInflater inflater, ViewGroup container){
        root=inflater.inflate(resId,container,false);
    }

    protected Context ctx=null;
    protected FragmentTActivity parent=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx=getActivity().getBaseContext();
        parent=(FragmentTActivity)getActivity();
    }
    public void startFragment(Fragment fragment,String title){
        ((BackFragmentTActivity)getActivity()).addFragment(fragment,title);
    }
    public void startBackFragmentV4Act(Class <? extends BackFragmentTActivity> cls){
        startBackFragmentAct(new TIntent(ctx,cls));
    }
    public void startBackFragmentAct(Class <? extends com.wenoun.library.activity.BackFragmentTActivity> cls){
        startBackFragmentAct(new com.wenoun.library.intent.TIntent(ctx,cls));
    }
    public void startBackFragmentAct(TIntent intent){
        startActivity(intent);
    }
    public void startBackFragmentAct(com.wenoun.library.intent.TIntent intent){
        startActivity(intent);
    }
    public void onSelect(){}
    public void onDisselect(){}
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        View listItem = listAdapter.getView(0, null, listView);
        listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

//        for (int i = 0; i < listAdapter.getCount(); i++) {
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
//            totalHeight += listItem.getMeasuredHeight();
//        }
        totalHeight = listItem.getMeasuredHeight() * listAdapter.getCount();

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    public void setOnClickListener(int viewId, View.OnClickListener listener){
        if(null!=root)
            root.findViewById(viewId).setOnClickListener(listener);
    }
    public void setOnSetView(int viewId,TFragmentInterface.OnSetViewListener listener){
        if(null!=root)
            listener.OnSet(root.findViewById(viewId));
    }
}
