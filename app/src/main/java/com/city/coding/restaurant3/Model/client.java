package com.city.coding.restaurant3.Model;

import com.city.coding.restaurant3.Helper.Helper;

/*
each client should have this basic information
name , email , password , gender , phoneNumber
to create an account for the client.

* */
public class client {
    //static section
    //male
    private static final int MALE = 1 ;
    //female
    private static final int FEMALE = 2;
    //Other
    private static final int OTHER = 3;
    //end
    private String name ;
    private String Email ;
    private String password ;
    private int gender ;
    private String phoneNumber ;


    //constructor
    public client (String name , String Email , String password ,
                   int gender , String phoneNumber){
        setName(name);
        setEmail(Email);
        setPassword(password);
        setGender(gender);
        setPhoneNumber(phoneNumber);
    }

    //get client full name
    public String getName() {
        return name;
    }

    //get client Email
    public String getEmail() {
        return Email;
    }

    //get client password
    public String getPassword() {
        return password;
    }

    //get client gender
    public int getGender() {
        return gender;
    }

    //get client phone Number
    public String getPhoneNumber() {
        return phoneNumber;
    }

    //set client full name
    private void setName(String name) {
        this.name = name;
    }

    //get client Email
    private void setEmail(String email) {
        Email = email;
    }

    //get client password
    private void setPassword(String password) {
        this.password = password;
    }

    //get client gender
    private void setGender(int gender) {
        this.gender = gender;
    }

    //get client Phone Number
    private void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    //get all client information
    private String getClietnInfo(client c){
        StringBuilder client = new StringBuilder();
        // get all information from client object
        client.append("Name :"+c.getName()+"\n");
        client.append("Email : " + c.getEmail()+"\n");
        client.append("Gender : " +Helper.myGender(c.gender) + "\n");
        client.append("Phone Number : "+c.getPhoneNumber());
        //return client information as String
        return client.toString();
    }
    //end getClientInfo


}
