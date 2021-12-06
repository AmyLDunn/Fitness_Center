package com.example.fitnesscenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.example.fitnesscenter.helper.Account;
import com.example.fitnesscenter.helper.ScheduledClass;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

public class ScheduledClassTest {

    ScheduledClass classOne, classTwo, classThree;

    @Before
    public void setup(){

        Calendar start = Calendar.getInstance();
        start.set(Calendar.YEAR, 2021);
        start.set(Calendar.MONTH, Calendar.NOVEMBER);
        start.set(Calendar.DAY_OF_MONTH, 10);
        start.set(Calendar.HOUR, 0);
        start.set(Calendar.MINUTE, 0);

        Calendar end = Calendar.getInstance();
        end.set(Calendar.YEAR, 2021);
        end.set(Calendar.MONTH, Calendar.NOVEMBER);
        end.set(Calendar.DAY_OF_MONTH, 10);
        end.set(Calendar.HOUR, 0);
        end.set(Calendar.MINUTE, 45);

        Calendar start1 = Calendar.getInstance();
        start1.set(Calendar.YEAR, 2021);
        start1.set(Calendar.MONTH, Calendar.NOVEMBER);
        start1.set(Calendar.DAY_OF_MONTH, 10);
        start1.set(Calendar.HOUR, 0);
        start1.set(Calendar.MINUTE, 30);

        Calendar end1 = Calendar.getInstance();
        end1.set(Calendar.YEAR, 2021);
        end1.set(Calendar.MONTH, Calendar.NOVEMBER);
        end1.set(Calendar.DAY_OF_MONTH, 10);
        end1.set(Calendar.HOUR, 1);
        end1.set(Calendar.MINUTE, 0);

        Calendar start2 = Calendar.getInstance();
        start2.set(Calendar.YEAR, 2021);
        start2.set(Calendar.MONTH, Calendar.NOVEMBER);
        start2.set(Calendar.DAY_OF_MONTH, 10);
        start2.set(Calendar.HOUR, 2);
        start2.set(Calendar.MINUTE, 0);

        Calendar end2 = Calendar.getInstance();
        end2.set(Calendar.YEAR, 2021);
        end2.set(Calendar.MONTH, Calendar.NOVEMBER);
        end2.set(Calendar.DAY_OF_MONTH, 10);
        end2.set(Calendar.HOUR, 3);
        end2.set(Calendar.MINUTE, 0);

        classOne = new ScheduledClass(0, "Jogging", "Bob", 10, 0, start.getTimeInMillis(), end.getTimeInMillis(), start.get(Calendar.DAY_OF_WEEK), "Beginner");
        classTwo = new ScheduledClass(0, "Jogging", "Bob", 10, 0, start1.getTimeInMillis(), end1.getTimeInMillis(), start1.get(Calendar.DAY_OF_WEEK), "Beginner");
        classThree = new ScheduledClass(1, "Boxing", "Jill", 30, 0, start2.getTimeInMillis(), end2.getTimeInMillis(), start2.get(Calendar.DAY_OF_WEEK), "Beginner");
    }

    @Test
    public void testGetDateString(){
        assertEquals("November 10, 2021", classOne.getDateString());
    }

    @Test
    public void testGetStartTimeString(){
        assertEquals("12:00 p.m.", classOne.getStartTimeString());
    }

    @Test
    public void testGetEndTimeString(){
        assertEquals("12:45 p.m.", classOne.getEndTimeString());
    }

    @Test
    public void testGetClassScheduleString(){
        assertEquals("November 10, 2021, 12:00 p.m. to 12:45 p.m.", classOne.getClassScheduleString());
    }

    @Test
    public void testEquals() {
        assertEquals(true, classOne.equals(classTwo));
        assertEquals(false, classOne.equals(classThree));
    }

    @Test
    public void testConflicting() {
        assertEquals(true, classOne.isConflicting(classTwo));
        assertEquals(false, classOne.isConflicting(classThree));
    }

}
