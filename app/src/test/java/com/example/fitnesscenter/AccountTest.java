package com.example.fitnesscenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.fitnesscenter.database.DBHelper;
import com.example.fitnesscenter.helper.Account;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AccountTest {

    Account accountOne, accountTwo, accountThree;
    ArrayList<String> list1, list2;

    @Before
    public void setup(){

        //Setting up different accounts
        accountOne = new Account(0, "Bob", 0);
        accountTwo = new Account(1, "Joe", 1);
        accountThree = new Account(2, "Jim", -1);

        //Setting up different lists of classes
        list1 = new ArrayList();
        list2 = new ArrayList();

        list1.add("running");
        list1.add("boxing");
        list1.add("weight training");
        list1.add("calisthenics");
        list1.add("gymnastics");

        list2.add("calisthenics");
        list2.add("boxing");
        list2.add("gymnastics");

    }

    @Test
    public void testGetTypeName(){
        assertEquals("gym member", accountOne.getTypeName(accountOne.getType()));
        assertEquals("instructor", accountTwo.getTypeName(accountTwo.getType()));
        assertEquals("administrator", accountThree.getTypeName(accountThree.getType()));
    }

    @Test
    public void testAvailableCoursesNotEnrolled(){
        ArrayList<String> list3 = DBHelper.getAvailableCoursesNotEnrolled(list1,list2);
        assertEquals(2, list3.size());
    }

    @Test
    public void testUsernames() {
        assertEquals(true, accountOne.sameUsername(accountOne));
        assertEquals(false, accountOne.sameUsername(accountTwo));
    }



}