package com.example.chris.androidcodingchallenge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class LocalDataSourceImpl extends SQLiteOpenHelper implements LocalDataSource{

    private static final String TABLE_NAME = "Images";
    private static final String KEY_NAME = "url";
    private static final String VALUE_NAME = "image";
    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "AndroidChallenge.db";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("+
    KEY_NAME + " TEXT," +
            VALUE_NAME + " BLOB)";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public LocalDataSourceImpl(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void addImage(String key, Bitmap value) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, key);
        values.put(VALUE_NAME, DbBitmapUtility.getBytes(value));
        db.insert(TABLE_NAME, null, values);
    }

    @Override
    public Bitmap getImage(String key) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + VALUE_NAME + " FROM " + TABLE_NAME + " WHERE " + KEY_NAME + " = '" + key + "'", null);
        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            byte[] image  = cursor.getBlob(cursor.getColumnIndex(VALUE_NAME));
            cursor.close();
            return DbBitmapUtility.getImage(image);
        }
        else{
            cursor.close();
            return null;
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    private static class DbBitmapUtility {

        // convert from bitmap to byte array
        static byte[] getBytes(Bitmap bitmap) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        }

        // convert from byte array to bitmap
        static Bitmap getImage(byte[] image) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }
    }
}

