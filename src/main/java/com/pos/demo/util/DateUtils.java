package com.pos.demo.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

public class DateUtils {

    public static boolean isIndependenceDay(LocalDate date) {
        if (date.getMonth() == Month.JULY && date.getDayOfMonth() == 4 && !isWeekend(date)) {
            return true;
        }
        return (date.getMonth() == Month.JULY && date.getDayOfMonth() == 5 && date.getDayOfWeek() == DayOfWeek.MONDAY)
                || (date.getMonth() == Month.JULY && date.getDayOfMonth() == 3 && date.getDayOfWeek() == DayOfWeek.FRIDAY);
    }

    public static boolean isLaborDay(LocalDate date) {
        return date.getMonth() == Month.SEPTEMBER && date.getDayOfWeek() == DayOfWeek.MONDAY && date.getDayOfMonth() <= 7;
    }

    public static boolean isApprovedFreeHoliday(LocalDate date) {
        return isIndependenceDay(date) || isLaborDay(date);
    }

    private static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }
}
