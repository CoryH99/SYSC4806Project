package sysc4806.project.demo.presentationHandling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TimeSlotHandling {

    // FORMAT:
    /*
    Day: START-END;Day2: START-END....
     */
    public static final String DEFAULT_TIME = "Monday: 9:00-17:00;Tuesday: 9:00-17:00;Wednesday: 9:00-17:00;Thursday: 9:00-17:00;Friday: 9:00-17:00;";

    public static final String MONDAY = "Monday";
    public static final String TUESDAY = "Tuesday";
    public static final String WEDNESDAY = "Wednesday";
    public static final String THURSDAY = "Thursday";
    public static final String FRIDAY = "Friday";

    private static final String TIMESLOT_DIVIDER = "-";


    public static String translateToTimeSlot(String start, String end){
        return start +
                TIMESLOT_DIVIDER +
                end;
    }

    public static List<String> getStartEnd(String timeslot){
        String[] tempList = timeslot.split(TIMESLOT_DIVIDER);
        return new ArrayList<>(Arrays.asList(tempList).subList(0, 2));
    }

    public static String convertToTimeslot(List<String> availabilities){
        StringBuilder resultTimeslot = new StringBuilder();
        String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        for (int i=0; i<5; i++){
            resultTimeslot.append(DAYS[i]).append(": ");
            resultTimeslot.append(availabilities.get(i)).append(";");
        }
        return resultTimeslot.toString();
    }

    public static HashMap<String, String> fromTimeslotToMap(String timeslot){
        HashMap<String, String> weekMap = new HashMap<>();
        String[] days = timeslot.split(";");
        for (String day: days){
            String[] day_split = day.split(": ");
            weekMap.put(day_split[0], day_split[1]);
        }

        return weekMap;
    }


}
