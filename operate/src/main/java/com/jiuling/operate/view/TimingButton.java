package com.jiuling.operate.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.Button;

import com.jiuling.operate.R;


public class TimingButton extends Button {
    private int total, interval;
    private String psText;

    public TimingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取自定义属性，并赋值
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TimingButton);
        total = typedArray.getInteger(R.styleable.TimingButton_tb_totalTime, 60000);
        interval = typedArray.getInteger(R.styleable.TimingButton_tb_timeInterval, 1000);
        psText = typedArray.getString(R.styleable.TimingButton_tb_psText);
//        setBackgroundResource(R.drawable.timing_button2); //设置默认样式
        typedArray.recycle();
    }

    //执行
    public void start() {
        TimeCount time = new TimeCount(total, interval);
        time.start();
    }

    public class TimeCount extends CountDownTimer {
        private long countDownInterval;

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
            this.countDownInterval = countDownInterval;
        }

        @Override
        public void onFinish() {//计时完毕时触发
            setText(psText);
            setEnabled(true);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            setEnabled(false);
            setText(millisUntilFinished / countDownInterval + "秒");
        }
    }
}
