package wenoun.in.library.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import wenoun.in.library.R;

public class EditTextDialog extends BaseDialog {
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
		setContentView(R.layout.edittext_dialog);
		setLayout();


	}
	public EditTextDialog(Context context){
		this(context, STYLE_TANSAN);
	}
	public EditTextDialog(Context context,String editMsg){
		this(context, STYLE_TANSAN,editMsg);
	}
	public EditTextDialog(Context context, int style) {
		super(context,R.style.TranslucentTheme);
		// Dialog 배경을 투명 처리 해준다.
		this.ctx = getContext();
		if(style==STYLE_TANSAN) {
			this.styleId=R.style.TranslucentTheme;
		}else if(style==STYLE_WEMOM){
			this.styleId=R.style.TranslucentWeMomTheme;
		}
	}
	public EditTextDialog(Context context, int style,String editMsg) {
		super(context,R.style.TranslucentTheme);
		// Dialog 배경을 투명 처리 해준다.
		this.ctx = getContext();
		if(style==STYLE_TANSAN) {
			this.styleId=R.style.TranslucentTheme;
		}else if(style==STYLE_WEMOM){
			this.styleId=R.style.TranslucentWeMomTheme;
		}
		this.editMsg=editMsg;
	}
	private TextView titleTv;
	private TextView msgTv;
	private EditText et;
	private Button confirmButton;
	private Button cancelButton;
	private String titleStr;
	private String msgStr;
	private String confirmStr;
	private String cancelStr;
	private Context ctx;
	private String editMsg="";
	/*
	 * Layout
	 */
	private void setLayout() {
		titleTv = (TextView) findViewById(R.id.title_tv);
		msgTv = (TextView) findViewById(R.id.msg_tv);
		et=(EditText)findViewById(R.id.et);
		confirmButton=(Button)findViewById(R.id.confirm_button);
		cancelButton=(Button)findViewById(R.id.cancel_button);
		if(null!=editMsg){
			et.setText(editMsg);
		}
		if(isEmpty(titleStr))
			findViewById(R.id.title_layout).setVisibility(View.GONE);
		else {
			findViewById(R.id.title_layout).setVisibility(View.VISIBLE);
			titleTv.setText(titleStr);
		}
		msgTv.setText(msgStr);
		if(null==msgStr||msgStr.length()<=0||msgStr.isEmpty())
			msgTv.setVisibility(View.GONE);
		if(!isEmpty(confirmStr))
			confirmButton.setText(confirmStr);
		confirmButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (null != confirmClickListener) {
					confirmClickListener.onClick(EditTextDialog.this, v,et.getText().toString());
				}
			}
		});
		if(!isEmpty(cancelStr))
			cancelButton.setText(cancelStr);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(null!=cancelClickListener){
					cancelClickListener.onClick(EditTextDialog.this,v);
				}else{
					EditTextDialog.this.cancel();
				}
			}
		});
	}
	TDialogInterface.OnEditConfirmListener confirmClickListener=null;
	TDialogInterface.OnButtonClickListener cancelClickListener=null;
	public EditTextDialog setTitle(String str){
		titleStr=str;
		return this;
	}
	public EditTextDialog setMsg(String str){
		msgStr=str;
		return this;
	}
	public EditTextDialog setConfirm(String str, final TDialogInterface.OnEditConfirmListener confirmClickListener){
		this.confirmClickListener=confirmClickListener;
		this.confirmStr=str;

		return this;
	}
	public EditTextDialog setCancel(String str, final TDialogInterface.OnButtonClickListener cancelClickListener){
		this.cancelClickListener=cancelClickListener;
		this.cancelStr=str;

		return this;
	}
}
