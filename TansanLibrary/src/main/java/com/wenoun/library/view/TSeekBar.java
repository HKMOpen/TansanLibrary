package com.wenoun.library.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.SeekBar;

/**
 * Created by jeyhoon on 16. 3. 16..
 */
public class TSeekBar extends SeekBar {
    public TSeekBar(Context context) {
        super(context);
        initView();
    }

    public TSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TSeekBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }
    private void initView(){
        try {
            setProgressDrawable(getContext().getResources().getDrawable(com.wenoun.library.R.drawable.seekbar_progress));
            setThumb(getContext().getResources().getDrawable(com.wenoun.library.R.drawable.seekbar_thumb));
        }catch(Exception e){}
    }
}
