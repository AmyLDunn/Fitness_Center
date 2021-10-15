package com.example.fitnesscenter;

public class Account {

    int id;
    String username;
    String password;
    int accountType;

    Account(int id, String username, String password, int accountType){
        this.id = id;
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }

}
