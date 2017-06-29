package com.sourcey.intelligentmedicalsystem.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sourcey.intelligentmedicalsystem.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ly on 2017/6/15.
 * 用户表操作类
 */

public class UserDBDao {
    private Context context;
    MyDBOpenHelper dbOpenHelper;

    public UserDBDao(Context context) {
        this.context = context;
        dbOpenHelper = new MyDBOpenHelper(context);
    }

    /**
     * 登陆
     */
    //登录用
    public Boolean login(String username,String password){
        SQLiteDatabase sdb=dbOpenHelper.getReadableDatabase();
        String sql="select * from tb_user where username=? and password=?";
        Cursor cursor=sdb.rawQuery(sql, new String[]{username,password});
        if(cursor.moveToFirst()==true){
            cursor.close();
            return true;
        }
        return false;
    }
    //注册用
    public Boolean register(User user )throws Exception {

        SQLiteDatabase sdb=dbOpenHelper.getReadableDatabase();
        if (sdb.isOpen()) {
            String sql = "insert into tb_user(username,password,email,phone) values(?,?,?,?)";
            Object obj[] = {user.getUsername(), user.getPassword(), user.getEmail(), user.getPhone()};
            sdb.execSQL(sql, obj);
            return true;
        }
        return false;
    }

    /**
     * 添加一条记录
     */
    public Boolean add(String username, String password, String email, String phone) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            // db.execSQL("insert into person (name,age) values (?,?)",new Object[]{name,age});
            // db.execSQL("insert into person ",null) // 不合法的sql语句
            ContentValues values = new ContentValues();

            values.put("username", username);
            values.put("password", password);
            values.put("email",email);
            values.put("phone",phone);
            db.insert("tb_user", "null", values); // 组拼sql语句完成的添加的操作
            db.close();
            return true;
        }
        return false;
    }

    /**
     * 添加一条记录
     */
    public Boolean add(User user) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            // db.execSQL("insert into person (name,age) values (?,?)",new Object[]{name,age});
            // db.execSQL("insert into person ",null) // 不合法的sql语句
            ContentValues values = new ContentValues();
            String userame=user.getUsername();
            String password=user.getPassword();
            String email=user.getEmail();
            String phone=user.getPhone();
            values.put("username", userame);
            values.put("password", password);
            values.put("email",email);
            values.put("phone",phone);
            db.insert("tb_user", "name", values); // 组拼sql语句完成的添加的操作
            db.close();
            return true;
        }
        return false;
    }

    /**
     * 删除一条记录
     */
    public void delete(String name) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            db.delete("tb_user", "username=?", new String[] { name });
            db.close();
        }
    }

    /**
     * 数据库的更改操作
     * 根据username更新内容
     */
    public void update(String username,User newUser) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            ContentValues values = new ContentValues();

            String userame=newUser.getUsername();
            String password=newUser.getPassword();
            String email=newUser.getEmail();
            String phone=newUser.getPhone();
            values.put("username", userame);
            values.put("password", password);
            values.put("email",email);
            db.update("tb_user", values, "username=?", new String[]{username});
            db.close();
        }
    }
    /**
     * 数据库的查询操作 判断有无该数据
     * 查询数据库的所有用户
     */
    public List<User> findByitem(String item) {
        List<User> users = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.query("tb_user", null, "username=?",new String[] { item}, null, null, null);
            if (cursor.moveToFirst()) {
                users = new ArrayList<User>();//创建列表
                User user= new User();
                int id=cursor.getInt(cursor.getColumnIndex("id"));
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));

                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
                user.setPhone(phone);
                users.add(user);//插入数据到列表中
            }
            cursor.close();
            db.close();
        }
        return users;

    }


    /**
     * 查询所有信息
     */
    public List<User> findAll() {
        List<User> users = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

        if (db.isOpen()) {
            Cursor cursor = db.query("tb_user", null, null, null, null, null,
                    null);
            users = new ArrayList<User>();//创建列表
            while (cursor.moveToNext()) {
                User user = new User();
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));

                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
                user.setPhone(phone);
                users.add(user);//插入数据到列表中
            }
            cursor.close();
            db.close();
        }
        return users;
    }
}
