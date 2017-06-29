package com.sourcey.intelligentmedicalsystem.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.sourcey.intelligentmedicalsystem.db.Configuration;

/**
 * 从assets的diseaselist.txt获取所有疾病名字
 */


public class ConstactUtil {
	private static Context mcontext;

	/**
	 * 获取所有数据
	 * 
	 * @return
	 */
	public static Map<String,String> getAllCallRecords(Context context) {
		ConstactUtil.mcontext=context;
		Map<String,String> temp = new HashMap<String, String>();
		fillData(temp, "diseaselist.txt");


//		UserDBDao dbDao=new UserDBDao(MyApplication.getContext());
//		List<User> users=dbDao.findAll();
//		for(User user:users){
//			String id= Integer.toString(user.getId());
//			String username=user.getUsername();
//			String password=user.getPassword();
//			String email=user.getPassword();
//			String phone=user.getPhone();
//			temp.put(username,phone);
//		}

//		Cursor c = context.getContentResolver().query(
//				ContactsContract.Contacts.CONTENT_URI,
//				null,
//				null,
//				null,
//				ContactsContract.Contacts.DISPLAY_NAME
//						+ " COLLATE LOCALIZED ASC");
//		if (c.moveToFirst()) {
//			do {
//				// 获得联系人的ID号
//				String contactId = c.getString(c
//						.getColumnIndex(ContactsContract.Contacts._ID));
//				// 获得联系人姓名
//				String name = c
//						.getString(c
//								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//				// 查看该联系人有多少个电话号码。如果没有这返回值为0
//				int phoneCount = c
//						.getInt(c
//								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
//				String number=null;
//				if (phoneCount > 0) {
//					// 获得联系人的电话号码
//					Cursor phones = context.getContentResolver().query(
//							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//							null,
//							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
//									+ " = " + contactId, null, null);
//					if (phones.moveToFirst()) {
//						number = phones
//								.getString(phones
//								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//						}
//					phones.close();
//				}
//				temp.put(name, number);
//			} while (c.moveToNext());
//		}
//		c.close();
		return temp;
	}
	/**
	 * 读取(diseaselist.txt
	 * */
	private  static void fillData(Map<String,String> temp, String schemaName) {
		BufferedReader in = null;
		try {
			AssetManager assetManager=ConstactUtil.mcontext.getResources().getAssets();

			in = new BufferedReader(new InputStreamReader(assetManager.open(Configuration.DB_PATH + "/" + schemaName)));

			Log.d("path","路径:"+Configuration.DB_PATH + "/" + schemaName);
			String line;
			String buffer = "";
			while ((line = in.readLine()) != null) {
				String item=line.substring(line.indexOf(".htm")+4,line.length()).trim();
				temp.put(item,null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
