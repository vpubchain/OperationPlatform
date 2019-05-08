package com.jiuling.operate.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jiuling.commonbusiness.base.ProgressBaseActivity;
import com.jiuling.commonbusiness.base.baseRecycler.creator.DefaultLoadCreator;
import com.jiuling.commonbusiness.base.baseRecycler.creator.DefaultRefreshCreator;
import com.jiuling.commonbusiness.base.baseRecycler.recycler.LoadRefreshRecyclerView;
import com.jiuling.commonbusiness.base.baseRecycler.recycler.WrapRecyclerView;
import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.commonbusiness.util.SharedPreferencesUtils;
import com.jiuling.operate.R;
import com.jiuling.operate.R2;
import com.jiuling.operate.adapter.NodePriceAdapter;
import com.jiuling.operate.contract.IOperateContactUsActivityView;
import com.jiuling.operate.contract.IOperatePriceActivityView;
import com.jiuling.operate.di.component.DaggerOperateContactUslComponent;
import com.jiuling.operate.di.component.DaggerOperatePriceComponent;
import com.jiuling.operate.di.module.OperateContactUsModule;
import com.jiuling.operate.di.module.OperatePriceModule;
import com.jiuling.operate.entity.PriceBean;
import com.jiuling.operate.entity.RenewNodeBean;
import com.jiuling.operate.presenter.OperateContactUsActivityPresenter;
import com.jiuling.operate.presenter.OperatePriceActivityPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

public class OperatePriceActivity extends BaseOperateActivity<OperatePriceActivityPresenter> implements IOperatePriceActivityView {

    private TextView tv_preview;

    private WrapRecyclerView rcv_node_price;
    private DefaultLoadCreator defaultLoadCreator;
    private DefaultRefreshCreator defaultRefreshCreator;
    private NodePriceAdapter nodePriceAdapter;

    private boolean isRenew = false;
    private RenewNodeBean renewNodeBean;



    @Override
    protected void setupAcitivtyComponent(ApplicationComponent applicationComponent) {
//        DaggerOperateControlComponent.builder().applicationComponent(applicationComponent).operateControlModule(new OperateControlModule(this)).build().injectOperateControlActivity(this);
        DaggerOperatePriceComponent.builder().applicationComponent(applicationComponent).operatePriceModule(new OperatePriceModule(this)).build().injectOperatePriceActivity(this);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_price;
    }

    @Override
    protected void initView() {

        //判断是否为节点付费
        renewNodeBean = (RenewNodeBean) getIntent().getSerializableExtra("renewNodeBean");
        if (renewNodeBean!= null){
            isRenew = true;
        }

        tv_preview = findViewById(R.id.tv_preview);
        rcv_node_price = findViewById(R.id.rcv_node_price);
        defaultRefreshCreator=new DefaultRefreshCreator();
        defaultLoadCreator=new DefaultLoadCreator();
        rcv_node_price.setLayoutManager(new LinearLayoutManager(OperatePriceActivity.this));



    }

    @Override
    protected void initData() {

        if (!isRenew){
            SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
            String authorization = sharedPreferencesUtils.getAcceeToken(this);
            mPresenter.getNodePrice("Bearer "+authorization,true);
        }else{

            //设置为续费界面
            tv_preview.setText("续费");

            List<PriceBean.ProductTypeListBean> productTypeListBeanList = new ArrayList<>();

            for (int i =0;i<renewNodeBean.getTypList().size();i++){
                RenewNodeBean.TypListBean.DtlBean dtlBean = renewNodeBean.getTypList().get(i).getDtl();
                productTypeListBeanList.add(new PriceBean.ProductTypeListBean(dtlBean.getId(),dtlBean.getProductId(),dtlBean.getName(),dtlBean.getDetail(),dtlBean.getType(),dtlBean.getBasePrice()
                        ,dtlBean.getPromotionPrice(),dtlBean.getStartPromotionTime(),dtlBean.getEndPromotionTime(),dtlBean.getCreateTime(),dtlBean.getUpdateTime()
                        ,dtlBean.getCreateBy(),dtlBean.getUpdateBy(),dtlBean.getDelFlag()));

            }
            nodePriceAdapter = new NodePriceAdapter(this,productTypeListBeanList,R.layout.item_node_info,isRenew);
            rcv_node_price.setAdapter(nodePriceAdapter);
//            rcv_node_price.addRefreshViewCreator(defaultRefreshCreator);
//            rcv_node_price.addLoadViewCreator(defaultLoadCreator);

        }

    }

    @Override
    protected void initEvent() {


    }

    @Override
    public void showData(ArrayList data) {

    }

    @Override
    public void getNodePrice(List<PriceBean> priceBeans) {

        nodePriceAdapter = new NodePriceAdapter(this,priceBeans.get(0).getProductTypeList(),R.layout.item_node_info,isRenew);
        rcv_node_price.setAdapter(nodePriceAdapter);
//        rcv_node_price.addRefreshViewCreator(defaultRefreshCreator);
//        rcv_node_price.addLoadViewCreator(defaultLoadCreator);

    }


}