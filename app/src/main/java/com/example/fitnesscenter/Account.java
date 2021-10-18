package com.example.fitnesscenter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The Account class has to implement Parcelable so that it can be passed between different activities
 */
public class Account implements Parcelable {

    int id;
    String username;
    String password;
    AccountType accountType;

    /**
     * Default constructor when making a new Account object
     * @param id the id of the account
     * @param username the username of the account
     * @param password the password of the account
     * @param accountType the type of the account
     */
    Account(int id, String username, String password, AccountType accountType){
        this.id = id;
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }

    /**
     * Parcelable constructor. This is used to rebuild this same account object in another activity
     * @param in the parcel object that was passed
     */
    protected Account(Parcel in) {
        id = in.readInt();
        username = in.readString();
        password = in.readString();
        accountType = AccountType.valueOf(in.readInt());
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * This turns the Account object into a Parcel to be passed
     * @param parcel
     * @param i
     */
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeInt(accountType.getValue());
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public String getUsername() {
        return username;
    }
}
