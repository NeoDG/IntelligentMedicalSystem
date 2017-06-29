package com.sourcey.intelligentmedicalsystem.httpUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;

public class GetThread extends Thread {

	String content;
	HttpCallbackListener listener;
	
	public GetThread(String content, HttpCallbackListener listner){
		this.content=content;
		this.listener=listner;
	}
	
	@Override
	public void run(){
		content= URLEncoder.encode(content);
		String url="http://118.89.178.240:5000/ask/"+content;
		String result = null;
		
		try{
			HttpClient httpClient=new DefaultHttpClient();
			HttpGet httpGet=new HttpGet(url);
			HttpResponse httpResponse=httpClient.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode()==200){
				HttpEntity entity=httpResponse.getEntity();
				result=EntityUtils.toString(entity,"utf-8");
			}else{
				result="发送失败";
			}
			if(listener!=null){
				listener.onFinish(result);
			}
		}catch(Exception e){
			e.printStackTrace();
			if(listener!=null){
				listener.onError(e);
			}
		}
	}
}
