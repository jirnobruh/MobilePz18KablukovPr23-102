package com.example.pr18kablukovpr23_102;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB {

    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 2; // Увеличили версию для добавления новых таблиц
    
    // Данные для Task 6
    private static final String DB_TABLE = "mytab";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IMG = "img";
    public static final String COLUMN_TXT = "txt";

    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_IMG + " integer, " +
                    COLUMN_TXT + " text" +
                    ");";

    // Данные для Task 7 (Lesson 53)
    public static final String COMPANY_TABLE = "company";
    public static final String COMPANY_ID = "_id";
    public static final String COMPANY_NAME = "name";

    public static final String PHONE_TABLE = "phone";
    public static final String PHONE_ID = "_id";
    public static final String PHONE_COMPANY = "company";
    public static final String PHONE_NAME = "name";

    private static final String COMPANY_CREATE = "create table " + COMPANY_TABLE + "("
            + COMPANY_ID + " integer primary key, "
            + COMPANY_NAME + " text" + ");";

    private static final String PHONE_CREATE = "create table " + PHONE_TABLE + "("
            + PHONE_ID + " integer primary key autoincrement, "
            + PHONE_COMPANY + " integer, "
            + PHONE_NAME + " text" + ");";

    private final Context mCtx;
    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void close() {
        if (mDBHelper != null) mDBHelper.close();
    }

    // Методы для Task 6
    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }

    // Методы для Task 7
    public Cursor getCompanyData() {
        return mDB.query(COMPANY_TABLE, null, null, null, null, null, null);
    }

    public Cursor getPhoneData(long companyID) {
        return mDB.query(PHONE_TABLE, null, PHONE_COMPANY + " = " + companyID, null, null, null, null);
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
            db.execSQL(COMPANY_CREATE);
            db.execSQL(PHONE_CREATE);

            // Данные для Task 6
            ContentValues cv = new ContentValues();
            for (int i = 1; i < 5; i++) {
                cv.put(COLUMN_TXT, "sometext " + i);
                cv.put(COLUMN_IMG, android.R.drawable.ic_menu_report_image);
                db.insert(DB_TABLE, null, cv);
            }

            // Данные для Task 7
            String[] companies = { "Samsung", "HTC", "LG" };
            for (int i = 0; i < companies.length; i++) {
                cv.clear();
                cv.put(COMPANY_ID, i + 1);
                cv.put(COMPANY_NAME, companies[i]);
                db.insert(COMPANY_TABLE, null, cv);
            }

            String[] phonesSamsung = { "Galaxy S II", "Galaxy Nexus", "Galaxy Note" };
            String[] phonesHTC = { "Sensation", "Desire HD", "Wildfire S", "Hero" };
            String[] phonesLG = { "Optimus", "Optimus 2X", "Optimus Black" };

            for (String s : phonesSamsung) {
                cv.clear();
                cv.put(PHONE_COMPANY, 1);
                cv.put(PHONE_NAME, s);
                db.insert(PHONE_TABLE, null, cv);
            }
            for (String s : phonesHTC) {
                cv.clear();
                cv.put(PHONE_COMPANY, 2);
                cv.put(PHONE_NAME, s);
                db.insert(PHONE_TABLE, null, cv);
            }
            for (String s : phonesLG) {
                cv.clear();
                cv.put(PHONE_COMPANY, 3);
                cv.put(PHONE_NAME, s);
                db.insert(PHONE_TABLE, null, cv);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion == 1 && newVersion == 2) {
                db.execSQL(COMPANY_CREATE);
                db.execSQL(PHONE_CREATE);
                // Заполнение можно вынести в отдельный метод, но для краткости добавим здесь
                ContentValues cv = new ContentValues();
                String[] companies = { "Samsung", "HTC", "LG" };
                for (int i = 0; i < companies.length; i++) {
                    cv.clear();
                    cv.put(COMPANY_ID, i + 1);
                    cv.put(COMPANY_NAME, companies[i]);
                    db.insert(COMPANY_TABLE, null, cv);
                }
                // ... и так далее для телефонов
            }
        }
    }
}