package com.example.fitnesscenter.helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fitnesscenter.helper.Account;
import com.example.fitnesscenter.helper.AccountType;
import com.example.fitnesscenter.helper.ClassType;

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
 * DBHelper mydb = new DBHelper(this);
 * Account myAccount = mydb.getAccount("Username", "Password");
 * ArrayList<Account> allAccounts = mydb.getAllAccounts();
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

    /**
     * This method runs the first time the database is created and no more
     * @param db is the database to check for
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE accounts (id INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL UNIQUE, password TEXT NOT NULL, accountType INTEGER NOT NULL DEFAULT 2)");
        db.execSQL("INSERT INTO accounts (username, password, accountType) VALUES ('admin', 'admin123', 2)");
        db.execSQL("CREATE TABLE classtypes (id INTEGER UNIQUE PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL UNIQUE, description TEXT)");
    }

    /**
     * This method upgrades the database if any changes are made
     * @param db is the database to be upgraded
     * @param oldVersion is the version number
     * @param newVersion is the new version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS accounts");
        db.execSQL("DROP TABLE IF EXISTS classtypes");
        onCreate(db);
    }

    /**
     * This returns all of the entries in the accounts table
     * @return an ArrayList<Account> of all Account objects created from the table
     */
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

    /**
     * This adds an account entry to the database and returns the account object associated with it
     */
    public Account addAccount(AccountType accountType, String username, String password){
        if ( accountExists(username) ) {
            return null;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ACCOUNTS_COLUMN_TYPE, accountType.getValue());
        contentValues.put(ACCOUNTS_COLUMN_USERNAME, username);
        contentValues.put(ACCOUNTS_COLUMN_PASSWORD, password);
        int id = (int) db.insert(ACCOUNTS_TABLE_NAME, null, contentValues);
        Account userAccount = new Account(id, username, password, accountType);
        return userAccount;
    }

    /**
     * This gets a specific account from the database
     * @param username is the username of the account
     * @param password is the password of the account
     * @return an Account object if the username-password pair is found in one entry
     *         or null if that pair was not found
     */
    public Account getAccount(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM accounts WHERE username = ? AND password = ?", new String[]{username, password});
        Account thisAccount = null;
        if ( res.getCount() > 0 ) {
            res.moveToFirst();
            @SuppressLint("Range") int id = res.getInt(res.getColumnIndex(ACCOUNTS_COLUMN_ID));
            @SuppressLint("Range") AccountType accountType = AccountType.valueOf(res.getInt(res.getColumnIndex(ACCOUNTS_COLUMN_TYPE)));
            thisAccount = new Account(id, username, password, accountType);
        }
        if (!res.isClosed()){
            res.close();
        }
        return thisAccount;
    }

    /**
     * This checks if a username is already taken for an account
     * @param username to be searched for
     * @return true if the username is being used or false if it's not in the database
     */
    public boolean accountExists(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM accounts WHERE username = ?", new String[]{username});
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

    /**
     * Deletes an account from the database
     * @param id of the account to be deleted
     * @return true if the account was successfully deleted
     */
    public int deleteAccount(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("accounts", "id = ?", new String[] { Integer.toString(id)});
    }

    /**
     * This returns all of the entries in the classtypes table
     * @return an ArrayList<ClassType> of all ClassType objects created from the table
     */
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

    /**
     * Adds a new class type to the classtypes table if the name isn't present
     * @param name of the class type to be added
     * @param description of the class type to be added
     * @return true if the class type was successfully added
     */
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

    /**
     * This checks if a name is already taken for a class type
     * @param name to be searched for
     * @return true if the name is being used or false if it's not in the database
     */
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

    /**
     * Updates the name and/or description of a class type if the new name is available
     * @param id of the class type to be updated
     * @param name new name of the class type
     * @param description new description of the class type
     * @return true if the update was successful
     */
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

    /**
     * Deletes a classtype from the database
     * @param id of the classtype to be deleted
     * @return true if the classtype was successfully deleted
     */
    public int deleteClassType(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("classtypes", "id = ?", new String[] { Integer.toString(id)});
    }

}
