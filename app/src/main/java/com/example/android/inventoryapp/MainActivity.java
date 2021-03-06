package com.example.android.inventoryapp;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.inventoryapp.data.ShopContract.ShopEntry;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    /** Identifier for the pet data loader */
    private static final int SHOP_LOADER = 0;

    /** Adapter for the ListView */
    ShopCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        // Find the ListView which will be populated with the pet data
        ListView shopListView = (ListView) findViewById(R.id.list);

        // Find and set empty view on the ListView, so that it only shows when the list has 0 items.
        View emptyView = findViewById(R.id.empty_view);
        shopListView.setEmptyView(emptyView);

        // Setup an Adapter to create a list item for each row of pet data in the Cursor.
        // There is no pet data yet (until the loader finishes) so pass in null for the Cursor.
        mCursorAdapter = new ShopCursorAdapter(this, null);
        shopListView.setAdapter(mCursorAdapter);

        // Setup the item click listener
        shopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Create new intent to go to {@link EditorActivity}
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);

                // Form the content URI that represents the specific pet that was clicked on,
                // by appending the "id" (passed as input to this method) onto the
                // {@link PetEntry#CONTENT_URI}.
                // For example, the URI would be "content://com.example.android.pets/pets/2"
                // if the pet with ID 2 was clicked on.
                Uri currentShopUri = ContentUris.withAppendedId(ShopEntry.CONTENT_URI, id);

                // Set the URI on the data field of the intent
                intent.setData(currentShopUri);

                // Launch the {@link EditorActivity} to display the data for the current pet.
                startActivity(intent);
            }
        });

        // Kick off the loader
        getLoaderManager().initLoader(SHOP_LOADER, null, this);}


        private void insertShop(){
            // Create a ContentValues object where column names are the keys,
            // and Toto's pet attributes are the values.
            ContentValues values = new ContentValues();
            values.put(ShopEntry.COLUMN_SUPPLIER_NAME,"Nokia");
            values.put(ShopEntry.COLUMN_PRODUCT_NAME,"S8");
            values.put(ShopEntry.COLUMN_QUANTITY,"1");
            values.put(ShopEntry.COLUMN_PRODUCT_PRICE,"800 PLN");
            values.put(ShopEntry.COLUMN_SUPPLIER_PHONE_NUMBER,"0048666666666");


            // Insert a new row for Toto into the provider using the ContentResolver.
            // Use the {@link PetEntry#CONTENT_URI} to indicate that we want to insert
            // into the pets database table.
            // Receive the new content URI that will allow us to access Toto's data in the future.
            Uri newUri = getContentResolver().insert(ShopEntry.CONTENT_URI,values);
        }

    private void deleteAllShop() {
        int rowsDeleted = getContentResolver().delete(ShopEntry.CONTENT_URI,null,null);
        Log.v("MainActivity",rowsDeleted + " rows deleted from pet database");
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu options from the res/menu/menu_catalog.xml file.
            // This adds menu items to the app bar.
            getMenuInflater().inflate(R.menu.menu_catalog, menu);
            return true;
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertShop();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                deleteAllShop();
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Define a projection that specifies the columns from the table we care about.
        String[] projection = {
                ShopEntry._ID,
                ShopEntry.COLUMN_PRODUCT_NAME,
                ShopEntry.COLUMN_SUPPLIER_NAME,
                ShopEntry.COLUMN_QUANTITY,
                ShopEntry.COLUMN_SUPPLIER_PHONE_NUMBER,
                ShopEntry.COLUMN_PRODUCT_PRICE };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                ShopEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Update {@link PetCursorAdapter} with this new cursor containing updated pet data
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }
}

