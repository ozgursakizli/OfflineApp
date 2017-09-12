package com.ozgursakizli.offlineapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ozgursakizli.offlineapp.models.ProductModel;

import java.util.List;

/**
 * Created by ozgursakizli on 10.09.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "PRODUCTLIST.db";
    private static final int VERSION = 1;
    private static DatabaseHelper dbInstance;

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return dbInstance;
    }

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + ProductListScheme.ProductListTable.NAME + "(" + " _id integer primary key autoincrement, " +
                ProductListScheme.ProductListTable.Columns.productID + ", " +
                ProductListScheme.ProductListTable.Columns.productName + ", " +
                ProductListScheme.ProductListTable.Columns.productPrice + ", " +
                ProductListScheme.ProductListTable.Columns.productImage +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST " + ProductListScheme.ProductListTable.NAME);
        onCreate(sqLiteDatabase);
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + ProductListScheme.ProductListTable.NAME;
        return db.rawQuery(query, null);
    }

    public void deleteAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "DELETE FROM " + ProductListScheme.ProductListTable.NAME;
        db.execSQL(query);
    }

    public synchronized boolean insertData(List<ProductModel> productList) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i = 0; i < productList.size(); i++) {
            ContentValues values = new ContentValues();
            ProductModel productModel = productList.get(i);
            values.put(ProductListScheme.ProductListTable.Columns.productID, productModel.getProductId());
            values.put(ProductListScheme.ProductListTable.Columns.productName, productModel.getName());
            values.put(ProductListScheme.ProductListTable.Columns.productPrice, productModel.getPrice());
            values.put(ProductListScheme.ProductListTable.Columns.productImage, productModel.getImage());
            db.insert(ProductListScheme.ProductListTable.NAME, null, values);
        }

        return true;
    }

}
