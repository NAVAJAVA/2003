package com.example.nava.a2003.General;


import java.io.Serializable;
import java.util.List;
import com.google.firebase.database.IgnoreExtraProperties;



/**
 * Created by Nava on 10/05/2017.
 */
@IgnoreExtraProperties
public class Event  implements Serializable {
     String name;
     String date;
     String time;
     String description;
     String bankAccountDetails;
     List<Guest> guestList;
     int counterGuests;
     String invitationPath;

    public Event()
    {

    }
    public Event(String name, String date, String time,String desc,String bankAccountDetails, String invitationPath, List<Guest> guestList) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.guestList = null;
        this.bankAccountDetails = bankAccountDetails;
        this.invitationPath = invitationPath;
        this.description = desc;
        this.counterGuests = 0;
    }

    public String getName() {
        return name;
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

    public List<Guest> getGuestList() {
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

    public void setGuestList(List<Guest> guestList) {
        this.guestList = guestList;
    }

    public void setCounterGuests(int counterGuests) {
        this.counterGuests = counterGuests;
    }

    public String getInvitationPath() {
        return invitationPath;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInvitationPath(String invitationPath) {
        this.invitationPath = invitationPath;
    }
}

