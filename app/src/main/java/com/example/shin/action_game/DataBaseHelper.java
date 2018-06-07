package com.example.shin.action_game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "rank.db";
    public static final String TABLE_NAME = "rank";
    public static final String COL_1 = "id";
    public static final String COL_2 = "score";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        // SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +
                    " (ID INTEGER PRIMARY KEY AUTOINCREMENT, SCORE INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put(COL_2, Integer.valueOf(score));
        long  result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) return false;
        else return true;
    }

    public Cursor getFirstData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " WHERE id=1;", null);
        return res;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+ COL_2 + "  from " + TABLE_NAME, null);
        return res;
    }

    public boolean updateData(String id, String score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues =  new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_2, score);
        Cursor cur = getFirstData();
        if (cur.getCount() == 0) {
            return insertData(score);
        }
        cur.moveToFirst();
        int max = Integer.parseInt(cur.getString(1));
        if (max < Integer.parseInt(score)) {
            try {
                db.update(TABLE_NAME, contentValues, "id = ?", new String[] { id });
            } catch (SQLException e) {
                insertData(score);
            }
            return true;
        }
        return false;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] { id });
    }
}
