package com.example.artapp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class CustomDB extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME = "movie.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "phone_info";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "phone_name";
    public static final String COLUMN_PHONE_NUMBER = "art_name";
    public static final String COLUMN_RATING = "rating";

    public static final String COLUMN_REVIEW = "review";


    public static final String COLUMN_PHONE = "phone_number"; // COLUMN_PHONE을 COLUMN_PHONE_NUMBER로 변경


    public CustomDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_PHONE_NUMBER + " TEXT, " +
                "rating REAL DEFAULT 0.0, " +
                COLUMN_REVIEW + " TEXT);"; // "review" 열 추가
        db.execSQL(query);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    /**
     * 연락처 전체 가져오기
     *
     * @return
     */
    public Cursor readAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    /**
     * 작품 등록
     *
     * @param name        작가 이름
     * @param artworkName 작품명
     * @param rating      평가 값
     */
    public void addArtwork(String name, String artworkName, float rating, String review) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PHONE_NUMBER, artworkName);
        cv.put(COLUMN_RATING, rating);
        cv.put(COLUMN_REVIEW, review); // 리뷰 추가
        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "작품 등록 실패", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "작품 등록 성공", Toast.LENGTH_SHORT).show();
        }
    }
}


