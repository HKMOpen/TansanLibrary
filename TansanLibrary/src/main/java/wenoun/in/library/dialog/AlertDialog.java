package wenoun.in.library.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import wenoun.in.library.R;

public class AlertDialog extends BaseDialog {
	public static final int STYLE_TANSAN=0;
	public static final int STYLE_WEMOM=1;
	private int styleId=R.style.TranslucentTheme;
	public interface OnCloseClickListener{
		public void onClick(AlertDialog d, View v);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ctx.setTheme(styleId);
		super.onCreate(savedInstanceState);
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.8f;
		getWindow().setAttributes(lpWindow);
		setContentView(R.layout.alert_dialog);
		setLayout();


	}
	public AlertDialog(Context context){
		this(context, STYLE_TANSAN);
	}
	public AlertDialog(Context context, int style) {
		super(context,R.style.TranslucentTheme);
		// Dialog 배경을 투명 처리 해준다.
		this.ctx = getContext();
		if(style==STYLE_TANSAN) {
			this.styleId=R.style.TranslucentTheme;
		}else if(style==STYLE_WEMOM){
			this.styleId=R.style.TranslucentWeMomTheme;
		}
	}
	private TextView titleTv;
	private TextView msgTv;
	private Button closeButton;
	private String titleStr;
	private String msgStr;
	private String closeStr;
	private Context ctx;
	/*
	 * Layout
	 */
	private void setLayout() {
		titleTv = (TextView) findViewById(R.id.title_tv);
		msgTv = (TextView) findViewById(R.id.msg_tv);
		closeButton=(Button)findViewById(R.id.close_button);
		if(isEmpty(titleStr))
			findViewById(R.id.title_layout).setVisibility(View.GONE);
		else {
			findViewById(R.id.title_layout).setVisibility(View.VISIBLE);
			titleTv.setText(titleStr);
		}
		msgTv.setText(msgStr);
		if(!isEmpty(closeStr))
			closeButton.setText(closeStr);
		closeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != closeClickListener) {
					closeClickListener.onClick(AlertDialog.this, v);
				}else{
					dismiss();
				}
			}
		});
	}
	OnCloseClickListener closeClickListener=null;
	public AlertDialog setTitle(String str){
		titleStr=str;
		return this;
	}
	public AlertDialog setMsg(String str){
		msgStr=str;
		return this;
	}
	public AlertDialog setClose(String str, final OnCloseClickListener closeClickListener){
		this.closeClickListener=closeClickListener;
		this.closeStr=str;

		return this;
	}
}
