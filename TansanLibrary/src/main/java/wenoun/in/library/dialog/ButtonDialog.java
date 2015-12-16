package wenoun.in.library.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import wenoun.in.library.R;

public class ButtonDialog extends BaseDialog {
	public static final int STYLE_TANSAN=0;
	public static final int STYLE_WEMOM=1;
	private int styleId=R.style.TranslucentTheme;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ctx.setTheme(styleId);
		super.onCreate(savedInstanceState);
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.8f;
		getWindow().setAttributes(lpWindow);
		setContentView(R.layout.button_dialog);
		setLayout();


	}
	public ButtonDialog(Context context){
		this(context, STYLE_TANSAN);
	}
	public ButtonDialog(Context context,int style) {
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
	private Button confirmButton;
	private Button cancelButton;
	private String titleStr;
	private String msgStr;
	private String confirmStr;
	private String cancelStr;
	private Context ctx;
	/*
	 * Layout
	 */
	private void setLayout() {
		titleTv = (TextView) findViewById(R.id.title_tv);
		msgTv = (TextView) findViewById(R.id.msg_tv);
		confirmButton=(Button)findViewById(R.id.confirm_button);
		cancelButton=(Button)findViewById(R.id.cancel_button);
		if(isEmpty(titleStr))
			findViewById(R.id.title_layout).setVisibility(View.GONE);
		else {
			findViewById(R.id.title_layout).setVisibility(View.VISIBLE);
			titleTv.setText(titleStr);
		}
		msgTv.setText(msgStr);
		if(!isEmpty(confirmStr))
			confirmButton.setText(confirmStr);
		confirmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != confirmClickListener) {
					confirmClickListener.onClick(ButtonDialog.this, v);
				}
			}
		});
		if(!isEmpty(cancelStr))
			cancelButton.setText(cancelStr);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(null!=cancelClickListener){
					cancelClickListener.onClick(ButtonDialog.this,v);
				}else{
					ButtonDialog.this.cancel();
				}
			}
		});
	}
	TDialogInterface.OnButtonClickListener confirmClickListener=null;
	TDialogInterface.OnButtonClickListener cancelClickListener=null;
	public ButtonDialog setTitle(String str){
		titleStr=str;
		return this;
	}
	public ButtonDialog setMsg(String str){
		msgStr=str;
		return this;
	}
	public ButtonDialog setConfirm(String str, final TDialogInterface.OnButtonClickListener confirmClickListener){
		this.confirmClickListener=confirmClickListener;
		this.confirmStr=str;

		return this;
	}
	public ButtonDialog setCancel(String str, final TDialogInterface.OnButtonClickListener cancelClickListener){
		this.cancelClickListener=cancelClickListener;
		this.cancelStr=str;

		return this;
	}
}
