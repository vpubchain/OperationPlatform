package com.jiuling.operate.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.commonbusiness.util.SharedPreferencesUtils;
import com.jiuling.commonbusiness.util.StartActivityUtils;
import com.jiuling.commonbusiness.util.ToastUtils;
import com.jiuling.operate.R;
import com.jiuling.operate.R2;
import com.jiuling.operate.contract.IOperatePaymentActivityView;
import com.jiuling.operate.di.component.DaggerOperatePaymentComponent;
import com.jiuling.operate.di.module.OperatePaymentModule;
import com.jiuling.operate.entity.OrderNumberBean;
import com.jiuling.operate.entity.PayResult;
import com.jiuling.operate.entity.RequestAlipayBean;
import com.jiuling.operate.entity.RequestBalancePaymentBean;
import com.jiuling.operate.entity.RequestOrderNumberBean;
import com.jiuling.operate.presenter.OperatePaymentActivityPresenter;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OperatePaymentActivity extends BaseOperateActivity<OperatePaymentActivityPresenter> implements IOperatePaymentActivityView {


    @BindView(R2.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R2.id.node_count)
    TextView nodeCount;
    @BindView(R2.id.tv_maybe_pay)
    TextView tvMaybePay;
    @BindView(R2.id.tv_remaining_time)
    TextView tvRemainingTime;
    @BindView(R2.id.rb_alipay)
    RadioButton rbAlipay;
    @BindView(R2.id.rb_wechat)
    RadioButton rbWechat;
    @BindView(R2.id.rb_balance)
    RadioButton rbBalance;
    @BindView(R2.id.tv_actual_payment)
    TextView tvActualPayment;
    @BindView(R2.id.bt_pay)
    Button btPay;
    private int product_type_id;
    private String quantity;
    private String price;

    private String orderNumber;

    // 0 支付宝  1 微信   2  余额
    private int paymentType = 0;

    private double money = 0;


    //是否是支付订单
    private boolean hasOrderNumber;
    private String quantityOfOrder;
    private String order;
    private String amount;


    @Override
    protected void setupAcitivtyComponent(ApplicationComponent applicationComponent) {
        DaggerOperatePaymentComponent.builder().applicationComponent(applicationComponent).operatePaymentModule(new OperatePaymentModule(this)).build().injectOperatePaymentActivity(this);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_payment;
    }

    @Override
    protected void initView() {



    }

    @Override
    protected void initData() {

        hasOrderNumber = getIntent().hasExtra("payorder");
        if (hasOrderNumber){
            orderNumber = getIntent().getStringExtra("payorder");
            quantityOfOrder = getIntent().getStringExtra("quantity");
            amount = getIntent().getStringExtra("amount");
            tvOrderNumber.setText("订单编号:"+orderNumber);
            nodeCount.setText("节点数量:"+quantityOfOrder);
            tvMaybePay.setText("应付金额:"+amount);
            tvActualPayment.setText("￥ "+amount);
//            money = orderNumberBean.getAmount();

        }else {
            product_type_id = getIntent().getIntExtra("product_type_id", -1);
            quantity = getIntent().getStringExtra("quantity");
            price = getIntent().getStringExtra("price");


            RequestOrderNumberBean requestOrderNumberBean = new RequestOrderNumberBean(product_type_id, quantity, price);

            SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
            String authorization = sharedPreferencesUtils.getAcceeToken(this);
            mPresenter.getOrderNubmer("Bearer " + authorization, requestOrderNumberBean, true);
        }




    }

    @Override
    protected void initEvent() {

        btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (paymentType == 0){
                    RequestAlipayBean requestAlipayBean = new RequestAlipayBean(orderNumber,"test","5m");
                    SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
                    String authorization = sharedPreferencesUtils.getAcceeToken(OperatePaymentActivity.this);
                    mPresenter.getAlipayInfo("Bearer " + authorization, requestAlipayBean, true);
                }else if (paymentType == 1){
                    ToastUtils.showToast("暂不支持微信支付");
                }else {
                    RequestBalancePaymentBean requestBalancePaymentBean = new RequestBalancePaymentBean(orderNumber);
                    SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
                    String authorization = sharedPreferencesUtils.getAcceeToken(OperatePaymentActivity.this);
                    mPresenter.balancePayment("Bearer " + authorization, requestBalancePaymentBean, true);
                }





            }
        });


        rbAlipay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    rbBalance.setChecked(false);
                    rbWechat.setChecked(false);
                    paymentType = 0;
                }

            }
        });

        rbWechat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    rbBalance.setChecked(false);
                    rbAlipay.setChecked(false);
                    paymentType = 1;
                }
            }
        });

        rbBalance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    rbAlipay.setChecked(false);
                    rbWechat.setChecked(false);
                    paymentType = 2;
                }
            }
        });


    }

    @Override
    public void showData(ArrayList data) {

    }

    @Override
    public void getOrderNubmer(OrderNumberBean orderNumberBean) {

//        ToastUtils.showToast(orderNumberBean.toString());
        orderNumber = orderNumberBean.getSerialNumber();
        tvOrderNumber.setText("订单编号:"+orderNumberBean.getSerialNumber());
        nodeCount.setText("节点数量:"+orderNumberBean.getQuantity());
        tvMaybePay.setText("应付金额:"+orderNumberBean.getAmount());
        tvActualPayment.setText("￥ "+orderNumberBean.getAmount());

        money = orderNumberBean.getAmount();




    }

    @Override
    public void getAlipayInfo(String alipayInfo) {


        Log.i("body","获取支付宝结果"+alipayInfo);
//        final String orderInfo = "alipay_sdk=alipay-sdk-java-3.6.0.ALL&app_id=2019022663386050&biz_content=%7B%22out_trade_no%22%3A%22OD2019041514345622343341249%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22test%22%2C%22timeout_express%22%3A%225m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fpaas.vpubchain.org%2Falipay%2Fnotify&sign=Ml0WoxRF6Y%2BF1UNbmU9Y7pMwqJDY%2B%2BIUNRU9MbEEcEd2w%2F76Iq1Ek3PV%2F4jFch1%2B5PRk9Tq31KZzz2bYWn5wsyPXiHxYHSRQqIG4TpnwOzY1F6nXkYnPk3wUDvwHiA5BxdHU%2BD3AFE3nuGtSRNFvEN3NcvkK8Z2zvNy1yeVmm%2FnKXlWLutG1gpFp1aJj%2FzSFxy9QULw3V1FkpGUfrYOwOWfQ%2BrCGyd%2FuivS7Y%2FUarC4jFSVWmk0K6lsnoHBbv%2FfyIjc64WAMdNfO1YTDiZfjVLteRdvXtwE9prizzIOiYVDeflF7jemAVQbTEKOtI88jvPImOMnevOmeZYn%2BoX9HCQ%3D%3D&sign_type=RSA2&timestamp=2019-04-15+14%3A35%3A41&version=1.0";
        final String orderInfo = alipayInfo;
                // 订单信息

                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(OperatePaymentActivity.this);
                        Map<String, String> result = alipay.payV2(orderInfo, true);

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();



    }

    @Override
    public void balancePaymentResult(String result) {
//        ToastUtils.showToast("余额支付成功"+result);

        Intent intent = new Intent(OperatePaymentActivity.this,OperatePaySuccessActivity.class);
        intent.putExtra("amount",money);
        intent.putExtra("orderNumber",orderNumber);
        StartActivityUtils.goToAct(OperatePaymentActivity.this,intent,true);

    }


//
//    @OnClick({R2.id.iv_reduce,R2.id.iv_increase,R2.id.bt_buy})
//    public void viewsOnClick(View view) {
//
//    }

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;


    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。

                        Intent intent = new Intent(OperatePaymentActivity.this,OperatePaySuccessActivity.class);
                        intent.putExtra("amount",money);
                        intent.putExtra("orderNumber",orderNumber);
                        StartActivityUtils.goToAct(OperatePaymentActivity.this,intent,true);


                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtils.showToast("支付失败:" + payResult);
                    }
                    break;
                }

                default:
                    break;
            }
        };
    };



}