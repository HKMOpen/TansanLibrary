package wenoun.in.library.button;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import wenoun.in.library.R;

/**
 * Created by jeyhoon on 15. 10. 29..
 */
public class ArrMenuButton extends Button {
    private int opendBtn;
    private int closedBtn;
    private boolean isShown=false;
    public ArrMenuButton(Context ctx){
        super(ctx);
        setResourceId();
    }

    public ArrMenuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setResourceId();
    }

    public ArrMenuButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setResourceId();
    }
    @TargetApi(21)
    public ArrMenuButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setResourceId();
    }
    private void setResourceId(){
        opendBtn= R.drawable.btn_back_white;
        closedBtn= R.drawable.btn_menu_arr_white;
        setBackgroundResource(closedBtn);
        setId(0);

    }

    @Override
    public void setBackgroundResource(int resource){

        super.setBackgroundResource(resource);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_UP:
                isShown=!isShown;
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
                startAnimation(anim);
                anim.setAnimationListener(myAnimationListener);




                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

    }
    public void setShown(boolean shown){
        isShown=shown;
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        startAnimation(anim);
        anim.setAnimationListener(myAnimationListener);
//        if(isShown) {
//            setBackgroundResource(opendBtn);
//        }
//        else {
//            setBackgroundResource(closedBtn);
//        }
    }
    public boolean isShown(){
        return isShown;
    }

    @Override
    public void startAnimation(Animation animation) {
        super.startAnimation(animation);


    }
    Animation.AnimationListener myAnimationListener = new Animation.AnimationListener() {
        public void onAnimationEnd(Animation animation) {

        }
        @Override
        public void onAnimationRepeat(Animation animation) {
        }
        @Override
        public void onAnimationStart(Animation animation) {
            if(!isShown) {
                setBackgroundResource(closedBtn);
            }
            else {
                setBackgroundResource(opendBtn);
            }
        }
    };
}