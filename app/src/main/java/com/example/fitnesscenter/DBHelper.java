package com.example.fitnesscenter;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * This is the database helper class to update the information in the
 * Fitness center database.
 *
 * To use it, make a new DBHelper object in the onCreate method of an
 * activity and you can call its methods to update the database. You have to
 * pass the activity itself to the helper as well.
 *
 * Ex.
 * mydb = new DBHelper(this);
 * Account myAccount = mydb.getAccount("Username", "Password");
 *
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FitnessCenterData.db";

    public static final String ACCOUNTS_TABLE_NAME = "accounts";
    public static final String ACCOUNTS_COLUMN_ID = "id";
    public static final String ACCOUNTS_COLUMN_USERNAME = "username";
    public static final String ACCOUNTS_COLUMN_PASSWORD = "password";
    public static final String ACCOUNTS_COLUMN_TYPE = "accountType";

    public static final String CLASSTYPES_TABLE_NAME = "classtypes";
    public static final String CLASSTYPES_COLUMN_ID = "id";
    public static final String CLASSTYPES_COLUMN_NAME = "name";
    public static final String CLASSTYPES_COLUMN_DESCRIPTION = "description";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE accounts (id INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, password TEXT NOT NULL, accountType INTEGER NOT NULL DEFAULT 2)");
        db.execSQL("INSERT INTO accounts (username, password, accountType) VALUES (admin, admin123, 2)");
        db.execSQL("CREATE TABLE classtypes (id INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL UNIQUE, description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS accounts");
        db.execSQL("DROP TABLE IF EXISTS classtypes");
        onCreate(db);
    }

    public ArrayList<Account> getAllAccounts(){
        ArrayList<Account> accounts = new ArrayList<Account>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM accounts", null);
        res.moveToFirst();
        while (!res.isAfterLast()){
            @SuppressLint("Range") int id = res.getInt(res.getColumnIndex(ACCOUNTS_COLUMN_ID));
            @SuppressLint("Range") String username = res.getString(res.getColumnIndex(ACCOUNTS_COLUMN_USERNAME));
            @SuppressLint("Range") String password = res.getString(res.getColumnIndex(ACCOUNTS_COLUMN_PASSWORD));
            @SuppressLint("Range") AccountType accountType = AccountType.valueOf(res.getInt(res.getColumnIndex(ACCOUNTS_COLUMN_TYPE)));
            accounts.add(new Account(id, username, password, accountType));
        }
        if (!res.isClosed()){
            res.close();
        }
        return accounts;
    }

    public Account getAccount(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM accounts WHERE username = "+username+" AND password = "+password+"", null);
        res.moveToFirst();
        @SuppressLint("Range") int id = res.getInt(res.getColumnIndex(ACCOUNTS_COLUMN_ID));
        @SuppressLint("Range") AccountType accountType = AccountType.valueOf(res.getInt(res.getColumnIndex(ACCOUNTS_COLUMN_TYPE)));
        if (!res.isClosed()){
            res.close();
        }
        Account thisAccount = new Account(id, username, password, accountType);
        return thisAccount;
    }

    public boolean accountExists(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM accounts WHERE username = "+username+"", null);
        if ( res.getCount() > 0 ) {
            if (!res.isClosed()){
                res.close();
            }
            return true;
        }
        if (!res.isClosed()){
            res.close();
        }
        return false;
    }

    public int deleteAccount(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("accounts", "id = ?", new String[] { Integer.toString(id)});
    }

    public ArrayList<ClassType> getAllClassTypes(){
        ArrayList<ClassType> classtypes = new ArrayList<ClassType>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM classtypes", null);
        res.moveToFirst();
        while (!res.isAfterLast()){
            @SuppressLint("Range") int id = res.getInt(res.getColumnIndex(CLASSTYPES_COLUMN_ID));
            @SuppressLint("Range") String name = res.getString(res.getColumnIndex(CLASSTYPES_COLUMN_NAME));
            @SuppressLint("Range") String description = res.getString(res.getColumnIndex(CLASSTYPES_COLUMN_DESCRIPTION));
            classtypes.add(new ClassType(id, name, description));
        }
        if (!res.isClosed()){
            res.close();
        }
        return classtypes;
    }

    public boolean addClassType(String name, String description) {
        if ( classTypeExists(name) ) {
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLASSTYPES_COLUMN_NAME, name);
        contentValues.put(CLASSTYPES_COLUMN_DESCRIPTION, description);
        db.insert(CLASSTYPES_TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean classTypeExists(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM classtypes WHERE name = "+name+"", null);
        if ( res.getCount() > 0 ) {
            if (!res.isClosed()){
                res.close();
            }
            return true;
        }
        if (!res.isClosed()){
            res.close();
        }
        return false;
    }

    public boolean updateClassType(int id, String name, String description) {
        if ( classTypeExists(name) ) {
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CLASSTYPES_COLUMN_NAME, name);
        contentValues.put(CLASSTYPES_COLUMN_DESCRIPTION, description);
        db.update(CLASSTYPES_TABLE_NAME, contentValues, "id = ?", new String[] { Integer.toString(id)});
        return true;
    }

    public int deleteClassType(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("classtypes", "id = ?", new String[] { Integer.toString(id)});
    }

}
