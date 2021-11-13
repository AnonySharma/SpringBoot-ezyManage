package com.ankit.ezymanage.model;

import java.sql.Timestamp;

public class Staff {
    private int staffId;
    private int shopId;
    private String name;
    private Timestamp dateOfJoining;
    private String designation;
    private int salary;

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(Timestamp dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public Staff() {
    }

    public Staff(int staffId, String name, Timestamp dateOfJoining, String designation, int salary, int shopId) {
        this.staffId = staffId;
        this.name = name;
        this.dateOfJoining = dateOfJoining;
        this.designation = designation;
        this.salary = salary;
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        return "Staff [dateOfJoining=" + dateOfJoining + ", designation=" + designation + ", id=" + staffId + ", name="
                + name + ", salary=" + salary + ", shopId=" + shopId + "]";
    }

}
