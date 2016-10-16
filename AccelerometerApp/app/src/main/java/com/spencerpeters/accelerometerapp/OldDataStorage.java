//package com.spencerpeters.accelerometerapp;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
///**
// * Created by Vadim on 10/16/2016.
// */
//
//public class OldDataStorage extends SQLiteOpenHelper {
//
//    private static final int DATABASE_VERSION = 2;
//    private static final String CREATE_DATABASE =
//            "CREATE TABLE PastData(WorkoutTime";
//    private static final String DATABASE_NAME = null;
//    private static final java.lang.String DICTIONARY_TABLE_CREATE = null;
//
//    OldDataStorage(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(DICTIONARY_TABLE_CREATE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//    }
//}
//}
