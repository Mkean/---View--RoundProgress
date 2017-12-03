package com.bwie.diyround;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：王庆
 * 时间：2017/12/3
 */

public class MyRound extends View {
    private Paint textPaint;//文字画笔
    private Paint RectPaint;//外接圆画笔
    private Paint RoundPaint;//内圆画笔
    private RectF rectF;//外接矩形
    private float startSweepValue;//外接圆开始角度
    private float currentPercent;//文字当前旋转度数
    private float TargetPercent;//目标度数
    private int mRadius = 100;//圆形半径
    private int mCircleX;//设置X轴
    private int mCircleY;//设置Y轴
    private int textSize;//字号大小

    public MyRound(Context context) {
        super(context);
    }

    public MyRound(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyRound(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyRound(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        currentPercent = 0;
        startSweepValue = -90;

        //设置文字画笔属性
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(mRadius / 2);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textSize = (int) textPaint.getTextSize();

        //设置内圆画笔
        RoundPaint = new Paint();
        RoundPaint.setStyle(Paint.Style.FILL);
        RoundPaint.setColor(Color.GREEN);
        RoundPaint.setAntiAlias(true);

        //设置外接圆
        RectPaint = new Paint();
        RectPaint.setColor(Color.BLUE);
        RectPaint.setStyle(Paint.Style.STROKE);
        RectPaint.setAntiAlias(true);
        RectPaint.setStrokeWidth(5);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measure(widthMeasureSpec), measure(widthMeasureSpec));

        //获取原点位置
        mCircleX = getMeasuredWidth() / 2;
        mCircleY = getMeasuredHeight() / 2;
//        int h = getHeight();
//        int w = getWidth();
//        Log.e("TAG",h+"  "+w);

        //判断如果半径超过X轴的位置，就会画到屏幕外面
        if (mRadius > mCircleX) {
            mRadius = mCircleX;
            //因为半径重新赋值了，所以文字字号也要重新赋值
            textPaint.setTextSize(mRadius / 2);
            //获取文字的字号
            textSize = (int) textPaint.getTextSize();
        }

        /*
        * 设置外接矩形
        * mCircleX - mRadius：X轴距离减去圆的半径就是距离左边的距离，以下同理
        * */
        rectF = new RectF(mCircleX - mRadius, mCircleY - mRadius, mCircleX + mRadius, mCircleY + mRadius);
    }

    //接口实现方法
    private int measure(int widthMeasureSpec) {
        int result = 0;
        //获取模式
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        //获取自定义view大小
        int size = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
//            Log.e("TAG1", size + "");
        } else {
            result = mRadius * 2;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
//        Log.e("TAG0", size + "");
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        Log.e("TAG", mCircleX + "  " + mCircleX + "  " + mRadius + "  " + textSize);
        //内圆
        canvas.drawCircle(mCircleX, mCircleY, mRadius, RoundPaint);
        //文字
        canvas.drawText(currentPercent + "%", (float) (mCircleX - textSize / 0.8), (float) (mCircleY + textSize / 2.5), textPaint);
        /*外接圆
         *startSweepValue 为-90，正北方
         *360 * currentPercent / TargetPercent将360分成100，取相应的大小
         *
          **/
        canvas.drawArc(rectF, -90, 360 * currentPercent / 100, false, RectPaint);
    }

    //设置目标度数
    public void setTargetPercent(float TargetPercent) {
        this.TargetPercent = TargetPercent;
    }


    //获取文字度数，旋转度数
    public synchronized float getCurrentPercent() {
        return currentPercent;
    }

    //设置文字度数，旋转度数
    public synchronized void setCurrentPercent(float currentPercent) {
        //判断如果文字度数大于目标度数，则将目标度数赋值给文字度数
        if (TargetPercent < currentPercent) {
            currentPercent = TargetPercent;
        }
        //如果文字度数小于或等于目标度数，则给文字度数赋值，并重新调用绘制方法
        if (TargetPercent >= currentPercent) {
            this.currentPercent = currentPercent;
            postInvalidate();
        }

    }


}
