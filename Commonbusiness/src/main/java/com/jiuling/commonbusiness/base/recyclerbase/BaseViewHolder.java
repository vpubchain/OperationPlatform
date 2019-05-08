package com.jiuling.commonbusiness.base.recyclerbase;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;


/**
 * @author Rayman
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    View convertView;
    Context context;
    private boolean isHasOther=false;

    public boolean isHasOther() {
        return isHasOther;
    }

    public void setHasOther(boolean hasOther) {
        isHasOther = hasOther;
    }

    public BaseViewHolder(View itemView, Context context) {
        super(itemView);
        this.convertView = itemView;
        this.context = context;
    }

    /**
     * 设置文字
     *
     * @param id
     * @param text
     */
    public void setText(int id, CharSequence text) {
        TextView tx = (TextView) convertView.findViewById(id);
        tx.setText(text);
    }

    public void setTextColor(int id, int color) {
        TextView tx = (TextView) convertView.findViewById(id);
        tx.setTextColor(color);
    }


    /**
     * 设置Resource图片
     *
     * @param id
     * @param resourceId
     */
    public void setImageResource(int id, int resourceId) {
        ImageView img = (ImageView) convertView.findViewById(id);
        img.setImageResource(resourceId);
    }

    /**
     * 加载网络图片
     *
     * @param id
     * @param url
     */
    public void loadImage(int id, String url) {


//        XUtilsHelper.getBitmapUtils().display(convertView.findViewById(id),url);
        Glide.with(context).load(url).into((ImageView) convertView.findViewById(id));

    }

    public void loadLocalImage(int id,int sourceId){
        Glide.with(context).load(sourceId).into((ImageView) convertView.findViewById(id));
    }

    /**
     * 设置高
     *
     * @param id
     */
    public void setHeight(int id, int height) {

        View view = convertView.findViewById(id);
        view.getLayoutParams().height= height;

    }

    /**
     * 设置宽
     *
     * @param id
     */
    public void setWidth(int id, int width) {

        View view = convertView.findViewById(id);
        view.getLayoutParams().width= width;

    }





    /**
     * 设置EditText的Hint
     *
     * @param id
     * @param hint
     */
    public void setHint(int id, String hint) {
        EditText et = (EditText) convertView.findViewById(id);
        et.setHint(hint);
    }

    /**
     * 设置View的可见性
     *
     * @param id
     * @param visibility
     */
    public void setVisibility(int id, int visibility) {
        View view = convertView.findViewById(id);
        view.setVisibility(visibility);
    }

    public void setOnclickListener(int id, View.OnClickListener listener) {
        View v = convertView.findViewById(id);
        v.setOnClickListener(listener);
    }

    public void setHorizontalList(int recyclerId, final int resId, final int textId, ArrayList<? extends BaseRecyclerBean> data) {
        RecyclerView recyclerView = (RecyclerView) convertView.findViewById(recyclerId);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new BaseRecyclerAdapter(data) {
            @Override
            protected int setLayoutId() {
                return resId;
            }

            @Override
            protected <T extends BaseRecyclerBean> void convert(BaseViewHolder holder, T bean) {
                BaseContentBean baseContentBean = (BaseContentBean) bean;
                holder.setText(textId, baseContentBean.getContent());
            }
        });
    }

    public void setVerticalList(int recyclerId, final int resId, final int textId, ArrayList<? extends BaseRecyclerBean> data) {
        RecyclerView recyclerView = (RecyclerView) convertView.findViewById(recyclerId);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new BaseRecyclerAdapter(data) {
            @Override
            protected int setLayoutId() {
                return resId;
            }

            @Override
            protected <T extends BaseRecyclerBean> void convert(BaseViewHolder holder, T bean) {
                BaseContentBean baseContentBean = (BaseContentBean) bean;
                holder.setText(textId, baseContentBean.getContent());
            }
        });
    }

    public View getTargetView(int id) {
        return convertView.findViewById(id);
    }
}
