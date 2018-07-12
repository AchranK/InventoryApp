package com.example.android.inventoryapp.data;

import android.content.ContentResolver;
import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;


public final class ShopContract {



    public static final String CONTENT_AUTHORITY = "com.example.android.inventoryapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_SHOP = "shop";


    public static final class ShopEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_SHOP);


        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SHOP;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SHOP;


        public final static String TABLE_NAME = "Shop";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PRODUCT_NAME = "PRODUCT_NAME";
        public final static String COLUMN_SUPPLIER_NAME = "SUPPLIER_NAME";
        public final static String COLUMN_PRODUCT_PRICE = "PRICE";
        public final static String COLUMN_QUANTITY = "QUANTITY";
        public final static String COLUMN_SUPPLIER_PHONE_NUMBER = "SUPPLIER_PHONE_NUMBER";


    }
}


