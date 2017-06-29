package com.sourcey.intelligentmedicalsystem.httpUtils;

public interface HttpCallbackListener {

	void onFinish(String response);
	void onError(Exception e);
}
