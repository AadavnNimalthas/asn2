package com.example.staffrating.model;

public class ProfessorProfile implements StaffMemberProfile {
    @Override
    public String displayTitle(String name) {
        return "Professor " + name;
    }
}
