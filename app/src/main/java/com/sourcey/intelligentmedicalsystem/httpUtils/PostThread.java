package com.sourcey.intelligentmedicalsystem.httpUtils;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PostThread extends Thread {
	
	String content;
	HttpCallbackListener listener;
	String url;
	String username,password;
	int i;
	
	public PostThread(String content, HttpCallbackListener listener, int i){
		this.content=content;
		this.listener=listener;
		this.i=i;
		this.url="http://59.69.101.2/med/upload";
	}

	public PostThread(String username,String password,HttpCallbackListener listener,int i){
		this.username=username;
		this.password=password;
		//this.phonenumber=phonenumber;
		this.listener=listener;
		this.i=i;
		this.url="http://59.69.101.2/med/signup";
	}

	@Override
	public void run(){
		//HttpClient httpClient=new DefaultHttpClient();
		//String url="http://118.89.178.240:5000/upload";
		String result = null;
		Log.e("url",url);
		try{
			//JSONObject jsonObj = new JSONObject();
			//jsonObj.put("user", content);
			List<NameValuePair> params=new ArrayList<NameValuePair>();
			if(i==1){
				params.add(new BasicNameValuePair("user",content));
			}else if(i==2){
				Log.e("i",i+"");


				params.add(new BasicNameValuePair("username",username));
				params.add(new BasicNameValuePair("password",password));
				//params.add(new BasicNameValuePair("phoneumber",phonenumber));
			}

			//StringEntity entity=new StringEntity(jsonObj.toString(),HTTP.UTF_8);
			//entity.setContentType("application/json");
			//Log.d("1",content);
			HttpPost httpPost = new HttpPost(url);
			Log.e("http",httpPost.toString());
			//httpPost.addHeader("charset", HTTP.UTF_8);
		    //httpPost.setHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
			
			//httpPost.setEntity(entity);
			httpPost.setEntity(new UrlEncodedFormEntity(params, org.apache.http.protocol.HTTP.UTF_8));
			HttpClient client = new DefaultHttpClient();
			
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 7000);
          
            client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);
			Log.e("11","11");
			HttpResponse response = client.execute(httpPost);
	
			Log.d("a",""+response.getStatusLine().getStatusCode());

			if(response.getStatusLine().getStatusCode()==200){
				HttpEntity httpEntity=response.getEntity();
				BufferedReader reader=new BufferedReader(
						new InputStreamReader(httpEntity.getContent()));
				String line = null;
				String jsonResult="";
				while((line = reader.readLine()) != null) {
					jsonResult += line;
				}
				//String jsonResult=reader.readLine();
				//Log.e("r",jsonResult);
				if (jsonResult != null) {
					  JSONObject json =new JSONObject(jsonResult);
					  Log.e("############",json.toString());
					  if (json != null) {
						  if(i==1){
							  result=json.getString("user");
						  }else if(i==2){
							  result=json.getString("status");
						  }

					  } 
			     }
				if(listener!=null){
					listener.onFinish(result);
				}
			}else{
				if(listener!=null){
					
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			Log.e("stacktrace",Log.getStackTraceString(e));
			if(listener!=null){
				listener.onError(e);

			}
		}
	}

}
