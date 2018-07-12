package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.R;
import com.example.android.inventoryapp.data.ShopContract;
import com.example.android.inventoryapp.data.ShopContract.ShopEntry;

/**
 * {@link ShopCursorAdapter} is an adapter for a list or grid view
 * that uses a {@link Cursor} of pet data as its data source. This adapter knows
 * how to create list items for each row of pet data in the {@link Cursor}.
 */
public class ShopCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link ShopCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public ShopCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view,final Context context,Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);


        // Find the columns of pet attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(ShopEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ShopEntry.COLUMN_PRODUCT_PRICE);
        int supplierColumnIndex = cursor.getColumnIndex(ShopEntry.COLUMN_SUPPLIER_NAME);
        int phoneColumnIndex = cursor.getColumnIndex(ShopEntry.COLUMN_SUPPLIER_PHONE_NUMBER);
        int quantityColumnIndex = cursor.getColumnIndex(ShopEntry.COLUMN_QUANTITY);



        // Read the pet attributes from the Cursor for the current pet
        String shopName = cursor.getString(nameColumnIndex);
        String shopPrice = cursor.getString(priceColumnIndex);
        String shopSupplier = cursor.getString(supplierColumnIndex);
        String shopPhone = cursor.getString(phoneColumnIndex);
        String shopQuantity = cursor.getString(quantityColumnIndex);

        ImageButton saleButton = view.findViewById ( R.id.sale_button );


        // Read the book attributes from the Cursor for the current book

        final int quantity = cursor.getInt(quantityColumnIndex);
        final String id = cursor.getString ( cursor.getColumnIndex ( ShopContract.ShopEntry._ID ) );




        saleButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Uri mCurrentShopUri = Uri.withAppendedPath ( ShopContract.ShopEntry.CONTENT_URI, id );
                ContentValues values = new ContentValues (  );

                if (quantity >= 1) {
                    values.put( ShopContract.ShopEntry.COLUMN_QUANTITY, quantity -1);

                    int rowsAffected = context.getContentResolver ().update ( mCurrentShopUri, values, null, null );

                    Toast.makeText (context, context.getApplicationContext ().getResources ().getString ( R.string.editor_dec_product ), Toast.LENGTH_SHORT).show ();

                } else {
                    Toast.makeText(context,

                            context.getApplicationContext().getResources().getString( R.string.no_product_to_sell ),

                            Toast.LENGTH_SHORT).show();
                }
            }
        } );


        // Update the TextViews with the attributes for the current pet
        nameTextView.setText(shopName);
        summaryTextView.setText(shopSupplier);
        summaryTextView.setText(shopPrice);

        summaryTextView.setText(shopPhone);

        summaryTextView.setText(shopQuantity);





    }
}