package com.jiuling.operate.ui;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jiuling.commonbusiness.base.BasePresenter;
import com.jiuling.commonbusiness.base.ProgressBaseActivity;
import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.commonbusiness.util.DisplayUtil;
import com.jiuling.commonbusiness.util.SharedPreferencesUtils;
import com.jiuling.commonbusiness.util.ShowPopUtil;
import com.jiuling.commonbusiness.util.StartActivityUtils;
import com.jiuling.commonbusiness.util.ToastUtils;
import com.jiuling.operate.R;
import com.jiuling.operate.presenter.OperateConfirmBuyActivityPresenter;

import java.util.ArrayList;

public abstract class BaseOperateActivity<T extends BasePresenter> extends ProgressBaseActivity<T> implements View.OnClickListener {


    // 0 未登陆   1 已经登陆   2 处于控制台
    public static int type = 0;

    private ImageView iv_more;
    private PopupWindow window;

    private TextView tv_loginout;
    private ShowPopUtil showPopUtil;

    // 0   1

    private TextView tvControl;
    private TextView tvHomepage;
    private TextView tvPrice;
    private TextView tvConstraUs;
    private TextView tvLogin;
    private TextView tvRegister;
    private TextView tv_login;



    // 2
    private TextView tv_back;
    private TextView tv_account;
    private TextView my_node;
    private TextView tv_my_order;
    private TextView tv_base_info;
    private TextView tv_capital_flow;
    private TextView tv_recharge;
    private SharedPreferencesUtils sharedPreferencesUtilsToGetType = new SharedPreferencesUtils();


    @Override
    protected void initData() {


        if (tv_loginout !=null){
            tv_loginout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopUtil = new ShowPopUtil(R.layout.pw_confirm, BaseOperateActivity.this,0.7,0.2) {

                        @Override
                        protected void initView(View view) {

                            TextView tv_confirm = view.findViewById(R.id.tv_confirm);
                            tv_confirm.setText("您确定要退出登录吗?");

                            Button bt_cancel = view.findViewById(R.id.bt_cancel);
                            Button bt_confirm = view.findViewById(R.id.bt_confirm);

                            bt_cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showPopUtil.dismiss();
                                }
                            });

                            bt_confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showPopUtil.dismiss();

                                    //删除登录信息
                                    SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(BaseOperateActivity.this,SharedPreferencesUtils.LOGIN);
                                    sharedPreferencesUtils.cleanInfo();
                                    StartActivityUtils.goToAct(BaseOperateActivity.this, OperateMainActivity.class,true);

                                }
                            });

                        }
                    };

                    showPopUtil.showPop();
                }
            });
        }


        setType();

    }

    //将所有判断当前状态方法放到一个函数中
    protected void setType(){

        String access_token = sharedPreferencesUtilsToGetType.getAcceeToken(this);
        long expires_in = Long.parseLong(sharedPreferencesUtilsToGetType.getExpiration(this));
        long currentTime = System.currentTimeMillis();

        if (access_token != null && !access_token.equals("") && expires_in > currentTime) {
            type = 1;
        } else {
            type = 0;
        }
    }

    @Override
    protected void showWindow(View view) {

        if (type == 0||type == 1){

            showMain(view);

        }else {

            showOperate(view);

        }


    }

    private void showMain(View view) {

        // 用于PopupWindow的View
        View contentView = LayoutInflater.from(this).inflate(R.layout.pw_more, null, false);
        tvControl = contentView.findViewById(R.id.tv_control);
        tvHomepage = contentView.findViewById(R.id.tv_homepage);
        tvPrice = contentView.findViewById(R.id.tv_price);
        tvConstraUs = contentView.findViewById(R.id.tv_constra_us);
        tvLogin = contentView.findViewById(R.id.tv_login);
        tvRegister = contentView.findViewById(R.id.tv_register);
        if (type == 1){
            tvLogin.setText("退出");
        }
//
        tvControl.setOnClickListener(this);
        tvHomepage.setOnClickListener(this);
        tvPrice.setOnClickListener(this);
        tvConstraUs.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);



//        tv_login = contentView.findViewById(R.id.tv_login);
//        tv_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(BaseOperateActivity.this, OperateLoginActivity.class);
//                startActivity(intent);
//                overridePendingTransition(R.anim.open_in, R.anim.open_out);
//            }
//        });

        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        window = new PopupWindow(contentView, DisplayUtil.dip2px(this,150), LinearLayout.LayoutParams.WRAP_CONTENT, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        window.setAnimationStyle(R.style.AnimationPreview);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
//        window.showAsDropDown(view, 0, 0);
        // 或者也可以调用此方法显示PopupWindow，其中：
        // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
        // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
        window.showAtLocation(view, Gravity.RIGHT | Gravity.TOP, 0, 0);


    }

    private void showOperate(View view) {

        // 用于PopupWindow的View
        View contentView = LayoutInflater.from(this).inflate(R.layout.pw_more_operate, null, false);
        tv_back = contentView.findViewById(R.id.tv_back);
        tv_account = contentView.findViewById(R.id.tv_account);
        my_node = contentView.findViewById(R.id.my_node);
        tv_my_order = contentView.findViewById(R.id.tv_my_order);
        tv_base_info = contentView.findViewById(R.id.tv_base_info);
        tv_capital_flow = contentView.findViewById(R.id.tv_capital_flow);
        tv_recharge = contentView.findViewById(R.id.tv_recharge);
//
        tv_back.setOnClickListener(this);
        tv_account.setOnClickListener(this);
        my_node.setOnClickListener(this);
        tv_my_order.setOnClickListener(this);
        tv_base_info.setOnClickListener(this);
        tv_capital_flow.setOnClickListener(this);
        tv_recharge.setOnClickListener(this);



        // 创建PopupWindow对象，其中：
        // 第一个参数是用于PopupWindow中的View，第二个参数是PopupWindow的宽度，
        // 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
        window = new PopupWindow(contentView, DisplayUtil.dip2px(this,150), LinearLayout.LayoutParams.WRAP_CONTENT, true);
        // 设置PopupWindow的背景
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        // 设置PopupWindow是否能响应外部点击事件
        window.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        window.setTouchable(true);
        window.setAnimationStyle(R.style.AnimationPreview);
        // 显示PopupWindow，其中：
        // 第一个参数是PopupWindow的锚点，第二和第三个参数分别是PopupWindow相对锚点的x、y偏移
//        window.showAsDropDown(view, 0, 0);
        // 或者也可以调用此方法显示PopupWindow，其中：
        // 第一个参数是PopupWindow的父View，第二个参数是PopupWindow相对父View的位置，
        // 第三和第四个参数分别是PopupWindow相对父View的x、y偏移
        window.showAtLocation(view, Gravity.RIGHT | Gravity.TOP, 0, 0);
    }


    @Override
    public void onClick(View v) {

        Log.i("body","zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
        int i = v.getId();
        if (i == R.id.tv_control) {

            StartActivityUtils.goToAct(BaseOperateActivity.this,OperateControlActivity.class);

        } else if (i == R.id.tv_homepage) {

            StartActivityUtils.goToAct(BaseOperateActivity.this,OperateMainActivity.class);

        } else if (i == R.id.tv_price) {

            StartActivityUtils.goToAct(BaseOperateActivity.this,OperatePriceActivity.class);

        } else if (i == R.id.tv_constra_us){

            StartActivityUtils.goToAct(BaseOperateActivity.this, OperateContactUsActivity.class);

        } else if (i == R.id.tv_login ) {
            if (type == 0){
                StartActivityUtils.goToAct(BaseOperateActivity.this, OperateLoginActivity.class);
            } else {
                //退出
                showLoginOutPop();
            }


        } else if (i == R.id.tv_register) {
            StartActivityUtils.goToAct(BaseOperateActivity.this, OperateRegisterActivity.class);
        }
        ////////////////////////////
        // private TextView tv_back;
        //    private TextView tv_account;
        //    private TextView my_node;
        //    private TextView tv_my_order;
        //    private TextView tv_base_info;
        //    private TextView tv_capital_flow;
        else if (i == R.id.tv_back){
            StartActivityUtils.goToAct(BaseOperateActivity.this,OperateMainActivity.class);
            finish();
        }else if (i == R.id.tv_account){
            StartActivityUtils.goToAct(BaseOperateActivity.this,OperateControlActivity.class);
        }else if (i == R.id.my_node){
            StartActivityUtils.goToAct(BaseOperateActivity.this,OperateMyNodeActivity.class);
        }else if (i == R.id.tv_my_order){
            StartActivityUtils.goToAct(BaseOperateActivity.this,OperateMyOrderActivity.class);
        }else if (i == R.id.tv_base_info){
            StartActivityUtils.goToAct(BaseOperateActivity.this,OperateInformationActivity.class);
        }else if (i == R.id.tv_capital_flow){
            StartActivityUtils.goToAct(BaseOperateActivity.this,OperateCapitalFlowActivity.class);
        }else if (i == R.id.tv_recharge){
            StartActivityUtils.goToAct(BaseOperateActivity.this,OperateRechargeActivity.class);
        }

        window.dismiss();


    }


    protected void showLoginOutPop(){

        showPopUtil = new ShowPopUtil(R.layout.pw_confirm, BaseOperateActivity.this,0.7,0.2) {

            @Override
            protected void initView(View view) {

                TextView tv_confirm = view.findViewById(R.id.tv_confirm);
                tv_confirm.setText("您确定要退出登录吗?");

                Button bt_cancel = view.findViewById(R.id.bt_cancel);
                Button bt_confirm = view.findViewById(R.id.bt_confirm);

                bt_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopUtil.dismiss();
                    }
                });

                bt_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopUtil.dismiss();

                        //删除登录信息
                        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(BaseOperateActivity.this,SharedPreferencesUtils.LOGIN);
                        sharedPreferencesUtils.cleanInfo();
                        StartActivityUtils.goToAct(BaseOperateActivity.this, OperateMainActivity.class,true);
                        type = 0;

                    }
                });

            }
        };

        showPopUtil.showPop();

    }

    //获取电话号码
    protected String getPhone(){

        try{
            SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
            String phone = sharedPreferencesUtils.getPhone(this);
            phone = phone.substring(0,3)+"****"+phone.substring(7,11);
            return phone;
        }catch (Exception e){
            return "";
        }


    }
}
