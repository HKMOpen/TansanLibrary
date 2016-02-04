package com.wenoun.library.dialog;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by jeyhoon on 15. 10. 28..
 */
public class BaseDialog extends Dialog {
    public BaseDialog(Context ctx){
        super(ctx);
    }
    public BaseDialog(Context context,int style) {
        super(context,style);
    }
    public boolean isEmpty(String str){
        if(null==str||str.length()<=0||str.equals(""))
            return true;
        else
            return false;
    }
    public boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}
