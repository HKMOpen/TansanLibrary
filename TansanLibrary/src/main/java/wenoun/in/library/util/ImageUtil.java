package wenoun.in.library.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.DisplayMetrics;

public class ImageUtil {
	private static final int corner=10; 
	public static int PxToDp(Context ctx,int px) {
		DisplayMetrics dm = ctx.getResources().getDisplayMetrics();
		int size = Math.round(px * dm.density);
		return size;
	}
	public static Drawable getDrawable(Context ctx,int backColor) {
		GradientDrawable g = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, new int[] {
						backColor, backColor });
		g.setShape(GradientDrawable.RECTANGLE);
		g.setCornerRadius(PxToDp(ctx,corner));
		return g;
	}
	public static Drawable getDrawable(Context ctx,int backColor, int tl,int tr, int br, int bl) {
		GradientDrawable g = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, new int[] {
						backColor, backColor });
		g.setShape(GradientDrawable.RECTANGLE);
		float[] radii={PxToDp(ctx,tl),PxToDp(ctx,tl),PxToDp(ctx,tr),PxToDp(ctx,tr),PxToDp(ctx,br),PxToDp(ctx,br),PxToDp(ctx,bl),PxToDp(ctx,bl)};
		g.setCornerRadii(radii);
		return g;
	}
	private static Drawable getTrBack(Context ctx) {
		int btnColor = 0x000000FF;
		GradientDrawable g = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, new int[] { btnColor,
						btnColor, btnColor });
		g.setShape(GradientDrawable.RECTANGLE);
		g.setCornerRadius(PxToDp(ctx,corner));
		return g;
	}
	private static Drawable getButtonBack(Context ctx) {
		int btnColor = 0x550000FF;
		GradientDrawable g = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, new int[] { btnColor,
						btnColor, btnColor });
		g.setShape(GradientDrawable.RECTANGLE);
		g.setCornerRadius(PxToDp(ctx,corner));
		return g;
	}

	private static Drawable getButtonPressedBack(Context ctx) {
		int btnPressedColor = 0x90C5CDFB;
		GradientDrawable g = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, new int[] {
						btnPressedColor, btnPressedColor, btnPressedColor });
		g.setShape(GradientDrawable.RECTANGLE);
		g.setCornerRadius(PxToDp(ctx,corner));
		return g;
	}
	public static Drawable getSelectedBack(Context ctx) {
		int btnPressedColor = 0x90C5CDFB;
		GradientDrawable g = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, new int[] {
						btnPressedColor, btnPressedColor, btnPressedColor });
		g.setShape(GradientDrawable.RECTANGLE);
		g.setCornerRadius(PxToDp(ctx,corner));
		return g;
	}

	public static StateListDrawable getButton(Context ctx) {
		Drawable normal=getButtonBack(ctx);
		Drawable press=getButtonPressedBack(ctx);
		StateListDrawable imageDraw = new StateListDrawable();
		imageDraw.addState(new int[] { android.R.attr.state_pressed }, press);
		imageDraw.addState(new int[] { -android.R.attr.state_pressed }, normal);
		return imageDraw;
	}
	public static StateListDrawable getTrBg(Context ctx){
		Drawable normal=getTrBack(ctx);
		Drawable press=getButtonPressedBack(ctx);
		StateListDrawable imageDraw = new StateListDrawable();
		imageDraw.addState(new int[] { android.R.attr.state_pressed }, press);
		imageDraw.addState(new int[] { -android.R.attr.state_pressed }, normal);
		return imageDraw;
	}
	public static boolean checkApi(){
		if (Build.VERSION.SDK_INT >= 16) {
			return true;
		} else {
			return false;
		}
	}

}
