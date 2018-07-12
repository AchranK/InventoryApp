package com.example.android.inventoryapp;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.data.ShopContract;
import com.example.android.inventoryapp.data.ShopContract.ShopEntry;


public class ItemDetailActivity extends AppCompatActivity {

    private static final int EXISTING_SHOP_LOADER = 0;
    private Uri mCurrentShopUri;

    private TextView mBookSupPhone;
    private TextView mQuantity;

    @Override
    protected void onCreate(Bundle savedInstante) {
        super.onCreate ( savedInstante );
        setContentView ( R.layout.item_detail_activity );


        Intent intent = getIntent ();
        mCurrentShopUri = intent.getData ();

        TextView mName = findViewById(R.id.product_name);
        TextView mProductPrice = findViewById(R.id.product_price);
         mQuantity = findViewById(R.id.quantity);
        TextView mBookSupName = findViewById(R.id.supplier_name);
        mBookSupPhone = findViewById ( R.id.edit_supplier_phone_number );

        Button mPlusButton = findViewById ( R.id.plus_button );
        Button mMinButton = findViewById ( R.id.min_button );
        Button mCallSupButton = findViewById ( R.id.call_sup_button );
        Button mEditButton = findViewById ( R.id.edit_button );
        Button mDeleteButton = findViewById ( R.id.delete_button );

        mDeleteButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                // Respond to a click on the "Delete"
            }
        } );

        mEditButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( ItemDetailActivity.this, EditorActivity.class);
                intent.setData ( mCurrentShopUri );
                startActivity ( intent );
            }
        } );

        mPlusButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                String[] projection = {ShopEntry._ID, ShopEntry.COLUMN_QUANTITY};
                Cursor cursor = getContentResolver().query(mCurrentShopUri, projection, null,
                        null, null, null);
                cursor.moveToFirst();
                int quantity = cursor.getInt(cursor.getColumnIndex(ShopEntry.COLUMN_QUANTITY));
                quantity++;
                ContentValues values = new ContentValues();
                values.put(ShopEntry.COLUMN_QUANTITY, quantity);
                getContentResolver().update(mCurrentShopUri, values, null, null);
                mQuantity.setText(String.valueOf(quantity));
            }
        });

        mMinButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                decreaseCount ();
            }

        });

        //Opening phone app, with the Supplier phone number in it, if the user gives us their permission
        mCallSupButton.setOnClickListener( new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                String supPhoneString = mBookSupPhone.getText().toString().trim();

                Intent intent = new Intent(Intent.ACTION_DIAL);

                intent.setData(Uri.parse("tel:" + supPhoneString));

                startActivity(intent);

            }

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void decreaseCount() {
        String[] projection = {ShopEntry._ID, ShopEntry.COLUMN_QUANTITY};
        Cursor cursor = getContentResolver().query(mCurrentShopUri, projection, null,
                null, null, null);
        cursor.moveToFirst();
        int quantity = cursor.getInt(cursor.getColumnIndex(ShopEntry.COLUMN_QUANTITY));

        if ( quantity <= 0) {
            Toast.makeText ( this, "Stock is empty", Toast.LENGTH_SHORT ).show ();
        }
        else  {
            quantity--;
            ContentValues values = new ContentValues();
            values.put(ShopEntry.COLUMN_QUANTITY, quantity);
            getContentResolver().update(mCurrentShopUri, values, null, null);
            mQuantity.setText(String.valueOf(quantity));
        }}

    private void deleteBook() {
        if (mCurrentShopUri != null) {
            int rowsDeleted = getContentResolver ().delete ( mCurrentShopUri, null, null );

            if (rowsDeleted == 0) {
                Toast.makeText ( this, getString ( R.string.editor_delete_product_failed ),
                        Toast.LENGTH_SHORT ).show ();
            } else {
                Toast.makeText ( this, getString ( R.string.editor_delete_product_successful ), Toast.LENGTH_SHORT ).show ();
            }
        }
        finish ();
    }
}
