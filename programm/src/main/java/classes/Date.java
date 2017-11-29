package classes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public   class Date{
    private int minute;
    private int hour;
    private int day;
    private int month;
    private int year;
    private Boolean done;

    public Date(int minute, int hour, int day, int month, int year, Boolean done){
        this.minute = minute;
        this.hour = hour;
        this.day = day;
        this.month = month;
        this.year = year;
        this.done = done;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean bool) {
        this.done = bool;
    }

    public static String getCurrentWorkweek(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        LocalDate now = LocalDate.now();

        LocalDate monday = now.with(DayOfWeek.MONDAY);
        LocalDate friday = now.with(DayOfWeek.FRIDAY);

        String week =  monday.format(formatter)+" - "+  friday.format(formatter);

        return week;
    }
    public static String getNextWorkweek(String prevWeek){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");

        LocalDate now = LocalDate.parse(prevWeek.substring(0,8),formatter);
        LocalDate next = now.plusWeeks(1);

        LocalDate monday = next.with(DayOfWeek.MONDAY);
        LocalDate friday = next.with(DayOfWeek.FRIDAY);

        return monday.format(formatter)+" - "+friday.format(formatter);
    }
    public static String getPreviousWorkweek(String nexWeek){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");

        LocalDate now = LocalDate.parse(nexWeek.substring(0,8),formatter);
        LocalDate next = now.minusWeeks(1);

        LocalDate monday = next.with(DayOfWeek.MONDAY);
        LocalDate friday = next.with(DayOfWeek.FRIDAY);

        return monday.format(formatter)+" - "+  friday.format(formatter);
    }

    public String toString (Boolean date, Boolean time) {
        String text = "";

        if (date) {
            text += getFormatedDay() + "." + getFormatedMonth() + "." + year;
        }

        if (time) {
            text += " "+getFormatedHour() + ":" + getFormatedMinute();
        }

        return text;
    }


    public String getFormatedMinute (){
        if (minute < 10){
            return "0" + minute;
        } else {
            return "" + minute;
        }
    }

    public String getFormatedHour(){
        if (hour < 10){
            return "0" + hour;
        } else {
            return "" + hour;
        }
    }

    public String getFormatedDay (){
        if (day < 10){
            return "0" + day;
        } else {
            return "" + day;
        }
    }

    public String getFormatedMonth (){
        if (month < 10){
            return "0" + month;
        } else {
            return "" + month;
        }
    }
}
