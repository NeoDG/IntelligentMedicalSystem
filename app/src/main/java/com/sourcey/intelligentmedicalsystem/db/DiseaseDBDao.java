package com.sourcey.intelligentmedicalsystem.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sourcey.intelligentmedicalsystem.bean.Disease;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ly on 2017/6/23.
 * 疾病表的操作类
 */

public class DiseaseDBDao {

        private Context context;
        MyDBOpenHelper dbOpenHelper;

        public DiseaseDBDao(Context context) {
            this.context = context;
            dbOpenHelper = new MyDBOpenHelper(context);
        }




        public List<Disease> findAll() {
            List<Disease> diseases = null;
            SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

            if (db.isOpen()) {
                Cursor cursor = db.query("disease_common2", null, null, null, null, null,
                        null);
                diseases = new ArrayList<Disease>();//创建列表
                while (cursor.moveToNext()) {

                    String diseasename = cursor.getString(cursor.getColumnIndex("disease"));
                    String pre = cursor.getString(cursor.getColumnIndex("pre"));
                    String obj= cursor.getString(cursor.getColumnIndex("obj"));

                    Disease disease=new Disease(diseasename,pre,obj);
                    diseases.add(disease);//插入数据到列表中
                }
                cursor.close();
                db.close();
            }
            return diseases;
        }

        public List<Disease> findItemByName(String diseaseName){
            List<Disease> diseases = null;
            SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

            if (db.isOpen()) {
                Cursor cursor = db.query("disease_common2", null, "disease=?",new String[] { diseaseName}, null, null,
                        null);
                diseases = new ArrayList<Disease>();//创建列表
                if (cursor.moveToFirst()) {

                    do{

                        Disease disease=new Disease();

                        int id=cursor.getInt(cursor.getColumnIndex("id"));
                        String pre = cursor.getString(cursor.getColumnIndex("pre"));
                        String obj = cursor.getString(cursor.getColumnIndex("obj"));

                       disease.setId(id);
                        disease.setDisease(diseaseName);
                        disease.setPre(pre);
                        disease.setObj(obj);

                        diseases.add(disease);
                    }while (cursor.moveToNext());

                }
                cursor.close();
                db.close();
            }
            return diseases;
        }

    }
