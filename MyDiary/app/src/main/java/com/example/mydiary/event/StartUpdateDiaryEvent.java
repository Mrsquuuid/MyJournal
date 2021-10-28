package com.example.mydiary.event;

/**
 * Created by Yuzhe You on 2021/10/25.
 * No.1159774
 */

public class StartUpdateDiaryEvent {

    private int position;

    public StartUpdateDiaryEvent(int position) {
        this.position = position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}