package com.example.fitnesscenter.helper;

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

    /**
     * Converts an integer into an AccountType
     * @param accountType the integer value to be converted
     * @return the AccountType corresponding to that integer
     */
    public static AccountType valueOf(int accountType){
        return (AccountType) map.get(accountType);
    }

    /**
     * Converts an AccountType into an integer
     * @return the integer value of the AccountType object
     */
    public int getValue(){
        return value;
    }
}
