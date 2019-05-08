package com.jiuling.operate.ui;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jiuling.commonbusiness.base.ProgressBaseActivity;
import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.commonbusiness.eventbus.ShowToastMainActivityEvent;
import com.jiuling.commonbusiness.util.MobileUtil;
import com.jiuling.commonbusiness.util.StartActivityUtils;
import com.jiuling.commonbusiness.util.ToastUtils;
import com.jiuling.operate.R;
import com.jiuling.operate.R2;
import com.jiuling.operate.contract.IOperateLoginActivityView;
import com.jiuling.operate.di.component.DaggerOperateLoginComponent;
import com.jiuling.operate.di.module.OperateLoginModule;
import com.jiuling.operate.presenter.OperateLoginActivityPresenter;
import com.jiuling.operate.view.TimingButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

@Route(path = "/ModuleOperate/OperateLogin")
public class OperateLoginActivity extends BaseOperateActivity<OperateLoginActivityPresenter> implements IOperateLoginActivityView {


    @BindView(R2.id.iv_more)
    ImageView ivMore;
    @BindView(R2.id.et_username)
    EditText etUsername;
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

    @Autowired()
    String outTime;


    @Override
    protected void onResume() {
        super.onResume();

        ARouter.getInstance().inject(this);
        if (outTime!=null&&outTime!=""){
            ToastUtils.showToast("登录凭证过期，请重新登录");
        }
    }

    @Override
    protected void setupAcitivtyComponent(ApplicationComponent applicationComponent) {

//        DaggerOperateMainComponent.builder().applicationComponent(applicationComponent).operateMainModule(new OperateMainModule(this)).build().injectOperateMainActivity(this);

        DaggerOperateLoginComponent.builder().applicationComponent(applicationComponent).operateLoginModule(new OperateLoginModule(this)).build().injectOperateLoginActivity(this);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_operate_login;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initEvent() {
//        tvRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtils.showToast("dfasdf");
//                StartActivityUtils.goToAct(OperateLoginActivity.this, OperateRegisterActivity.class);
//            }
//        });
    }

    @Override
    public void showData(ArrayList data) {

    }


    @OnClick({R2.id.iv_more,R2.id.tb_code,R2.id.tv_next,R2.id.tv_forget_password,R2.id.tv_register})
    public void viewsOnClick(View view){

        int i = view.getId();
        if (i == R.id.iv_more) {//                StartActivityUtils.goToAct(OperateMainActivity.this,OperateLoginActivity.class);

        } else if (i == R.id.tb_code) {//                StartActivityUtils.goToAct(OperateMainActivity.this,OperateLoginActivity.class);

            String mobileNumber = etUsername.getText().toString();

            if (mobileNumber.equals("")){
                ToastUtils.showToast("手机号码不能为空");
            }else if (!MobileUtil.isMobileNO(mobileNumber)){
                ToastUtils.showToast("手机号错误");
            }else {
                mPresenter.getLoginCode(mobileNumber, true);
            }


        } else if (i == R.id.tv_next) {

            mPresenter.login(etUsername.getText().toString(),etCode.getText().toString(),true);

            //                StartActivityUtils.goToAct(OperateMainActivity.this,OperateLoginActivity.class)

        } else if (i == R.id.tv_forget_password) {//                StartActivityUtils.goToAct(OperateMainActivity.this,OperateLoginActivity.class);



        } else if (i == R.id.tv_register) {
            StartActivityUtils.goToAct(OperateLoginActivity.this, OperateRegisterActivity.class);
        }

    }


    //获取到验证码
    @Override
    public void getLoginCode() {
        tbCode.start();
        ToastUtils.showToast("验证码已发送");

    }

    @Override
    public void loginSuccess() {
        ToastUtils.showToast("登录成功");

        type = 2;
        StartActivityUtils.goToAct(OperateLoginActivity.this,OperateControlActivity.class,true);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    //定义处理接收的方法
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(ShowToastMainActivityEvent showToastMainActivityEvent){

        Log.i("evnetBus","登录凭证过期，请重新登录");
        ToastUtils.showToast("登录凭证过期，请重新登录");

    }

}
