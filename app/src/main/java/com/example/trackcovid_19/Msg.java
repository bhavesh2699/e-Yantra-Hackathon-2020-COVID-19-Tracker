package com.example.trackcovid_19;

public class Msg {
    private String text;
    private int type;
    public Msg(String text, int type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public int getType() {
        return type;
    }
}
