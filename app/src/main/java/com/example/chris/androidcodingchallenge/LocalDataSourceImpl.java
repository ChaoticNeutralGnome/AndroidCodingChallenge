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

    private static final String DB_TABLE = "Images";
    private static final String KEY_NAME = "url";
    private static final String KEY_IMAGE = "image";
    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "AndroidChallenge.db";
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + "("+
    KEY_NAME + " TEXT," +
    KEY_IMAGE + " BLOB)";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DB_TABLE;

    public LocalDataSourceImpl(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void addImage(String key, Bitmap value) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, key);
        values.put(KEY_IMAGE, DbBitmapUtility.getBytes(value));
        db.insert(DB_TABLE, null, values);
    }

    @Override
    public Bitmap getImage(String key) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + KEY_IMAGE  + " FROM " + DB_TABLE + " WHERE " + KEY_NAME + " = '" + key + "'", null);
        if(cursor.getCount() == 1){
            cursor.moveToFirst();
            byte[] image  = cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE));
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
        public static byte[] getBytes(Bitmap bitmap) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            return stream.toByteArray();
        }

        // convert from byte array to bitmap
        public static Bitmap getImage(byte[] image) {
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        }
    }
}

