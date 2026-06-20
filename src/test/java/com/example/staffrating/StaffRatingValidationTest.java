package com.example.staffrating;

import com.example.staffrating.model.RoleType;
import com.example.staffrating.model.StaffRating;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StaffRatingValidationTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidRating() {
        StaffRating rating = new StaffRating();
        rating.setName("John Doe");
        rating.setEmail("john@example.com");
        rating.setRoleType(RoleType.TA);
        rating.setClarity(8);
        rating.setNiceness(9);
        rating.setKnowledgeableScore(10);
        rating.setComment("Great!");

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidEmail() {
        StaffRating rating = new StaffRating();
        rating.setName("John Doe");
        rating.setEmail("invalid-email"); // Invalid
        rating.setRoleType(RoleType.TA);
        rating.setClarity(8);
        rating.setNiceness(9);
        rating.setKnowledgeableScore(10);

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    public void testMissingName() {
        StaffRating rating = new StaffRating();
        rating.setEmail("john@example.com");
        rating.setRoleType(RoleType.TA);
        rating.setClarity(8);
        rating.setNiceness(9);
        rating.setKnowledgeableScore(10);

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")));
    }

    @Test
    public void testScoreOutsideBounds() {
        StaffRating rating = new StaffRating();
        rating.setName("John Doe");
        rating.setEmail("john@example.com");
        rating.setRoleType(RoleType.TA);
        rating.setClarity(11); // Invalid
        rating.setNiceness(0); // Invalid
        rating.setKnowledgeableScore(10);

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("clarity")));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("niceness")));
    }

    @Test
    public void testCommentTooLong() {
        StaffRating rating = new StaffRating();
        rating.setName("John Doe");
        rating.setEmail("john@example.com");
        rating.setRoleType(RoleType.TA);
        rating.setClarity(8);
        rating.setNiceness(9);
        rating.setKnowledgeableScore(10);
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 501; i++) {
            sb.append("a");
        }
        rating.setComment(sb.toString()); // 501 chars

        Set<ConstraintViolation<StaffRating>> violations = validator.validate(rating);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("comment")));
    }
}
