package com.jiuling.commonbusiness.base.baseRecycler.creator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;

import com.jiuling.commonbusiness.R;


/**
 * Created by HP on 2018/4/29.
 */

public class DefaultLoadCreator extends LoadViewCreator {
    // 加载数据的ImageView
    private View mRefreshIv;
    private LinearLayout ll_loading;


    @Override
    public View getLoadView(Context context, ViewGroup parent) {
        View refreshView = LayoutInflater.from(context).inflate(R.layout.layout_loading_footer_view, parent, false);
        mRefreshIv = refreshView.findViewById(R.id.refresh_iv);
        ll_loading = refreshView.findViewById(R.id.ll_loading);
        return refreshView;
    }

    @Override
    public void onPull(int currentDragHeight, int loadViewHeight, int currentLoadStatus) {
        float rotate = ((float) currentDragHeight) / loadViewHeight;
        // 不断上拉的过程中不断的旋转图片
        mRefreshIv.setRotation(rotate * 360);
        ll_loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoading() {
// 加载的时候不断旋转
        RotateAnimation animation = new RotateAnimation(0, 720,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setRepeatCount(-1);
        animation.setDuration(1000);
        mRefreshIv.startAnimation(animation);
    }

    @Override
    public void onStopLoad() {
        // 停止加载的时候清除动画
        mRefreshIv.setRotation(0);
        mRefreshIv.clearAnimation();
        ll_loading.setVisibility(View.GONE);
    }

    @Override
    public void noLoadingAndActionUp() {
        ll_loading.setVisibility(View.GONE);
    }
}
