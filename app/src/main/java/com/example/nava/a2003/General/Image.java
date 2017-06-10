package com.example.nava.a2003.General;

/**
 * Created by Nava on 09/06/2017.
 */

public class Image {
    String name;
    String url;

    public Image (){

    }

    public Image(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
