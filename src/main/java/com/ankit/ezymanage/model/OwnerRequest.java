package com.ankit.ezymanage.model;

import java.util.Date;

public class OwnerRequest {
    private int id;
    private int userId;
    private Date date;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OwnerRequest() {
    }

    public OwnerRequest(int id, int userId, Date date, String status) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.status = status;
    }

    @Override
    public String toString() {
        return "OwnerRequest [date=" + date + ", id=" + id + ", status=" + status + ", userId=" + userId + "]";
    }

}
