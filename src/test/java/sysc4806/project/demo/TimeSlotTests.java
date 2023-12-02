package sysc4806.project.demo;

import org.junit.jupiter.api.Test;
import sysc4806.project.demo.presentationHandling.TimeSlotHandling;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TimeSlotTests {

    @Test
    public void testFromTimeSlotToMap(){
        HashMap<String, String> result = TimeSlotHandling.fromTimeslotToMap(TimeSlotHandling.DEFAULT_TIME);

        assertEquals("9:00-17:00", result.get("Tuesday"));
        assertEquals("9:00-17:00", result.get("Friday"));
        assertEquals("9:00-17:00", result.get("Monday"));
    }

    @Test
    public void testConvertToTimeslot(){
        String expected = "Monday: 9:00-17:00;Tuesday: 9:00-17:00;Wednesday: 9:00-17:00;Thursday: 9:00-17:00;Friday: 9:00-17:00;";

        List<String> test = new ArrayList<>();
        test.add("9:00-17:00");
        test.add("9:00-17:00");
        test.add("9:00-17:00");
        test.add("9:00-17:00");
        test.add("9:00-17:00");

        assertEquals(expected, TimeSlotHandling.convertToTimeslot(test));
    }
}
