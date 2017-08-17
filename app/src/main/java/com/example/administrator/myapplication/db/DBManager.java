package com.example.administrator.myapplication.db;

/**
 * Created by Administrator on 2017/3/20.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.myapplication.mode.Person;

//参考：http://blog.csdn.net/liuhe688/article/details/6715983
public class DBManager
{
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context)
    {
        Log.d("DB", "DBManager --> Constructor");
        helper = new DatabaseHelper(context);
        // 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0,
        // mFactory);
        // 所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * add persons
     *
     * @param persons
     */
    public void add(List<Person> persons)
    {
        Log.d("DB", "DBManager --> add");
        // 采用事务处理，确保数据完整性
        db.beginTransaction(); // 开始事务
        try
        {
            for (Person person : persons)
            {
                db.execSQL("INSERT INTO " + DatabaseHelper.TABLE_NAME
                        + " VALUES(null, ?, ?, ?)", new Object[] { person.name,
                        person.age, person.info });
                // 带两个参数的execSQL()方法，采用占位符参数？，把参数值放在后面，顺序对应
                // 一个参数的execSQL()方法中，用户输入特殊字符时需要转义
                // 使用占位符有效区分了这种情况
            }
            db.setTransactionSuccessful(); // 设置事务成功完成
        }
        finally
        {
            db.endTransaction(); // 结束事务
        }
    }

    /**
     * update person's age
     *
     * @param person
     */
    public void updateAge(Person person)
    {
        Log.d("DB", "DBManager --> updateAge");
        ContentValues cv = new ContentValues();
        cv.put("age", person.age);
        db.update(DatabaseHelper.TABLE_NAME, cv, "name = ?",
                new String[] { person.name });
    }

    /**
     * delete old person
     *
     * @param person
     */
    public void deleteOldPerson(Person person)
    {
        Log.d("DB", "DBManager --> deleteOldPerson");
        db.delete(DatabaseHelper.TABLE_NAME, "age >= ?",
                new String[] { String.valueOf(person.age) });
    }

    /**
     * query all persons, return list
     *
     * @return List<Person>
     */
    public List<Person> query()
    {
        Log.d("DB", "DBManager --> query");
        ArrayList<Person> persons = new ArrayList<Person>();
        Cursor c = queryTheCursor();
        while (c.moveToNext())
        {
            Person person = new Person();
            person._id = c.getInt(c.getColumnIndex("_id"));
            person.name = c.getString(c.getColumnIndex("name"));
            person.age = c.getInt(c.getColumnIndex("age"));
            person.info = c.getString(c.getColumnIndex("info"));
            persons.add(person);
        }
        c.close();
        return persons;
    }

    /**
     * query all persons, return cursor
     *
     * @return Cursor
     */
    public Cursor queryTheCursor()
    {
        Log.d("DB", "DBManager --> queryTheCursor");
        Cursor c = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME,
                null);
        return c;
    }

    /**
     * close database
     */
    public void closeDB()
    {
        Log.d("DB", "DBManager --> closeDB");
        // 释放数据库资源
        db.close();
    }

}
