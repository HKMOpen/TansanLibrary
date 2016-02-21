package com.wenoun.library.dialog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.wenoun.library.R;

import java.util.Locale;

//import wenoun.in.library.R;

public class ContactDialog extends BaseDialog {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.8f;
		getWindow().setAttributes(lpWindow);
		setContentView(R.layout.contact_dialog);
		setLayout();


	}
	public ContactDialog(Context context,String appName){
		super(context,R.style.TranslucentTheme);
		this.ctx=context;
	}
	private Context ctx;
	private String appName="";
	/*
	 * Layout
	 */
	private void setLayout() {
		findViewById(R.id.kakaotalk).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("kakaoplus://plusfriend/friend/@tansandevteam"));
				ctx.startActivity(intent);
				dismiss();
			}
		});
		findViewById(R.id.mail).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(Intent.ACTION_SEND);
				String[] mailaddr = {"tansan.team@gmail.com","wenoun@wenoun.com"};

				it.setType("plaine/text");
				it.putExtra(Intent.EXTRA_EMAIL, mailaddr); // 받는사람
				Locale setLocale = ctx.getResources().getConfiguration().locale;
				String title=" 문의합니다.";
				String msg="* 문의 내용 :";
				if(setLocale.getCountry().equals(Locale.KOREA.getCountry())){
					title=" 문의합니다.";
					msg="* 문의 내용 :";
				}else if(setLocale.getCountry().equals(Locale.JAPAN.getCountry())){
					title="お問い合わせください。";
					msg="*お問い合わせ内容：";
				}else if(setLocale.getCountry().equals(Locale.CHINA.getCountry())){
					title="联系";
					msg="*联系方式：";
				}else if(setLocale.getCountry().equals(Locale.FRENCH.getCountry())){
					title = "Contact";
					msg = "* Coordonnées:";
				}else{
					title = "Contact";
					msg = "* Contact content :";
				}
				it.putExtra(Intent.EXTRA_SUBJECT, "["+appName+"]"+title); // 제목
				it.putExtra(Intent.EXTRA_TEXT, "\n\n"+msg+"\n\n"); // 첨부내용
				ctx.startActivity(it);
			}
		});
	}
}
