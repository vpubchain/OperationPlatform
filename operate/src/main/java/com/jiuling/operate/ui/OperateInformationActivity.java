package com.jiuling.operate.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.commonbusiness.util.SharedPreferencesUtils;
import com.jiuling.commonbusiness.util.ToastUtils;
import com.jiuling.operate.R;
import com.jiuling.operate.R2;
import com.jiuling.operate.contract.IOperateInformationActivityView;
import com.jiuling.operate.di.component.DaggerOperateInformationComponent;
import com.jiuling.operate.di.module.OperateInformationModule;
import com.jiuling.operate.entity.PersonalInfoBean;
import com.jiuling.operate.entity.RequestChangePhoneBean;
import com.jiuling.operate.entity.SetUserInfoBean;
import com.jiuling.operate.presenter.OperateInformationActivityPresenter;
import com.jiuling.operate.view.TimingButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OperateInformationActivity extends BaseOperateActivity<OperateInformationActivityPresenter> implements IOperateInformationActivityView {


    @BindView(R2.id.tv_account)
    TextView tvAccount;
    @BindView(R2.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R2.id.tv_loginout)
    TextView tvLoginout;
    @BindView(R2.id.tv_personal_info)
    TextView tvPersonalInfo;
    @BindView(R2.id.tv_change_phone)
    TextView tvChangePhone;
    @BindView(R2.id.et_email)
    EditText etEmail;
    @BindView(R2.id.et_username)
    EditText etUsername;
    @BindView(R2.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R2.id.tv_next)
    TextView tvNext;
    @BindView(R2.id.tv_email)
    TextView tvEamil;
    @BindView(R2.id.tv_username)
    TextView tvUsername;
    @BindView(R2.id.tv_phone)
    TextView tvPhone;
    @BindView(R2.id.tb_code)
    TimingButton tb_code;

    //当前更改的信息类型   个人信息 1 or 手机号 2
    private static int updateType = 1;



    @Override
    protected void setupAcitivtyComponent(ApplicationComponent applicationComponent) {
        DaggerOperateInformationComponent.builder().applicationComponent(applicationComponent).operateInformationModule(new OperateInformationModule(this)).build().injectOperateInformationActivity(this);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_personal_information;
    }

    @Override
    protected void initView() {
        tvPhoneNumber.setText(getPhone());
    }

    @Override
    protected void initData() {

        getInfo();

    }

    private void getInfo() {

        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
        String authorization = sharedPreferencesUtils.getAcceeToken(this);
        mPresenter.getPersonalInfo("Bearer " + authorization, true);


    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void showData(ArrayList data) {

    }


    @Override
    public void getPersonalInfo(PersonalInfoBean personalInfoBean) {

        etEmail.setText(personalInfoBean.getEmail());
        etPhoneNumber.setText(personalInfoBean.getPhone());
        etUsername.setText(personalInfoBean.getName());

    }

    @Override
    public void setPersonalInfo(Boolean result) {
        ToastUtils.showToast("更新成功");
    }

    @Override
    public void getChangePhoneCode(Boolean result) {
        tb_code.start();
        ToastUtils.showToast("验证码已经发送");
    }

    @Override
    public void changePhone(Boolean result) {
        ToastUtils.showToast("手机修改成功，请重新登录");
    }


    @OnClick({R2.id.tv_next,R2.id.tv_personal_info,R2.id.tv_change_phone,R2.id.tv_loginout,R2.id.tb_code})
    public void onClick(View view){

        int i = view.getId();
        if (i == R.id.tv_next) {


            String email = etEmail.getText().toString();
            String name = etUsername.getText().toString();
            String phone = etPhoneNumber.getText().toString();

            SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
            String authorization = sharedPreferencesUtils.getAcceeToken(this);

            if (updateType == 1){
                SetUserInfoBean setUserInfoBean = new SetUserInfoBean(email,name,phone);
                mPresenter.setPersonalInfo("Bearer " + authorization,setUserInfoBean, true);
            }else {


                RequestChangePhoneBean requestChangePhoneBean = new RequestChangePhoneBean(email,name);
                mPresenter.changePhone("Bearer " + authorization,phone,requestChangePhoneBean,true);


            }

        }else if(i == R.id.tv_personal_info){

            updateType = 1;
            tvEamil.setText("邮箱");
            tvUsername.setText("用户名");
            tvPhone.setText("手机号");
            tb_code.setVisibility(View.GONE);
            //
            getInfo();
            etPhoneNumber.setFocusable(false);


        }else if (i == R.id.tv_change_phone){

            updateType = 2;
            tvEamil.setText("旧手机");
            tvUsername.setText("新手机");

            etEmail.setText("");
            etUsername.setText("");
            etPhoneNumber.setText("");

            tvPhone.setText("验证码");
            tb_code.setVisibility(View.VISIBLE);

        }else if (i == R.id.tv_loginout){
            showLoginOutPop();
        }else if (i == R.id.tb_code){
            SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
            String authorization = sharedPreferencesUtils.getAcceeToken(this);
            mPresenter.getChangePhoneCode("Bearer " + authorization,etEmail.getText().toString(),true);
        }

    }

}
