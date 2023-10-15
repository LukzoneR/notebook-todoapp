package ch.walica.sqlite_szablon.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import ch.walica.sqlite_szablon.R;
import ch.walica.sqlite_szablon.model.Car;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "cars_db";
    public static final String TABLE_NAME = "cars";
    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_BODY = "body";
    public static final String COL_DATE = "created_date";

    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 2);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_TITLE + " TEXT, " + COL_BODY + " TEXT, " + COL_DATE + " INTEGER)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public void addCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, car.getTitle());
        cv.put(COL_BODY, car.getBody());
        cv.put(COL_DATE, car.getCreatedDate());
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) {
            Toast.makeText(context, R.string.addNoToast, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.addToast, Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getAllCars() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = null;
        if(db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void deleteCar(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(id)});
        if(result == -1) {
            Toast.makeText(context, R.string.deleteNoToast, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.deleteToast, Toast.LENGTH_SHORT).show();
        }
    }

    public void updateCar(Car car) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, car.getTitle());
        cv.put(COL_BODY, car.getBody());
        cv.put(COL_DATE, car.getCreatedDate());
        int result = db.update(TABLE_NAME, cv, COL_ID + "=?", new String[]{String.valueOf(car.getId())});
        if(result == -1) {
            Toast.makeText(context, R.string.saveNo, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, R.string.save, Toast.LENGTH_SHORT).show();
        }
    }
}
