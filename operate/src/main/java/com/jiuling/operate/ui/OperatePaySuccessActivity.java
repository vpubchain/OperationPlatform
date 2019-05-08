package com.jiuling.operate.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.commonbusiness.util.StartActivityUtils;
import com.jiuling.operate.R;
import com.jiuling.operate.R2;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OperatePaySuccessActivity extends BaseOperateActivity {


    @BindView(R2.id.tv_amount_all)
    TextView tvAmountAll;
    @BindView(R2.id.tv_amount)
    TextView tvAmount;
    @BindView(R2.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R2.id.bt_my_control)
    Button btMyControl;

    private String orderNumber;
    private double amount;

    @Override
    protected void setupAcitivtyComponent(ApplicationComponent applicationComponent) {

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void initView() {

        amount = getIntent().getDoubleExtra("amount",0);
        orderNumber = getIntent().getStringExtra("orderNumber");

        tvAmountAll.setText("￥ "+amount);
        tvAmount.setText("金额￥ "+amount+"元");
        tvOrderNumber.setText(orderNumber);
        

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        btMyControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivityUtils.goToAct(OperatePaySuccessActivity.this,OperateControlActivity.class,true);
            }
        });
    }

    @Override
    public void showData(ArrayList data) {

    }


}
