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
    }

    public TTogleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TTogleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @TargetApi(21)
    public TTogleButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private void setResourceId(){
        notCheckedBGId= R.drawable.tb_off;
        checkedBGId=R.drawable.tb_on;
        notEnableBGId=R.drawable.tb_off;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(isChecked)
                    setBackgroundResource(notCheckedBGId);
                else
                    setBackgroundResource(checkedBGId);
                isChecked=!isChecked;
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if(enabled){
            if(isChecked)
                setBackgroundResource(notCheckedBGId);
            else
                setBackgroundResource(checkedBGId);
        }else
            setBackgroundResource(notCheckedBGId);
    }
    public void setChecked(boolean checked){
        isChecked=checked;
    }
}