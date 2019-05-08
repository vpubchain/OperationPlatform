package com.jiuling.operate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jiuling.commonbusiness.util.ToastUtils;
import com.jiuling.operate.R;

import java.util.List;

public class RechargeAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<String> rechargeList;

    public RechargeAdapter(Context context, List<String> rechargeList) {
        layoutInflater = LayoutInflater.from(context);
        this.rechargeList = rechargeList;
    }

    @Override
    public int getCount() {
        return rechargeList.size();
    }

    @Override
    public Object getItem(int position) {
        return rechargeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_recharge, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.tv_recharge_money);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(rechargeList.get(position));
//        holder.text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtils.showToast("充值金额"+rechargeList.get(position));
//            }
//        });

        return convertView;
    }

    class ViewHolder {
        TextView text;
    }

}
