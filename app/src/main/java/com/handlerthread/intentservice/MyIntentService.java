package com.handlerthread.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author zhongyao
 * @date 2018/8/15
 *
 * 1、IntentService用于执行后台耗时操作，当任务执行完成后，它会自动停止。
 *
 * 2、它的优先级比线程高很多，所以IntentService比较适合一些高优先级的后台任务。
 *
 * 3、在实现上，IntentService封装了HandlerThread和Handler。
 *
 * 4、IntentService按顺序执行后台任务。
 */

public class MyIntentService extends IntentService {
    public static final String SLEEP = "sleep";
    public static final String SLEEP2 = "sleep2";
    public static final String YAO = "yao";

    public MyIntentService() {
        super("intentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(YAO, "IntentService-->onCreate");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        //Log.d(YAO, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        boolean b = Looper.getMainLooper() == Looper.myLooper();
        //为false，可知在子线程中
        //Log.v(YAO, String.valueOf(b));
        if (!b) {
            Log.d(YAO, "运行在子线程中...");
        }

        String action = intent.getStringExtra("task_action");
        if (action.equals(SLEEP)) {
            Log.d(YAO, "IntentService-->onHandleIntent-->do sleep 3s--Task1");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (action.equals(SLEEP2)){
            Log.d(YAO,"IntentService-->onHandleIntent-->do sleep 3s--Task2");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(YAO, "IntentService-->onDestroy");
    }
}
