package com.example.fitnesscenter.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.fitnesscenter.helper.Account;
import com.example.fitnesscenter.helper.ScheduledClass;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    public static final String CLASSES_COLUMN_DIFFICULTY = "difficulty";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /**
     * This is called automatically the first time that the app is opened.
     * It creates all of the databases and populates the accounts database with the admin account
     * @param db
     */
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
                CLASSES_COLUMN_END+" INTEGER, "+
                CLASSES_COLUMN_DIFFICULTY+" TEXT)");
        db.execSQL("INSERT INTO "+ACCOUNTS_TABLE_NAME+"("+ACCOUNTS_COLUMN_USERNAME+", "+ACCOUNTS_COLUMN_PASSWORD+", "+
                ACCOUNTS_COLUMN_TYPE+") VALUES (?, ?, ?)", new String[]{"admin", "admin123", "2"});
    }

    /**
     * This is called automatically when the version numbers of the database don't match
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ACCOUNTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CLASS_TYPES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+CLASSES_TABLE_NAME);
        onCreate(db);
    }

    /**
     * This searches the accounts list for all accounts
     * @return The cursor that can access all accounts and their information
     */
    public Cursor getAllAccounts(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+ACCOUNTS_TABLE_NAME+" WHERE "+ACCOUNTS_COLUMN_TYPE+" IN (0, 1)", null);
    }

    /**
     * This searches for a specific account in the accounts table
     * @param username The username to search for
     * @param password The password to search for
     * @return An Account object with the account data required
     */
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

    /**
     * This checks if an account already exists
     * @param username The username to check for
     * @return True if the username is taken and false otherwise
     */
    public boolean accountExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+ACCOUNTS_TABLE_NAME+" WHERE "+ACCOUNTS_COLUMN_USERNAME+" = ?", new String[]{username});
        if ( result.getCount() > 0 ) {
            return true;
        }
        return false;
    }

    /**
     * This adds a new row to the accounts table
     * @param username The username of the new account
     * @param password The password of the new account
     * @param type The type of the new account (0 = member, 1 = instructor, 2 = admin)
     */
    public void addAccount(String username, String password, int type){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO "+ACCOUNTS_TABLE_NAME+"("+ACCOUNTS_COLUMN_USERNAME+", "+ACCOUNTS_COLUMN_PASSWORD+", "+
                ACCOUNTS_COLUMN_TYPE+") VALUES (?, ?, ?)", new String[]{username, password, String.valueOf(type)});
    }

    /**
     * This deletes an account
     * @param id Id of the account to be deleted
     */
    public void deleteAccount(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT FROM "+ACCOUNTS_TABLE_NAME+" WHERE "+ACCOUNTS_COLUMN_ID+" = "+id, null);
        cursor.moveToFirst();
        String username = cursor.getString(cursor.getColumnIndexOrThrow(ACCOUNTS_COLUMN_USERNAME));
        cursor.close();
        db.execSQL("DELETE FROM "+ACCOUNTS_TABLE_NAME+" WHERE "+ACCOUNTS_COLUMN_ID+"="+id+"");
        db.execSQL("DELETE FROM "+CLASSES_TABLE_NAME+" WHERE "+CLASSES_COLUMN_INSTRUCTOR+" = ?", new String[]{username});
    }

    /**
     * This gets all of the class types (not scheduled classes) in the classTypes table
     * @return A cursor with access to all class types
     */
    public Cursor getAllClassTypes(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+CLASS_TYPES_TABLE_NAME+"", null);
    }

    /**
     * This gets all of the class type names as Strings
     * @return An arraylist of all the class type names
     */
    public ArrayList<String> getAllClassTypeNames(){
        ArrayList<String> myList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+CLASS_TYPES_TABLE_NAME, null);
        if ( cursor.moveToFirst() ){
            do {
                myList.add(cursor.getString(cursor.getColumnIndexOrThrow(CLASS_TYPES_COLUMN_NAME)));
            } while ( cursor.moveToNext());
        }
        return myList;
    }

    /**
     * Checks to see if a class type exists in the database
     * @param name The class type name to search for
     * @return True if the class type is already there, false otherwise
     */
    public boolean classTypeExists(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+CLASS_TYPES_TABLE_NAME+" WHERE "+CLASS_TYPES_COLUMN_NAME+" = ?", new String[]{name});
        if ( result.getCount() > 0 ){
            return true;
        }
        return false;
    }

    /**
     * Adds a new class type to the database
     * @param name The name of the new class type
     * @param description The description of the new class type
     */
    public void addClassType(String name, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO "+CLASS_TYPES_TABLE_NAME+" ("+CLASS_TYPES_COLUMN_NAME+", "+CLASS_TYPES_COLUMN_DESCRIPTION+") VALUES (?, ?)", new String[]{name, description});
    }

    /**
     * Updates a class type in the database
     * @param id The id of the class type to be updated
     * @param name The new name of the class type
     * @param description The new description of the class type
     */
    public void updateClassType(int id, String name, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT FROM "+CLASS_TYPES_TABLE_NAME+" WHERE "+CLASS_TYPES_COLUMN_ID+" = "+id, null);
        cursor.moveToFirst();
        String oldName = cursor.getString(cursor.getColumnIndexOrThrow(CLASS_TYPES_COLUMN_NAME));
        cursor.close();
        db.execSQL("UPDATE "+CLASS_TYPES_TABLE_NAME+" SET "+CLASS_TYPES_COLUMN_NAME+" = ?, "+CLASS_TYPES_COLUMN_DESCRIPTION+" = ? WHERE "+CLASS_TYPES_COLUMN_ID+" = ?", new String[]{name, description, String.valueOf(id)});
        db.execSQL("UPDATE "+CLASSES_TABLE_NAME+" SET "+CLASSES_COLUMN_TYPE+" = ? WHERE "+CLASSES_COLUMN_TYPE+" = ?", new String[]{name, oldName});
    }

    /**
     * Deletes a class type from the database
     * @param id The id of the class type to be deleted
     */
    public void deleteClassType(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT FROM "+CLASS_TYPES_TABLE_NAME+" WHERE "+CLASS_TYPES_COLUMN_ID+" = "+id, null);
        cursor.moveToFirst();
        String type = cursor.getString(cursor.getColumnIndexOrThrow(CLASS_TYPES_COLUMN_NAME));
        cursor.close();
        db.execSQL("DELETE FROM "+CLASS_TYPES_TABLE_NAME+" WHERE "+CLASS_TYPES_COLUMN_ID+"="+id+"");
        db.execSQL("DELETE FROM "+CLASSES_TABLE_NAME+" WHERE "+CLASSES_COLUMN_TYPE+" = ?", new String[]{type});
    }

    /**
     * This adds a specific scheduled class to the database
     * @param type The type of class taken from the class_types table
     * @param instructor The instructor's name
     * @param capacity The max capacity
     * @param startTime The start date and time
     * @param endTime The end date and time
     */
    public void addClass(String type, String instructor, int capacity, long startTime, long endTime, String difficulty){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO "+CLASSES_TABLE_NAME+" ("+
                CLASSES_COLUMN_TYPE+", "+
                CLASSES_COLUMN_INSTRUCTOR+", "+
                CLASSES_COLUMN_CAPACITY+", "+
                CLASSES_COLUMN_START+", "+
                CLASSES_COLUMN_END+", "+
                CLASSES_COLUMN_DIFFICULTY+") VALUES (?, ?, ?, ?, ?, ?)",
                new String[]{type, instructor, String.valueOf(capacity), String.valueOf(startTime), String.valueOf(endTime), difficulty});
    }

    /**
     * This updates an existing scheduled class in the database
     * @param id The id of the class to be updated
     * @param type The new type of the class taken from the class_types table
     * @param instructor The name of the new instructor
     * @param capacity The new capacity
     * @param startTime The new start date and time
     * @param endTime The new end date and time
     */
    public void updateClass(int id, String type, String instructor, int capacity, long startTime, long endTime, String difficulty){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+CLASSES_TABLE_NAME+" SET "+
                CLASSES_COLUMN_TYPE+" = ?, "+
                CLASSES_COLUMN_INSTRUCTOR+" = ?, "+
                CLASSES_COLUMN_CAPACITY+" = "+capacity+", "+
                CLASSES_COLUMN_START+" = "+startTime+", "+
                CLASSES_COLUMN_END+" = "+endTime+", "+
                CLASSES_COLUMN_DIFFICULTY+" = ? WHERE "+
                CLASSES_COLUMN_ID+" = "+id, new String[]{type, instructor, difficulty});
    }

    /**
     * This deletes a scheduled class
     * @param id The id of the class to be deleted
     */
    public void deleteClass(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+CLASSES_TABLE_NAME+" WHERE "+CLASSES_COLUMN_ID+" = ?", new String[]{String.valueOf(id)});
    }

    /**
     * This checks to see if a class of a particular type is scheduled on a particule day
     * @param type The type of the class
     * @param startTime The day to check
     * @return A String with the name of the instructor that is teaching that class or null if
     *         no class is scheduled
     */
    public String classExists(String type, long startTime){
        Calendar day = Calendar.getInstance();
        day.setTime(new Date(startTime));
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        startTime = day.getTime().getTime();
        day.add(Calendar.DATE, 1);
        long endTime = day.getTime().getTime();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+CLASSES_TABLE_NAME+" WHERE "+
                CLASSES_COLUMN_TYPE+" = ? AND "+CLASSES_COLUMN_START+" >= "+startTime+" AND "+
                CLASSES_COLUMN_END+" < "+endTime, new String[]{type});
        cursor.moveToFirst();
        if ( cursor.getCount() > 0){
            return cursor.getString(cursor.getColumnIndexOrThrow(CLASSES_COLUMN_INSTRUCTOR));
        }
        return null;
    }

    /**
     * This gets all the information for one particular scheduled class
     * @param id The id of the scheduled class
     * @return A ScheduledClass object that holds all of the database information on it
     */
    public ScheduledClass getClass(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+CLASSES_TABLE_NAME+" WHERE "+CLASSES_COLUMN_ID+" = "+id, null);
        cursor.moveToFirst();
        int classId = cursor.getInt(cursor.getColumnIndexOrThrow(CLASSES_COLUMN_ID));
        String type = cursor.getString(cursor.getColumnIndexOrThrow(CLASSES_COLUMN_TYPE));
        int capacity = cursor.getInt(cursor.getColumnIndexOrThrow(CLASSES_COLUMN_CAPACITY));
        long startTime = cursor.getLong(cursor.getColumnIndexOrThrow(CLASSES_COLUMN_START));
        long endTime = cursor.getLong(cursor.getColumnIndexOrThrow(CLASSES_COLUMN_END));
        String difficulty = cursor.getString(cursor.getColumnIndexOrThrow(CLASSES_COLUMN_DIFFICULTY));
        return new ScheduledClass(classId, type, capacity, startTime, endTime, difficulty);
    }

    /**
     * This searches for all of the classes that match the searchKey
     * @param searchKey This is the string to filter the classes by
     * @return A cursor that can iterate over all relevant classes
     */
    public Cursor getAllClasses(String searchKey){
        SQLiteDatabase db = this.getWritableDatabase();
        if ( searchKey == null ){
            return db.rawQuery("SELECT * FROM "+CLASSES_TABLE_NAME, null);
        }
        return db.rawQuery("SELECT * FROM "+CLASSES_TABLE_NAME+" WHERE "+CLASSES_COLUMN_TYPE+" LIKE ? OR "+CLASSES_COLUMN_INSTRUCTOR+" LIKE ?", new String[]{"%"+searchKey+"%", "%"+searchKey+"%"});
    }

    /**
     * Gets all the classes that a particular instructor is teaching
     * @param username The username of the instructor
     * @return A cursor that can iterate over all of that instructor's scheduled classes
     */
    public Cursor getMyClasses(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM "+CLASSES_TABLE_NAME+" WHERE "+CLASSES_COLUMN_INSTRUCTOR+" = ?", new String[]{username});
    }

}
