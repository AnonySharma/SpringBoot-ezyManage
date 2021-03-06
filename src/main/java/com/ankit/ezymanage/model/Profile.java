package com.ankit.ezymanage.model;

import java.util.Date;

public class Profile {
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNumber;
    private Character gender;
    private String email;
    private String address;
    private String aadhaarNumber;
    private Date dateOfBirth;

    public Profile() {
    }

    public Profile(String username) {
        this.username = username;
    }

    public Profile(String username, String firstName, String middleName, String lastName, String phoneNumber,
            Character gender, Date dateOfBirth, String email, String address, String aadhaarNumber) {
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        if (gender == null)
            setGender('N');
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.address = address;
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(String aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Profile [aadhaarNumber=" + aadhaarNumber + ", address=" + address + ", dateOfBirth=" + dateOfBirth
                + ", email=" + email + ", firstName=" + firstName + ", gender=" + gender + ", lastName=" + lastName
                + ", middleName=" + middleName + ", phoneNumber=" + phoneNumber + ", username=" + username + "]";
    }

}
