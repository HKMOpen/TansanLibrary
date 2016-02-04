package com.wenoun.library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.wenoun.library.R;

//import wenoun.in.library.R;


public class VersionDialog extends Dialog {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.8f;
		getWindow().setAttributes(lpWindow);
		setContentView(R.layout.version_dialog);
		setLayout();
		setNewVersion(mNew);
		setNowVersion(mNow);
		
	}

	public VersionDialog(Context context, String newVersion, String nowVersion) {
		super(context, R.style.TranslucentTheme);
		this.ctx = context;
		this.mNew = newVersion;
		this.mNow = nowVersion;
	}

	private void setNewVersion(String title) {
		mNewVersion.setText(title);
	}

	private void setNowVersion(String content) {
		mNowVersion.setText(content);
	}

	private TextView mNewVersion;
	private TextView mNowVersion;
	private Button update;
	private String mNew;
	private String mNow;
	private Context ctx;

	/*
	 * Layout
	 */
	private void setLayout() {
		mNewVersion = (TextView) findViewById(R.id.new_version);
		mNowVersion = (TextView) findViewById(R.id.app_version);
		update=(Button)findViewById(R.id.update_btn);
		update.setOnClickListener(VersionListener);
	}
	private View.OnClickListener VersionListener = new View.OnClickListener(){
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("market://details?id="+ctx.getPackageName()));
			ctx.startActivity(intent);
			
		}
		
	};

}
