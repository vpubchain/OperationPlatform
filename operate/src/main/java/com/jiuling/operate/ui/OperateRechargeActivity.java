package com.jiuling.operate.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.commonbusiness.util.SharedPreferencesUtils;
import com.jiuling.commonbusiness.util.ToastUtils;
import com.jiuling.operate.R;
import com.jiuling.operate.R2;
import com.jiuling.operate.adapter.RechargeAdapter;
import com.jiuling.operate.contract.IOperateRechargeActivityView;
import com.jiuling.operate.di.component.DaggerOperateRechargeComponent;
import com.jiuling.operate.di.module.OperateReChargeModule;
import com.jiuling.operate.entity.PayResult;
import com.jiuling.operate.entity.RequestRechargeBean;
import com.jiuling.operate.presenter.OperateRechargeActivityPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OperateRechargeActivity extends BaseOperateActivity<OperateRechargeActivityPresenter> implements IOperateRechargeActivityView {


    @BindView(R2.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R2.id.tv_loginout)
    TextView tvLoginout;
    @BindView(R2.id.et_recharge_money)
    EditText etRechargeMoney;
    @BindView(R2.id.gv_amount)
    GridView gv_amount;
    @BindView(R2.id.rb_alipay)
    RadioButton rb_alipay;
    @BindView(R2.id.rb_wechat)
    RadioButton rb_wechat;
    @BindView(R2.id.bt_recharge)
    Button btRecharge;
    @BindView(R2.id.tv_real_money)
    TextView tv_real_money;

    private RechargeAdapter rechargeAdapter;
    private List<String> rechareList = new ArrayList<>();


    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;


    // 0 支付宝  1 微信
    private int paymentType = 0;



    @Override
    protected void setupAcitivtyComponent(ApplicationComponent applicationComponent) {
//        DaggerOperateControlComponent.builder().applicationComponent(applicationComponent).operateControlModule(new OperateControlModule(this)).build().injectOperateControlActivity(this);
        DaggerOperateRechargeComponent.builder().applicationComponent(applicationComponent).operateReChargeModule(new OperateReChargeModule(this)).build().injectReChargeControlActivity(this);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initView() {

            rechareList.clear();
            rechareList.add("￥ "+10);
            rechareList.add("￥ "+20);
            rechareList.add("￥ "+30);
            rechareList.add("￥ "+50);
            rechareList.add("￥ "+100);
            rechareList.add("￥ "+200);
            rechareList.add("￥ "+300);
            rechareList.add("￥ "+500);
            rechareList.add("￥ "+1000);

        rechargeAdapter = new RechargeAdapter(this,rechareList);
        gv_amount.setAdapter(rechargeAdapter);

        gv_amount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                etRechargeMoney.setText(rechareList.get(position).substring(2,rechareList.get(position).length()));


            }
        });
        tvPhoneNumber.setText(getPhone());

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

        etRechargeMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_real_money.setText("￥ "+s.toString());
            }
        });


        btRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (paymentType == 0){
                    String rechargeAmount = tv_real_money.getText().toString().substring(2,tv_real_money.getText().toString().length());
                    ToastUtils.showToast("充值金额"+rechargeAmount);

                    RequestRechargeBean requestRechargeBean = new RequestRechargeBean(rechargeAmount,1);
                    SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
                    String authorization = sharedPreferencesUtils.getAcceeToken(OperateRechargeActivity.this);
                    mPresenter.recharge("Bearer " + authorization, requestRechargeBean, true);
                }else {
                    ToastUtils.showToast("暂不支持微信支付");
                }





            }
        });

        tvLoginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginOutPop();
            }
        });


        rb_alipay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    rb_wechat.setChecked(false);
                    paymentType = 0;
                }

            }
        });


        rb_wechat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    rb_alipay.setChecked(false);
                    paymentType = 1;
                }
            }
        });

    }

    @Override
    public void showData(ArrayList data) {

    }


    @Override
    public void getRechargeInfo(String alipayInfo) {

        final String orderInfo = alipayInfo;
        // 订单信息

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(OperateRechargeActivity.this);
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
//                        ToastUtils.showToast("支付成功:" + payResult);
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
