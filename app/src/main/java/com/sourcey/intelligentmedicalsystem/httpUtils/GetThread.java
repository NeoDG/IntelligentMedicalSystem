package com.sourcey.intelligentmedicalsystem.httpUtils;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URLEncoder;

public class GetThread extends Thread {

	String content;
	HttpCallbackListener listener;
	String url;
	String username,password;
	int i;
	
	public GetThread(String content, HttpCallbackListener listner){
		this.content=content;
		this.listener=listner;
		this.url="http://59.69.101.2/med/ask/"+content;
		this.i=1;
	}

	public GetThread(String username,String password,HttpCallbackListener listner){
		this.username=username;
		this.password=password;
		this.url="http://59.69.101.2/med/login";
		this.listener=listner;
		this.i=2;

	}
	
	@Override
	public void run(){
		if(i==1)
		{
			content= URLEncoder.encode(content);
		}
		//String url="http://118.89.178.240:5000/ask/"+content;
		String result = null;
		
		try{
			HttpClient httpClient=new DefaultHttpClient();
			HttpGet httpGet=new HttpGet(url);
			if(i==2){
				httpGet.addHeader(BasicScheme.authenticate( new UsernamePasswordCredentials(username, password), "UTF-8", false));
				Log.e("url",url);

			}
			HttpResponse httpResponse=httpClient.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode()==200){
				HttpEntity entity=httpResponse.getEntity();
				result=EntityUtils.toString(entity,"utf-8");
				Log.e("r1",result);
			}else{
				HttpEntity entity=httpResponse.getEntity();
				result=EntityUtils.toString(entity,"utf-8");
				Log.e("r",result);
			}

			if(listener!=null){
				listener.onFinish(result);
			}
		}catch(Exception e){
			e.printStackTrace();
			if(listener!=null){
//				listener.onError(e);
			}
		}
	}
}
