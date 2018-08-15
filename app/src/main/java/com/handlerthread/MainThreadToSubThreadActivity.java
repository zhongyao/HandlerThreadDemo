package com.handlerthread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 主线程发送消息到子线程
 * 
 * @author zhongyao
 * 
 */
public class MainThreadToSubThreadActivity extends Activity {
	private final int MSG_HELLO = 0;
	private Handler mHandler;

	private Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subthread);

		new CustomThread().start();// 新建并启动CustomThread实例

		btn = (Button) findViewById(R.id.btn);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String str = "hello";
				Log.d("yao", "MainThread is ready to send msg:" + str);
				mHandler.obtainMessage(MSG_HELLO, str).sendToTarget();
			}
		});

	}

	class CustomThread extends Thread {
		@Override
		public void run() {
			Looper.prepare();// 1、初始化Looper
			mHandler = new Handler() {// 2、绑定handler到CustomThread实例的Looper对象
				public void handleMessage(Message msg) {// 3、定义处理消息的方法
					switch (msg.what) {
					case MSG_HELLO:
						Log.d("yao", "CustomThread receive msg:"
								+ (String) msg.obj);
					}
				}
			};
			Looper.loop();// 4、启动消息循环
		}
	}
}
