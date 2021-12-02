package com.example.fitnesscenter.helper;

public class Account {

    int id;
    String username;
    int type;

    public Account(int id, String username, int type){
        this.id = id;
        this.username = username;
        this.type = type;
    }

    public int getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public int getType(){
        return type;
    }

    public static String getTypeName(int type){
        if ( type == 0 ) {
            return "gym member";
        }
        if (type == 1 ) {
            return "instructor";
        }
        return "administrator";
    }

}
