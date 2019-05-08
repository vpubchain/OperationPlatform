package com.jiuling.operate.ui;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.commonbusiness.util.SharedPreferencesUtils;
import com.jiuling.commonbusiness.util.StartActivityUtils;
import com.jiuling.commonbusiness.util.ToastUtils;
import com.jiuling.operate.R;
import com.jiuling.operate.R2;
import com.jiuling.operate.adapter.ViewPagerAdapter;
import com.jiuling.operate.contract.IOperateCapitalFlowActivityView;
import com.jiuling.operate.contract.IOperateMyOrderActivityView;
import com.jiuling.operate.di.component.DaggerOperateCapitalFlowComponent;
import com.jiuling.operate.di.module.OperateCapitalFlowModule;
import com.jiuling.operate.presenter.OperateCapitalFlowActivityPresenter;
import com.jiuling.operate.presenter.OperatePaymentActivityPresenter;
import com.jiuling.operate.util.ShowPopUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class OperateCapitalFlowActivity extends BaseOperateActivity<OperateCapitalFlowActivityPresenter> implements IOperateCapitalFlowActivityView {


    @BindView(R2.id.tv_account)
    TextView tvAccount;
    @BindView(R2.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R2.id.tv_loginout)
    TextView tvLoginout;
    @BindView(R2.id.bt_all_node)
    Button btAllNode;
    @BindView(R2.id.bt_node_waiting)
    Button btNodeWaiting;
    @BindView(R2.id.bt_node_running)
    Button btNodeRunning;
    @BindView(R2.id.vp_node_info)
    ViewPager vpNodeInfo;
    @BindView(R2.id.et_serach_content)
    EditText et_serach_content;
    @BindView(R2.id.iv_serach)
    ImageView iv_serach;


    private PagerAdapter nodeAdapter;
    private ShowPopUtil showPopUtil;





    @Override
    protected void setupAcitivtyComponent(ApplicationComponent applicationComponent) {
        DaggerOperateCapitalFlowComponent.builder().applicationComponent(applicationComponent).operateCapitalFlowModule(new OperateCapitalFlowModule(this)).build().injectOperateCapitalFlowActivity(this);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initView() {
        tvPhoneNumber.setText(getPhone());
        nodeAdapter = new ViewPagerAdapter(getSupportFragmentManager(),3);
        vpNodeInfo.setAdapter(nodeAdapter);

        btAllNode.setBackgroundColor(getResources().getColor(R.color.node_button));
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
        tvPhoneNumber.setText(sharedPreferencesUtils.getPhone(this));


        tvAccount.setText("资金流水");
        btAllNode.setText("全部记录");
        btNodeWaiting.setText("充值记录");
        btNodeRunning.setText("消费记录");


    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initEvent() {


    }

    @Override
    public void showData(ArrayList data) {

    }


    @OnClick({R2.id.bt_all_node,R2.id.bt_node_waiting,R2.id.bt_node_running,R2.id.iv_serach,R2.id.tv_loginout})
    public void viewClick(View view){
        int i = view.getId();
        if (i == R.id.bt_all_node) {
            vpNodeInfo.setCurrentItem(0, true);
            btAllNode.setBackgroundColor(getResources().getColor(R.color.node_button));
            btNodeWaiting.setBackgroundColor(Color.WHITE);
            btNodeRunning.setBackgroundColor(Color.WHITE);

        } else if (i == R.id.bt_node_waiting) {
            vpNodeInfo.setCurrentItem(1, true);
            btNodeWaiting.setBackgroundColor(getResources().getColor(R.color.node_button));
            btAllNode.setBackgroundColor(Color.WHITE);
            btNodeRunning.setBackgroundColor(Color.WHITE);

        } else if (i == R.id.bt_node_running) {
            vpNodeInfo.setCurrentItem(2, true);
            btNodeRunning.setBackgroundColor(getResources().getColor(R.color.node_button));
            btNodeWaiting.setBackgroundColor(Color.WHITE);
            btAllNode.setBackgroundColor(Color.WHITE);

        } else if (i == R.id.iv_serach){
            ToastUtils.showToast("搜索");
        } else if (i == R.id.tv_loginout){

            showLoginOutPop();

        }
    }







}