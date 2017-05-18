package com.example.nava.a2003;

import android.provider.ContactsContract;

/**
 * Created by Nava on 11/05/2017.
 */

public class Guest {

    private String name;
    private String email;

    public Guest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
