package wenoun.in.library.antiSmartManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;

import java.util.Random;

/**
 * Created by jeyhoon on 16. 1. 20..
 *
 *
 *
 * <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
 *
 <receiver
 android:name="wenoun.in.library.antiSmartManager.Rec"
 android:enabled="true">
 <intent-filter>
 <action android:name="android.intent.action.BOOT_COMPLETED" />
 <action android:name="android.intent.action.ACTION_POWER_CONNECTED" />
 <action android:name="android.intent.action.ACTION_POWER_DISCONNECTED" />
 </intent-filter>
 </receiver>

 <activity
 android:name="wenoun.in.library.antiSmartManager.Act"
 android:excludeFromRecents="true"
 android:taskAffinity=":antiSmartManager"/>
 */
public class Rec extends BroadcastReceiver {
    private final static String SMART_MANAGER_PACKAGE_NAME = "com.samsung.android.sm";

    @Override
    public void onReceive(final Context context, Intent intent) {
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())
                || Intent.ACTION_POWER_CONNECTED.equals(intent.getAction())
                || Intent.ACTION_POWER_DISCONNECTED.equals(intent.getAction())) {
            boolean isSmartManagerExist = false;

            try {
                context.getPackageManager().getPackageInfo(SMART_MANAGER_PACKAGE_NAME, PackageManager.GET_META_DATA);
                isSmartManagerExist = true;
            } catch (PackageManager.NameNotFoundException e) {
            }

            if(isSmartManagerExist) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent serviceIntent = new Intent(context, Act.class);
                        serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(serviceIntent);
                    }
                }, new Random().nextInt(3000));
            }
        }
    }
}
