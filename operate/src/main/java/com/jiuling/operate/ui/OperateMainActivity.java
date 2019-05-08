package com.jiuling.operate.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.jiuling.commonbusiness.base.ProgressBaseActivity;
import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.commonbusiness.util.DateConvertUtils;
import com.jiuling.commonbusiness.util.SharedPreferencesUtils;
import com.jiuling.commonbusiness.util.StartActivityUtils;
import com.jiuling.operate.R;
import com.jiuling.operate.contract.IOperateMainActivityView;
import com.jiuling.operate.di.component.DaggerOperateMainComponent;
import com.jiuling.operate.di.module.OperateMainModule;
import com.jiuling.operate.entity.OperateMainBean;
import com.jiuling.operate.presenter.OperateMainActivityPresenter;

import java.util.ArrayList;


public class OperateMainActivity extends BaseOperateActivity<OperateMainActivityPresenter> implements IOperateMainActivityView, View.OnClickListener {

    private TextView tvControl;
    private TextView tvHomepage;
    private TextView tvPrice;
    private TextView tvConstraUs;
    private TextView tvLogin;
    private TextView tvRegister;


    private ImageView iv_more;
    private TextView tv_login;

    private PieChart mPieChart;
    private LineChart mPieChart2;
    private final static String timeFormat = "yyyy-MM-dd";



    @Override
    protected void setupAcitivtyComponent(ApplicationComponent applicationComponent) {
        DaggerOperateMainComponent.builder().applicationComponent(applicationComponent).operateMainModule(new OperateMainModule(this)).build().injectOperateMainActivity(this);

    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_operate;
    }

    @Override
    protected void initView() {


    }


    //设置数据
    private void setData(ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

//        数据和颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
        colors.add(getResources().getColor(R.color.chart_color2));
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        //取消百分比数字
        data.setValueTextSize(0f);
        data.setValueTextColor(Color.BLACK);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        //刷新
        mPieChart.invalidate();
    }


    //传递数据集
    private void setData2(ArrayList<Entry> values) {

        LineDataSet set1;

        if (mPieChart2.getData() != null && mPieChart2.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mPieChart2.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mPieChart2.getData().notifyDataChanged();
            mPieChart2.notifyDataSetChanged();
        } else {
            // 创建一个数据集,并给它一个类型
            set1 = new LineDataSet(values, "收入统计");

            // 在这里设置线
            set1.enableDashedLine(10f, 0f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(getResources().getColor(R.color.line_color));
            set1.setCircleColor(getResources().getColor(R.color.line_color));
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(0f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
//            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // 填充背景只支持18以上
                //Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.ic_launcher);
                //set1.setFillDrawable(drawable);
//                set1.setFillColor(Color.YELLOW);
                set1.setFillColor(getResources().getColor(R.color.chart_color));
            } else {
                set1.setFillColor(Color.BLACK);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            //添加数据集
            dataSets.add(set1);

            //创建一个数据集的数据对象
            LineData data = new LineData(dataSets);

            //谁知数据
            mPieChart2.setData(data);
        }
    }


    @Override
    protected void initData() {
        super.initData();

        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
        String authorization = sharedPreferencesUtils.getAcceeToken(this);
        mPresenter.getMasternodeCount("Bearer " + authorization,false);

    }

    @Override
    protected void initEvent() {

    }



    @Override
    public void showData(ArrayList data) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        setType();

    }

    @Override
    public void getMasternodeCount(OperateMainBean operateMainBean) {

        //获取到图标所需数据
        mPieChart = findViewById(R.id.mPieChart);
        mPieChart2 = findViewById(R.id.mPieChart2);

       setPieChart(operateMainBean);
       setPieChart2(operateMainBean);

    }

    private void setPieChart2(OperateMainBean operateMainBean) {

        //数据图处理
        //设置手势滑动事件
//        linechart.setOnChartGestureListener(this);
        //设置数值选择监听
//        linechart.setOnChartValueSelectedListener(this);
        //后台绘制
//        mPieChart2.setDrawGridBackground(false);
        //设置描述文本
        mPieChart2.getDescription().setEnabled(false);
        //设置支持触控手势
//        mPieChart2.setTouchEnabled(true);
        //设置缩放
//        mPieChart2.setDragEnabled(true);
        //设置推动
//        mPieChart2.setScaleEnabled(true);
        //如果禁用,扩展可以在x轴和y轴分别完成
//        mPieChart2.setPinchZoom(true);

//        ////////////
//        //x轴
//        LimitLine llXAxis = new LimitLine(10f, "标记");
//        //设置线宽
//        llXAxis.setLineWidth(4f);
//        //
//        llXAxis.enableDashedLine(10f, 10f, 0f);
//        //设置
//        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        llXAxis.setTextSize(10f);

        XAxis xAxis = mPieChart2.getXAxis();
//        xAxis.enableGridDashedLine(10f, 0f, 0f);
//        xAxis.setDrawGridLines(true);

//        LimitLine ll1 = new LimitLine(150f, "优秀");
//        ll1.setLineWidth(4f);
//        ll1.enableDashedLine(10f, 10f, 0f);
//        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//        ll1.setTextSize(10f);
//
//        LimitLine ll2 = new LimitLine(30f, "不及格");
//        ll2.setLineWidth(4f);
//        ll2.enableDashedLine(10f, 10f, 0f);
//        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        ll2.setTextSize(10f);

        YAxis leftAxis = mPieChart2.getAxisLeft();

        //重置所有限制线,以避免重叠线
        leftAxis.removeAllLimitLines();
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        leftAxis.setDrawGridLines(true);

        // 限制数据(而不是背后的线条勾勒出了上面)
        leftAxis.setDrawLimitLinesBehindData(true);



        //这里我模拟一些数据
        ArrayList<Entry> values = new ArrayList<Entry>();


        for (int i = 0;i<operateMainBean.getData().size();i++){

            long time = DateConvertUtils.dateToTimeStamp(operateMainBean.getTime().get(i),timeFormat);
            values.add(new Entry(time,operateMainBean.getData().get(i).floatValue()));
            Log.i("shezhi",operateMainBean.getTime().get(i)+"           "+time+"                "+operateMainBean.getData().get(i).floatValue());
        }


        IAxisValueFormatter iAxisValueFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Log.i("shezhi",value+"时间");
//                return DateConvertUtils.timeStampToDate((long) value,"yyyy-MM-dd");
                String data = DateConvertUtils.timeStampToDate((long) value,timeFormat);
                Log.i("shezhi",data.substring(data.length()-2,data.length())+"转换");
//                return data.substring(data.length()-2,data.length());
                return data;
            }
        };
        xAxis.setValueFormatter(iAxisValueFormatter);



        //设置数据
        setData2(values);
        //默认动画
        mPieChart2.animateX(2500);
        //刷新
//        mChart.invalidate();
        // 得到这个文字
        Legend l = mPieChart2.getLegend();
        // 修改文字 ...
        l.setForm(Legend.LegendForm.LINE);

    }

    private void setPieChart(OperateMainBean operateMainBean) {

        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);
        //设置中间文件
        mPieChart.setCenterText("活动节点/总节点\n"+operateMainBean.getData().get(operateMainBean.getData().size()-1)+"/"+operateMainBean.getTotal());

        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        // 触摸旋转
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);


        //模拟数据
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(operateMainBean.getTotal()-operateMainBean.getData().get(operateMainBean.getData().size()-1), ""));
        entries.add(new PieEntry(operateMainBean.getData().get(operateMainBean.getData().size()-1), ""));


        //设置数据
        setData(entries);

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mPieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // 输入标签样式
        mPieChart.setEntryLabelColor(Color.BLACK);
        mPieChart.setEntryLabelTextSize(10f);


    }


//    @OnClick({R2.id.tv_control,R2.id.tv_homepage,R2.id.tv_price,R2.id.tv_constra_us,R2.id.tv_login,R2.id.tv_register})
//    public void viewsOnClick(View view){
//
//        switch (view.getId()){
//            case R2.id.tv_control:
//                StartActivityUtils.goToAct(OperateMainActivity.this,OperateLoginActivity.class);
//                break;
//        }
//
//    }


}
