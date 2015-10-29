package wenoun.in.library.toast;


import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TansanToast extends Toast {
    Context mContext;
    private boolean newApi=false;
    public static final int TYPE_TANSAN=0;
    public static final int TYPE_TANSAN_TR=1;
    public static final int TYPE_WHITE=2;
    public static final int TYPE_WHITE_TR=3;
    public static final int TYPE_BLACK=4;
    public static final int TYPE_BLACK_TR=5;
	public static final int TYPE_WEMOM=6;
	public static final int TYPE_WEMOM_TR=7;
    private final int COLOR_TANSAN=0xFFC5CDFB;
    private final int COLOR_TANSAN_TR=0x80C5CDFB;
    private final int COLOR_WHITE=0xFFFFFFFF;
    private final int COLOR_WHITE_TR=0x80FFFFFF;
    private final int COLOR_BLACK=0xFF000000;
    private final int COLOR_BLACK_TR=0x80000000;
	private final int COLOR_WEMOM=0xFFFBC5C5;
	private final int COLOR_WEMOM_TR=0x80FBC5C5;
    
    public TansanToast(Context context) {
        super(context);
        mContext = context;
        if (Build.VERSION.SDK_INT >= 16) {
			newApi=true;
		}else{
			newApi=false;
		}
    }
    public void showToast(String msg){
        // http://developer.android.com/guide/topics/ui/notifiers/toasts.html
        showToast(msg,TYPE_TANSAN,LENGTH_SHORT);
    }
    public void showToast(String msg,int duration){
        // http://developer.android.com/guide/topics/ui/notifiers/toasts.html
        showToast(msg,TYPE_TANSAN,duration);
    }
    public void showToast(String msg, int type,int duration){
        // http://developer.android.com/guide/topics/ui/notifiers/toasts.html
        //LayoutInflater inflater;
        View v;
        /*if(false){
            //Activity act = (Activity)mContext;
            //inflater = act.getLayoutInflater();
            //v = inflater.inflate(R.layout.toast_layout, null);
            v=getViews(type);
        }else{  // same*/
            //inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //v = inflater.inflate(R.layout.toast_layout, null);
            v=getViews(type,msg);
        //}
        show(this,v,duration);
    }
    @SuppressWarnings("deprecation")
	private View getViews(int type, String msg){
    	View result=null;
    	int backColor=COLOR_TANSAN;
    	int textColor=COLOR_WHITE;
    	switch(type){
    	case TYPE_TANSAN :
    		backColor=COLOR_TANSAN;
    		textColor=COLOR_WHITE;
    		break;
    	case TYPE_TANSAN_TR :
    		backColor=COLOR_TANSAN_TR;
    		textColor=COLOR_WHITE;
    		break;
    	case TYPE_WHITE :
    		backColor=COLOR_WHITE;
    		textColor=COLOR_BLACK;
    		break;
    	case TYPE_WHITE_TR :
    		backColor=COLOR_WHITE_TR;
    		textColor=COLOR_BLACK;
    		break;
    	case TYPE_BLACK :
    		backColor=COLOR_BLACK;
    		textColor=COLOR_WHITE;
    		break;
    	case TYPE_BLACK_TR :
    		backColor=COLOR_BLACK_TR;
    		textColor=COLOR_WHITE;
    		break;
			case TYPE_WEMOM :
				backColor=COLOR_WEMOM;
				textColor=COLOR_WHITE;
				break;
			case TYPE_WEMOM_TR :
				backColor=COLOR_WEMOM_TR;
				textColor=COLOR_WHITE;
				break;
    	}
    	LinearLayout rootLayout=new LinearLayout(mContext);
    	ViewGroup.LayoutParams rootParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
    	rootLayout.setLayoutParams(rootParams);
    	rootLayout.setPadding(pxToDp(10), pxToDp(10), pxToDp(10), pxToDp(10));
    	rootLayout.setGravity(Gravity.CENTER);
    	rootLayout.setOrientation(LinearLayout.VERTICAL);
    	if(newApi){
    		rootLayout.setBackground(getBack(rootLayout.getWidth(),rootLayout.getHeight(),backColor));
    	}else{
    		rootLayout.setBackgroundDrawable(getBack(rootLayout.getWidth(),rootLayout.getHeight(),backColor));
    	}
    	TextView tv=new TextView(mContext);
    	tv.setTextColor(textColor);
    	tv.setTextSize(18);
    	tv.setPaintFlags(tv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
    	tv.setText(msg);
    	rootLayout.addView(tv);
    	result=rootLayout;
    	return result;
    }

    private void show(Toast toast, View v, int duration){
        toast.setGravity(Gravity.CENTER|Gravity.BOTTOM, 0, pxToDp(70));
        toast.setDuration(duration);
        toast.setView(v);
        toast.show();
    }
    private int pxToDp(int px) {
		DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
		int size = Math.round(px * dm.density);
		return size;
	}
    private Drawable getBack(int w, int h, int color) {
		GradientDrawable g = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, new int[] {
						color, color });
		g.setShape(GradientDrawable.RECTANGLE);
		g.setCornerRadius(pxToDp(20));
		return g;
	}
 
}
