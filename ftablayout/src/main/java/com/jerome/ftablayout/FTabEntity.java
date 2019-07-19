package com.jerome.ftablayout;

public class FTabEntity {
    private String name;

    public FTabEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name;
    }
}
