package com.example.staffrating.model;

public class ProfileFactory {
    public static StaffMemberProfile getProfile(RoleType roleType) {
        if (roleType == null) return new GenericStaffProfile();
        switch (roleType) {
            case TA:
                return new TAProfile();
            case PROF:
                return new ProfessorProfile();
            case INSTRUCTOR:
                return new InstructorProfile();
            case STAFF:
            default:
                return new GenericStaffProfile();
        }
    }
}
