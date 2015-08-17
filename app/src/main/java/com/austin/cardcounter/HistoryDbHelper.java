package com.austin.cardcounter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class HistoryDbHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 4;
	public static final String DATABASE_NAME = "History.db";
	
	public HistoryDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db) {
        db.execSQL(PinochleHistoryFragment.SqlCreateEntries(PinochleHistoryFragment.TABLE_NAME));
        db.execSQL(FiveHundredHistoryFragment.SqlCreateEntries(FiveHundredHistoryFragment.TABLE_NAME));
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	db.execSQL("DROP TABLE IF EXISTS " + PinochleHistoryFragment.TABLE_NAME);
    	db.execSQL("DROP TABLE IF EXISTS " + FiveHundredHistoryFragment.TABLE_NAME);
        onCreate(db);
    }

}
