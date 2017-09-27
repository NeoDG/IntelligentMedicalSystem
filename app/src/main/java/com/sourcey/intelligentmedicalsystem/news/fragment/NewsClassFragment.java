package com.sourcey.intelligentmedicalsystem.news.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.sourcey.intelligentmedicalsystem.NewsActivity;
import com.sourcey.intelligentmedicalsystem.R;
import com.sourcey.intelligentmedicalsystem.bean.NewsGson;
import com.sourcey.intelligentmedicalsystem.httpUtils.PixUtil;
import com.sourcey.intelligentmedicalsystem.news.BaseFragent;
import com.sourcey.intelligentmedicalsystem.news.NewsDetailsActivity;
import com.sourcey.intelligentmedicalsystem.news.adapter.NewsAdapter;
import com.sourcey.intelligentmedicalsystem.news.contrant.NewsContract;
import com.sourcey.intelligentmedicalsystem.news.presenter.NewsPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class NewsClassFragment extends BaseFragent implements NewsContract.View{
    private NewsAdapter adapter;
    private NewsContract.Presenter mPresenter;

    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据
    private int type;
    private AppCompatActivity activity;


    public static NewsClassFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        NewsClassFragment fragment = new NewsClassFragment();
        bundle.putInt("type", 6);
        fragment.setArguments(bundle);
        return fragment;
    }



    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    private int pageIndex = 1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = 6;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (NewsActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_type, container, false);
        ButterKnife.bind(this, view);


        mPresenter=new NewsPresenter(this,getContext());

        recyclerView.setAdapter(adapter = new NewsAdapter(activity));
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        //添加边框
        SpaceDecoration itemDecoration = new SpaceDecoration((int) PixUtil.convertDpToPixel(8, getContext()));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);

        //更多加载
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                Log.e("更多","更多");
                mPresenter.loadData(type,pageIndex);
                pageIndex=pageIndex+1;
            }

            @Override
            public void onMoreClick() {

            }
        });
        //写刷新事件
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        pageIndex = 0;
                        mPresenter.loadData(type,pageIndex);
                    }
                }, 1000);
            }
        });

        //点击事件
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ArrayList<String> data = new ArrayList<String>();
                data.add(adapter.getAllData().get(position).getPicUrl());
                data.add(adapter.getAllData().get(position).getUrl());
                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
                //用Bundle携带数据
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("data", data);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        isViewPrepared = true;

        return view;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void returnData(List<NewsGson.NewslistBean> datas) {


        adapter.addAll(datas);

        Log.e("adapter",adapter.getAllData().size()+"");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void lazyFetchDataIfPrepared() {
        Log.e("data",type+""+isViewPrepared+"&&&"+hasFetchData);
        if (isViewPrepared && getUserVisibleHint() && !hasFetchData) {
            lazyFetchData();
            hasFetchData = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //视图销毁 数据设置为空
        hasFetchData=false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyFetchDataIfPrepared();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyFetchDataIfPrepared();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hasFetchData = false;
        isViewPrepared = false;
    }

    protected void lazyFetchData() {
        mPresenter.loadData(type,pageIndex);
        pageIndex=pageIndex+1;
    }
}
