package com.sourcey.intelligentmedicalsystem;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sourcey.intelligentmedicalsystem.adapter.CoiAdapter;
import com.sourcey.intelligentmedicalsystem.adapter.SpaceItemDecoration;
import com.sourcey.intelligentmedicalsystem.bean.Record;
import com.sourcey.intelligentmedicalsystem.db.RecordDBDao;
import com.sourcey.intelligentmedicalsystem.utils.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 病例activity
 */

public class RecordActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private CoiAdapter coiAdapter;
    private String[][] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        initdata();
    }

    void initdata() {
        RecordDBDao db=new RecordDBDao(getApplicationContext());
        List<Record> records=db.findByitem(MyApplication.getUserId());
        SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        int rnum=0;
        if(records!=null){
            rnum=records.size();
        }

        data = new String[rnum][4];
        for(int i = 0; i< rnum; i++) {
            Record record=records.get(i);
            long time=record.getTime();
            data[i][0] = "问诊时间："+sdf.format(new Date(time))+"----No." + (i+1);
            data[i][1] = "主诉病情："+record.getQuest();
            data[i][2] = "诊断结果："+record.getSolution();
            data[i][3] = "用药意见："+record.getMedicine();
        }
        coiAdapter = new CoiAdapter(this, data);
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerViewCoi);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(coiAdapter);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(40));
    }
}


//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//
//import com.sourcey.materiallogindemo.adapter.RecordAdapter;
//import com.sourcey.materiallogindemo.bean.Record;
//import com.sourcey.materiallogindemo.db.RecordDBDao;
//import com.sourcey.materiallogindemo.utils.MyApplication;
//
//import java.util.ArrayList;
//import java.util.List;

//public class RecordActivity extends AppCompatActivity {
//    private ListView listView;
//    private List<Record> data = new ArrayList();
////    private List<String> data=new ArrayList<>();
//    RecordAdapter recordAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_record);
//        initRecords();
//        recordAdapter=new RecordAdapter(getApplicationContext(),R.layout.record_item,data);
////        ArrayAdapter<String> adapter=new ArrayAdapter<String>(RecordActivity.this,R.layout.simpleitem,data);
//        listView= (ListView) findViewById(R.id.record_listview);
//        listView.setAdapter(recordAdapter);
//
//    }
//
//    private void initRecords() {
//        RecordDBDao rdb=new RecordDBDao(getApplicationContext());
//        List<Record> records=rdb.findByitem(MyApplication.getUserId());
//        if(records==null){
//                    for(int i=0;i<5;i++){
//            Record record=new Record("1","2","3");
////            String record="1";
//            data.add(record);
//            Record record2=new Record("3","4","5");
////            String record2="2";
//            data.add(record2);
//        }
//        }else {
//            for (Record record : records) {
//                data.add(record);
//            }
//        }
////        for(int i=0;i<5;i++){
////            Record record=new Record("1","2","3");
//////            String record="1";
////            data.add(record);
////            Record record2=new Record("3","4","5");
//////            String record2="2";
////            data.add(record2);
////        }
//    }
//}
