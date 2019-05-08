package com.jiuling.commonbusiness.base.recyclerbase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * @author Rayman
 * @date 2017/11/23
 */
public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private int layoutId;
    protected List<? extends BaseRecyclerBean> data;
    public Context context;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private boolean clickFlag = true;

    //最后一个item是否为其他layout
    private boolean isHasOther=false;

    /**
     * @param layoutId 布局id
     * @param data     数据源
     */
    public BaseRecyclerAdapter(int layoutId, List<? extends BaseRecyclerBean> data) {
        this.layoutId = layoutId;
        this.data = data;
    }

    /**
     * @param data 数据源
     */
    public BaseRecyclerAdapter(List<? extends BaseRecyclerBean> data) {
        this.data = data;
        this.layoutId = setLayoutId();
    }

    /**
     * @param data 数据源
     */
    public BaseRecyclerAdapter(List<? extends BaseRecyclerBean> data, boolean isHasOther) {
        this.data = data;
        this.layoutId = setLayoutId();
        this.isHasOther=isHasOther;
    }

    @Override
    public int getItemViewType(int position) {

        if (!isHasOther){
            return 1;
        }else{
            if (position==data.size()){
                return 2;
            }else{
                return 1;
            }

        }
//        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final BaseViewHolder holder;
        if (viewType==1){
            View v = LayoutInflater.from(context).inflate(layoutId, parent, false);
             holder = new BaseViewHolder(v, context);
            //单击事件回调
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickFlag) {
                        if (onItemClickListener != null)
                            onItemClickListener.onItemClickListener(v, holder.getLayoutPosition());
                    }
                    clickFlag = true;
                }
            });
            //单击长按事件回调
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemLongClickListener != null)
                        onItemLongClickListener.onItemLongClickListener(v, holder.getLayoutPosition());
                    clickFlag = false;
                    return false;
                }
            });
        }else{
            View v = LayoutInflater.from(context).inflate(setOtherLayoutId(), parent, false);
            holder = new BaseViewHolder(v, context);
            holder.setHasOther(true);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (position<data.size()){
            convert(holder, data.get(position));
        }else{
            convert(holder, null);
        }

    }

    /**
     * 设置当前LayoutId
     *
     * @return
     */
    protected abstract int setLayoutId();

    /**
     * 设置other LayoutId
     *
     * @return
     */
    protected int setOtherLayoutId(){
        return 0;
    }


    /**
     * 处理具体的事件
     *
     * @param holder
     * @param bean
     * @param <T>
     */
    protected abstract <T extends BaseRecyclerBean> void convert(BaseViewHolder holder, T bean);


    @Override
    public int getItemCount() {
        if (!isHasOther){
            return data.size();
        }else{
            return data.size()+1;
        }

//        return 3;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(View v, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClickListener(View v, int position);
    }

    public void notifyRvDataSetChanged(List<? extends BaseRecyclerBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
