package com.example.fitnesscenter;

import android.annotation.SuppressLint;
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

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE accounts (id INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, password TEXT NOT NULL, accountType INTEGER NOT NULL DEFAULT 2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS accounts");
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

    public Integer deleteAccount(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("accounts", "id = ?", new String[] { Integer.toString(id)});
    }
}
