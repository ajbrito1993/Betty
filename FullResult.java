package com.example.alexbrito.betty;

import java.util.ArrayList;

public class FullResult {

    /*** Member Variables**/
    private String title;
    private  String value;

    private ArrayList<String> full;

    public FullResult(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
