package com.royal.androidclub25sun11.model;

import androidx.annotation.NonNull;

public class UserModel {

        String firstName;
        String lastName;
        Integer credit;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    @Override
    public String toString() {
        return firstName+"  "+lastName+" "+credit;
    }
}


