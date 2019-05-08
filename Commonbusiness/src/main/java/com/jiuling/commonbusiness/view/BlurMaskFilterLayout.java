package com.jiuling.commonbusiness.view;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.jiuling.commonbusiness.base.BaseApplication;
import com.jiuling.commonbusiness.util.DisplayUtil;


public class BlurMaskFilterLayout extends LinearLayout {

	private Paint mPaint;
	private int padding=DisplayUtil.dip2px(BaseApplication.getInstance(),5);
	private int roundX= DisplayUtil.dip2px(BaseApplication.getInstance(),100);
	private int roundY= DisplayUtil.dip2px(BaseApplication.getInstance(),100);


	public BlurMaskFilterLayout(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public BlurMaskFilterLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BlurMaskFilterLayout(Context context) {
		super(context);
		init();
	}

	private void init() {

		setWillNotDraw(false);

		setLayerType(LAYER_TYPE_SOFTWARE, null);
		mPaint = new Paint();
		mPaint.setColor(0xffD4E2EE);
		mPaint.setMaskFilter(new BlurMaskFilter(padding, Blur.SOLID));


	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		RectF r1 = new RectF();
		r1.left = 0 + padding;
		r1.top = 0 + padding;
		r1.right = getWidth() - padding;
		r1.bottom = getHeight() - padding;
		canvas.drawRoundRect(r1, roundX, roundY, mPaint);
		Paint paint = new Paint();
		paint.setColor(0xffffffff);
		canvas.drawRoundRect(r1, roundX, roundY, paint);
	}

}