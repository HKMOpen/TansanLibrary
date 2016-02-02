package wenoun.in.library.button;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import wenoun.in.library.R;

/**
 * Created by jeyhoon on 15. 10. 29..
 */
public class TTogleButton extends Button {
    private int notCheckedBGId;
    private int checkedBGId;
    private int notEnableBGId;
    private boolean isChecked=false;
    public TTogleButton(Context ctx){
        super(ctx);
        setResourceId();
    }

    public TTogleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setResourceId();
    }

    public TTogleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setResourceId();
    }
    @TargetApi(21)
    public TTogleButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setResourceId();
    }
    private void setResourceId(){
        notCheckedBGId= R.drawable.library_tb_off;
        checkedBGId=R.drawable.library_tb_on;
        notEnableBGId=R.drawable.library_tb_off;
        setBackgroundResource(notCheckedBGId);
        setPadding(0,0,0,0);
        setTextSize(12);
        setTextColor(0xFF000000);
    }

    @Override
    public void setBackgroundResource(int resource){
        super.setBackgroundResource(resource);
        if(isChecked)
            setText(R.string.on_str);
        else
            setText(R.string.off_str);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_UP:
                if(isEnabled()) {
                    isChecked = !isChecked;
                    if (!isChecked)
                        setBackgroundResource(notCheckedBGId);
                    else
                        setBackgroundResource(checkedBGId);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if(enabled){
            if(isChecked) {
                setBackgroundResource(checkedBGId);
                setTextColor(0xff000000);
            }
            else {
                setBackgroundResource(notCheckedBGId);
                setTextColor(0xff000000);
            }
        }else {
            setBackgroundResource(notCheckedBGId);
            setTextColor(0xff999999);
        }

    }
    public void setChecked(boolean checked){
        isChecked=checked;
        if(isChecked&&isEnabled())
            setBackgroundResource(checkedBGId);
        else
            setBackgroundResource(notCheckedBGId);
    }
    public boolean isChecked(){
        return isChecked;
    }
}