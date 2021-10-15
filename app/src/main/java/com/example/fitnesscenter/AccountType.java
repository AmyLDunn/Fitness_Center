package com.example.fitnesscenter;

import java.util.HashMap;
import java.util.Map;

public enum AccountType {

    MEMBER(0),
    INSTRUCTOR(1),
    ADMIN(2);

    private int value;
    private static Map map = new HashMap<>();

    private AccountType(int value){
        this.value = value;
    }

    static {
        for (AccountType accountType: AccountType.values()){
            map.put(accountType.value, accountType);
        }
    }

    public static AccountType valueOf(int accountType){
        return (AccountType) map.get(accountType);
    }
}
