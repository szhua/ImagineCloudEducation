package com.imagine.cloud.util.search;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.imagine.cloud.bean.SearchRecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szhua on 2017/7/7/007.
 * github:https://github.com/szhua
 * ImagineCloudEducation
 * SearchRecordManager
 */

public class SearchRecordManager {


    private volatile static SearchRecordManager mInstance;
    private RecordSQLiteOpenHelper helper;
    private SQLiteDatabase db;

    private SearchRecordManager(Context context) {
        helper = new RecordSQLiteOpenHelper(context.getApplicationContext());
    }

    public static SearchRecordManager getInstance(Context context) {
        SearchRecordManager inst = mInstance;
        if (inst == null) {
            synchronized (SearchRecordManager.class) {
                inst = mInstance;
                if (inst == null) {
                    inst = new SearchRecordManager(context);
                    mInstance = inst;
                }
            }
        }
        return inst;
    }

    /**
     * 插入数据
     */
    public void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }
    /**
     * 检查数据库中是否已经有该条记录
     */
    public boolean hasData(String tempName){
        //查询中有这个数据
        List<SearchRecordBean> resultData = queryData(tempName);
        if(resultData!=null&&!resultData.isEmpty()){
            return  true ;
        }
        //确切的有这个数据；
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        while (cursor.moveToNext()){
            return  true ;
        }
        return false;
    }
    /**
     * 清空数据
     */
    public void deleteData() throws  Exception {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    public List<SearchRecordBean> queryData(String tempName) {
     List<SearchRecordBean> recordData =new ArrayList<>();
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));//获取第二列的值
            SearchRecordBean searchRecordBean =new SearchRecordBean() ;
            searchRecordBean.setSearchKey(name);
            recordData.add(searchRecordBean);
        }
     return  recordData ;
    }



}
