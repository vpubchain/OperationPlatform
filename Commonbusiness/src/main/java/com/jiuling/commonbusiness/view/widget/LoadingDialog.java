package com.jiuling.commonbusiness.view.widget;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.jiuling.commonbusiness.R;


/**
 * Created by Rayman on 2016/9/9.
 */
public class LoadingDialog {

    public Dialog dialog;
    private LinearLayout mLl_loading;
    private Animation animation;

    public void showDialog(Activity activity) {
        animation = AnimationUtils.loadAnimation(activity, R.anim.loading_animation);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_loading, null);
        mLl_loading = (LinearLayout) view.findViewById(R.id.ll_loading);
        mLl_loading.setAnimation(animation);
        animation.start();
        if (dialog == null) {
            dialog = new Dialog(activity, R.style.alertDialog);
            dialog.setCancelable(true);
            dialog.setContentView(view);
            dialog.show();
        }
    }

    public void dialogDismiss() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
