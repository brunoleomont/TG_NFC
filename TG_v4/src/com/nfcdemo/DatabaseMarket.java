package com.nfcdemo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class DatabaseMarket extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "NFC_TG.db";

	// Market table name
	private static final String Mercado = "Mercado";

	// Mercado Table Columns names
	private static final String KEY_ID = "ID_MER";
	private static final String KEY_NAME = "NOME_MER";
	private static final String KEY_CNPJ = "CNPJ_MER";
	private static final String KEY_LOGIN = "LOGIN_MER";
	private static final String KEY_SENHA = "SENHA_MER";

	public DatabaseMarket(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_MarketS_TABLE = "CREATE TABLE " + Mercado + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ KEY_NAME + " TEXT," 
				+ KEY_CNPJ + " TEXT,"
				+ KEY_LOGIN + " TEXT,"
				+ KEY_SENHA + " TEXT,"
				 + ")";
		db.execSQL(CREATE_MarketS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + Mercado);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Market Operations
	 */

	// Adding new Market
	void addMarket(Market market) {
		SQLiteDatabase dm = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, market.getNOME_MER());
		values.put(KEY_CNPJ, market.getCNPJ_MER());
		values.put(KEY_LOGIN, market.getLOGIN_MER());
		values.put(KEY_SENHA, market.getSENHA_MER());

		// Inserting Row
		dm.insert(Mercado, null, values);
		dm.close(); // Closing database connection
	}

	// Getting single Market
	Market getMarket(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(Mercado, new String[] { KEY_ID,
				KEY_NAME, KEY_CNPJ, KEY_LOGIN, KEY_SENHA }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Market Market = new Market(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
		// return Market
		return Market;
	}
	
	// Getting All Markets
	public List<Market> getAllMarkets() {
		List<Market> MarketList = new ArrayList<Market>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + Mercado;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Market Market = new Market();
				Market.setID_MER(Integer.parseInt(cursor.getString(0)));
				Market.setNOME_MER(cursor.getString(1));
				Market.setCNPJ_MER(cursor.getString(2));
				Market.setLOGIN_MER(cursor.getString(3));
				Market.setSENHA_MER(cursor.getString(4));
				// Adding Market to list
				MarketList.add(Market);
			} while (cursor.moveToNext());
		}

		// return Market list
		return MarketList;
	}

	// Updating single Market
	public int updateMarket(Market Market) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, Market.getCNPJ_MER());
		values.put(KEY_CNPJ, Market.getCNPJ_MER());
		values.put(KEY_LOGIN, Market.getLOGIN_MER());
		values.put(KEY_SENHA, Market.getSENHA_MER());

		// updating row
		return db.update(Mercado, values, KEY_ID + " = ?",
				new String[] { String.valueOf(Market.getID_MER()) });
	}

	// Deleting single Market
	public void deleteMarket(Market Market) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(Mercado, KEY_ID + " = ?",
				new String[] { String.valueOf(Market.getID_MER()) });
		db.close();
	}


	// Getting Markets Count
	public int getMarketsCount() {
		String countQuery = "SELECT  * FROM " + Mercado;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
