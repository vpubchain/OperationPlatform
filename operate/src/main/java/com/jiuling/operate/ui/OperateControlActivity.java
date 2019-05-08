package com.jiuling.operate.ui;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.jiuling.commonbusiness.base.ProgressBaseActivity;
import com.jiuling.commonbusiness.di.component.ApplicationComponent;
import com.jiuling.commonbusiness.util.DateConvertUtils;
import com.jiuling.commonbusiness.util.SharedPreferencesUtils;
import com.jiuling.commonbusiness.util.ToastUtils;
import com.jiuling.operate.R;
import com.jiuling.operate.R2;
import com.jiuling.operate.contract.IOperateControlActivityView;
import com.jiuling.operate.di.component.DaggerOperateControlComponent;
import com.jiuling.operate.di.module.OperateControlModule;
import com.jiuling.operate.entity.StatisticsIncomeBean;
import com.jiuling.operate.entity.UserMasternodeCountBean;
import com.jiuling.operate.presenter.OperateControlActivityPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OperateControlActivity extends BaseOperateActivity<OperateControlActivityPresenter> implements IOperateControlActivityView {


    @BindView(R2.id.iv_more)
    ImageView ivMore;
    @BindView(R2.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R2.id.tv_loginout)
    TextView tvLoginout;
    @BindView(R2.id.tv_amount)
    TextView tvAmount;
    @BindView(R2.id.tv_node_count)
    TextView tvNodeCount;
    @BindView(R2.id.tv_my_income)
    TextView tvMyIncome;
    @BindView(R2.id.tv_month_statistics)
    TextView tvMonthStatistics;
    @BindView(R2.id.tv_year_statistics)
    TextView tvYearStatistics;
    @BindView(R2.id.tv_all_net)
    TextView tv_all_net;
    @BindView(R2.id.lv_staticstics_has_info)
    LinearLayout lv_staticstics_has_info;
    @BindView(R2.id.tv_expiring)
    TextView tv_expiring;
    @BindView(R2.id.linechart)
    LineChart linechart;


    private static String timeFormat = "yyyy-MM-dd";
//    private final static String timeFormat = "yyyy-MM";

    private SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();
    private String authorization = "";

    @Override
    protected void setupAcitivtyComponent(ApplicationComponent applicationComponent) {
        DaggerOperateControlComponent.builder().applicationComponent(applicationComponent).operateControlModule(new OperateControlModule(this)).build().injectOperateControlActivity(this);
    }

    @Override
    protected int setLayoutID() {
        return R.layout.activity_control_main;
    }

    @Override
    protected void initView() {
        type = 2;


    }

    @Override
    protected void initData() {

//        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this,SharedPreferencesUtils.LOGIN);
//        String authorization = sharedPreferencesUtils.getInfo(SharedPreferencesUtils.ACCEE_TOKEN);





    }

    @Override
    protected void onResume() {
        super.onResume();
        authorization = sharedPreferencesUtils.getAcceeToken(this);
        mPresenter.getUserMasternodeIncomeDay("Bearer "+authorization,true);
        mPresenter.getUserMasternodeCount("Bearer "+authorization,true);
        tvPhoneNumber.setText(getPhone());
        Log.i("body","Bearer "+authorization);
    }

    @Override
    protected void initEvent() {


    }

    @Override
    public void showData(ArrayList data) {

    }


    @Override
    public void getUserMasternodeCountBean(UserMasternodeCountBean userMasternodeCountBean) {

        tv_all_net.setText("全网节点数:"+userMasternodeCountBean.getAllNet()+"");
        tvNodeCount.setText(userMasternodeCountBean.getEnabled()+"/"+userMasternodeCountBean.getTotal());
        tvAmount.setText("账户余额 (现金)  ￥"+userMasternodeCountBean.getAmount()+"");


    }

    //获取到日收入统计
    @Override
    public void getUserMasternodeIncomeDay(StatisticsIncomeBean statisticsIncomeBean,boolean isDay) {

//            ToastUtils.showToast(statisticsIncomeBean.toString());

        if (isDay){
            timeFormat = "yyyy-MM-dd";
        }else {
            timeFormat = "yyyy-MM";
        }

        tvMyIncome.setText(statisticsIncomeBean.getAmount()+"VP");
        int timeSize = statisticsIncomeBean.getTime().size();
        tv_expiring.setText("截止日期:"+statisticsIncomeBean.getTime().get(timeSize-1));


        //数据图处理
        //设置手势滑动事件
//        linechart.setOnChartGestureListener(this);
        //设置数值选择监听
//        linechart.setOnChartValueSelectedListener(this);
        //后台绘制
        linechart.setDrawGridBackground(false);
        //设置描述文本
        linechart.getDescription().setEnabled(false);
        //设置支持触控手势
        linechart.setTouchEnabled(true);
        //设置缩放
        linechart.setDragEnabled(true);
        //设置推动
        linechart.setScaleEnabled(true);
        //如果禁用,扩展可以在x轴和y轴分别完成
        linechart.setPinchZoom(true);

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

        XAxis xAxis = linechart.getXAxis();
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setDrawGridLines(true);

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

        YAxis leftAxis = linechart.getAxisLeft();
        YAxis rightAxis = linechart.getAxisRight();

        //重置所有限制线,以避免重叠线
        leftAxis.removeAllLimitLines();
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        leftAxis.setDrawGridLines(true);

        // 限制数据(而不是背后的线条勾勒出了上面)
        leftAxis.setDrawLimitLinesBehindData(true);

        //////////////

        //这里我模拟一些数据
        ArrayList<Entry> values = new ArrayList<Entry>();
//
//        values.add(new Entry(0, 10,"08-1"));
//        values.add(new Entry(1, 15,""));
//        values.add(new Entry(2, 25,""));
//        values.add(new Entry(3, 19,""));
//        values.add(new Entry(4, 25,"08-10"));
//        values.add(new Entry(5, 16,""));
//        values.add(new Entry(6, 140,""));
//        values.add(new Entry(7, 24,""));
//        values.add(new Entry(8, 27,"08-21"));
//
////
////        xAxis.setValueFormatter((value, axis) -> values.get((int)value).getData()+"");
//
//        leftAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
//                Log.i("shezhi",value+"转换");
//                return "￥" + value;
//            }
//        });





        for (int i = 0;i<statisticsIncomeBean.getData().size();i++){

            String timetest = statisticsIncomeBean.getTime().get(i);

            long time = DateConvertUtils.dateToTimeStamp(timetest,timeFormat);

//            long time = DateConvertUtils.dateToTimeStamp(statisticsIncomeBean.getTime().get(i),timeFormat);
            values.add(new Entry(time,statisticsIncomeBean.getData().get(i).floatValue()));
            Log.i("shezhi",statisticsIncomeBean.getTime().get(i)+"           "+time+"                "+statisticsIncomeBean.getData().get(i).floatValue());
        }


        IAxisValueFormatter iAxisValueFormatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Log.i("shezhi",value+"时间");
//                return DateConvertUtils.timeStampToDate((long) value,"yyyy-MM-dd");
                String data = DateConvertUtils.timeStampToDate((long) value,timeFormat);
                Log.i("shezhi",data.substring(data.length()-2,data.length())+"转换");
                return data.substring(data.length()-5,data.length());
//                return data;
            }
        };
        xAxis.setValueFormatter(iAxisValueFormatter);



        //设置数据
        setData(values);
        //默认动画
        linechart.animateX(2500);
        //刷新
//        mChart.invalidate();
        // 得到这个文字
        Legend l = linechart.getLegend();
        // 修改文字 ...
        l.setForm(Legend.LegendForm.LINE);



    }

    //传递数据集
    private void setData(ArrayList<Entry> values) {

        LineDataSet set1;

        if (linechart.getData() != null && linechart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) linechart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            linechart.getData().notifyDataChanged();
            linechart.notifyDataSetChanged();
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
            linechart.setData(data);
        }
    }




    @OnClick({R2.id.tv_month_statistics,R2.id.tv_year_statistics,R2.id.tv_loginout})
    public void viewsOnClick(View view) {

        int i = view.getId();
        if (i == R.id.tv_month_statistics) {
            tvMonthStatistics.setTextColor(getResources().getColor(R.color.control_button1));
            tvYearStatistics.setTextColor(getResources().getColor(R.color.control_button2));
            mPresenter.getUserMasternodeIncomeDay("Bearer "+authorization,true);
            ToastUtils.showToast("日收入统计");
        } else if (i == R.id.tv_year_statistics) {
            tvMonthStatistics.setTextColor(getResources().getColor(R.color.control_button2));
            tvYearStatistics.setTextColor(getResources().getColor(R.color.control_button1));
            mPresenter.getUserMasternodeIncomeMonth("Bearer "+authorization,true);
            ToastUtils.showToast("月收入统计");
        } else if (i == R.id.tv_loginout){
            showLoginOutPop();
        }
    }
}