package com.dusenbery.firebase_authentication;

public class User {
    String firstName;
    String lastName;
    String Email;

    long createdAt;

    public User (){};
    public User(String firstName,String lastName, String email,long createdAt){
        this.firstName=firstName;
        this.lastName=lastName;
        this.Email=email;
        this.createdAt=createdAt;
    }


    public String getFirstname() {
        return firstName;
    }

    public String getLastname() {
        return firstName;
    }

    public String getEmail() {
        return Email;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
