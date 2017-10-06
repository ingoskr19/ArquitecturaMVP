package co.com.etn.arquitecturamvpbase.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import co.com.etn.arquitecturamvpbase.schemes.IProductScheme;

/**
 * Created by Erika on 2/10/2017.
 */

class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(IProductScheme.PRODUCT_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int newVersion) {
        Log.w(Constants.DATABASE_NAME, " update version to " + newVersion);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  " + IProductScheme.PRODUCT_TABLE);
    }
}
