package com.developer.arsltech.tasbeehcounter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelperClass extends SQLiteOpenHelper {
    public static  final String DATABASE_NAME="students.db";
    public static  final String TABLE_NAME="zikar";
    public static  final String COL_1="ID";
    public static  final String COL_2="zikar";
    public static  final String COL_3="count";
    public static  final String COL_4="remainingcount";

    public HelperClass(Context context) {
        super(context, "tasbeehZikarCounter.db", null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+"(ID INTEGER PRIMARY KEY,ZIKAR TEXT,COUNT INTEGER,REMAININGCOUNT INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String zikar, int count, int remaingCount){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,zikar);
        contentValues.put(COL_3,count);
        contentValues.put(COL_4,remaingCount);


        long success= db.insert(TABLE_NAME,null,contentValues);
        if(success == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cur = db.rawQuery("select * from "+TABLE_NAME,null);
        return cur;
    }


    public boolean updateData(String name,int remainingCount){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4,remainingCount);

        db.update(TABLE_NAME,contentValues, "zikar = ?",new String[]{name});
        return true;
    }

    public Integer deleteData(String zikarName){
        SQLiteDatabase db=this.getWritableDatabase();

        return db.delete(TABLE_NAME, "zikar = ?",new String[]{zikarName});
    }

    public  void deletAll(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }

    public long getCounter(){
        SQLiteDatabase db=this.getWritableDatabase();

        long numRows = DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM todo_list", null);

        return numRows;

    }

}
