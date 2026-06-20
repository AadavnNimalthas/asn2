package com.example.staffrating.model;

public class TAProfile implements StaffMemberProfile {
    @Override
    public String displayTitle(String name) {
        return "Teaching Assistant " + name;
    }
}
