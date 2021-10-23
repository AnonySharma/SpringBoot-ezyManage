package com.ankit.ezymanage.model;

import java.util.UUID;

public class Role {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role() {
    }

    public Role(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public Role(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
