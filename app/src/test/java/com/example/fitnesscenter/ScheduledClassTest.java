package com.example.fitnesscenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.example.fitnesscenter.helper.Account;
import com.example.fitnesscenter.helper.ScheduledClass;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

public class ScheduledClassTest {

    ScheduledClass classOne;

    @Before
    public void setup(){
        Calendar start = Calendar.getInstance();
        start.set(Calendar.YEAR, 2021);
        start.set(Calendar.MONTH, Calendar.NOVEMBER);
        start.set(Calendar.DAY_OF_MONTH, 10);
        start.set(Calendar.HOUR, 12);
        start.set(Calendar.MINUTE, 0);

        Calendar end = Calendar.getInstance();
        end.set(Calendar.YEAR, 2021);
        end.set(Calendar.MONTH, Calendar.NOVEMBER);
        end.set(Calendar.DAY_OF_MONTH, 10);
        end.set(Calendar.HOUR, 12);
        end.set(Calendar.MINUTE, 45);

        classOne = new ScheduledClass(0, "Jogging", "Bob", 10, 0, start.getTimeInMillis(), end.getTimeInMillis(), start.get(Calendar.DAY_OF_WEEK), "Beginner");
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

}
