package com.example.addressapp2;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class PhoneBookDB extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "address.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "phone_info";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "phone_name";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_PHONE = "phone_number"; // COLUMN_PHONE을 COLUMN_PHONE_NUMBER로 변경


    public PhoneBookDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE_NUMBER + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * 연락처 전체 가져오기
     * @return
     */
    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    /**
     * 연락처 등록
     * @param name //이름
     * @param phone_number // 전화번호
     */
    public void addPhoneNumber(String name, String phone_number){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PHONE_NUMBER, phone_number);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "실패", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "성공", Toast.LENGTH_SHORT).show();
        }
    }

    public void deletePhoneNumber(String name, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = PhoneBookDB.COLUMN_NAME + " = ? AND " + PhoneBookDB.COLUMN_PHONE_NUMBER + " = ?";
        String[] whereArgs = {name, phone};
        db.delete(PhoneBookDB.TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

}
