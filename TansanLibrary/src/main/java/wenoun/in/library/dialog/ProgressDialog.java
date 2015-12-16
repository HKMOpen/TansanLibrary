package wenoun.in.library.dialog;

import android.app.Dialog;
import android.content.Context;

import wenoun.in.library.R;

/**
 * Created by jeyhoon on 15. 11. 2..
 */
public class ProgressDialog {
    public static Dialog getProgressDialog(Context ctx) {
        Dialog dialog = new Dialog(ctx, R.style.dialog);
        dialog.setContentView(R.layout.dialog_progress);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
