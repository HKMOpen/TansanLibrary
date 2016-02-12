package com.wenoun.library.intent;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.wenoun.library.activity.BackFragmentTActivity;

/**
 * Created by jeyhoon on 16. 2. 7..
 */
public class TIntent extends Intent implements Parcelable{
    public TIntent(Context packageContext, Class<? extends BackFragmentTActivity> cls) {
        super(packageContext, cls);
    }

    protected TIntent(Parcel in) {
        super(String.valueOf(in));
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Intent> CREATOR = new Creator<Intent>() {
        @Override
        public Intent createFromParcel(Parcel in) {
            return new TIntent(in);
        }

        @Override
        public Intent[] newArray(int size) {
            return new TIntent[size];
        }
    };
}
