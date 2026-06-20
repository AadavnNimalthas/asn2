package com.example.staffrating.model;

public class GenericStaffProfile implements StaffMemberProfile {
    @Override
    public String displayTitle(String name) {
        return "Staff Member " + name;
    }
}
