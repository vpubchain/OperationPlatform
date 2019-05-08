package com.vpubchain.wallet.ui.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vpubchain.wallet.R;

/**
 * @author John L. Jegutanis
 */
public class HeaderWithFontIcon extends LinearLayout {
    private final TextView messageView;

    public HeaderWithFontIcon(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.test_header_with_font_icon, this, true);

        messageView = (TextView) findViewById(R.id.message);
    }



    public void setMessage(int resid) {
        messageView.setText(resid);
    }
}
