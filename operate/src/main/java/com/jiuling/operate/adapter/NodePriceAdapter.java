package com.jiuling.operate.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.jiuling.commonbusiness.Constans;
import com.jiuling.commonbusiness.base.baseRecycler.MultiTypeSupport;
import com.jiuling.commonbusiness.base.baseRecycler.adapter.CommonRecyclerAdapter;
import com.jiuling.commonbusiness.base.baseRecycler.holder.ViewHolder;
import com.jiuling.commonbusiness.util.DateConvertUtils;
import com.jiuling.commonbusiness.util.NumberArithmeticUtils;
import com.jiuling.commonbusiness.util.StartActivityUtils;
import com.jiuling.commonbusiness.util.StringFormat;
import com.jiuling.commonbusiness.util.ToastUtils;
import com.jiuling.operate.R;
import com.jiuling.operate.contract.IOperateControlActivityView;
import com.jiuling.operate.entity.PriceBean;
import com.jiuling.operate.ui.OperateConfirmBuyActivity;
import com.jiuling.operate.ui.OperatePaymentActivity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Chain-Cloud on 2018/4/24.
 */

public class NodePriceAdapter extends CommonRecyclerAdapter {

    private Activity activity;
    private boolean isRenew;

    private long currentTime;


    public NodePriceAdapter(Activity activity, List data, int layoutId,boolean isRenew) {
        super(activity, data, layoutId);
        this.activity=activity;
        this.isRenew = isRenew;
        currentTime = System.currentTimeMillis();
    }


    public NodePriceAdapter(Context context, List data, MultiTypeSupport multiTypeSupport) {
        super(context, data, multiTypeSupport);
    }



    @Override
    public void convert(final ViewHolder holder, Object item) {

        final PriceBean.ProductTypeListBean productTypeListBean= (PriceBean.ProductTypeListBean) item;
        Log.i("body",((PriceBean.ProductTypeListBean) item).getName()+"price");
        holder.setText(R.id.tv_node_name,"维公链主节点");
        holder.setText(R.id.tv_node_info1,productTypeListBean.getName());

        Log.i("jiage",productTypeListBean.getPromotionPrice()+"价格");

        if (currentTime>=productTypeListBean.getStartPromotionTime()&&currentTime<=productTypeListBean.getEndPromotionTime()){
            holder.setText(R.id.tv_node_price,"￥ "+productTypeListBean.getPromotionPrice());
            holder.setText(R.id.tv_node_original_price,"原价￥ "+productTypeListBean.getBasePrice());
            ((TextView)holder.getView(R.id.tv_node_original_price)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }else {
            holder.setText(R.id.tv_node_price,"￥ "+productTypeListBean.getBasePrice());
            holder.setViewVisibility(R.id.tv_node_original_price,View.GONE);
        }


        holder.setText(R.id.tv_node_info2,productTypeListBean.getDetail());
        if (productTypeListBean.getType() == 1){
            holder.setText(R.id.tv_node_time,"包月");
        }else if (productTypeListBean.getType() == 2){
            holder.setText(R.id.tv_node_time,"包年");
        }

        holder.setOnclickListener(R.id.bt_buy, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isRenew){
                    Intent intent = new Intent(activity,OperateConfirmBuyActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("buy_info",productTypeListBean);
                    intent.putExtra("buy_info",bundle);
                    StartActivityUtils.goToAct(activity,intent);
                }else {
                    Intent intent = new Intent(activity,OperatePaymentActivity.class);
                    intent.putExtra("product_type_id",productTypeListBean.getId());
                    intent.putExtra("quantity","1");

                    if (currentTime>=productTypeListBean.getStartPromotionTime()&&currentTime<=productTypeListBean.getEndPromotionTime()){
                        intent.putExtra("price",productTypeListBean.getPromotionPrice()+"");
                    }else {
                        intent.putExtra("price",productTypeListBean.getBasePrice()+"");
                    }

                    StartActivityUtils.goToAct(activity,intent);
                }


            }
        });

        if (isRenew){
            holder.setText(R.id.bt_buy,"立即续费");
        }


    }


}
