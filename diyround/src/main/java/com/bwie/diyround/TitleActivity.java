package com.bwie.diyround;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 作者：王庆
 * 时间：2017/12/3
 */

public class TitleActivity extends RelativeLayout {


    private ImageView img;
    private TextView textView;

    public TitleActivity(Context context) {
        super(context);
    }

    public TitleActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.title, this);
        img = view.findViewById(R.id.img);
        textView = view.findViewById(R.id.sub);
    }

    public TitleActivity(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImg(OnClickListener listener) {
        img.setOnClickListener(listener);
    }

    public void setTextView(OnClickListener listener) {
        textView.setOnClickListener(listener);
    }
}
