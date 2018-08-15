package com.handlerthread;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SubThreaduseHandlerActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Log.d("yao", "Runnable开启线程方式");
			}
		}).start();
		
		new Thread(){
			@Override
			public void run() {
				super.run();
				Log.d("yao", "thread开启线程方式");
			}
		}.start();

		
		/*new Thread(){
			
			public Handler handler;
			public void run() {
				Looper.prepare();
				
				handler = new Handler(){
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
					}
				};
				
			};
		};
		
		new Thread(){
			public void run() {
//				handler
			};
		};*/
		
	}
}
