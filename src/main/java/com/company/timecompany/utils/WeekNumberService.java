package com.company.timecompany.utils;

import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class WeekNumberService {
    public int getWeekNumber(String dateString) throws ParseException, ParseException {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date date = df.parse(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public boolean isDateBefore(String enteredDate, String desiredDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate enteredLocalDate = LocalDate.parse(enteredDate, formatter);
        LocalDate desiredLocalDate = LocalDate.parse(desiredDate, formatter);
        return enteredLocalDate.isBefore(desiredLocalDate);
    }

    public static List<LocalDate> getDatesForWeek(int weekNumber, int year) {
        LocalDate date = LocalDate.ofYearDay(year, 1);
        while (date.getDayOfWeek() != DayOfWeek.MONDAY) {
            date = date.plusDays(1);
        }
        date = date.plusWeeks(weekNumber - 1);
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dates.add(date.plusDays(i));
        }
        return dates;
    }

}
