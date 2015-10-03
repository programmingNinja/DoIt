package com.example.rshah4.doit;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by rshah4 on 9/24/15.
 */
public class Tasks implements Parcelable{
    int id;
    String task;
    long createdTime;
    long completeTime;
    int status;
    int priority;

    Tasks() {
    }

    Tasks(String task) {

        this.task = task;
        this.priority = 0;
        this.status = 0;
        this.createdTime = new Date().getTime();
        this.completeTime = 0;
    }

    Tasks(String name, long createdTime, long completeTime, int status, int priority) {
        this.task = name;
        this.completeTime = completeTime;
        this.createdTime = createdTime;
        this.status = status;
        this.priority = priority;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(long completeTime) {
        this.completeTime = completeTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(task);
        dest.writeLong(createdTime);
        dest.writeLong(completeTime);
        dest.writeInt(status);
        dest.writeInt(priority);
        dest.writeInt(id);

    }

    @Override
    public String toString() {
        return "Id="+this.id
        +"\nname="+this.task
        +"\nstatus="+this.status
        +"\npriority="+this.priority
        +"\nCreated="+this.createdTime
        +"\nCompleted="+this.completeTime ;
    }
    public static final Parcelable.Creator<Tasks> CREATOR = new Parcelable.Creator<Tasks>() {
                public Tasks createFromParcel(Parcel in) {

                    Tasks temp = new Tasks(in.readString(), in.readLong(), in.readLong(), in.readInt(), in.readInt());
                    temp.setId(in.readInt());
                    return temp;
                }

        public Tasks[] newArray(int size) {
                         return new Tasks[size];
                     }
             };
}
