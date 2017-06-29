package com.sourcey.intelligentmedicalsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sourcey.intelligentmedicalsystem.adapter.MyFragmentAdapter;
import com.sourcey.intelligentmedicalsystem.bean.Disease;
import com.sourcey.intelligentmedicalsystem.db.DiseaseDBDao;
import com.sourcey.intelligentmedicalsystem.fragment.DiseaseDetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 疾病详情activity
 */


public class DiseaseDetailActivity extends AppCompatActivity {
    public static final String TAG = "TabActivity";
//    public static final String []sTitle = new String[]{"ITEM FIRST","ITEM SECOND","ITEM THIRD"};
    public List<String>  sTitle=new ArrayList<>();
    public List<String> content=new ArrayList<>();
    private int tabNum=0;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private TextView tvIntroduction;
    private Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_detail);
        initView();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        tvIntroduction= (TextView) findViewById(R.id.introduction);
        toolbar= (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case android.R.id.home:
//
//                        finish();
//                        break;
//
//                    default:
//                        break;
//                }
//                return true;
//            }
//        });
        Intent intent=getIntent();
        String diseaseName=intent.getStringExtra("disease_name");
        toolbar.setTitle(diseaseName);

        DiseaseDBDao db=new DiseaseDBDao(this);
        List<Disease> diseases=db.findItemByName(diseaseName);

        if(diseases!=null) {
            tabNum=diseases.size();
            for (Disease disease1 : diseases) {
                String pre = disease1.getPre().trim();
                String obj = disease1.getObj();
//                Log.d("dansibingf",obj);
                mTabLayout.addTab(mTabLayout.newTab().setText(pre));
                sTitle.add(pre.replace("\\n",""));
                content.add(obj.replace("\\n","\n    ").trim());
//                if(!pre.equals("概述")){
//                    Log.d("dansibingf",obj);
//                    mTabLayout.addTab(mTabLayout.newTab().setText(pre));
//                    sTitle.add(pre);
//                    content.add(obj);
//                }else{
//                    tvIntroduction.setText(obj);
//                }

                Log.d("disease", disease1.getDisease());
            }
        }


//        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[0]));
//        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[1]));
//        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[2]));
      //  mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[3]));
      //  mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[4]));
      //  mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[5]));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                Log.i(TAG,"onTabSelected:"+tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabLayout.setupWithViewPager(mViewPager);
        List<Fragment> fragments = new ArrayList<>();
        for(int i=0;i<tabNum-1;i++){
            Fragment f= DiseaseDetailFragment.newInstance(content.get(i));

            fragments.add(f);

        }
//        fragments.add(DiseaseDetailFragment.newInstance());
//        fragments.add(SecondFragment.newInstance());
//        fragments.add(ThirdFragment.newInstance());


        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(),fragments, sTitle);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               Log.i(TAG,"select page:"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
