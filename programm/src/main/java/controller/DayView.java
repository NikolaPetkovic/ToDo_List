package controller;

import classes.ReTask;

import java.util.ArrayList;

public class DayView {
    private String name;
    private ArrayList<ReTask> dailyToDoList;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ReTask> getDailyToDoList() {
        return dailyToDoList;
    }

    public void setDailyToDoList(ArrayList<ReTask> dailyToDoList) {
        this.dailyToDoList = dailyToDoList;
    }
}
