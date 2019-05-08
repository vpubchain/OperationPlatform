package com.jiuling.commonbusiness.base.baseRecycler.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.jiuling.commonbusiness.base.baseRecycler.MultiTypeSupport;
import com.jiuling.commonbusiness.base.baseRecycler.holder.ViewHolder;

import java.util.List;

/**
 * Created by HP on 2018/4/29.
 */

public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    protected Context mContext;
    protected LayoutInflater mInflater;
    //数据怎么办？
    protected List<T> mData;
    // 布局怎么办？
    private int mLayoutId;

    // 多布局支持
    private MultiTypeSupport mMultiTypeSupport;

    public CommonRecyclerAdapter(Context context, List<T> data, int layoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mData = data;
        this.mLayoutId = layoutId;
    }

    /**
     * 多布局支持
     */
    public CommonRecyclerAdapter(Context context, List<T> data, MultiTypeSupport<T> multiTypeSupport) {
        this(context, data, -1);
        this.mMultiTypeSupport = multiTypeSupport;
    }

    /**
     * 根据当前位置获取不同的viewType
     */
    @Override
    public int getItemViewType(int position) {
        // 多布局支持
        if (mMultiTypeSupport != null) {
            return mMultiTypeSupport.getLayoutId(mData.get(position), position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 多布局支持
        if (mMultiTypeSupport != null) {
            mLayoutId = viewType;
        }
        // 先inflate数据
        View itemView = mInflater.inflate(mLayoutId, parent, false);
        // 返回ViewHolder
        ViewHolder holder = new ViewHolder(itemView);


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // 设置点击和长按事件
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClickListener(v,position);
                }
            });
        }
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongClickListener.onItemLongClickListener(v,position);
                    return false;
                }
            });
        }

        // 绑定怎么办？回传出去
        convert(holder, mData.get(position));
    }

    /**
     * 利用抽象方法回传出去，每个不一样的Adapter去设置
     *
     * @param item 当前的数据
     */
    public abstract void convert(ViewHolder holder, T item);

    @Override
    public int getItemCount() {
        if (mData == null){
            return 0;
        }
        return mData.size();
    }

    /***************
     * 设置条目点击和长按事件
     *********************/
    public OnItemClickListener mItemClickListener;
    public OnLongClickListener mLongClickListener;

    public interface OnItemClickListener {
        void onItemClickListener(View v, int position);
    }

    public interface OnLongClickListener {
        void onItemLongClickListener(View v, int position);
    }


    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener longClickListener) {
        this.mLongClickListener = mLongClickListener;
    }
}