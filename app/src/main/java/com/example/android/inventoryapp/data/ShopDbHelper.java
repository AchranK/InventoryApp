package com.example.android.inventoryapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.inventoryapp.data.ShopContract.ShopEntry;

/**
 * Database helper for Pets app. Manages database creation and version management.
 */
public class ShopDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ShopDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "shop.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link ShopDbHelper}.
     *
     * @param context of the app
     */
    public ShopDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_SHOP_TABLE=  " CREATE TABLE " + ShopEntry.TABLE_NAME + " ( "
                + ShopEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ShopEntry.COLUMN_PRODUCT_NAME + " TEXT, "
                + ShopEntry.COLUMN_SUPPLIER_NAME + " TEXT, "
                + ShopEntry.COLUMN_QUANTITY + " INTEGER NOT NULL, "
                + ShopEntry.COLUMN_PRODUCT_PRICE + " INTEGER NOT NULL, "
                + ShopEntry.COLUMN_SUPPLIER_PHONE_NUMBER + " INTEGER NOT NULL );";


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_SHOP_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}

