/*
 * Copyright (c) 2015. WeNoun™. TANSAN, Since 2014.
 * Code By Jey.
 */

package wenoun.in.library.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import wenoun.in.library.R;

/**
 * Created by SnakeJey on 2015-02-04.
 */
 /*
 <activity android:name="wenoun.in.library.activity.WeNoun"/>
 */
public class WeNoun extends Activity{
    private Context ctx=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.TranslucentTheme);
        super.onCreate(savedInstanceState);
        ctx=this;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.wenoun_info_layout);
    }
    public void exitAct(View v){
        finish();
    }
    public void goHomepage(View v){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.wenoun.com")));
    }
    public void sendNorContact(View v){
        startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:wenoun@wenoun.com")));
    }
    public void sendBilContact(View v){
        startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:billing@wenoun.com")));
    }
    public static String DEV_PAGE="https://play.google.com/store/apps/dev?id=5954317455002110606";
    public static Uri DEV_PAGE_URI=Uri.parse("https://play.google.com/store/apps/dev?id=5954317455002110606");
}
