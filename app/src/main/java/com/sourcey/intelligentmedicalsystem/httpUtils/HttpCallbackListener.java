package com.sourcey.intelligentmedicalsystem.httpUtils;

import org.json.JSONException;

public interface HttpCallbackListener {

	void onFinish(String response) throws JSONException;
	void onError(Exception e);
}
