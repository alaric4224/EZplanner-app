package com.example.myapplication;

public class SampleData {
    //private int timenum;
    private String avail_date;
    private String avail_time;

    public SampleData(String avail_date, String avail_time)
    {
        //this.timenum = timenum;
        this.avail_date = avail_date;
        this.avail_time = avail_time;
    }
//    public int getTimenum()
//    {
//        return this.timenum;
//    }

    public String getAvail_Date()
    {
        return this.avail_date;
    }

    public String getAvail_Time()
    {
        return this.avail_time;
    }
}