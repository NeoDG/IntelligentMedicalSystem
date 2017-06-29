package com.sourcey.intelligentmedicalsystem.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *数据库helper类
 */

public class MyDBOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="AIDoctor.db";//数据库名称
    private static final int SCHEMA_VERSION=1;//版本号,则是升级之后的,升级方法请看onUpgrade方法里面的判断
    Context mContext;

    public MyDBOpenHelper(Context context) {

        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
        mContext=context;
    }//数据库

    // 数据库第一次被创建的时候 调用
    @Override
    public void onCreate(SQLiteDatabase db) {//数据表
        executeAssetsSQL(db, "disease_common2.sql");
        System.out.println("创建表");
        //创建用户表tb_user
        String sql="create table tb_user(id integer primary key autoincrement,username varchar(20),password varchar(20),email varchar(20),phone varchar(20))";
        String sql2="create table tb_record(id integer primary key autoincrement ,userid integer, quest varchar(256),solution varchar(256),medicine varchar(256),time integer)";
        db.execSQL(sql);
        db.execSQL(sql2);
    }

    //修改数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
    /**
     * 读取数据库文件（.sql），并执行sql语句
     * */
    private void executeAssetsSQL(SQLiteDatabase db, String schemaName) {
        BufferedReader in = null;
        try {
            AssetManager assetManager=mContext.getResources().getAssets();

            in = new BufferedReader(new InputStreamReader(assetManager.open(Configuration.DB_PATH + "/" + schemaName)));

            Log.d("path","路径:"+Configuration.DB_PATH + "/" + schemaName);
            String line;
            String buffer = "";
            while ((line = in.readLine()) != null) {
                buffer += line;
                if (line.trim().endsWith(";")) {
                    db.execSQL(buffer);//buffer.replace(";", "")
                    buffer = "";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                Log.e("db-error", e.toString());
            }
        }
    }

}