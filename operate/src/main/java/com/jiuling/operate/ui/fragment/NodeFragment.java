package com.jiuling.operate.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jiuling.commonbusiness.base.ProgressFragment;
import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.commonbusiness.entity.BaseResponse;
import com.jiuling.commonbusiness.util.SharedPreferencesUtils;
import com.jiuling.commonbusiness.util.StartActivityUtils;
import com.jiuling.commonbusiness.util.ToastUtils;
import com.jiuling.operate.R;
import com.jiuling.operate.R2;
import com.jiuling.operate.adapter.NodeFragmentAdapter;
import com.jiuling.operate.contract.IOperateNodeFragmentView;
import com.jiuling.operate.di.component.DaggerOperateNodeComponent;
import com.jiuling.operate.di.module.OperateNodeModule;
import com.jiuling.operate.entity.CapitalFlowInfoBean;
import com.jiuling.operate.entity.MasterNodeBean;
import com.jiuling.operate.entity.OrderInfoBean;
import com.jiuling.operate.entity.RenewNodeBean;
import com.jiuling.operate.entity.RequestCancelOrderBean;
import com.jiuling.operate.entity.RequestNodeBean;
import com.jiuling.operate.entity.RequestOrderBean;
import com.jiuling.operate.presenter.OperateNodeFragmentPresenter;
import com.jiuling.operate.ui.BaseOperateActivity;
import com.jiuling.operate.ui.OperateConfirmBuyActivity;
import com.jiuling.operate.ui.OperateMainActivity;
import com.jiuling.operate.ui.OperateMyNodeActivity;
import com.jiuling.operate.ui.OperatePaymentActivity;
import com.jiuling.operate.ui.OperatePriceActivity;
import com.jiuling.operate.util.ShowPopUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;

public class NodeFragment extends ProgressFragment<OperateNodeFragmentPresenter> implements IOperateNodeFragmentView {

    @BindView(R2.id.rcv_content)
    RecyclerView rcvContent;
    @BindView(R2.id.srl_content)
    SwipeRefreshLayout srlContent;

    //当前是否可以加载更多
    private boolean isLoadingMore = true;
    private boolean refresh = true;
    private int currentPage = 1;
    private int totalPage = 0;

    private NodeFragmentAdapter nodeFragmentAdapter;

    private SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
    private  String accessToken;

    //我的节点相关数据
    private List<MasterNodeBean.RecordsBean> masterRecordsBeanList = new ArrayList<>();
    //我的订单相关数据
    private List<OrderInfoBean.RecordsBean> orderRecordsBeanList = new ArrayList<>();
    //我的资金流水相关数据
    private List<CapitalFlowInfoBean.RecordsBean> capitalFlowBeanList = new ArrayList<>();

    //viewpager中的第N个
    private String type;
    //属于那种类型的fragment  我的节点   我的订单   资金流水   分别为  1  2   3
    private int fragmentType = 1;

    //list 点击中的action
    public static final String ACTION = "action";
    //list 点击中的action 的具体行为
    public static final String INSTALL = "install";
    public static final String NODERENEW = "nodeRenew";
    public static final String CANCELORDER = "cancelOrder";
    public static final String PAYORDER = "payOrder";
    //节点ID   通用
    public static final String INSTALLNODEID = "installNodeId";
    //订单号
    public static final String ORDERNUMBER = "orderNumber";
    public static final String QUANTIRY = "quantity";
    public static final String AMOUNT = "amount";

    private ShowPopUtil showPopUtil;
    private String nodeId;
    private String orderNumber;
    private String quantity;
    private String amount;

    private Handler nodeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            nodeId = bundle.getString(INSTALLNODEID);
            orderNumber = bundle.getString(ORDERNUMBER,"");
            quantity = bundle.getString(QUANTIRY,"");
            amount = bundle.getString(AMOUNT,"");
            String action = bundle.getString(ACTION);

            switch (action){

                case INSTALL:

                    showTis("确定现在开始安装节点并托管?",tisHandler,1);

                    break;

                case NODERENEW:

                    //节点续费
//                    mPresenter.installTrusteeshipNode("Bearer " + accessToken,"bsMasternode/installTrusteeshipNode/"+installNodeId,false);
                    mPresenter.nodeRenew("Bearer " + accessToken,"bsProduct/allDtlsByMid/"+nodeId,false);

                    break;

                case CANCELORDER:

                    showTis("您确定要取消订单?",tisHandler,2);

                    break;

                case PAYORDER:
                    //支付订单
                    Intent intent = new Intent(getContext(),OperatePaymentActivity.class);
                    intent.putExtra("payorder",orderNumber);
                    intent.putExtra("quantity",quantity);
                    intent.putExtra("amount",amount);
                    StartActivityUtils.goToAct(getActivity(),intent);
                    break;



                    default:
                        break;

            }



        }
    };


    private Handler tisHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 1:
                    //托管安装
                    mPresenter.installTrusteeshipNode("Bearer " + accessToken,"bsMasternode/installTrusteeshipNode/"+nodeId,false);
                    break;
                case 2:
                    //取消订单
                    RequestCancelOrderBean requestCancelOrderBean = new RequestCancelOrderBean(orderNumber);
                    mPresenter.cancleOrder("Bearer " + accessToken,requestCancelOrderBean,true);
                    break;
            }


        }
    };



    @Override
    public void init() {

        type = getArguments().getString("type");
        fragmentType = getArguments().getInt("fragmentType");

        accessToken = sharedPreferencesUtils.getAcceeToken(getContext());

        rcvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        //动画
        rcvContent.setItemAnimator(new DefaultItemAnimator());
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
//        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.shape_transparent_division));
//        rcvContent.addItemDecoration(dividerItemDecoration);
        rcvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == SCROLL_STATE_DRAGGING && isLoadingMore) {
                    if (rcvContent.computeVerticalScrollOffset() > 0) {// 有滚动距离，说明可以加载更多，解决了 items 不能充满 RecyclerView的问题及滑动方向问题
                        boolean isBottom = false;
                        isBottom = rcvContent.computeVerticalScrollExtent()
                                + rcvContent.computeVerticalScrollOffset()
                                == rcvContent.computeVerticalScrollRange();
                        // 也可以使用 方法2
                        // isBottom = !RecyclerView.canScrollVertically(1) ;
                        if (isBottom) {
                            // 说明滚动到底部,触发加载更多
                            Log.i("body","说明滚动到底部,触发加载更多");
                            isLoadingMore = false;
                            if (currentPage < totalPage){
                                getData(currentPage+1,fragmentType);
                            }else {
                                ToastUtils.showToast("没有更多数据了...");
                            }

                        }
                    }
                }

            }
        });


        srlContent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                currentPage = 1;
                if (fragmentType == 1){
                    masterRecordsBeanList.clear();
                }else if (fragmentType == 2){
                    orderRecordsBeanList.clear();
                }else if (fragmentType == 3){
                    capitalFlowBeanList.clear();
                }

                getData(currentPage,fragmentType);

            }
        });



//        ToastUtils.showToast(type);


    }


    @Override
    public int setLayout() {
        return R.layout.fragment_all;
    }

    @Override
    public void setupAcitivtyComponent(ApplicationComponent appComponent) {
        DaggerOperateNodeComponent.builder().applicationComponent(appComponent).operateNodeModule(new OperateNodeModule(this)).build().injectNodeFragment(this);
    }

    @Override
    public void showData(ArrayList data) {

    }

    @Override
    public void requestTimeOut() {

    }


    @Override
    public void onResume() {
        super.onResume();

        currentPage = 1;
        if (fragmentType == 1){
            masterRecordsBeanList.clear();
        }else if (fragmentType == 2){
            orderRecordsBeanList.clear();
        }else if (fragmentType == 3){
            capitalFlowBeanList.clear();
        }
        getData(currentPage,fragmentType);
    }

    @Override
    public void getMasterNodeInfo(MasterNodeBean masterNodeBean) {


        if (masterRecordsBeanList.size() == 0){
            masterRecordsBeanList = masterNodeBean.getRecords();
            refresh = true;
        }else {
            masterRecordsBeanList.addAll(masterNodeBean.getRecords());
            refresh = false;
        }
        totalPage = masterNodeBean.getPages();
        currentPage = masterNodeBean.getCurrent();

        if (refresh){
            Log.i("qipa","fragmentType"+fragmentType+"setAdapter");
            nodeFragmentAdapter = new NodeFragmentAdapter(nodeHandler,getContext(),masterRecordsBeanList,R.layout.item_node_status,fragmentType);
            rcvContent.setAdapter(nodeFragmentAdapter);
        }else {
            nodeFragmentAdapter.notifyDataSetChanged();
            Log.i("qipa","fragmentType"+fragmentType+"notifyDataSetChanged");
//            nodeFragmentAdapter = new NodeFragmentAdapter(nodeHandler,getContext(),masterRecordsBeanList,R.layout.item_node_status,fragmentType);
//            rcvContent.setAdapter(nodeFragmentAdapter);
        }



        isLoadingMore = true;

        if(srlContent.isRefreshing()){
            srlContent.setRefreshing(false);
        }

    }

    @Override
    public void getUserOrderInfo(OrderInfoBean orderInfoBean) {


        if (orderRecordsBeanList.size() == 0){
            orderRecordsBeanList = orderInfoBean.getRecords();
            refresh = true;
        }else {
            orderRecordsBeanList.addAll(orderInfoBean.getRecords());
            refresh = false;
        }

        totalPage = orderInfoBean.getPages();
        currentPage = orderInfoBean.getCurrent();

        if (refresh){
            nodeFragmentAdapter = new NodeFragmentAdapter(nodeHandler,getContext(),orderRecordsBeanList,R.layout.item_order_info,fragmentType);
            rcvContent.setAdapter(nodeFragmentAdapter);
        }else{
            nodeFragmentAdapter.notifyDataSetChanged();
        }


        isLoadingMore = true;

        if(srlContent.isRefreshing()){
            srlContent.setRefreshing(false);
        }



    }

    @Override
    public void getCapitalFlowInfo(CapitalFlowInfoBean capitalFlowInfoBean) {

        if (capitalFlowBeanList.size() == 0){
            capitalFlowBeanList = capitalFlowInfoBean.getRecords();
            refresh = true;
        }else {
            capitalFlowBeanList.addAll(capitalFlowInfoBean.getRecords());
            refresh = false;
        }

        totalPage = capitalFlowInfoBean.getPages();
        currentPage = capitalFlowInfoBean.getCurrent();

        if (refresh){
            nodeFragmentAdapter = new NodeFragmentAdapter(nodeHandler,getContext(),capitalFlowBeanList,R.layout.item_capital_flow,fragmentType);
            rcvContent.setAdapter(nodeFragmentAdapter);
        }else {
            nodeFragmentAdapter.notifyDataSetChanged();
        }


        isLoadingMore = true;

        if(srlContent.isRefreshing()){
            srlContent.setRefreshing(false);
        }


    }

    @Override
    public void installTrusteeshipNode(Boolean installTrusteeshipNodeResult) {


        showTis("已提交主节点安装请求!",tisHandler,100);

    }

    @Override
    public void nodeRenew(RenewNodeBean renewNodeBean) {

        //节点续费
        Intent intent = new Intent();
        intent.setClass(getActivity(),OperatePriceActivity.class);
        intent.putExtra("renewNodeBean",renewNodeBean);
//        intent.putExtra("quantity",tvBuyCount.getText().toString());
//        intent.putExtra("price",tv_order_money.getText().toString());
        StartActivityUtils.goToAct(getActivity(),intent);




//        StartActivityUtils.goToAct(getActivity(),OperatePriceActivity.class);

    }

    @Override
    public void cancleOrder(boolean b) {
        ToastUtils.showToast("订单删除成功");
    }

    private void getData(int currentPage,int fragmentType){

        if (fragmentType == 1){
            RequestNodeBean requestNodeBean = new RequestNodeBean(currentPage+"","5","create_time","false",type,null);
            mPresenter.getMasterNodeInfo("Bearer " + accessToken,requestNodeBean,false);
        }else if (fragmentType == 2){
            RequestOrderBean requestOrderBean = new RequestOrderBean(currentPage+"","5","create_time","false",type,null);
            mPresenter.getUserOrderInfo("Bearer " + accessToken,requestOrderBean,false);
        }else if (fragmentType == 3){
            mPresenter.getCapitalFlowInfo("Bearer " + accessToken,"record/"+type,currentPage,8,"",false);
        }

    }


    private void showTis(final String message, final Handler handler, final int handlerCode){

        showPopUtil = new ShowPopUtil(R.layout.pw_confirm, getActivity(),0.7,0.2) {

            @Override
            protected void initView(View view) {

                TextView tv_confirm = view.findViewById(R.id.tv_confirm);
                tv_confirm.setText(message);

                Button bt_cancel = view.findViewById(R.id.bt_cancel);
                Button bt_confirm = view.findViewById(R.id.bt_confirm);

                if (handlerCode == 100){
                    bt_cancel.setVisibility(View.GONE);
                }else {
                    bt_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showPopUtil.dismiss();
                        }
                    });
                }



                bt_confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopUtil.dismiss();

                        handler.sendEmptyMessage(handlerCode);

                    }
                });

            }
        };

        showPopUtil.showPop();


    }
}
