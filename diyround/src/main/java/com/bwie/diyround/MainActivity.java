package com.bwie.diyround;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MyRound myRound;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //获取当前文字度数
            float Percent = myRound.getCurrentPercent();
            Percent += 1;
            if (Percent > 100) {
                Percent = 0;
                Log.e("TAG", "到100了！哈哈");
            }
            //设置文字度数
            myRound.setCurrentPercent(Percent);
            handler.sendEmptyMessageDelayed(1, 100);
        }
    };
    private TitleActivity title;
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myRound = (MyRound) findViewById(R.id.myRound);
        title = (TitleActivity) findViewById(R.id.title);
        bt = (Button) findViewById(R.id.bt);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置目标度数
                myRound.setTargetPercent(100);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessageDelayed(1, 100);
                    }
                }).start();
                myRound.invalidate();
            }
        });


        title.setImg(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你点击了图片！", Toast.LENGTH_SHORT).show();
            }
        });
        title.setTextView(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你点击了提交！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeMessages(1);
    }
}
