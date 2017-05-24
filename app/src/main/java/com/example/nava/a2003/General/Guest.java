package com.example.nava.a2003.General;


/**
 * Created by Nava on 11/05/2017.
 */

public class Guest {

    private String name;
    private String PhoneNumber;
    private int seat;
    private  int amountOfGuests;


    public Guest() {

    }

    public Guest(String name, String phoneNumber, int seat, int amountOfGuests) {
        this.name = name;
        PhoneNumber = phoneNumber;
        this.seat = seat;
        this.amountOfGuests = amountOfGuests;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getAmountOfGuests() {
        return amountOfGuests;
    }

    public void setAmountOfGuests(int amountOfGuests) {
        this.amountOfGuests = amountOfGuests;
    }
}
