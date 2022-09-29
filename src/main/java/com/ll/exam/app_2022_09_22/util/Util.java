package com.ll.exam.app_2022_09_22.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Util {
    public static class date {

        public static int getEndDayOf(int year, int month) {
            String yearMonth = year + "-";
            String monthStr = month + "";

            if ( monthStr.length() == 1 ) {
                monthStr = "0" + monthStr;
            }

            yearMonth += monthStr;

            return getEndDayOf(yearMonth);
        }

        public static int getEndDayOf(String yearMonth) {
            LocalDate convertedDate = LocalDate.parse(yearMonth + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            convertedDate = convertedDate.withDayOfMonth(
                    convertedDate.getMonth().length(convertedDate.isLeapYear()));

            return convertedDate.getDayOfMonth();
        }
    }
}
