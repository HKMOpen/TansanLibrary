package wenoun.in.library.dialog;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.Toast;

import wenoun.in.library.util.Util;

public class SelectDialog extends Dialog {
	private boolean newApi= Util.checkApi();
	public interface OnItemSelectListener{
		public void onSelect(SelectDialog dialog, View v, int position);
	}
	OnItemSelectListener mOnItemSelectListener;
	public SelectDialog setOnItemSelectListener(OnItemSelectListener mOnItemSelectListener){
//		setDepart(false);
		this.mOnItemSelectListener=mOnItemSelectListener;
		return this;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.8f;
		getWindow().setAttributes(lpWindow);
		//setContentView(R.layout.select_dialog);
		//setLayout();
		setContentView(getLayout());
		
		
	}
	public SelectDialog(Context context) {
		// Dialog 배경을 투명 처리 해준다.
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
		this.ctx = context;
		//this.getVersion=notFirst;
	}
	public SelectDialog(Context context, ArrayList<String> titleList, OnItemSelectListener mOnItemSelectListener) {
		// Dialog 배경을 투명 처리 해준다.
		//super(context, android.R.style.Theme_Translucent_NoTitleBar);
		this(context);
		setMenu(titleList,mOnItemSelectListener);
		//this.getVersion=notFirst;
	}
	public SelectDialog(Context context,String title, ArrayList<String> titleList, OnItemSelectListener mOnItemSelectListener) {
		// Dialog 배경을 투명 처리 해준다.
		//super(context, android.R.style.Theme_Translucent_NoTitleBar);
		this(context,titleList,mOnItemSelectListener);
		setTitle(title);
		//this.getVersion=notFirst;
	}
	public SelectDialog setMenu(ArrayList<String> titleList, OnItemSelectListener mOnItemSelectListener){
		setDepart(false);
		this.titleList=titleList;
		setOnItemSelectListener(mOnItemSelectListener);
		return this;
	}
	public SelectDialog setMenu(String[] titleListArr, OnItemSelectListener mOnItemSelectListener){
		setDepart(false);
		this.titleListArr=titleListArr;
		isArr=true;
		setOnItemSelectListener(mOnItemSelectListener);
		return this;
	}
	public SelectDialog addMenu(String title){
		titleList.add(title);
		//clickList.add(click);
		return this;
	}
	public SelectDialog setDepart(boolean is){
		isDepart=is;
		return this;
	}
	public SelectDialog setTitle(String title){
		this.titleStr=title;
		if(title.length()>0)
			isTitle=true;
		return this;
	}
	boolean isDepart=false;
	String titleStr="";
	boolean isTitle=false;
	Context ctx;
	ArrayList<String> titleList=new ArrayList<String>();
	String[] titleListArr=null;
	boolean isArr=false;
	View[] layout=new View[5];
	TextView[] tv=new TextView[5];
	View.OnClickListener onClick=new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(null!=mOnItemSelectListener){
				mOnItemSelectListener.onSelect(SelectDialog.this, v, v.getId());
			}
		}
	};
	//ProgressDialog dialog;
	//TextView name_tv;
	//TextView status_tv;
	
	/*
	 * Layout
	 */
	private View getLayout(){
		LinearLayout root=new LinearLayout(ctx);
		LayoutParams rootParams=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		root.setGravity(Gravity.CENTER);
		root.setOrientation(LinearLayout.VERTICAL);
		root.setLayoutParams(rootParams);
		LinearLayout parent=new LinearLayout(ctx);
		LayoutParams parentParams=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		parent.setGravity(Gravity.CENTER);
		parent.setOrientation(LinearLayout.VERTICAL);
		//parent.setBackgroundResource(R.drawable.dialog_white_bg);
		if(newApi){
			parent.setBackground(Util.getDrawable(ctx, Color.WHITE));
		}else{
			parent.setBackgroundDrawable(Util.getDrawable(ctx, Color.WHITE));
		}
		LinearLayout.LayoutParams tvParams=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		
		if(isTitle){
			tvParams.setMargins(Util.PxToDp(ctx,10), Util.PxToDp(ctx,0), Util.PxToDp(ctx,10), Util.PxToDp(ctx,0));
			parent.setPadding(Util.PxToDp(ctx,0), Util.PxToDp(ctx,0), Util.PxToDp(ctx,0), Util.PxToDp(ctx,10));
			TextView titleTv=new TextView(ctx);
			LinearLayout.LayoutParams titleParams=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
			titleTv.setPadding(Util.PxToDp(ctx,10), Util.PxToDp(ctx,10), Util.PxToDp(ctx,10), Util.PxToDp(ctx,10));
			titleTv.setGravity(Gravity.CENTER);
			titleTv.setTextSize(20);
			titleTv.setTextColor(Color.WHITE);
			titleTv.setLayoutParams(titleParams);
			titleTv.setText(titleStr);
			if(newApi){
				titleTv.setBackground(Util.getDrawable(ctx, 0xffc5cdfb, 10, 10, 0, 0));
			}else{
				titleTv.setBackgroundDrawable(Util.getDrawable(ctx, 0xffc5cdfb, 10, 10, 0, 0));
			}
			parent.addView(titleTv);
		}else{
			parent.setPadding(Util.PxToDp(ctx,10), Util.PxToDp(ctx,10), Util.PxToDp(ctx,10), Util.PxToDp(ctx,10));
		}
		parent.setLayoutParams(parentParams);
		ScrollView sv=new ScrollView(ctx);
		sv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		int Count=0;
		if(isArr){
			Count=titleListArr.length;
		}else{
			Count=titleList.size();
		}
		for(int i=0; i<Count; i++){
			TextView tv=new TextView(ctx);
			tv.setId(i);
			String txt="";
			if(isArr){
				txt=titleListArr[i];
			}else{
				txt=titleList.get(i);
			}
			tv.setText(txt);
			tv.setOnClickListener(onClick);
//			if(isDepart)
//				tv.setOnClickListener(clickList.get(i));
//			else{
//				if(Count<=clickList.size()&&null!=clickList.get(i))
//					tv.setOnClickListener(clickList.get(i));
//				else
//					tv.setOnClickListener(onClick);
//			}
			tv.setClickable(true);
			tv.setPadding(0, Util.PxToDp(ctx, 10), 0, Util.PxToDp(ctx, 10));
			tv.setTextColor(Color.BLACK);
			//tv.setBackgroundResource(R.drawable.tr_bg);
			if(newApi){
				tv.setBackground(Util.getTrBg(ctx));
			}else{
				tv.setBackgroundDrawable(Util.getTrBg(ctx));
			}
			tv.setTextSize(18);
			tv.setLayoutParams(tvParams);
			tv.setMinEms(10);
			tv.setMaxEms(15);
			tv.setGravity(Gravity.CENTER);
			//tv.setTextAppearance(ctx, R.style.select_dialog_tv);
			//sv.addView(tv);
			parent.addView(tv);
			
			View div=getDiv();
			if(i!=Count-1){
				parent.addView(div);
				//sv.addView(tv);
			}
		}
		sv.addView(parent);
		root.addView(sv);
		return root;
	}
	private View getDiv(){
		View div=new View(ctx);
		LinearLayout.LayoutParams divParams=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,Util.PxToDp(ctx,1));
		div.setBackgroundColor(Color.parseColor("#FFC5CDFB"));
		divParams.bottomMargin=Util.PxToDp(ctx, 2);
		divParams.topMargin=Util.PxToDp(ctx, 2);
		div.setLayoutParams(divParams);
		return div;
	}
	
	public void goMarket(View v) {

	}
	
	private void showToast(String msg){
		Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
	}


}
