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
import com.jiuling.operate.contract.IOperateMyNodeActivityView;
import com.jiuling.operate.di.component.DaggerOperateMyNodeComponent;
import com.jiuling.operate.di.module.OperateMyNodeModule;
import com.jiuling.operate.presenter.OperateMyNodeActivityPresenter;
import com.jiuling.operate.util.ShowPopUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class OperateMyNodeActivity extends BaseOperateActivity<OperateMyNodeActivityPresenter> implements IOperateMyNodeActivityView {


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
    @BindView(R2.id.bt_node_exception)
    Button btNodeException;
    @BindView(R2.id.vp_node_info)
    ViewPager vpNodeInfo;
    @BindView(R2.id.et_serach_content)
    EditText et_serach_content;
    @BindView(R2.id.iv_serach)
    ImageView iv_serach;

    private PagerAdapter nodeAdapter;


    @Override
    protected void setupAcitivtyComponent(ApplicationComponent applicationComponent) {
        DaggerOperateMyNodeComponent.builder().applicationComponent(applicationComponent).operateMyNodeModule(new OperateMyNodeModule(this)).build().injectOperateMyNodeActivity(this);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_my_node;
    }

    @Override
    protected void initView() {

//        nodeAdapter = new ViewPagerAdapter(getSupportFragmentManager(),1);
//        vpNodeInfo.setAdapter(nodeAdapter);
//
//        btAllNode.setBackgroundColor(getResources().getColor(R.color.node_button));
//        tvPhoneNumber.setText(getPhone());

    }

    @Override
    protected void onResume() {
        super.onResume();

        nodeAdapter = new ViewPagerAdapter(getSupportFragmentManager(),1);

        vpNodeInfo.setAdapter(nodeAdapter);
        btAllNode.setBackgroundColor(getResources().getColor(R.color.node_button));
        tvPhoneNumber.setText(getPhone());



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

    @OnClick({R2.id.bt_all_node,R2.id.bt_node_waiting,R2.id.bt_node_running,R2.id.bt_node_exception,R2.id.iv_serach,R2.id.tv_loginout})
    public void viewClick(View view){
        int i = view.getId();
        if (i == R.id.bt_all_node) {
            vpNodeInfo.setCurrentItem(0, true);
            btAllNode.setBackgroundColor(getResources().getColor(R.color.node_button));
            btNodeWaiting.setBackgroundColor(Color.WHITE);
            btNodeRunning.setBackgroundColor(Color.WHITE);
            btNodeException.setBackgroundColor(Color.WHITE);

        } else if (i == R.id.bt_node_waiting) {
            vpNodeInfo.setCurrentItem(1, true);
            btNodeWaiting.setBackgroundColor(getResources().getColor(R.color.node_button));
            btAllNode.setBackgroundColor(Color.WHITE);
            btNodeRunning.setBackgroundColor(Color.WHITE);
            btNodeException.setBackgroundColor(Color.WHITE);

        } else if (i == R.id.bt_node_running) {
            vpNodeInfo.setCurrentItem(2, true);
            btNodeRunning.setBackgroundColor(getResources().getColor(R.color.node_button));
            btNodeWaiting.setBackgroundColor(Color.WHITE);
            btAllNode.setBackgroundColor(Color.WHITE);
            btNodeException.setBackgroundColor(Color.WHITE);

        } else if (i == R.id.bt_node_exception) {
            vpNodeInfo.setCurrentItem(3, true);
            btNodeException.setBackgroundColor(getResources().getColor(R.color.node_button));
            btNodeWaiting.setBackgroundColor(Color.WHITE);
            btNodeRunning.setBackgroundColor(Color.WHITE);
            btAllNode.setBackgroundColor(Color.WHITE);

        } else if (i == R.id.iv_serach){
            ToastUtils.showToast("搜索");
        } else if (i == R.id.tv_loginout){

            showLoginOutPop();

        }
    }



}