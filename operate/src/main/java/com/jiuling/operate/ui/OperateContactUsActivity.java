package com.jiuling.operate.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.commonbusiness.util.SharedPreferencesUtils;
import com.jiuling.commonbusiness.util.ToastUtils;
import com.jiuling.operate.R;
import com.jiuling.operate.R2;
import com.jiuling.operate.contract.IOperateContactUsActivityView;
import com.jiuling.operate.di.component.DaggerOperateContactUslComponent;
import com.jiuling.operate.di.module.OperateContactUsModule;
import com.jiuling.operate.entity.ContactUsBean;
import com.jiuling.operate.presenter.OperateContactUsActivityPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OperateContactUsActivity extends BaseOperateActivity<OperateContactUsActivityPresenter> implements IOperateContactUsActivityView {


    @BindView(R2.id.et_name)
    EditText etName;
    @BindView(R2.id.et_phone)
    EditText etPhone;
    @BindView(R2.id.et_email)
    EditText etEmail;
    @BindView(R2.id.et_content)
    EditText etContent;
    @BindView(R2.id.tv_next)
    TextView tvNext;

    @Override
    protected void setupAcitivtyComponent(ApplicationComponent applicationComponent) {
        DaggerOperateContactUslComponent.builder().applicationComponent(applicationComponent).operateContactUsModule(new OperateContactUsModule(this)).build().injectOperateContactUsActivity(this);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_contact_us;
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




    @OnClick({R2.id.tv_next})
    public void viewsOnClick(View view) {

        int i = view.getId();
        if (i == R.id.tv_next) {
            String name = etName.getText().toString();
            String phone = etPhone.getText().toString();
            String email = etEmail.getText().toString();
            String content = etContent.getText().toString();
            if (TextUtils.isEmpty(name)||TextUtils.isEmpty(phone)||TextUtils.isEmpty(email)||TextUtils.isEmpty(content)){
                ToastUtils.showToast("请填写完整信息");
            }else{
                SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
                String authorization = sharedPreferencesUtils.getAcceeToken(this);
                ContactUsBean contactUsBean = new ContactUsBean(name,phone,email,content);
                mPresenter.contactUs("Bearer "+authorization,contactUsBean,true);
            }
        }
    }

    @Override
    public void contactUs(Boolean result) {
        ToastUtils.showToast("信息提交完成");
    }
}