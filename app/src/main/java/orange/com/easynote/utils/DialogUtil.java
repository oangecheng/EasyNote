package orange.com.easynote.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;

/**
 * Created by Orange on 2016/12/24.
 */

public class DialogUtil extends Dialog{

    public DialogUtil(final Context context, final View view) {
        super(context);
        createUserDialog(view, false);
    }

    public DialogUtil(final Context context, final View view, final boolean isCancelable) {
        super(context);
        createUserDialog(view, isCancelable);


    }

    private void createUserDialog(View view, boolean isProgress) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(view);
        getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);

        if (isProgress) {
            setCancelable(true);
            setCanceledOnTouchOutside(true);
        } else {
            setCancelable(false);
            setCanceledOnTouchOutside(false);
        }
    }

}
