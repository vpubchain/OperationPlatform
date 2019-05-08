package com.jiuling.commonbusiness.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.jiuling.commonbusiness.R;

public class StartActivityUtils {

    public static void goToAct(Activity context,Class to){
        Intent intent = new Intent(context, to);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.open_in, R.anim.open_out);
    }

    public static void goToAct(Activity context,Intent intent){
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.open_in, R.anim.open_out);
    }

    public static void goToAct(Activity context,Intent intent,boolean finish){
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.open_in, R.anim.open_out);
        if (finish){
            context.finish();
        }
    }

    public static void goToAct(Activity context,Class to,boolean finish){
        Intent intent = new Intent(context, to);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.open_in, R.anim.open_out);
        if (finish){
            context.finish();
        }
    }



}
