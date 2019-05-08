package com.vpubchain.wallet.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.vpubchain.core.wallet.Wallet;
import com.vpubchain.wallet.Constants;
import com.vpubchain.wallet.R;
import com.vpubchain.wallet.util.Fonts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generates and shows a new passphrase
 */
public class SeedFragment extends Fragment {
    private static final Logger log = LoggerFactory.getLogger(SeedFragment.class);

    private WelcomeFragment.Listener listener;
    private boolean hasExtraEntropy = false;
    private TextView mnemonicView;

    public SeedFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        View view = inflater.inflate(R.layout.fragment_seed, container, false);
        View view = inflater.inflate(R.layout.activity_wallet_create, container, false);
//
        TextView seedFontIcon = (TextView) view.findViewById(R.id.tv_pre_change);
//        Fonts.setTypeface(seedFontIcon, Fonts.Font.COINOMI_FONT_ICONS);

        final TextView tv_next = (TextView) view.findViewById(R.id.tv_next);
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onSeedCreated(mnemonicView.getText().toString());
                }
            }
        });

        mnemonicView = (TextView) view.findViewById(R.id.seed);
        generateNewMnemonic();

        seedFontIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateNewMnemonic();
            }
        });


        return view;
    }

    private void generateNewMnemonic() {
        String mnemonic;
        if (hasExtraEntropy) {
            mnemonic = Wallet.generateMnemonicString(Constants.SEED_ENTROPY_EXTRA);
        } else {
            mnemonic = Wallet.generateMnemonicString(Constants.SEED_ENTROPY_DEFAULT);
        }
        mnemonicView.setText(mnemonic);
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);
        try {
            listener = (WelcomeFragment.Listener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement " + WelcomeFragment.Listener.class);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
