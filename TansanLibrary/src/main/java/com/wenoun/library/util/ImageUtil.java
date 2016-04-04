package com.wenoun.library.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
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
	public static Drawable getDrawable(Context ctx,int backColor, int radius) {
		GradientDrawable g = new GradientDrawable(
				GradientDrawable.Orientation.LEFT_RIGHT, new int[] {
				backColor, backColor });
		g.setShape(GradientDrawable.RECTANGLE);
		float[] radii={PxToDp(ctx,radius),PxToDp(ctx,radius),PxToDp(ctx,radius),PxToDp(ctx,radius),PxToDp(ctx,radius),PxToDp(ctx,radius),PxToDp(ctx,radius),PxToDp(ctx,radius)};
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
	public static class RoundedAvatarDrawable extends Drawable {
		private final Bitmap mBitmap;
		private final Paint mPaint;
		private final RectF mRectF;
		private final int mBitmapWidth;
		private final int mBitmapHeight;
        private int radiusPx=0;

		public RoundedAvatarDrawable(Bitmap bitmap,int radiusPx) {
            this.radiusPx=radiusPx;
			mBitmap = bitmap;
			mRectF = new RectF();
			mPaint = new Paint();
			mPaint.setAntiAlias(true);
			mPaint.setDither(true);
			final BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
			mPaint.setShader(shader);

			mBitmapWidth = mBitmap.getWidth();
			mBitmapHeight = mBitmap.getHeight();
		}

		@Override
		public void draw(Canvas canvas) {
			canvas.drawRoundRect(mRectF,radiusPx,radiusPx ,mPaint);
		}

		@Override
		protected void onBoundsChange(Rect bounds) {
			super.onBoundsChange(bounds);

			mRectF.set(bounds);
		}

		@Override
		public void setAlpha(int alpha) {
			if (mPaint.getAlpha() != alpha) {
				mPaint.setAlpha(alpha);
				invalidateSelf();
			}
		}

		@Override
		public void setColorFilter(ColorFilter cf) {
			mPaint.setColorFilter(cf);
		}

		@Override
		public int getOpacity() {
			return PixelFormat.TRANSLUCENT;
		}

		@Override
		public int getIntrinsicWidth() {
			return mBitmapWidth;
		}

		@Override
		public int getIntrinsicHeight() {
			return mBitmapHeight;
		}

		public void setAntiAlias(boolean aa) {
			mPaint.setAntiAlias(aa);
			invalidateSelf();
		}

		@Override
		public void setFilterBitmap(boolean filter) {
			mPaint.setFilterBitmap(filter);
			invalidateSelf();
		}

		@Override
		public void setDither(boolean dither) {
			mPaint.setDither(dither);
			invalidateSelf();
		}

		public Bitmap getBitmap() {
			return mBitmap;
		}

	}

}
