package com.vpubchain.wallet.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuling.operate.ui.OperateLoginActivity;
import com.vpubchain.wallet.Constants;
import com.vpubchain.wallet.R;
import com.vpubchain.wallet.data.BaseResponse;
import com.vpubchain.wallet.http.ApiService;
import com.vpubchain.wallet.http.RetrofitUtils;
import com.vpubchain.wallet.ui.widget.TimingButton;
import com.vpubchain.wallet.util.MobileUtil;
import com.vpubchain.wallet.util.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends Activity{

    private TimingButton timingButton;
    private TextView tv_next;
    private EditText et_username;
    private EditText et_code;

    protected ApiService mApi = RetrofitUtils.getInstance().create(ApiService.class);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        timingButton = findViewById(R.id.tb_code);
        tv_next = findViewById(R.id.tv_next);
        et_username = findViewById(R.id.et_username);
        et_code = findViewById(R.id.et_code);


    }

    @Override
    protected void onResume() {
        super.onResume();

        timingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobileNumber = et_username.getText().toString();

                if (mobileNumber.equals("")){
                    ToastUtils.showToast("手机号码不能为空");
                }else if (!MobileUtil.isMobileNO(mobileNumber)){
                    ToastUtils.showToast("手机号错误");
                }else {


                    mApi.getLoginCode("smsCode/"+ mobileNumber)
                            .unsubscribeOn(Schedulers.io())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<BaseResponse>() {
                                @Override
                                public void onSubscribe(Disposable d) {
                                    Log.i("body","onSubscribe");
                                }

                                @Override
                                public void onNext(BaseResponse baseResponse) {
                                    Log.i("body","onNext"+baseResponse.getData());
                                    if (baseResponse.getMsg().equals("手机号不存在")){
                                        ToastUtils.showToast(baseResponse.getMsg());
                                    }else{
                                        ToastUtils.showToast("验证码已发送");
                                        timingButton.start();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.i("body","onError"+e.toString());
                                }

                                @Override
                                public void onComplete() {
//                                调用获取验证码接口
//                                    Toast.makeText(OperateLoginActivity.this, "验证码已经发送", Toast.LENGTH_SHORT).show();

                                }
                            });
                }




            }
        });

        //点击登录成功后跳转到创建钱包页面
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobileNumber = et_username.getText().toString();
                String code = et_code.getText().toString();
                if (TextUtils.isEmpty(mobileNumber)||TextUtils.isEmpty(code)){
                    ToastUtils.showToast("请填写完整信息");
                }else if (!MobileUtil.isMobileNO(mobileNumber)){
                    ToastUtils.showToast("手机号错误");
                }else{

                    //登录
                mApi.login("mobile","server",code,mobileNumber)
                        .unsubscribeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        Log.i("body","onSubscribe");
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        Log.i("body","onNext");
                        if (baseResponse.getSuccess()){
//                            //调用获取验证码接口
//                            Toast.makeText(OperateLoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//
//                            //获取token
//                            SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(OperateLoginActivity.this);
//                            sharedPreferencesUtils.putInfo(Constants.LOGIN_TOKEN,"");


                        }else {
                            ToastUtils.showToast(baseResponse.getMsg());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.i("body","onError"+e.toString());
//                        Intent intent = new Intent();
//                        intent.setClass(OperateLoginActivity.this,WalletActivity.class);
//                        startActivity(intent);
//                        finish();
                    }

                    @Override
                    public void onComplete() {
//                        Intent intent = new Intent();
//                        intent.setClass(OperateLoginActivity.this,WalletActivity.class);
//                        startActivity(intent);
                    }
                });

                }


//                Intent intent = new Intent();
//                intent.setClass(LoginActivity.this,WalletActivity.class);
//                startActivity(intent);
//                finish();




            }
        });


    }


}
