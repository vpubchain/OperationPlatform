package com.jiuling.operate.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiuling.commonbusiness.base.ProgressBaseActivity;
import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.commonbusiness.util.MobileUtil;
import com.jiuling.commonbusiness.util.StartActivityUtils;
import com.jiuling.commonbusiness.util.ToastUtils;
import com.jiuling.operate.R;
import com.jiuling.operate.R2;
import com.jiuling.operate.contract.IOperateRegisterActivityView;
import com.jiuling.operate.di.component.DaggerOperateRegisterComponent;
import com.jiuling.operate.di.module.OperateRegisterModule;
import com.jiuling.operate.presenter.OperateRegisterActivityPresenter;
import com.jiuling.operate.view.TimingButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OperateRegisterActivity extends BaseOperateActivity<OperateRegisterActivityPresenter> implements IOperateRegisterActivityView {


    @BindView(R2.id.iv_more)
    ImageView ivMore;
    @BindView(R2.id.et_username)
    EditText etUsername;
    @BindView(R2.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R2.id.et_email_address)
    EditText etEmailAddress;
    @BindView(R2.id.et_code)
    EditText etCode;
    @BindView(R2.id.tb_code)
    TimingButton tbCode;
    @BindView(R2.id.tv_next)
    TextView tvNext;
    @BindView(R2.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R2.id.tv_register)
    TextView tvRegister;

    @Override
    protected void setupAcitivtyComponent(ApplicationComponent applicationComponent) {

        DaggerOperateRegisterComponent.builder().applicationComponent(applicationComponent).operateRegisterModule(new OperateRegisterModule(this)).build().injectOperateRegisterActivity(this);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_operate_register;
    }

    @Override
    protected void initView() {

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


    @OnClick({R2.id.tb_code,R2.id.tv_next,R2.id.tv_forget_password,R2.id.tv_register})
    public void viewsOnClick(View view) {

        int i = view.getId();
        if (i == R.id.tb_code) {

            if (etPhoneNumber.getText().toString().equals("")){
                ToastUtils.showToast("手机号码不能为空");
            }else if (!MobileUtil.isMobileNO(etPhoneNumber.getText().toString())){
                ToastUtils.showToast("手机号错误");
            }else {
                mPresenter.getRegisterCode(etPhoneNumber.getText().toString(), true);
            }


        } else if (i == R.id.tv_next) {

            mPresenter.checkInfo(etCode.getText().toString(),etPhoneNumber.getText().toString());

        } else if (i == R.id.tv_forget_password) {

        } else if (i == R.id.tv_register) {



        }


    }


    @Override
    public void getRegisterCode() {
        tbCode.start();
        ToastUtils.showToast("验证码已发送");
    }

    @Override
    public void registerSuccess(boolean result) {
        if (result){
            ToastUtils.showToast("注册成功");
            StartActivityUtils.goToAct(this,OperateLoginActivity.class);
        }else {
            ToastUtils.showToast("该手机号已被注册");
        }


    }

    @Override
    public void checkInfoResult(boolean success, String msg) {
        if (success){
            mPresenter.register(etCode.getText().toString(),etPhoneNumber.getText().toString(),true);
        }else{
            ToastUtils.showToast(msg);
        }

    }

}
