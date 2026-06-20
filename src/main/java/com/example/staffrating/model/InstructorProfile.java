package com.example.staffrating.model;

public class InstructorProfile implements StaffMemberProfile {
    @Override
    public String displayTitle(String name) {
        return "Instructor " + name;
    }
}
