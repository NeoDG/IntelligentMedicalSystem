package com.sourcey.intelligentmedicalsystem;

/**
 * app欢迎页（暂时没加）
 */


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ViewFlipper;

public class FirstActivity extends Activity {
	
	private ViewFlipper allFlipper;
	private Button enter;
	private Handler handler = new Handler(){
    	@Override
    	public void handleMessage(Message msg) {
    		// TODO Auto-generated method stub
    		switch (msg.what) {
    		case 1:
    			
    			Intent intent=new Intent(FirstActivity.this,AIActivity.class);
				
				startActivity(intent);
				finish();
    			break;
    		}
    	}
    };
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		        WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_first);
		//enter=(Button)findViewById(R.id.enter_button);
		//allFlipper = (ViewFlipper) findViewById(R.id.allFlipper);
        new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				handler.sendEmptyMessage(1); //给UI主线程发送消息
			}
        }, 2000); //启动等待3秒钟
        
        
       
	}

}
