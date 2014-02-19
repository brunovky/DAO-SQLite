package com.goldroid.db.factory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "/data/data/com.goldroid.view/databases/db_goldroid.db";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public SQLiteDatabase getDatabase() {
		return getReadableDatabase();
	}

}