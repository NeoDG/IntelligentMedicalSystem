package com.sourcey.intelligentmedicalsystem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.sourcey.intelligentmedicalsystem.adapter.MsgAdapter;
import com.sourcey.intelligentmedicalsystem.bean.ListViewItemData;
import com.sourcey.intelligentmedicalsystem.bean.Msg;
import com.sourcey.intelligentmedicalsystem.bean.PopupItem;
import com.sourcey.intelligentmedicalsystem.bean.Record;
import com.sourcey.intelligentmedicalsystem.db.RecordDBDao;
import com.sourcey.intelligentmedicalsystem.httpUtils.GetThread;
import com.sourcey.intelligentmedicalsystem.httpUtils.HttpCallbackListener;
import com.sourcey.intelligentmedicalsystem.httpUtils.PostThread;
import com.sourcey.intelligentmedicalsystem.utils.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 问答activity
 */

public class AIActivity extends Activity implements View.OnClickListener{

	public static int index=0;



	/** 点击复制 的按钮 */
	private TextView textView_copy;
	/** 复制文字跳出的popupwindow */
	private PopupWindow popupWindow;
	/** 设置popup的显示位置 */
	private int[] location;
	/**popup的宽*/
	private int popupWidth;
	/**popup的高*/
	private int popupHeight;

	private ListView msgListView;

	private EditText inputText;

	private Button send;
	
	private Button picture;
	
	private MsgAdapter adapter;

	private List<ListViewItemData> data = new ArrayList<>();
	
	public static final int SHOW_RESPONSE = 0; 

	public static final int SHOW_RESPONSE_MEDINCINE=2;

	public static final int SHOW_RESPONSE_ELSE=3;


	private PopupMenu popupMenu;

	private List<String> popupMenuItemList = new ArrayList<>();

	private int popupPosition=-1;

	private RecordDBDao rdb;
	//private WebView webView;
	
	//private FrameLayout webContainer;
	private Handler handler=new Handler(){
		public void handleMessage(Message msg){
			switch (msg.what){
				case SHOW_RESPONSE:
					String response=(String)msg.obj;


					adapter.notifyDataSetChanged();
					msgListView.setSelection(data.size());
					inputText.setText("");
					Msg msg1=new Msg(response,Msg.TYPE_RECEIVED);
					data.add(new ListViewItemData<>(msg1,0));
					break;
				case SHOW_RESPONSE_MEDINCINE:
					response=(String)msg.obj;


					adapter.notifyDataSetChanged();
					msgListView.setSelection(data.size());
					inputText.setText("");
					msg1=new Msg(response,Msg.TYPE_MEDICINE);
					data.add(new ListViewItemData<>(msg1,0));
					break;
				case SHOW_RESPONSE_ELSE:
					response=(String)msg.obj;


					adapter.notifyDataSetChanged();
					msgListView.setSelection(data.size());
					inputText.setText("");
					msg1=new Msg(response,Msg.TYPE_ELSE);
					data.add(new ListViewItemData<>(msg1,0));
					break;
				default:
					break;




			}
			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_ai);

		//webContainer=new FrameLayout(getApplicationContext());
		//webContainer=(FrameLayout)findViewById(R.id.web_container);
		//webView=new WebView(getApplicationContext());
		adapter = new MsgAdapter(AIActivity.this, data);
		inputText = (EditText) findViewById(R.id.input_text);
		send = (Button) findViewById(R.id.send);
		picture=(Button)findViewById(R.id.picture);
		popupMenu=new PopupMenu(this,findViewById(R.id.picture));//弹出菜单
		Toolbar tb = (Toolbar) findViewById(R.id.toolbarAI);
		tb.setTitle("");
		msgListView = (ListView) findViewById(R.id.msg_list_view);
		msgListView.setAdapter(adapter);
		
		Msg msg = new Msg("您好，我是智能医疗助手！您有什么健康问题吗？可以向我提问哟！（如：沙眼应该用什么药？)当然您也可以发送舌苔照片来进行辅助诊断哟~", Msg.TYPE_RECEIVED);
		data.add(new ListViewItemData(msg,0));
		adapter.notifyDataSetChanged();//notifyDataSetChanged()可以在修改适配器绑定的数组后，不用重新刷新Activity，通知Activity更新ListView。
		msgListView.setSelection(data.size());//让ListView定位到指定msgList.size()的位置



		//popupwindow
		LayoutInflater inflater = (LayoutInflater)
				getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View vPopupWindow=inflater.inflate(R.layout.popupwindow, null, false);
//		 new PopupWindow(vPopupWindow,300,300,true);
		popupWindow = new PopupWindow(vPopupWindow,300,300,true); //new PopupWindow(this);
		popupWindow.setWidth(LayoutParams.FILL_PARENT);
		popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
		//popupWindow.setContentView(LayoutInflater.from(this).inflate(R.layout.popupwindow, null));
		popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);

		popupWindow.setAnimationStyle(R.style.popwin_anim_style);

		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener(){

			@Override
			public void onDismiss() {

				data.remove(popupPosition);
				adapter.setType(1);
				adapter.notifyDataSetChanged();
				msgListView.setSelection(data.size());
				inputText.setText("");


			}
		});
		Button btn_add= (Button) vPopupWindow.findViewById(R.id.btn_add);
		Button btn_delete=(Button) vPopupWindow.findViewById(R.id.btn_delete);
		btn_add.setOnClickListener(this);
		btn_delete.setOnClickListener(this);

//		//长按弹出PopupList
//		popupMenuItemList.add(getString(R.string.copy));
//		popupMenuItemList.add(getString(R.string.delete));
//		popupMenuItemList.add(getString(R.string.share));
//		popupMenuItemList.add(getString(R.string.more));
//		PopupList popupList = new PopupList(this);
//		popupList.bind(msgListView, popupMenuItemList, new PopupList.PopupListListener() {
//			@Override
//			public boolean showPopupList(View adapterView, View contextView, int contextPosition) {
//				return true;
//			}
//
//			@Override
//			public void onPopupListClick(View contextView, int contextPosition, int position) {
//				Toast.makeText(AIActivity.this, contextPosition + "," + position, Toast.LENGTH_SHORT).show();
//			}
//		});
//		msgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				Toast.makeText(AIActivity.this, "onItemClicked:" + position, Toast.LENGTH_SHORT).show();
//			}
//		});


		//设置长按监听器

//		msgListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//			public void onCreateContextMenu(ContextMenu menu, View v,
//											ContextMenu.ContextMenuInfo menuInfo) {
//				menu.setHeaderTitle("选择操作");
//				menu.add(0, 0, 0, "更新该条");
//				menu.add(0, 1, 0, "删除该条");
//			}
//		});

		//popupwindow初始化
//		mScreenHeight = getScreenHeight();
//
//
//		LayoutInflater lf = (LayoutInflater) AIActivity.this
//				.getSystemService(LAYOUT_INFLATER_SERVICE);
//		contentView = lf.inflate(R.layout.popupwindow, null);
////		showinfo= (TextView)contentView.findViewById(R.id.show);
//		mPopupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT,
//				LayoutParams.WRAP_CONTENT);
//		mPopupWindow.setFocusable(true);
//		mPopupWindow.setOutsideTouchable(true);
//		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
//
		msgListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
										   int position, long id) {
//				Toast.makeText(AIActivity.this, "你长按了第" + position + "项",
//						Toast.LENGTH_SHORT).show();
				// 获取被点击项所在位置
//				WindowManager.LayoutParams params = getWindow().getAttributes();
//				if (params.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
//// 隐藏软键盘
//					getWindow().setSoftInputMode(
//							WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//					params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN;
//				}
				//popupitem
				PopupItem popupItem=new PopupItem("111","111");
				data.add(position+1,new ListViewItemData<>(popupItem,1));
				adapter.setType(1);
				adapter.notifyDataSetChanged();
				msgListView.setSelection(data.size());
				inputText.setText("");

				//popupWindow
				popupPosition=position+1;
				int[] a = new int[2];
				view.getLocationOnScreen(a);


				// 在指定位置显示弹窗, 以底部中间为基准点
				popupWindow.showAsDropDown(view,0,0);


				return false;
			}
		});

		popupMenu.getMenuInflater().inflate(R.menu.main, popupMenu.getMenu());
		popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
			public boolean onMenuItemClick(MenuItem item){
				switch(item.getItemId()){
				case R.id.camera_item:
					
					Intent intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	                intentPhoto.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
	                File out = new File(getPhotopath());
	                Uri uri = Uri.fromFile(out);
	                intentPhoto.putExtra(MediaStore.EXTRA_OUTPUT, uri);
	                
	                startActivityForResult(intentPhoto, 1); 
	               
					break;
				case R.id.album_item:
					
					Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			        intent.setType("image/*");  
			        intent.putExtra("crop", true);  
			        intent.putExtra("return-data", true);  
			        startActivityForResult(intent, 2);  
					
					break;
				default:
					break;
				}
				return true;
			}
		});
		
		picture.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				
				popupMenu.show();
			}
		});
		
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String content = inputText.getText().toString();
				
				if (!"".equals(content)) {
					
					Msg msg = new Msg(content, Msg.TYPE_SENT);
					data.add(new ListViewItemData(msg,0));
					adapter.setType(0);
					adapter.notifyDataSetChanged();
					msgListView.setSelection(data.size());
					inputText.setText("");
					GetThread getThread=new GetThread(content,new HttpCallbackListener(){
							@Override
							public void onFinish(String response) throws JSONException {
								// TODO Auto-generated method stub
								JSONObject json=new JSONObject(response);
								if(json!=null){
									Message message =new Message();
									message.what=SHOW_RESPONSE;
									message.obj=json.getString("answer");
									switch (json.getString("type")){
										case "":
											message.what=SHOW_RESPONSE_ELSE;
											break;
										case "相关药物":
											message.what=SHOW_RESPONSE_MEDINCINE;
											break;
										default:
											message.what=SHOW_RESPONSE;
											break;
									}
									handler.sendMessage(message);
								}

							}

							@Override
							public void onError(Exception e) {
								// TODO Auto-generated method stub
								e.printStackTrace();
								Message message =new Message();
								message.what=SHOW_RESPONSE_ELSE;
								message.obj="发送失败！";
								handler.sendMessage(message);
							}
					});
					getThread.start();
					
					
				}
			}
		});


	}

	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data1) {
        super.onActivityResult(requestCode, resultCode, data1);
        Bitmap bitmap = null;
        
        if( resultCode == Activity.RESULT_OK) {
        	if(requestCode==1){
        		 BitmapFactory.Options options = new BitmapFactory.Options();
                 options.inJustDecodeBounds = true; 
                 bitmap = BitmapFactory.decodeFile(getPhotopath());
                 options.inJustDecodeBounds = false;   
        	}else if(requestCode==2){
        		 Uri uri = data1.getData();
        		 try {
					bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  
        	}   
        	
        	int width = bitmap.getWidth();
    		int height = bitmap.getHeight();
    		float scaleWidth = ((float)540)/width;
    		float scaleHeight = scaleWidth;
    		Matrix matrix = new Matrix();
    		matrix.postScale(scaleWidth, scaleHeight);
    	    bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        	
    	    
        	ImageSpan imgSpan = new ImageSpan(this, bitmap);
            SpannableString spanString = new SpannableString("icon");
            spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            
           
            Msg msg = new Msg(spanString, Msg.TYPE_PIC);
			data.add(new ListViewItemData<>(msg,0));
			adapter.setType(1);
			adapter.notifyDataSetChanged();
			msgListView.setSelection(data.size());
			inputText.setText("");
            
            
        	 bitmap=compressBitmap(bitmap);
             String content=bitmapToBase64(bitmap);
             
             PostThread postThread=new PostThread(content,new HttpCallbackListener(){
             	public void onFinish(String response){
             		String s="";
             		Message message =new Message();
					message.what=SHOW_RESPONSE_ELSE;
					if(response.equals("0")){
						s="您的舌苔状态正常哦~";
					}else if(response.equals("1")){
						s="您属于黄苔，黄苔主里证、热证。";
					}else if(response.equals("2")){
						s="您属于白苔，白苔一般属肺，主表证、寒证，但临床上也有里证、热证而见白苔者。";
					}else if(response.equals("3")){
						s="您是灰黑苔，多主热证，亦有寒湿或虚寒证。灰黑苔多见于疾病比较严重的阶段";
					}
					message.obj=s;	
					handler.sendMessage(message);
             	}
             	public void onError(Exception e){
             		e.printStackTrace();
             		Log.d("b",""+e.getStackTrace());
						Message message =new Message();
						message.what=SHOW_RESPONSE_ELSE;
						message.obj="失败！";
						handler.sendMessage(message);
             	}
             },1);
             postThread.start();
             }  
    }  
	private String getPhotopath() {
        // 照片全路径  
        String fileName = "";
        // 文件夹路径  
        String pathUrl = Environment.getExternalStorageDirectory()+"/mymy/";
        String imageName = "imageTest.jpg";
        File file = new File(pathUrl);
        file.mkdirs();// 创建文件夹  
        fileName = pathUrl + imageName;  
        return fileName;  
    }  
	
	public static String bitmapToBase64(Bitmap bitmap) {
		  
	    String result = null;
	    ByteArrayOutputStream baos = null;
	    try {  
	        if (bitmap != null) {  
	            baos = new ByteArrayOutputStream();
	            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	  
	            baos.flush();  
	            baos.close();  
	  
	            byte[] bitmapBytes = baos.toByteArray();  
	            result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
	        }  
	    } catch (IOException e) {
	        e.printStackTrace();  
	    } finally {  
	        try {  
	            if (baos != null) {  
	                baos.flush();  
	                baos.close();  
	            }  
	        } catch (IOException e) {
	            e.printStackTrace();  
	        }  
	    }  
	    return result;  
	}  

	public Bitmap compressBitmap(Bitmap bitmap){
		
		
		
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while ( baos.toByteArray().length / 1024>300) {        
            baos.reset();
            options -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bit = BitmapFactory.decodeStream(isBm, null, null);
	    return bit;
	}

	/**
	 * 点击空白的地方关闭软键盘
	 * @param ev
	 * @return
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}

			return super.dispatchTouchEvent(ev);
		}

		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev)) {
			return true;
		}

		return onTouchEvent(ev);
	}

	public boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = {0, 0};
			//获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				//使EditText触发一次失去焦点事件
				//v.setFocusable(false);
//                v.setFocusable(true); //这里不需要是因为下面一句代码会同时实现这个功能
				//v.setFocusableInTouchMode(true);
				//v.requestFocus();
				v.clearFocus();
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取屏幕高度像素
	 *
	 * @return
	 */
	private int getScreenHeight() {
		// 获取屏幕实际像素
		DisplayMetrics displayMetrics = new DisplayMetrics();
		Display display = AIActivity.this.getWindowManager()
				.getDefaultDisplay();
		display.getMetrics(displayMetrics);
		return displayMetrics.heightPixels;
	}


	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.btn_add:
				//如果用户已经登陆，则修改record数据库;否则跳到登陆界面
				String username=getSharedPreferences("loginToken",0).getString("username",null);
				String token=getSharedPreferences("loginToken",0).getString("token",null);

				if(token!=null&&username!=null){
					int userId=MyApplication.getUserId();
					ListViewItemData  itemData=data.get(popupPosition-1);
					if(itemData.getDataType()==0){
						Msg msg= (Msg) itemData.getT();
						String content=msg.getContent();
						RecordDBDao rdb=new RecordDBDao(getApplicationContext());
						List<Record> records=rdb.findByitem(userId);
						int index=AIActivity.index;
						switch(msg.getType()){
							case Msg.TYPE_PIC:
								break;
							case Msg.TYPE_RECEIVED:
								if(records==null||records.size()<AIActivity.index){
									Record record=new Record();
									record.setUserid(userId);
									record.setSolution(content);
									Date now = new Date();
									long time=now.getTime();
									record.setTime(time);
									rdb.add(record);
								}else{
									Record record=records.get(0);
									record.setSolution(record.getSolution()+"\n"+content);
									Date now = new Date();
									long time=now.getTime();
									record.setTime(time);
									rdb.update(record.getId(),record);
								}
								popupWindow.dismiss();

								break;
							case Msg.TYPE_MEDICINE:
								if(records==null||records.size()<AIActivity.index){
									Record record=new Record();
									record.setUserid(userId);
									record.setMedicine(content);
									Date now = new Date();
									long time=now.getTime();
									record.setTime(time);
									rdb.add(record);
								}else{
									Record record=records.get(0);
									record.setMedicine(record.getMedicine()+"\n"+content);
									Date now = new Date();
									long time=now.getTime();
									record.setTime(time);
									rdb.update(record.getId(),record);
								}
								popupWindow.dismiss();
								break;
							case Msg.TYPE_SENT:
//								Log.d("xinxi",content);


								if(records==null||records.size()<AIActivity.index){
									Record record=new Record();
									record.setUserid(userId);
									record.setQuest(content);
									Date now = new Date();
									long time=now.getTime();
									record.setTime(time);
									rdb.add(record);
								}else{
									Record record=records.get(0);
									record.setQuest(record.getQuest()+"\n"+content);
									Date now = new Date();
									long time=now.getTime();
									record.setTime(time);
									rdb.update(record.getId(),record);
								}
								popupWindow.dismiss();
								break;
							default:
								break;
						}
					}
				}else{
					Toast.makeText(getBaseContext(), "您还没登陆，请先登陆", Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.btn_delete:

				data.remove(popupPosition-1);
				popupPosition--;
				adapter.notifyDataSetChanged();//notifyDataSetChanged()可以在修改适配器绑定的数组后，不用重新刷新Activity，通知Activity更新ListView。
				msgListView.setSelection(data.size());//让ListView定位到指定msgList.size()的位置
				popupWindow.dismiss();
				break;
			default:
				break;
		}
	}
}