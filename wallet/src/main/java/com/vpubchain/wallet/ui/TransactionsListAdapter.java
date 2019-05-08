package com.vpubchain.wallet.ui;

/*
 * Copyright 2011-2014 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vpubchain.core.coins.CoinType;
import com.vpubchain.core.coins.EthMain;
import com.vpubchain.core.coins.Value;
import com.vpubchain.core.coins.families.EthFamily;
import com.vpubchain.core.coins.families.Families;
import com.vpubchain.core.util.GenericUtils;
import com.vpubchain.core.wallet.AbstractAddress;
import com.vpubchain.core.wallet.AbstractTransaction;
import com.vpubchain.core.wallet.AbstractWallet;
import com.vpubchain.core.wallet.EthWalletPocketHD;
import com.vpubchain.core.wallet.families.eth.EthAddress;
import com.vpubchain.wallet.AddressBookProvider;
import com.vpubchain.wallet.R;
import com.vpubchain.wallet.data.EthResponse;
import com.vpubchain.wallet.ui.widget.CurrencyTextView;
import com.vpubchain.wallet.util.Fonts;
import com.vpubchain.wallet.util.NumberArithmeticUtils;
import com.vpubchain.wallet.util.StringFormat;
import com.vpubchain.wallet.util.TimeUtils;
import com.vpubchain.wallet.util.WalletUtils;

import org.bitcoinj.core.TransactionConfidence;
import org.bitcoinj.core.TransactionConfidence.ConfidenceType;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

/**
 * @author Andreas Schildbach
 */
public class TransactionsListAdapter extends BaseAdapter {
    private final Context context;
    private final LayoutInflater inflater;
    //币种账号 传进来
    private AbstractWallet walletPocket;
    //交易数据  比特系列
    private final List<AbstractTransaction> transactions = new ArrayList<>();
    private final Resources res;
    private int precision = 0;
    private int shift = 0;
    private boolean showEmptyText = false;

    private final int colorSignificant;
    private final int colorLessSignificant;
    private final int colorInsignificant;
    private final int colorError;
    private final int colorCircularBuilding = Color.parseColor("#44ff44");
    private final String minedTitle;
    private final String fontIconMined;
    private final String sentToTitle;
    private final String fontIconSentTo;
    private final String receivedWithTitle;
    private final String receivedFromTitle;
    private final String fontIconReceivedWith;

    private final Map<AbstractAddress, String> labelCache = new HashMap<>();
    private final static Object CACHE_NULL_MARKER = "";

    private static final String CONFIDENCE_SYMBOL_DEAD = "\u271D"; // latin cross
    private static final String CONFIDENCE_SYMBOL_UNKNOWN = "?";

    private static final int VIEW_TYPE_TRANSACTION = 0;

    private static final int ETH = 2;
    private static final int BTC = 1;

    //eth 交易数据
    private List<EthResponse.ResultBean> resultBeanList = new ArrayList<>();
    private boolean isEth = false;



    @Deprecated // TODO change AbstractWallet to WalletAccount
    public TransactionsListAdapter(final Context context, @Nonnull final AbstractWallet walletPocket) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        //传进来的WalletPocketHD
        this.walletPocket = walletPocket;
        isEth = false;

        res = context.getResources();
//        colorSignificant = res.getColor(R.color.gray_87_text);
//        colorLessSignificant = res.getColor(R.color.gray_54_sec_text_icons);
//        colorInsignificant = res.getColor(R.color.gray_26_hint_text);
        colorSignificant = res.getColor(R.color.white);
        colorLessSignificant = res.getColor(R.color.white);
        colorInsignificant = res.getColor(R.color.white);
        colorError = res.getColor(R.color.fg_error);
        minedTitle = res.getString(R.string.wallet_transactions_coinbase);
        fontIconMined = res.getString(R.string.font_icon_mining);
        sentToTitle = res.getString(R.string.sent_to);
        fontIconSentTo = res.getString(R.string.font_icon_send_coins);
        receivedWithTitle = res.getString(R.string.received_with);
        receivedFromTitle = res.getString(R.string.received_from);
        fontIconReceivedWith = res.getString(R.string.font_icon_receive_coins);
    }

    public TransactionsListAdapter(final Context context, List<EthResponse.ResultBean> resultBeanList,AbstractWallet walletPocket){

        this.context = context;
        inflater = LayoutInflater.from(context);
        //传进来的WalletPocketHD
        this.resultBeanList = resultBeanList;
        this.walletPocket = walletPocket;
        isEth = true;

        res = context.getResources();
//        colorSignificant = res.getColor(R.color.gray_87_text);
//        colorLessSignificant = res.getColor(R.color.gray_54_sec_text_icons);
//        colorInsignificant = res.getColor(R.color.gray_26_hint_text);
        colorSignificant = res.getColor(R.color.white);
        colorLessSignificant = res.getColor(R.color.white);
        colorInsignificant = res.getColor(R.color.white);
        colorError = res.getColor(R.color.fg_error);
        minedTitle = res.getString(R.string.wallet_transactions_coinbase);
        fontIconMined = res.getString(R.string.font_icon_mining);
        sentToTitle = res.getString(R.string.sent_to);
        fontIconSentTo = res.getString(R.string.font_icon_send_coins);
        receivedWithTitle = res.getString(R.string.received_with);
        receivedFromTitle = res.getString(R.string.received_from);
        fontIconReceivedWith = res.getString(R.string.font_icon_receive_coins);

    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        if (isEth){
            return ETH;
        }else {
            return BTC;
        }
    }

    public void setPrecision(final int precision, final int shift) {
        this.precision = precision;
        this.shift = shift;
        //刷新
        notifyData();
    }

    public void clear() {
        transactions.clear();

        notifyData();
    }


    public void replace(@Nonnull final AbstractTransaction tx) {
        transactions.clear();
        transactions.add(tx);

        notifyData();
    }

    //BalanceFragment.onLoadFinished()调用
    public void replace(@Nonnull final Collection<AbstractTransaction> transactions) {
        this.transactions.clear();
        this.transactions.addAll(transactions);

        showEmptyText = true;

        notifyData();
    }

    @Override
    public boolean isEmpty() {
        return showEmptyText && super.isEmpty();
    }

    @Override
    public int getCount() {
        if (isEth){
//            return 1;
            return resultBeanList.size();
        }else {
            return transactions.size();
        }

    }

    //每一个item的数据
    @Override
    public Object getItem(final int position) {

        if (isEth){
            if (position == resultBeanList.size())
                return null;

            return resultBeanList.get(position);
        }else {
            if (position == transactions.size())
                return null;

            return transactions.get(position);
        }

    }

    @Override
    public long getItemId(final int position) {
        if (position == transactions.size())
            return 0;

        //一定要写   卧槽
        if (isEth){
            if (position == resultBeanList.size())
                return 0;
            return WalletUtils.longHash(resultBeanList.get(position).getHash().getBytes());
        }else {
            if (position == transactions.size())
                return 0;
            //使用xxx作为id
            return WalletUtils.longHash(transactions.get(position).getHashBytes());
        }

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(final int position, View row, final ViewGroup parent) {
        final int type = getItemViewType(position);

        if (type == BTC) {
            if (row == null)
                row = inflater.inflate(R.layout.transaction_row, null);

            final AbstractTransaction tx = (AbstractTransaction) getItem(position);
            //给每个itemview 绑定数据
            bindView(row, tx);
        } else if (type == ETH){
            if (row == null)
                row = inflater.inflate(R.layout.transaction_row, null);

            final EthResponse.ResultBean tx = (EthResponse.ResultBean) getItem(position);
            //给每个itemview 绑定数据
            bindView(row, tx);
        }

        return row;
    }

    public void bindView(@Nonnull final View row, @Nonnull final Object txs) {
        Resources res = context.getResources();


        final TextView rowDirectionText = (TextView) row.findViewById(R.id.transaction_row_direction_text);
        final TextView rowDirectionFontIcon = (TextView) row.findViewById(R.id.transaction_row_direction_font_icon);
        Fonts.setTypeface(rowDirectionFontIcon, Fonts.Font.COINOMI_FONT_ICONS);
        final TextView rowConfirmationsFontIcon = (TextView) row.findViewById(R.id.transaction_row_confirmations_font_icon);
        Fonts.setTypeface(rowConfirmationsFontIcon, Fonts.Font.COINOMI_FONT_ICONS);
        final TextView rowMessageFontIcon = (TextView) row.findViewById(R.id.transaction_row_message_font_icon);
        Fonts.setTypeface(rowMessageFontIcon, Fonts.Font.COINOMI_FONT_ICONS);
        final TextView rowDate = (TextView) row.findViewById(R.id.transaction_row_time);
        final TextView rowLabel = (TextView) row.findViewById(R.id.transaction_row_label);
        final TextView rowAddress = (TextView) row.findViewById(R.id.transaction_row_address);
        final CurrencyTextView rowValue = (CurrencyTextView) row.findViewById(R.id.transaction_row_value);


        if (!isEth){
            AbstractTransaction tx = (AbstractTransaction) txs;
            //获取tx的状态   是否确认等等
            final ConfidenceType confidenceType = tx.getConfidenceType();
            final boolean isOwn = tx.getSource().equals(TransactionConfidence.Source.SELF);
//        final boolean isInternal = WalletUtils.isInternal(tx);
            //得到tx的金额
            final Value value = tx.getValue(walletPocket);
            final boolean sent = value.signum() < 0;
            //获取币种类型
            final CoinType type = walletPocket.getCoinType();

            // confidence
            if (confidenceType == ConfidenceType.PENDING) {
                rowLabel.setTextColor(colorInsignificant);
                rowValue.setTextColor(colorInsignificant);
                rowDirectionText.setTextColor(colorInsignificant);
                rowDirectionFontIcon.setTextColor(colorInsignificant);
                rowDirectionFontIcon.setBackgroundResource(R.drawable.transaction_row_circle_bg_pending);
            } else if (confidenceType == ConfidenceType.BUILDING) {
                rowLabel.setTextColor(colorSignificant);
                rowValue.setTextColor(colorSignificant);
                rowDirectionText.setTextColor(colorLessSignificant);
                rowDirectionFontIcon.setTextColor(colorLessSignificant);
                if (value.isNegative()) {
                    //表示发送
                    rowDirectionFontIcon.setBackgroundResource(R.drawable.transaction_row_circle_bg_send);
                    rowValue.setTextColor(res.getColor(R.color.send_color_fg));
                } else {
                    //表示接收
                    rowDirectionFontIcon.setBackgroundResource(R.drawable.transaction_row_circle_bg_receive);
                    rowValue.setTextColor(res.getColor(R.color.receive_color_fg));
                }
            } else if (confidenceType == ConfidenceType.DEAD) {
                rowLabel.setTextColor(colorSignificant);
                rowValue.setTextColor(colorSignificant);
                Fonts.strikeThrough(rowLabel);
                Fonts.strikeThrough(rowValue);
            } else {
                rowDirectionText.setTextColor(colorError);
                rowLabel.setTextColor(colorInsignificant);
                rowValue.setTextColor(colorInsignificant);
                rowDirectionFontIcon.setTextColor(colorInsignificant);
                rowDirectionFontIcon.setBackgroundResource(R.drawable.transaction_row_circle_bg_pending);
            }

            // 显示当前确认数  所以要获取最新区块头？   看看bitcoinJ的Transaction实现
            if (tx.getDepthInBlocks() < 4) {
                rowConfirmationsFontIcon.setVisibility(View.VISIBLE);
                rowConfirmationsFontIcon.setTextColor(colorLessSignificant);
                switch (tx.getDepthInBlocks()) {
                    case 0: // No confirmations
                        rowConfirmationsFontIcon.setText(res.getString(R.string.font_icon_progress_empty));
                        rowConfirmationsFontIcon.setTextColor(colorInsignificant); // PENDING
                        break;
                    case 1: // 1 out of 3 confirmations
                        rowConfirmationsFontIcon.setText(res.getString(R.string.font_icon_progress_one));
                        break;
                    case 2: // 2 out of 3 confirmations
                        rowConfirmationsFontIcon.setText(res.getString(R.string.font_icon_progress_two));
                        break;
                    case 3: // 3 out of 3 confirmations
                        rowConfirmationsFontIcon.setText(res.getString(R.string.font_icon_progress_full));
                        break;
                }
            } else {
                rowConfirmationsFontIcon.setVisibility(View.GONE);
            }

            // 币的来源
            if (tx.isGenerated()) {
                rowDirectionText.setText(minedTitle);
                rowDirectionFontIcon.setText(fontIconMined);
            } else {
                if (value.isNegative()) {
                    rowDirectionText.setText(sentToTitle);
                    rowDirectionFontIcon.setText(fontIconSentTo);
                } else if (walletPocket.getCoinType().getFamily() != Families.NXT) {
                    rowDirectionText.setText(receivedWithTitle);
                    rowDirectionFontIcon.setText(fontIconReceivedWith);
                } else {
                    rowDirectionText.setText(receivedFromTitle);
                    rowDirectionFontIcon.setText(fontIconReceivedWith);
                }
            }

            // 该交易的时间
            final long time = tx.getTimestamp();
            if (time > 0) {
                rowDate.setText(TimeUtils.  toRelativeTimeString(time));
                rowDate.setVisibility(View.VISIBLE);
            } else {
                rowDate.setVisibility(View.GONE);
            }

            // address - label
            final AbstractAddress address;
            final String label;

            //得到该交易的地址
            if (sent) {
                // //通过交易信息和钱包账号得到 地址List   发送到xxx地址
                List<AbstractAddress> sentTo = WalletUtils.getSendToAddress(tx, walletPocket);
                // 目前只显示第一个地址
                address = sentTo.size() == 0 ? null : sentTo.get(0);
            } else {
                // 接收的地址
                List<AbstractAddress> receivedWith = WalletUtils.getReceivedWithOrFrom(tx, walletPocket);
                // Should be one
                address = receivedWith.size() == 0 ? null : receivedWith.get(0);
            }

            if (address != null) {
                label = resolveLabel(address);
            } else {
                if (sent) {
                    // If no address found, assume it is an internal transfer
                    label = res.getString(R.string.internal_transfer);
                } else {
                    label = "?";
                }
            }

            if (label != null) {
                rowLabel.setText(label);
                if (address != null) {
                    rowAddress.setText(GenericUtils.addressSplitToGroups(address));
                    rowAddress.setVisibility(View.VISIBLE);
                } else {
                    rowAddress.setVisibility(View.GONE);
                }
            } else if (address != null) {
                rowLabel.setText(GenericUtils.addressSplitToGroups(address));
                rowAddress.setVisibility(View.GONE);
            } else {
                rowLabel.setText("???"); // should not happen
            }
            rowAddress.setVisibility(View.GONE);
            rowLabel.setTypeface(label != null ? Typeface.DEFAULT : Typeface.MONOSPACE);

            // value
            rowValue.setAlwaysSigned(true);
            rowValue.setAmount(value);

            // Show message label
            if (type.canHandleMessages()) {
            /*MessageFactory factory = type.getMessagesFactory();

            try {
                // TODO not efficient, should parse the message and save it to a database
                if (factory != null && factory.extractPublicMessage(tx) != null) {
                    rowMessageFontIcon.setVisibility(View.VISIBLE);
                } else {
                    rowMessageFontIcon.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                rowMessageFontIcon.setVisibility(View.GONE);
                ACRA.getErrorReporter().handleSilentException(e);
            }*/
                if (tx.getMessage() == null) {
                    rowMessageFontIcon.setVisibility(View.GONE);
                }
                else {
                    rowMessageFontIcon.setVisibility(View.VISIBLE);
                }
            } else {
                rowMessageFontIcon.setVisibility(View.GONE);
            }

        }else{

            EthResponse.ResultBean tx = (EthResponse.ResultBean) txs;

            //获取tx的状态   是否确认等等
            final int confidenceType = Integer.parseInt(tx.getConfirmations());
            //得到tx的金额
            final BigInteger valuebig = new BigInteger(tx.getValue());

            final boolean sent = valuebig.signum() < 0;
            //获取币种类型
            final EthFamily ethFamily = new EthMain();

            boolean isSend = true;

//            EthWalletPocketHD ethWalletPocketHDs = (EthWalletPocketHD) walletPocket;
//            String addresss = ethWalletPocketHDs.getAddress().getAddress();

            if (tx.getFrom().equals("")) {
                isSend = true;
            }else {
                isSend = false;
            }


            String valueFor = StringFormat.stringToFormat(NumberArithmeticUtils.safeDivide(valuebig,1e18),6);

            final Value value = new Value(ethFamily,valuebig.longValue());

            // confidence
            if (confidenceType < 6) {
                rowLabel.setTextColor(colorInsignificant);
                rowValue.setTextColor(colorInsignificant);
                rowDirectionText.setTextColor(colorInsignificant);
                rowDirectionFontIcon.setTextColor(colorInsignificant);
                rowDirectionFontIcon.setBackgroundResource(R.drawable.transaction_row_circle_bg_pending);
            } else if (confidenceType >= 6) {
                rowLabel.setTextColor(colorSignificant);
                rowValue.setTextColor(colorSignificant);
                rowDirectionText.setTextColor(colorLessSignificant);
                rowDirectionFontIcon.setTextColor(colorLessSignificant);
                if (isSend) {
                    //表示发送
                    rowDirectionFontIcon.setBackgroundResource(R.drawable.transaction_row_circle_bg_send);
                    rowValue.setTextColor(res.getColor(R.color.send_color_fg));
                } else {
                    //表示接收
                    rowDirectionFontIcon.setBackgroundResource(R.drawable.transaction_row_circle_bg_receive);
                    rowValue.setTextColor(res.getColor(R.color.receive_color_fg));
                }
            }
//            else if (confidenceType == ConfidenceType.DEAD) {
//                rowLabel.setTextColor(colorSignificant);
//                rowValue.setTextColor(colorSignificant);
//                Fonts.strikeThrough(rowLabel);
//                Fonts.strikeThrough(rowValue);
//            }
            else {
                rowDirectionText.setTextColor(colorError);
                rowLabel.setTextColor(colorInsignificant);
                rowValue.setTextColor(colorInsignificant);
                rowDirectionFontIcon.setTextColor(colorInsignificant);
                rowDirectionFontIcon.setBackgroundResource(R.drawable.transaction_row_circle_bg_pending);
            }

            // 显示当前确认数  所以要获取最新区块头？   看看bitcoinJ的Transaction实现
            if (Integer.parseInt(tx.getConfirmations()) < 4) {
                rowConfirmationsFontIcon.setVisibility(View.VISIBLE);
                rowConfirmationsFontIcon.setTextColor(colorLessSignificant);
                switch (Integer.parseInt(tx.getConfirmations())) {
                    case 0: // No confirmations
                        rowConfirmationsFontIcon.setText(res.getString(R.string.font_icon_progress_empty));
                        rowConfirmationsFontIcon.setTextColor(colorInsignificant); // PENDING
                        break;
                    case 1: // 1 out of 3 confirmations
                        rowConfirmationsFontIcon.setText(res.getString(R.string.font_icon_progress_one));
                        break;
                    case 2: // 2 out of 3 confirmations
                        rowConfirmationsFontIcon.setText(res.getString(R.string.font_icon_progress_two));
                        break;
                    case 3: // 3 out of 3 confirmations
                        rowConfirmationsFontIcon.setText(res.getString(R.string.font_icon_progress_full));
                        break;
                }
            } else {
                rowConfirmationsFontIcon.setVisibility(View.GONE);
            }





            // 币的来源
//            if (tx.isGenerated()) {
//                rowDirectionText.setText(minedTitle);
//                rowDirectionFontIcon.setText(fontIconMined);
//            } else {
                if (isSend) {
                    rowDirectionText.setText(sentToTitle);
                    rowDirectionFontIcon.setText(fontIconSentTo);
                } else {
                    rowDirectionText.setText(receivedFromTitle);
                    rowDirectionFontIcon.setText(fontIconReceivedWith);
                }
//            }

            // 该交易的时间
            final long time = Long.parseLong(tx.getTimeStamp());
            if (time > 0) {
                rowDate.setText(TimeUtils.  toRelativeTimeString(time));
                rowDate.setVisibility(View.VISIBLE);
            } else {
                rowDate.setVisibility(View.GONE);
            }

            // address - label
            final AbstractAddress address;
            final String label;

            //得到该交易的地址
            if (isSend) {

                address = new EthAddress(ethFamily,tx.getTo());

            } else {

                address = new EthAddress(ethFamily,tx.getFrom());
            }

            if (address != null) {
                label = resolveLabel(address);
            } else {
                if (isSend) {
                    // If no address found, assume it is an internal transfer
                    label = res.getString(R.string.internal_transfer);
                } else {
                    label = "?";
                }
            }

            if (label != null) {
                rowLabel.setText(label);
                if (address != null) {
                    rowAddress.setText(GenericUtils.addressSplitToGroups(address));
                    rowAddress.setVisibility(View.VISIBLE);
                } else {
                    rowAddress.setVisibility(View.GONE);
                }
            } else if (address != null) {
                rowLabel.setText(GenericUtils.addressSplitToGroups(address));
                rowAddress.setVisibility(View.GONE);
            } else {
                rowLabel.setText("???"); // should not happen
            }
            rowAddress.setVisibility(View.GONE);
            rowLabel.setTypeface(label != null ? Typeface.DEFAULT : Typeface.MONOSPACE);

            // value
            rowValue.setAlwaysSigned(true);
//            rowValue.setAmount(value);

            rowValue.setText(valueFor);

//            // Show message label
//            if (type.canHandleMessages()) {
//
//                if (tx.getMessage() == null) {
//                    rowMessageFontIcon.setVisibility(View.GONE);
//                }
//                else {
//                    rowMessageFontIcon.setVisibility(View.VISIBLE);
//                }
//            } else {
//                rowMessageFontIcon.setVisibility(View.GONE);
//            }





        }


    }

    private String resolveLabel(@Nonnull final AbstractAddress address) {
        final String cachedLabel = labelCache.get(address);
        if (cachedLabel == null) {
            final String label = AddressBookProvider.resolveLabel(context, address);
            if (label != null) {
                labelCache.put(address, label);
            } else {
                labelCache.put(address, (String)CACHE_NULL_MARKER);
            }
            return label;
        } else {
            return cachedLabel != CACHE_NULL_MARKER ? cachedLabel : null;
        }
    }

    public void clearLabelCache() {
        labelCache.clear();

        notifyData();
    }


    private void notifyData(){
        if (isEth){

        }else {
            notifyDataSetChanged();
        }
    }
}
