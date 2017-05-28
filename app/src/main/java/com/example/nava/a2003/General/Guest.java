package com.example.nava.a2003.General;


import java.io.Serializable;

/**
 * Created by Nava on 11/05/2017.
 */

public class Guest implements Serializable {

    private String name;
    private String email;
    private int seat;
    private boolean rsvp = false;


    public Guest() {

    }

    public Guest(String name, String email, int seat) {
        this.name = name;
        this.email = email;
        this.seat = seat;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
