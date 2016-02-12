package com.wenoun.library.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.wenoun.library.R;
import com.wenoun.library.activity.BackFragmentTActivity;

/**
 * Created by jeyhoon on 16. 2. 7..
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base,container,false);
    }

    protected Context ctx=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx=getActivity().getBaseContext();
    }

    public void startFragment(Fragment fragment,String title){
        ((BackFragmentTActivity)getActivity()).addFragment(fragment, title);
    }
    public void startBackFragmentV4Act(Class <? extends com.wenoun.library.activity.v4.BackFragmentTActivity> cls){
        startBackFragmentAct(new com.wenoun.library.intent.v4.TIntent(ctx,cls));
    }
    public void startBackFragmentAct(Class <? extends com.wenoun.library.activity.BackFragmentTActivity> cls){
        startBackFragmentAct(new com.wenoun.library.intent.TIntent(ctx,cls));
    }
    public void startBackFragmentAct(com.wenoun.library.intent.v4.TIntent intent){
        startActivity(intent);
    }
    public void startBackFragmentAct(com.wenoun.library.intent.TIntent intent){
        startActivity(intent);
    }
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
}
