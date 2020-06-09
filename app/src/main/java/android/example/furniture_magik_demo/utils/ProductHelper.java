package android.example.furniture_magik_demo.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.example.furniture_magik_demo.Model.Product;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ProductHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 3 ;
    public static final String TABLE_NAME = "product";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCT_NAME = "name";
    public static final String COLUMN_PRODUCT_TYPE = "type";
    public static final String COLUMN_PRODUCT_PRICE = "price";
    public static final String COLUMN_PRODUCT_DISCOUNT_PRICE = "discount_price";
    public static final String COLUMN_PRODUCT_IMAGE = "image";


    public ProductHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCT_NAME + " TEXT NOT NULL, " +
                COLUMN_PRODUCT_TYPE+ " TEXT NOT NULL, " +
                COLUMN_PRODUCT_PRICE + " NUMBER NOT NULL, " +
                COLUMN_PRODUCT_DISCOUNT_PRICE + " NUMBER NOT NULL, " +
                COLUMN_PRODUCT_IMAGE + " BLOB NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(sqLiteDatabase);
    }

    public boolean saveNewProduct(Product product, Context context) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_NAME, product.getName());
        values.put(COLUMN_PRODUCT_TYPE, product.getType());
        values.put(COLUMN_PRODUCT_PRICE, product.getPrice());
        values.put(COLUMN_PRODUCT_DISCOUNT_PRICE, product.getDiscount_price());
        values.put(COLUMN_PRODUCT_IMAGE,product.getImage());

        long result = db.insert(TABLE_NAME,null, values);
        db.close();
        if(result!=-1)
        {
            Toast.makeText(context, "Inserted Successfully.", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    public void deleteProductRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }

    public void updateProductRecord(long personId, Context context, Product updatedProduct) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE  "+TABLE_NAME+" SET name ='"+ updatedProduct.getName() + "', type ='" + updatedProduct.getType()+ "', price ='"+ updatedProduct.getPrice() + "', discount_price ='"+ updatedProduct.getDiscount_price() + "', image ='"+ updatedProduct.getImage() + "'  WHERE _id='" + personId + "'");
        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();
    }
}
