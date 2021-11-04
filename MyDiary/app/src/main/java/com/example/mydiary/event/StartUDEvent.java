package com.example.mydiary.event;

/**
 * Created by Yuzhe You on 2021/10/25.
 * No.1159774
 */

public class StartUDEvent {

    private int position;

    public StartUDEvent(int position) {
        this.position = position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}