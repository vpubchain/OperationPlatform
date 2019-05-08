package com.jiuling.operate.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NodeViewPagerAdapter extends PagerAdapter {

    private String nodeInfo;


    public NodeViewPagerAdapter(String nodeInfo) {
        this.nodeInfo = nodeInfo;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView tv = new TextView(container.getContext());
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(20);
        tv.setText("第" + position + "页");

        // 添加到ViewPager容器
        container.addView(tv);

        // 返回填充的View对象
        return tv;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);

        container.removeView((View) object);

    }
}
