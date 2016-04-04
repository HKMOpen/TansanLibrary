package com.wenoun.library.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.wenoun.library.R;
import com.wenoun.library.image.ImageUtils;
import com.wenoun.library.view.ColorPickerView;

//import wenoun.in.library.R;

public class TColorPickerDialog extends BaseDialog {
    public static final int STYLE_TANSAN = 0;
    public static final int STYLE_WEMOM = 1;
    public static final int STYLE_TR = 2;
    private int styleId = R.style.TranslucentTheme;
    private boolean isTr = false;
    private TDialogInterface.OnColorPickerListener colorPickerListener=null;
    private ColorPickerView.ColorListener colorListener = new ColorPickerView.ColorListener() {
        @Override
        public void onColorChanged(int color) {
            pickColor=color;
            colorPickerListener.onColorChanged(color);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ctx.setTheme(styleId);
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.colorpicker_dialog);
        setLayout();


    }

    public TColorPickerDialog(Context context) {
        this(context, STYLE_TR);
    }

    public TColorPickerDialog(Context context, int style) {
        super(context, R.style.TranslucentTheme);
        // Dialog 배경을 투명 처리 해준다.
        this.ctx = getContext();
        if (style == STYLE_TANSAN || style == STYLE_TR) {
            this.styleId = R.style.TranslucentTheme;
            if (style == STYLE_TR)
                isTr = true;
        } else if (style == STYLE_WEMOM) {
            this.styleId = R.style.TranslucentWeMomTheme;
        }
    }
    private ColorPickerView colorPicker;
    private Button confirmButton;
    private Button cancelButton;
    private String confirmStr;
    private Context ctx;
    private int pickColor=0xFFFFFF;

    /*
     * Layout
     */
    private void setLayout() {
        colorPicker = (ColorPickerView) findViewById(R.id.colorpicker);

        confirmButton = (Button) findViewById(R.id.confirm_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);
        if (!isEmpty(confirmStr))
            confirmButton.setText(confirmStr);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != colorPickerListener) {
                    colorPickerListener.onColorPick(TColorPickerDialog.this, colorPicker.getColor());
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TColorPickerDialog.this.cancel();
            }
        });
        if (isTr) {
            cancelButton.setBackgroundResource(R.color.tr);
            confirmButton.setBackgroundResource(R.color.tr);
            confirmButton.setPadding(ImageUtils.dpToPx(ctx, 5), ImageUtils.dpToPx(ctx, 5), ImageUtils.dpToPx(ctx, 10), ImageUtils.dpToPx(ctx, 5));
        }
        if(null!=colorPicker) {
            colorPicker.setColorListener(colorListener);
            colorPicker.setColor(pickColor);
        }
    }

//    TDialogInterface.OnButtonClickListener confirmClickListener = null;

    public TColorPickerDialog setOnColorPickerListener(final TDialogInterface.OnColorPickerListener colorPickerListener) {
        this.colorPickerListener = colorPickerListener;
//        colorPicker.setColorListener(colorListener);
        return this;
    }

    public TColorPickerDialog setColor(int color){
        this.pickColor=color;
        if(null!=colorPicker)
            colorPicker.setColor(color);
        return this;
    }
}
