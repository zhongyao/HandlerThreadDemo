
package com.handlerthread;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class AsytaskActivity extends Activity {

	private ImageView iv;
	private long timecurrentTimeMillis, timecurrentTimeMillis2;
	private long time;
	private String url = "http://g.hiphotos.baidu.com/image/pic/item/060828381f30e9244e3f894a49086e061d95f736.jpg"; 
	final OkHttpClient client = new OkHttpClient();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asynctask);

		initView();

//		new MyTask().execute();
	}
	private void initView() {
		iv = (ImageView) findViewById(R.id.iv);
	}
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(MainActivity.YAO,"onResume");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void execute() throws Exception{
       
        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
        	Log.d(MainActivity.YAO, "成功");
        } else {
            throw new IOException("Unexpected code " + response);
        }
        
//        client.newCall(request).enqueue(new Callback() {
//
//			@Override
//			public void onFailure(Request arg0, IOException arg1) {
//				
//			}
//
//			@Override
//			public void onResponse(Response response) throws IOException {
//				if(response.isSuccessful()){
//                    System.out.println(response.code());
//                    Log.d(MainActivity.YAO, "成功");
//                    System.out.println(response.body().string());
//                }
//							
//			}
//        });
    }

	private class MyTask extends AsyncTask<URL, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(URL... params) {

			try {
				timecurrentTimeMillis = System.currentTimeMillis();
				URL url = new URL(
						"http://g.hiphotos.baidu.com/image/pic/item/060828381f30e9244e3f894a49086e061d95f736.jpg");

				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(5 * 1000);
				conn.setReadTimeout(5 * 1000);
				conn.setRequestMethod("GET");
				conn.setUseCaches(true);
				if (conn.getResponseCode() == 200) {
					InputStream is = conn.getInputStream();

					timecurrentTimeMillis2 = System.currentTimeMillis();
					time = timecurrentTimeMillis2 - timecurrentTimeMillis;
					Log.d("yao", time + "");// 请求时间127ms
					return BitmapFactory.decodeStream(is);
				} else {
					Log.d("yao", "server异常...");
				}

//				timecurrentTimeMillis = System.currentTimeMillis();
//				HttpGet get = new HttpGet(
//						"http://g.hiphotos.baidu.com/image/pic/item/060828381f30e9244e3f894a49086e061d95f736.jpg");
//				HttpClient client = new DefaultHttpClient();
//				HttpResponse response = client.execute(get);
//				InputStream is = response.getEntity().getContent();
//				timecurrentTimeMillis2 = System.currentTimeMillis();
//				time = timecurrentTimeMillis2 - timecurrentTimeMillis;
//				Log.d("yao", time + "");// 请求时间183ms
//				return BitmapFactory.decodeStream(is);
				
				//可知使用HttpUrlConnection请求比HttpClient要快
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap);
			if (bitmap != null) {
				Log.d("yao", "onPostExecute执行ִ" + bitmap);
				iv.setImageBitmap(bitmap);
			}

		}

	}

}
