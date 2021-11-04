package com.example.mydiary.utils;

public  class User {
    private int userID;
    private String userName;
    private String userSurname;
    private String userAddress;
    private String userEmail;
    private String userOib;
    private String userPassport;

    public User (){}
    public  User(int id, String name, String surname, String address, String email,
                         String oib, String passport){
        this.userID = id;
        this.userName= name;
        this.userSurname=surname;
        this.userAddress=address;
        this.userEmail=email;
        this.userOib=oib;
        this.userPassport=passport;
    }

public void setUser( String name, String surname, String address, String email,
                    String oib, String passport){

    this.userName= name;
    this.userSurname=surname;
    this.userAddress=address;
    this.userEmail=email;
    this.userOib=oib;
    this.userPassport=passport;
}


    public void setUserID(int userID) {
        this.userID = userID;
    }
    public int getUserID(){
        return this.userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName()
    {
        return this.userName;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }
    public String getUserSurname(){
        return this.userSurname;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserAddress() {
        return this.userAddress;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserOib(String userOib) {
        this.userOib = userOib;
    }

    public String getUserOib() {
        return this.userOib;
    }

    public void setUserPassport(String userPassport) {
        this.userPassport = userPassport;
    }

    public String getUserPassport() {
        return this.userPassport;
    }
}
