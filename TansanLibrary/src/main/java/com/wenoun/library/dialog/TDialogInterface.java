package com.wenoun.library.dialog;

import android.view.View;

/**
 * Created by jeyhoon on 15. 12. 16..
 */
public class TDialogInterface {
    public interface OnButtonClickListener {
        public void onClick( BaseDialog d, View v);
    }
    public interface OnItemSelectListener{
        public void onSelect(BaseDialog dialog, View v, int position);
    }
    public interface OnEditConfirmListener{
        public void onClick(BaseDialog dialog, View v, String input);
    }
    public interface OnColorPickerListener{
        void onColorChanged(int color);
        void onColorPick(BaseDialog dialog,int color);
    }
}
