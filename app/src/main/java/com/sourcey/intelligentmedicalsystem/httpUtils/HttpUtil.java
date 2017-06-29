package com.sourcey.intelligentmedicalsystem.httpUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


public class HttpUtil {
	
	public static final int SHOW_RESPONSE = 0; 
	

	
	public static void sendRequest(final String content, final HttpCallbackListener listener){
		new Thread(new Runnable(){
			@Override
			public void run(){
				
				try{
					HttpClient httpClient=new DefaultHttpClient();
					HttpGet httpGet=new HttpGet(content);
					HttpResponse httpResponse=httpClient.execute(httpGet);
					if(httpResponse.getStatusLine().getStatusCode()==200){
						HttpEntity entity=httpResponse.getEntity();
						String response=EntityUtils.toString(entity,"utf-8");
						
						if(listener!=null){
							listener.onFinish(response);
						}
					}
				}catch(Exception e){
					if(listener!=null){
						listener.onError(e);
					}
				}
			}
		}).start();
	}
	 
	
}
