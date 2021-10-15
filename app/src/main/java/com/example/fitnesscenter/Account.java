package com.example.fitnesscenter;

public class Account {

    int id;
    String username;
    String password;
    AccountType accountType;

    Account(int id, String username, String password, AccountType accountType){
        this.id = id;
        this.username = username;
        this.password = password;
        this.accountType = accountType;
    }

}
