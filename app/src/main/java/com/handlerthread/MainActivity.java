package com.handlerthread;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.handlerthread.intentservice.MyIntentService;
import com.handlerthread.util.Logger;

/**
 *
 * HandlerThread的运用实例类
 *
 * 参考blog：
 * http://blog.csdn.net/feiduclear_up/article/details/46840523#comments
 * http://blog.csdn.net/lmj623565791/article/details/47079737
 *
 * @author zhongyao
 */
public class MainActivity extends Activity implements OnClickListener {

    private TextView mTvServiceInfo;

    private HandlerThread mCheckMsgThread;
    private Handler mCheckMsgHandler = new Handler() {

    };
    private boolean isUpdateInfo;
    private static final int MSG_UPDATE_INFO = 0x110;

    // 与UI线程管理的handler
    private Handler mHandler = new Handler();

    private Button btnIntentService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTvServiceInfo = findViewById(R.id.textView);
        btnIntentService = findViewById(R.id.btnIntentService);

        btnIntentService.setOnClickListener(this);
        // 创建后台线程
        initBackgroundThread();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 开始查询
        isUpdateInfo = true;
        Log.d("yao", "onResume");
        mCheckMsgHandler.sendEmptyMessage(MSG_UPDATE_INFO);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 停止查询
        isUpdateInfo = false;
        mCheckMsgHandler.removeMessages(MSG_UPDATE_INFO);

    }

    /**
     * 将HandlerThread中创建的looper传递给Handler。
     *
     * 也就意味着该Handler收到Message后，程序在HandlerThread创建的线程中运行
     */
    private void initBackgroundThread() {
        mCheckMsgThread = new HandlerThread("check-message-coming");
        mCheckMsgThread.start();
        mCheckMsgHandler = new Handler(mCheckMsgThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                Logger.d("mCheckMsgHandler--handleMessage");
                //为false，可知在子线程中
                Logger.d("是否在主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
                checkForUpdate();
                if (isUpdateInfo) {
                    Logger.d("mCheckMsgHandler--sendEmptyMessageDelayed");
                    mCheckMsgHandler.sendEmptyMessage(MSG_UPDATE_INFO);
                }
            }
        };

    }

    /**
     * 模拟从服务器解析数据
     */
    private void checkForUpdate() {
        try {
            // 模拟耗时
            Thread.sleep(3000);

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Logger.d("mHandler--post");
                    //为true：可知在UI线程中
                    Logger.d("是否在主线程：" + (Looper.getMainLooper() == Looper.myLooper()));
                    String result = "实时更新中，当前大盘指数：<font color='red'>%d</font>";
                    result = String.format(result,
                        (int)(Math.random() * 3000 + 1000));
                    mTvServiceInfo.setText(Html.fromHtml(result));
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放资源
        mCheckMsgThread.quit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnIntentService:
                Intent intentService = new Intent(this, MyIntentService.class);

                intentService.putExtra("task_action", MyIntentService.SLEEP);
                startService(intentService);

                intentService.putExtra("task_action", MyIntentService.SLEEP2);
                startService(intentService);
                break;
            default:
                break;
        }

    }

}
