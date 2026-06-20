package com.example.staffrating;

import com.example.staffrating.controller.StaffRatingController;
import com.example.staffrating.model.RoleType;
import com.example.staffrating.model.StaffRating;
import com.example.staffrating.service.StaffRatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class StaffRatingControllerTest {

    @Autowired
    private StaffRatingController controller;

    @Autowired
    private StaffRatingService service;

    @Test
    public void testGetIndex() {
        Model model = new ExtendedModelMap();
        String viewName = controller.index(model);
        assertEquals("index", viewName);
        assertTrue(model.containsAttribute("ratings"));
    }

    @Test
    public void testPostCreateSuccess() {
        StaffRating rating = new StaffRating();
        rating.setName("John");
        rating.setEmail("newtest@example.com");
        rating.setRoleType(RoleType.TA);
        rating.setClarity(8);
        rating.setNiceness(9);
        rating.setKnowledgeableScore(10);
        rating.setComment("Great!");

        BindingResult result = new BeanPropertyBindingResult(rating, "staffRating");
        Model model = new ExtendedModelMap();
        
        String viewName = controller.createRating(rating, result, model);
        assertEquals("redirect:/", viewName);
    }

    @Test
    public void testPostCreateFailureEmailExists() {
        StaffRating rating1 = new StaffRating();
        rating1.setName("Alice");
        rating1.setEmail("conflict@example.com");
        rating1.setRoleType(RoleType.TA);
        rating1.setClarity(8);
        rating1.setNiceness(9);
        rating1.setKnowledgeableScore(10);
        service.save(rating1);

        StaffRating rating2 = new StaffRating();
        rating2.setName("Bob");
        rating2.setEmail("conflict@example.com");
        rating2.setRoleType(RoleType.TA);
        rating2.setClarity(8);
        rating2.setNiceness(9);
        rating2.setKnowledgeableScore(10);

        BindingResult result = new BeanPropertyBindingResult(rating2, "staffRating");
        Model model = new ExtendedModelMap();
        
        String viewName = controller.createRating(rating2, result, model);
        assertEquals("create", viewName);
        assertTrue(result.hasErrors());
    }
}
