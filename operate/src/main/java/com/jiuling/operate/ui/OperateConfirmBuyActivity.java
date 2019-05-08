package com.jiuling.operate.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiuling.commonbusiness.base.ProgressBaseActivity;
import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.commonbusiness.util.SharedPreferencesUtils;
import com.jiuling.commonbusiness.util.StartActivityUtils;
import com.jiuling.commonbusiness.util.ToastUtils;
import com.jiuling.operate.R;
import com.jiuling.operate.R2;
import com.jiuling.operate.contract.IOperateConfirmBuyActivityView;
import com.jiuling.operate.di.component.DaggerOperateConfirmBuyComponent;
import com.jiuling.operate.di.module.OperateConfirmBuyModule;
import com.jiuling.operate.entity.NodeDetailsBean;
import com.jiuling.operate.entity.PriceBean;
import com.jiuling.operate.presenter.OperateConfirmBuyActivityPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OperateConfirmBuyActivity extends BaseOperateActivity<OperateConfirmBuyActivityPresenter> implements IOperateConfirmBuyActivityView {


    @BindView(R2.id.iv_more)
    ImageView ivMore;
    @BindView(R2.id.tv_node_name)
    TextView tvNodeName;
    @BindView(R2.id.tv_node_info1)
    TextView tvNodeInfo1;
    @BindView(R2.id.tv_node_price)
    TextView tvNodePrice;
    @BindView(R2.id.tv_node_original_price)
    TextView tvNodeOriginalPrice;
    @BindView(R2.id.tv_node_info2)
    TextView tvNodeInfo2;
    @BindView(R2.id.tv_node_time)
    TextView tvNodeTime;
    @BindView(R2.id.ll_info)
    TextView llInfo;
    @BindView(R2.id.iv_reduce)
    Button ivReduce;
    @BindView(R2.id.tv_buy_count)
    TextView tvBuyCount;
    @BindView(R2.id.iv_increase)
    Button ivIncrease;
    @BindView(R2.id.bt_buy)
    Button btBuy;
    @BindView(R2.id.tv_order_money)
    TextView tv_order_money;

    private PriceBean.ProductTypeListBean productTypeListBean;

    @Override
    protected void setupAcitivtyComponent(ApplicationComponent applicationComponent) {
//        DaggerOperateContactUslComponent.builder().applicationComponent(applicationComponent).operateContactUsModule(new OperateContactUsModule(this)).build().injectOperateContactUsActivity(this);
        DaggerOperateConfirmBuyComponent.builder().applicationComponent(applicationComponent).operateConfirmBuyModule(new OperateConfirmBuyModule(this)).build().injectOperateConfirmBuyActivity(this);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_confirm_buy;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getBundleExtra("buy_info");
        productTypeListBean = (PriceBean.ProductTypeListBean) bundle.get("buy_info");

        tvNodeName.setText(productTypeListBean.getName());

        long currentTime = System.currentTimeMillis();
        if (currentTime>=productTypeListBean.getStartPromotionTime()&&currentTime<=productTypeListBean.getEndPromotionTime()){
            //促销
            tvNodePrice.setText("￥ "+productTypeListBean.getPromotionPrice());
            tvNodeOriginalPrice.setText("原价"+"￥ "+productTypeListBean.getBasePrice());
            tv_order_money.setText(productTypeListBean.getPromotionPrice()+"");
        }else {
            tvNodePrice.setText("￥ "+productTypeListBean.getBasePrice());
            tvNodeOriginalPrice.setVisibility(View.GONE);
            tv_order_money.setText(productTypeListBean.getBasePrice()+"");
        }







        if (productTypeListBean.getType() == 1){
            tvNodeTime.setText("包月");
        }else {
            tvNodeTime.setText("包年");
        }



        tvNodeOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);




    }

    @Override
    protected void initData() {

        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
        String authorization = sharedPreferencesUtils.getAcceeToken(this);
        mPresenter.getNodeDetailById("Bearer "+authorization,"bsProduct/detailbyId/"+productTypeListBean.getId(),true);


    }

    @Override
    protected void initEvent() {


    }

    @Override
    public void showData(ArrayList data) {

    }



//
    @OnClick({R2.id.iv_reduce,R2.id.iv_increase,R2.id.bt_buy})
    public void viewsOnClick(View view) {

        int i = view.getId();
        if (i == R.id.iv_reduce) {
            mPresenter.setBuyCount(tvBuyCount,false);
        } else if (i == R.id.iv_increase) {
            mPresenter.setBuyCount(tvBuyCount,true);
        } else if (i == R.id.bt_buy){

            Intent intent = new Intent();
            intent.setClass(OperateConfirmBuyActivity.this,OperatePaymentActivity.class);
            intent.putExtra("product_type_id",productTypeListBean.getId());
            intent.putExtra("quantity",tvBuyCount.getText().toString());
            intent.putExtra("price",tv_order_money.getText().toString());
            StartActivityUtils.goToAct(OperateConfirmBuyActivity.this,intent);

        }
    }

    @Override
    public void getBuyCount(int i) {

        tv_order_money.setText(productTypeListBean.getPromotionPrice()* i+"");

    }

    @Override
    public void getNodeDetailById(NodeDetailsBean nodeDetailsBean) {

        tvNodeName.setText(nodeDetailsBean.getProduct().getName());
        tvNodeInfo1.setText(nodeDetailsBean.getProduct().getDetail());
        tvNodeInfo2.setText(nodeDetailsBean.getType().getName());
        llInfo.setText(nodeDetailsBean.getType().getDetail());

    }
}