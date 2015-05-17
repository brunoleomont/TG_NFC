package com.nfcdemo;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "ProdutoManager";

	// Products table name
	private static final String Produto = "Produto";

	// Products Table Columns names
	private static final String KEY_ID = "ID_PRO";
	private static final String KEY_TAG = "TAG_PRO";
	private static final String KEY_NAME = "NOME_PRO";
	private static final String KEY_PRECO = "PRECO_PRO";
	private static final String KEY_DATA = "DATA_PRO";
	private static final String KEY_FRASE = "FRASE_PRO";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_ProductS_TABLE = "CREATE TABLE " + Produto + "("
				+ KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ KEY_TAG + " TEXT,"
				+ KEY_NAME + " TEXT," 
				+ KEY_PRECO + " DOUBLE,"
				+ KEY_DATA + " TEXT,"
				+ KEY_FRASE + " TEXT"
				 + ")";
		db.execSQL(CREATE_ProductS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + Produto);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new Product
	void addProduct(Product Product) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TAG, Product.getTAG_PRO()); // Product tag
		values.put(KEY_NAME, Product.getNOME_PRO()); // Product Name
		values.put(KEY_PRECO, Product.getPRECO_PRO());
		values.put(KEY_DATA, Product.getDATA_PRO());

		// Inserting Row
		db.insert(Produto, null, values);
		db.close(); // Closing database connection
	}

	// Getting single Product
	Product getProduct(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(Produto, new String[] { KEY_ID,
				KEY_NAME, KEY_PRECO }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Product Product = new Product(cursor.getString(1), Double.parseDouble(cursor.getString(2)));
		// return Product
		return Product;
	}
	
	// Getting All Products
	public List<Product> getAllProducts() {
		List<Product> ProductList = new ArrayList<Product>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + Produto;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Product Product = new Product();
				Product.setID_PRO(Integer.parseInt(cursor.getString(0)));
				Product.setTAG_PRO(cursor.getString(1));
				Product.setNOME_PRO(cursor.getString(2));
				Product.setPRECO_PRO(Double.parseDouble(cursor.getString(3)));
				Product.setDATA_PRO(cursor.getString(4));
				// Adding Product to list
				ProductList.add(Product);
			} while (cursor.moveToNext());
		}

		// return Product list
		return ProductList;
	}

	// Updating single Product
	public int updateProduct(Product Product) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TAG, Product.getTAG_PRO()); // Product tag
		values.put(KEY_NAME, Product.getNOME_PRO()); // Product Name
		values.put(KEY_PRECO, Product.getPRECO_PRO());
		values.put(KEY_DATA, Product.getDATA_PRO());

		// updating row
		return db.update(Produto, values, KEY_ID + " = ?",
				new String[] { String.valueOf(Product.getID_PRO()) });
	}

	// Deleting single Product
	public void deleteProduct(Product Product) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(Produto, KEY_ID + " = ?",
				new String[] { String.valueOf(Product.getID_PRO()) });
		db.close();
	}


	// Getting Products Count
	public int getProductsCount() {
		String countQuery = "SELECT  * FROM " + Produto;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		// return count
		return cursor.getCount();
	}

}
