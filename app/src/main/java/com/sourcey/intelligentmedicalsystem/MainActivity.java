package com.sourcey.intelligentmedicalsystem;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sourcey.intelligentmedicalsystem.adapter.MyPagerAdapter;
import com.sourcey.intelligentmedicalsystem.db.MyDBOpenHelper;
import com.sourcey.intelligentmedicalsystem.db.RecordDBDao;
import com.sourcey.intelligentmedicalsystem.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面activity
 */

public class MainActivity extends Activity implements View.OnClickListener{

    private ViewPager mViewpagerFirst;
    private List<View> mViews;
    private LayoutInflater mInflater;
    private List<ImageView> mDots;
    private MyPagerAdapter myPagerAdapter;
    private Button _button_message;
    private Button _button_disease;
    private Button _button_user;
    private Button _button_ai;

    private MyDBOpenHelper dbOpenHelper;

    private int oldPosition;//记录上一个点的位置。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
//        DiseaseDBDao db=new DiseaseDBDao(this);
//        List<Disease> diseases=db.findAll();
//        for(Disease disease1:diseases){
//            Log.d("disease",disease1.getDisease());
//        }
        initViewpager();
        init();

    }

    private void init() {
        _button_message=(Button)findViewById(R.id.btn_message);
        _button_disease=(Button)findViewById(R.id.btn_disease);
        _button_user=(Button)findViewById(R.id.btn_user);
        _button_ai=(Button)findViewById(R.id.btn_ai);
        _button_message.setOnClickListener(this);
        _button_disease.setOnClickListener(this);
        _button_user.setOnClickListener(this);
        _button_ai.setOnClickListener(this);
        dbOpenHelper=new MyDBOpenHelper(getApplicationContext());

    }

    private void initViewpager() {
        mViewpagerFirst = (ViewPager) findViewById(R.id.viewpager_show);
        mInflater = getLayoutInflater();

        //初始化页面
        mViews = new ArrayList<View>();
        View view1 = mInflater.inflate(R.layout.viewpager_item_one, null);
        View view2 = mInflater.inflate(R.layout.viewpager_item_two, null);
        View view3 = mInflater.inflate(R.layout.viewpager_item_three, null);
//        View view1 = mInflater.inflate(R.layout.city1, null);
//        View view2 = mInflater.inflate(R.layout.city2, null);
       // View view3 = mInflater.inflate(R.layout.city3, null);

//        Button btn_1= (Button) view1.findViewById(R.id.btn_1);
//        btn_1.setText("ee");

        mViews.add(view1);
        mViews.add(view2);
        mViews.add(view3);


        mDots = new ArrayList<ImageView>();
        ImageView dotFirst = (ImageView) findViewById(R.id.dot_first);
        ImageView dotFSecond = (ImageView) findViewById(R.id.dot_second);
        ImageView dotThrid = (ImageView) findViewById(R.id.dot_thrid);
        mDots.add(dotFirst);
        mDots.add(dotFSecond);
        mDots.add(dotThrid);
        oldPosition = 0;
        mDots.get(oldPosition).setImageResource(R.drawable.dot_focused);

        myPagerAdapter = new MyPagerAdapter(mViews);
        mViewpagerFirst.setAdapter(myPagerAdapter);
        mViewpagerFirst.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {

                mDots.get(oldPosition).setImageResource(R.drawable.dot_normal);
                mDots.get(position).setImageResource(R.drawable.dot_focused);
                oldPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()){
            case R.id.btn_message:
                intent=new Intent(MainActivity.this,NewsActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_disease:
                intent=new Intent(MainActivity.this,DiseaseListActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_user:
               Boolean flag= MyApplication.getLoginFlag();
                if(MyApplication.getLoginFlag()) {
                    intent = new Intent(MainActivity.this, RecordActivity.class);
                    startActivity(intent);
                }else {
//                    intent = new Intent(MainActivity.this, RecordActivity.class);
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.btn_ai:
                if(AIActivity.index==0){
                    RecordDBDao rdb=new RecordDBDao(getApplicationContext());
                    AIActivity.index=rdb.getCountByUserId(MyApplication.getUserId());

                }
                AIActivity.index++;

                intent=new Intent(MainActivity.this,AIActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
