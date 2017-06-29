package com.sourcey.intelligentmedicalsystem.bean;

import android.text.SpannableString;
/**
问答界面的item消息实体
 */

public class Msg {

	public static final int TYPE_RECEIVED = 0;

	public static final int TYPE_SENT = 1;
	
	public static final int TYPE_PIC=-1;

	private String content;

	private int type;
	
	private SpannableString spanString;

	public Msg(String content, int type) {
		this.content = content;
		this.type = type;
	}
	public Msg(SpannableString spanString, int type){
		this.spanString=spanString;
		this.type=type;
	}


	public SpannableString getPicture(){
		return spanString;
	}


	public String getContent() {
		return content;
	}

	public int getType() {
		return type;
	}

}
