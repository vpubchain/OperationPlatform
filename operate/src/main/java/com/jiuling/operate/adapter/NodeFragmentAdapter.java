package com.jiuling.operate.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;

import com.jiuling.commonbusiness.base.baseRecycler.adapter.CommonRecyclerAdapter;
import com.jiuling.commonbusiness.base.baseRecycler.holder.ViewHolder;
import com.jiuling.commonbusiness.util.DateConvertUtils;
import com.jiuling.operate.R;
import com.jiuling.operate.entity.CapitalFlowInfoBean;
import com.jiuling.operate.entity.MasterNodeBean;
import com.jiuling.operate.entity.OrderInfoBean;
import com.jiuling.operate.ui.fragment.NodeFragment;

import org.w3c.dom.Node;

import java.util.List;

public class NodeFragmentAdapter extends CommonRecyclerAdapter {


    private int fragmentTpe = 1;
    private Handler handler;


    public NodeFragmentAdapter(Handler handler, Context context, List data, int layoutId, int fragmentType) {
        super(context, data, layoutId);

        this.fragmentTpe = fragmentType;
        this.handler = handler;

    }

    @Override
    public void convert(ViewHolder holder, Object item) {

        if (fragmentTpe == 1){
            final MasterNodeBean.RecordsBean recordsBean = (MasterNodeBean.RecordsBean) item;
            holder.setText(R.id.tv_node_name,recordsBean.getDtl().getNodeName());

            //运行状态
            if (recordsBean.getDtl().getStatus() == null){
                holder.setText(R.id.tv_status,"未运行");
            }else{
                holder.setText(R.id.tv_status,recordsBean.getDtl().getStatus());
            }

            //ip
            if (recordsBean.getDtl().getIp() == null){
                holder.setText(R.id.tv_ip,"未安装");
            }else{
                holder.setText(R.id.tv_ip,recordsBean.getDtl().getIp());
            }

            //安装状态  完成安装   同步区块   待安装   未安装
            if (recordsBean.getDtl().getStep() == 100){
                holder.setText(R.id.tv_install_status,"完成安装");
            }else if (recordsBean.getDtl().getStep()<100){
                holder.setText(R.id.tv_install_status,"同步区块");
            }else {
                //待修改
                holder.setText(R.id.tv_install_status,"待安装");
            }

            //钱包地址
            if (recordsBean.getDtl().getAccount() == null){
                holder.setText(R.id.tv_wallet_address,"未安装");
            }else{
                holder.setText(R.id.tv_wallet_address,recordsBean.getDtl().getAccount());
            }

            //到期时间
            holder.setText(R.id.tv_time,DateConvertUtils.timeStampToDate(recordsBean.getDtl().getMaturityTime()/1000,"yyyy-MM-dd HH:mm:ss"));


            holder.setOnclickListener(R.id.tv_node_renew, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //节点续费

                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString(NodeFragment.INSTALLNODEID,recordsBean.getDtl().getId()+"");
                    bundle.putString(NodeFragment.ACTION,NodeFragment.NODERENEW);
                    message.setData(bundle);
                    handler.sendMessage(message);


                }
            });



            if (recordsBean.getDtl().getStep() == 0 &&recordsBean.getDtl().getIsEntrust() == 0){

                holder.setViewVisibility(R.id.tv_trusteeship_install,View.VISIBLE);


                holder.setOnclickListener(R.id.tv_trusteeship_install, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //托管安装

                    Message message = new Message();
                    Bundle bundle = new Bundle();
                    bundle.putString(NodeFragment.INSTALLNODEID,recordsBean.getDtl().getId()+"");
                    bundle.putString(NodeFragment.ACTION,NodeFragment.INSTALL);
                    message.setData(bundle);
                    handler.sendMessage(message);


                }
            });

            }

//



        }else if (fragmentTpe == 2){

            final OrderInfoBean.RecordsBean recordsBean = (OrderInfoBean.RecordsBean) item;
            holder.setText(R.id.tv_order_name,"节点名称:"+recordsBean.getSerialNumber());
            if (recordsBean.getStatus()==1){
                holder.setText(R.id.tv_order_status,"已完成");
            } else{
                holder.setText(R.id.tv_order_status,"待支付");
                holder.setViewVisibility(R.id.ll_more,View.VISIBLE);
                holder.setOnclickListener(R.id.tv_pay_order, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //支付订单
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString(NodeFragment.INSTALLNODEID,recordsBean.getProductTypeId()+"");
                        bundle.putString(NodeFragment.ORDERNUMBER,recordsBean.getSerialNumber()+"");
                        bundle.putString(NodeFragment.QUANTIRY,recordsBean.getQuantity()+"");
                        bundle.putString(NodeFragment.AMOUNT,recordsBean.getAmount()+"");
                        bundle.putString(NodeFragment.ACTION,NodeFragment.PAYORDER);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                });

                holder.setOnclickListener(R.id.tv_cancel_order, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //取消订单
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString(NodeFragment.INSTALLNODEID,recordsBean.getProductTypeId()+"");
                        bundle.putString(NodeFragment.ORDERNUMBER,recordsBean.getSerialNumber()+"");
                        bundle.putString(NodeFragment.ACTION,NodeFragment.CANCELORDER);
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                });

            }
            holder.setText(R.id.tv_order_money,"订单金额:￥"+recordsBean.getAmount());
            holder.setText(R.id.tv_order_buy_number,"购买数量:"+recordsBean.getQuantity());
            holder.setText(R.id.tv_order_info,"产品消息:"+recordsBean.getProductDesc());
            holder.setText(R.id.tv_order_time,DateConvertUtils.timeStampToDate(recordsBean.getCreateTime()/1000,"yyyy-MM-dd HH:mm:ss"));



        }else if (fragmentTpe == 3){
            final CapitalFlowInfoBean.RecordsBean recordsBean = (CapitalFlowInfoBean.RecordsBean) item;

            if (recordsBean.getType().equals("2")){
                holder.setText(R.id.tv_money,"-"+recordsBean.getAmount()+"");
            }else{
                holder.setText(R.id.tv_money,"+"+recordsBean.getAmount()+"");
            }

            if (recordsBean.getPayWay() == 1){
                holder.setText(R.id.tv_pay_type,"支付宝");
            }else if (recordsBean.getPayWay() == 2){
                holder.setText(R.id.tv_pay_type,"微信");
            }else {
                holder.setText(R.id.tv_pay_type,"余额支付");
            }
            holder.setText(R.id.tv_capital_flow_number,recordsBean.getSerialNumber());
            holder.setText(R.id.tv_time,DateConvertUtils.timeStampToDate(recordsBean.getCreateTime()/1000,"yyyy-MM-dd HH:mm:ss"));

        }




    }

}

