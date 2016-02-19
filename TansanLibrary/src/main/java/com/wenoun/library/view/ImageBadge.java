package com.wenoun.library.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wenoun.library.R;

import java.io.File;

/**
 * Created by jeyhoon on 16. 2. 19..
 */
public class ImageBadge extends RelativeLayout {
    private View root = null;
    RelativeLayout layout = null;
    ImageView iv = null;
    TextView badge = null;
    AttributeSet attrs = null;
    String badgeTxt = "";
    boolean isTAR = true;
    private static final float DEFAULT_HDIP_DENSITY_SCALE = 1.5F;

    public ImageBadge(Context context) {
        super(context);
        this.createView();
    }

    public ImageBadge(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        this.createView();
    }

    public ImageBadge(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.createView();
    }

    public void setBadgeText(int cnt) {
        if (cnt != 0) {
            this.setBadgeText(String.valueOf(cnt));
        } else {
            this.setBadgeText((String) null);
        }

    }

    public void setBadgeText(String txt) {
        this.badgeTxt = txt;
        if (this.badgeTxt != null && this.badgeTxt.length() > 0) {
            this.badge.setVisibility(View.VISIBLE);
            this.badge.setText(this.badgeTxt);
        } else {
            this.badge.setVisibility(View.GONE);
        }

    }

    public void setImageResource(int id) {
//        final int dip50= ImageUtils.dpToPx(getContext(),50);
        Glide.with(getContext()).load(id)
                .centerCrop()
//                .override(dip50,dip50)
                .into(this.iv);
//        this.setImageDrawable(this.getContext().getResources().getDrawable(id));
    }

    public void setImageFile(File file) {
//        final int dip50= ImageUtils.dpToPx(getContext(),50);
        Glide.with(getContext()).load(file)
                .into(this.iv);
//        this.setImageDrawable(this.getContext().getResources().getDrawable(id));
    }

    public void setImageDrawable(Drawable dr) {
//        final int dip50= ImageUtils.dpToPx(getContext(),50);
        Glide.with(getContext()).load(dr)
//                .error(R.drawable.thumb).placeholder(R.drawable.thumb)
                .centerCrop()
//                .override(dip50,dip50)
                .into(this.iv);
    }

    public void setBadgeBackground(int id) {
        this.setBadgeBackground(this.getContext().getResources().getDrawable(id));
    }

    public void setBadgeBackground(Drawable dr) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.badge.setBackground(dr);
        } else {
            this.badge.setBackgroundDrawable(dr);
        }

    }

    private void createView() {
        this.root = inflate(getContext(), R.layout.view_imagebadge, null);
        this.layout = (RelativeLayout) root.findViewById(R.id.imagebadge_root);
        this.iv = (ImageView) root.findViewById(R.id.imagebadge_iv);
        this.badge = (TextView) root.findViewById(R.id.imagebadge_badge);
        addView(root);
//        if(this.attrs != null) {
//            int src_id = this.getContext().obtainStyledAttributes(this.attrs, kr.pe.tansan.imagebadge.R.styleable.ImageBadge).getResourceId(3, -1);
//            int text_size = this.getContext().obtainStyledAttributes(this.attrs, kr.pe.tansan.imagebadge.R.styleable.ImageBadge).getInt(4, 15);
//            this.badgeTxt = this.getContext().obtainStyledAttributes(this.attrs, kr.pe.tansan.imagebadge.R.styleable.ImageBadge).getString(7);
//            int badge_bg_id = this.getContext().obtainStyledAttributes(this.attrs, kr.pe.tansan.imagebadge.R.styleable.ImageBadge).getResourceId(6, -1);
//            int badge_bg_color = this.getContext().obtainStyledAttributes(this.attrs, kr.pe.tansan.imagebadge.R.styleable.ImageBadge).getColor(5, Color.parseColor("#FFFF0000"));
//            this.badge.setTextSize((float)text_size);
//            if(badge_bg_id == -1) {
//                this.badge.setBackgroundColor(badge_bg_color);
//            } else {
//                this.badge.setBackgroundResource(badge_bg_id);
//            }
//
//            if(src_id != -1) {
//                this.setImageResource(src_id);
//            }
//
//            if(this.isTAR) {
//                badgeParams.addRule(11);
//            } else {
//                badgeParams.addRule(8, 1);
//                badgeParams.addRule(7, 1);
//            }
//        }

        if (this.badgeTxt != null && this.badgeTxt.length() > 0) {
            this.badge.setVisibility(View.VISIBLE);
            this.badge.setText(this.badgeTxt);
        } else {
            this.badge.setVisibility(View.GONE);
        }

//        this.addView(this.iv);
//        this.addView(this.badge);
    }

    private int PxToDp(int pixel) {
        Context context = this.getContext();
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((float) pixel / 1.5F * scale);
    }

    private int DpToPx(int DP) {
        Context context = this.getContext();
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) ((float) DP / scale * 1.5F);
    }

    public ImageView getIv() {
        return this.iv;
    }
}
