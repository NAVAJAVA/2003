package com.example.nava.a2003;


import java.util.ArrayList;
import java.util.List;
import com.google.firebase.database.IgnoreExtraProperties;



/**
 * Created by Nava on 10/05/2017.
 */
@IgnoreExtraProperties
public class Event {
     String name;
     int eventId;
     String date;
     String time;
     String bankAccountDetails;
     ArrayList<Guest> guestList;
     int counterGuests;
     String invitationPath;

    public Event()
    {

    }
    public Event(String name, String date, String time,String bankAccountDetails, String invitationPath, ArrayList<Guest> guestList) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.guestList = guestList;
        this.bankAccountDetails = bankAccountDetails;
        this.invitationPath = invitationPath;
        this.counterGuests = 0;
    }

    public String getName() {
        return name;
    }

    public int getEventId() {
        return eventId;
    }

    public int getCounterGuests() {
        return counterGuests;
    }





    @Override public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ",date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", bank account details=" + bankAccountDetails +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getBankAccountDetails() {
        return bankAccountDetails;
    }

    public ArrayList<Guest> getGuestList() {
        return guestList;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setBankAccountDetails(String bankAccountDetails) {
        this.bankAccountDetails = bankAccountDetails;
    }

    public void setGuestList(ArrayList<Guest> guestList) {
        this.guestList = guestList;
    }

    public void setCounterGuests(int counterGuests) {
        this.counterGuests = counterGuests;
    }

    public String getInvitationPath() {
        return invitationPath;
    }
}


