package com.example.fitnesscenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.fitnesscenter.database.DBHelper;
import com.example.fitnesscenter.helper.Account;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AccountTest {

    Account accountOne, accountTwo;

    @Before
    public void setup(){
        accountOne = new Account("Bob", 0);
        accountTwo = new Account("Joe", 1);
    }

    @Test
    public void testGetTypeName(){
        assertEquals("gym member", accountOne.getTypeName());
        assertNotEquals("gym member", accountTwo.getTypeName());
    }

}