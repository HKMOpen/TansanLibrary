package com.wenoun.library.antiSmartManager;

import android.app.Activity;
import android.os.Bundle;

import com.wenoun.library.R;

/**
 * Created by jeyhoon on 16. 1. 20..
 *
 */
/*
 * AndroidManifest Added
 *
 * <activity name="com.wenoun.library.antiSmartManager.AntiSmartManager"/>
 */
public class Act extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.TranslucentAntiSmartTheme);
        finish();
    }
}
