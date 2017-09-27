package com.sourcey.intelligentmedicalsystem.news.presenter;


import android.content.Context;


import com.sourcey.intelligentmedicalsystem.bean.NewsGson;
import com.sourcey.intelligentmedicalsystem.httpUtils.NetWorkUtil;
import com.sourcey.intelligentmedicalsystem.news.contrant.NewsContract;
import com.sourcey.intelligentmedicalsystem.news.model.NewsModel;

import java.util.List;



public class NewsPresenter implements NewsContract.Presenter, NewsContract.OnLoadFirstDataListener {
    private NewsContract.View view;
    private NewsContract.Model model;
    private Context context;

    public NewsPresenter(NewsContract.View view,Context context) {
        this.view = view;
        this.model = new NewsModel();
        this.context=context;
    }



    @Override
    public void loadData(int type, int page) {
        model.loadData(type,this,page);
    }




    @Override
    public void onSuccess(List<NewsGson.NewslistBean> list) {
           view.returnData(list);
    }

    @Override
    public void onFailure(String str, Throwable e) {
        NetWorkUtil.checkNetworkState(context);
    }

}
