package com.example.mydiary.utils;

import java.io.Serializable;


public class Story implements Serializable {

    private String story;
    private String title;
    private byte [] image;
    private int ID;

    public Story(){}

    public Story (String s, String t, int id, byte[] image){
        this.story= s;
        this.title = t;
        this.ID = id;
        this.image = image;
    }
    public void setStory(String story) {
        this.story = story;
    }
    public String getStory(){
        return this.story;
    }
    public void setStoryTitle(String title) {
        this.title = title;
    }
    public String getStoryTitle(){
        return this.title;
    }
    public void setStoryID (int id){
       this.ID = id;
    }
    public int getStoryID(){
        return this.ID;
    }
    public void setStoryImage(byte[] image){this.image = image;}
    public byte[] getStoryImage(){return this.image;}


}
