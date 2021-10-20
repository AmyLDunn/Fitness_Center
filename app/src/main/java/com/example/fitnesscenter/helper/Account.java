package com.example.fitnesscenter.helper;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {

    String username;
    int type;

    public Account(String username, int type){
        this.username = username;
        this.type = type;
    }

    public String getUsername(){
        return username;
    }

    public int getType(){
        return type;
    }

    public String getTypeName(){
        if ( type == 0 ) {
            return "gym member";
        }
        if (type == 1 ) {
            return "instructor";
        }
        return "administrator";
    }

    protected Account(Parcel in) {
        username = in.readString();
        type = in.readInt();
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

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeInt(type);
    }

}
