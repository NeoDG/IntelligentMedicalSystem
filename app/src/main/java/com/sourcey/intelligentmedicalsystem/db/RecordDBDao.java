package com.sourcey.intelligentmedicalsystem.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.sourcey.intelligentmedicalsystem.bean.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ly on 2017/6/21.
 * 病例表操作类
 */

public class RecordDBDao {
    private Context context;
    MyDBOpenHelper dbOpenHelper;

    public RecordDBDao(Context context) {
        this.context = context;
        dbOpenHelper = new MyDBOpenHelper(context);
    }

    /**
     * 添加一条记录
     */
    public Boolean add(Record record) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            // db.execSQL("insert into person (name,age) values (?,?)",new Object[]{name,age});
            // db.execSQL("insert into person ",null) // 不合法的sql语句
            ContentValues values = new ContentValues();
            int userid=record.getUserid();
            String quest=record.getQuest();
            String solution=record.getSolution();
            String medicine=record.getMedicine();
            long time=record.getTime();

            values.put("userid",userid);
            values.put("quest", quest);
            values.put("solution", solution);
            values.put("medicine",medicine);
            values.put("time",time);
            db.insert("tb_record", "name", values); // 组拼sql语句完成的添加的操作
            db.close();
            return true;
        }
        return false;
    }

//    /**
//     * 删除一条记录
//     */
//    public void delete(String name) {
//        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
//        if (db.isOpen()) {
//            db.delete("tb_record", "username=?", new String[] { name });
//            db.close();
//        }
//    }

    /**
     * 数据库的更改操作
     * 根据userId更新内容
     */
    public void update(int id,Record newRecord) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();

            int userid=newRecord.getUserid();
            String quest=newRecord.getQuest();
            String solution=newRecord.getSolution();
            String medicine=newRecord.getMedicine();
            long time=newRecord.getTime();

            values.put("userid",userid);
            values.put("quest", quest);
            values.put("solution", solution);
            values.put("medicine",medicine);
            values.put("time",time);
            db.update("tb_record", values, "id=?", new String[]{Integer.toString(id)});
            db.close();
        }
    }
    /**
     * 数据库的查询操作 判断有无该数据
     * 查询数据库中用户的记录是否存在
     */
    public List<Record> findByitem(int  userId) {
        List<Record> records = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query("tb_record", null, "userid=?",new String[] { Integer.toString(userId)}, null, null, "time Desc",null);
            if (cursor.moveToFirst()) {
                records = new ArrayList<Record>();//创建列表
                do{

                    Record record= new Record();
                    int id=cursor.getInt(cursor.getColumnIndex("id"));
                    int userid = cursor.getInt(cursor.getColumnIndex("userid"));
                    int time=cursor.getInt(cursor.getColumnIndex("time"));
                    String quest = cursor.getString(cursor.getColumnIndex("quest"));
                    String solution = cursor.getString(cursor.getColumnIndex("solution"));
                    String medicine = cursor.getString(cursor.getColumnIndex("medicine"));

                   record.setId(id);
                    record.setUserid(userid);
                    record.setQuest(quest);
                    record.setMedicine(medicine);
                    record.setSolution(solution);
                    record.setTime(time);
                    records.add(record);//插入数据到列表中
                }while (cursor.moveToNext());

            }
            cursor.close();
            db.close();
        }
        return records;

    }

    public int getCountByUserId(int userid){
        int count=0;
        List<Record> records=findByitem(userid);
        if(records!=null){
            count=records.size();
        }
        return  count;
    }


    //通过id按时间降序得到所有record
    public List<Record> findAll() {
        List<Record> records = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query("tb_record", null, null, null, null, null, "time",null);
            if (cursor.moveToFirst()) {
                records = new ArrayList<Record>();//创建列表
                do {

                    Record record=new Record();
                    int id=cursor.getInt(cursor.getColumnIndex("id"));
                    int userid=cursor.getInt((cursor.getColumnIndex("userid")));
                    int time=cursor.getInt(cursor.getColumnIndex("time"));
                    String quest = cursor.getString(cursor.getColumnIndex("quest"));
                    String solution = cursor.getString(cursor.getColumnIndex("solution"));
                    String medicine = cursor.getString(cursor.getColumnIndex("medicine"));




                    record.setId(id);
                    record.setUserid(userid);
                    record.setQuest(quest);
                    record.setMedicine(medicine);
                    record.setSolution(solution);
                    record.setTime(time);
                    records.add(record);//插入数据到列表中
                }while(cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }
        return records;

    }

}
