package com.example.fitnesscenter.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fitnesscenter.helper.Account;

/**
 * This is the database helper class to update the information in the
 * Fitness center database.
 *
 * To use it, make a new DBHelper object in the onCreate method of an
 * activity and you can call its methods to update the database. You have to
 * pass the activity itself to the helper as well.
 *
 * Ex.
 * DBHelper mydb = new DBHelper(this);
 * Account myAccount = mydb.getAccount("Username", "Password");
 * ArrayList<Account> allAccounts = mydb.getAllAccounts();
 *
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "fitness_app_database";

    public static final String ACCOUNTS_TABLE_NAME = "accounts";
    public static final String ACCOUNTS_COLUMN_ID = "_id";
    public static final String ACCOUNTS_COLUMN_USERNAME = "username";
    public static final String ACCOUNTS_COLUMN_PASSWORD = "password";
    public static final String ACCOUNTS_COLUMN_TYPE = "type";

    public static final String CLASS_TYPES_TABLE_NAME = "class_types";
    public static final String CLASS_TYPES_COLUMN_ID = "_id";
    public static final String CLASS_TYPES_COLUMN_NAME = "name";
    public static final String CLASS_TYPES_COLUMN_DESCRIPTION = "description";

    public static final String CLASSES_TABLE_NAME = "scheduled_classes";
    public static final String CLASSES_COLUMN_ID = "_id";
    public static final String CLASSES_COLUMN_TYPE = "type";
    public static final String CLASSES_COLUMN_INSTRUCTOR = "instructor";
    public static final String CLASSES_COLUMN_CAPACITY = "capacity";
    public static final String CLASSES_COLUMN_START = "startTime";
    public static final String CLASSES_COLUMN_END = "endTime";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ACCOUNTS_TABLE_NAME+
                "("+ACCOUNTS_COLUMN_ID+" INTEGER PRIMARY KEY, "+
                ACCOUNTS_COLUMN_USERNAME+" TEXT, "+
                ACCOUNTS_COLUMN_PASSWORD+" TEXT, "+
                ACCOUNTS_COLUMN_TYPE+" INTEGER)");
        db.execSQL("CREATE TABLE "+CLASS_TYPES_TABLE_NAME+
                "("+CLASS_TYPES_COLUMN_ID+" INTEGER PRIMARY KEY, "+
                CLASS_TYPES_COLUMN_NAME+" TEXT, "+
                CLASS_TYPES_COLUMN_DESCRIPTION+" TEXT)");
        db.execSQL("CREATE TABLE "+CLASSES_TABLE_NAME+"("+
                CLASSES_COLUMN_ID+" INTEGER PRIMARY KEY, "+
                CLASSES_COLUMN_TYPE+" TEXT, "+
                CLASSES_COLUMN_INSTRUCTOR+" TEXT, "+
                CLASSES_COLUMN_CAPACITY+" INTEGER, "+
                CLASSES_COLUMN_START+" INTEGER, "+
                CLASSES_COLUMN_END+"INTEGER)");
        db.execSQL("INSERT INTO "+ACCOUNTS_TABLE_NAME+"("+ACCOUNTS_COLUMN_USERNAME+", "+ACCOUNTS_COLUMN_PASSWORD+", "+
                ACCOUNTS_COLUMN_TYPE+") VALUES (?, ?, ?)", new String[]{"admin", "admin123", "2"});
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ACCOUNTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CLASS_TYPES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CLASSES_TABLE_NAME);
        onCreate(db);
    }

    public Cursor getAllAccounts(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+ACCOUNTS_TABLE_NAME+" WHERE "+ACCOUNTS_COLUMN_TYPE+" IN (0, 1)", null);
    }

    public Account getAccount(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+ACCOUNTS_TABLE_NAME+" WHERE "+ACCOUNTS_COLUMN_USERNAME+" = ? AND "+ACCOUNTS_COLUMN_PASSWORD+" = ?",new String[]{username, password});
        Account thisAccount = null;
        if (result.getCount() > 0){
            result.moveToFirst();
            int accountType = result.getInt(result.getColumnIndexOrThrow(ACCOUNTS_COLUMN_TYPE));
            thisAccount = new Account(username, accountType);
        }
        return thisAccount;
    }

    public boolean accountExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+ACCOUNTS_TABLE_NAME+" WHERE "+ACCOUNTS_COLUMN_USERNAME+" = ?", new String[]{username});
        if ( result.getCount() > 0 ) {
            return true;
        }
        return false;
    }

    public void addAccount(String username, String password, int type){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO "+ACCOUNTS_TABLE_NAME+"("+ACCOUNTS_COLUMN_USERNAME+", "+ACCOUNTS_COLUMN_PASSWORD+", "+
                ACCOUNTS_COLUMN_TYPE+") VALUES (?, ?, ?)", new String[]{username, password, String.valueOf(type)});
    }

    public void deleteAccount(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ACCOUNTS_TABLE_NAME+" WHERE "+ACCOUNTS_COLUMN_ID+"="+id+"");
    }

    public Cursor getAllClassTypes(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+CLASS_TYPES_TABLE_NAME+"", null);
    }

    public boolean classTypeExists(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+CLASS_TYPES_TABLE_NAME+" WHERE "+CLASS_TYPES_COLUMN_NAME+" = ?", new String[]{name});
        if ( result.getCount() > 0 ){
            return true;
        }
        return false;
    }

    public void addClassType(String name, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO "+CLASS_TYPES_TABLE_NAME+" ("+CLASS_TYPES_COLUMN_NAME+", "+CLASS_TYPES_COLUMN_DESCRIPTION+") VALUES (?, ?)", new String[]{name, description});
    }

    public void updateClassType(int id, String name, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+CLASS_TYPES_TABLE_NAME+" SET "+CLASS_TYPES_COLUMN_NAME+" = ?, "+CLASS_TYPES_COLUMN_DESCRIPTION+" = ? WHERE "+CLASS_TYPES_COLUMN_ID+" = ?", new String[]{name, description, String.valueOf(id)});
    }

    public void deleteClassType(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+CLASS_TYPES_TABLE_NAME+" WHERE "+CLASS_TYPES_COLUMN_ID+"="+id+"");
    }

    public void addClass(String type, String instructor, int capacity, long startTime, long endTime){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO "+CLASSES_TABLE_NAME+" ("+
                CLASSES_COLUMN_TYPE+", "+
                CLASSES_COLUMN_INSTRUCTOR+", "+
                CLASSES_COLUMN_CAPACITY+", "+
                CLASSES_COLUMN_START+", "+
                CLASSES_COLUMN_END+") VALUES (?, ?, ?, ?, ?)",
                new String[]{type, instructor, String.valueOf(capacity), String.valueOf(startTime), String.valueOf(endTime)});
    }

    public void updateClass(int id, String type, String instructor, int capacity, long startTime, long endTime){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+CLASSES_TABLE_NAME+" SET "+
                CLASSES_COLUMN_TYPE+" = ?, "+
                CLASSES_COLUMN_INSTRUCTOR+" = ?, "+
                CLASSES_COLUMN_CAPACITY+" = "+capacity+", "+
                CLASSES_COLUMN_START+" = "+startTime+", "+
                CLASSES_COLUMN_END+" = "+endTime+" WHERE "+
                CLASSES_COLUMN_ID+" = "+id, new String[]{type, instructor});
    }

    public void deleteClass(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+CLASSES_TABLE_NAME+" WHERE "+CLASSES_COLUMN_ID+" = "+id, null);
    }

    public Cursor getAllClasses(String searchKey){
        SQLiteDatabase db = this.getWritableDatabase();
        if ( searchKey != null ){
            return db.rawQuery("SELECT * FROM "+CLASSES_TABLE_NAME, null);
        }
        return db.rawQuery("SELECT * FROM "+CLASSES_TABLE_NAME+" WHERE "+CLASSES_COLUMN_TYPE+" LIKE ? OR "+CLASSES_COLUMN_INSTRUCTOR+" LIKE ?", new String[]{searchKey, searchKey});
    }

    public Cursor getMyClasses(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+CLASSES_TABLE_NAME+" WHERE "+CLASSES_COLUMN_INSTRUCTOR+" = ?", new String[]{username});
    }

}
