package com.example.staffrating;

import com.example.staffrating.controller.StaffRatingController;
import com.example.staffrating.model.RoleType;
import com.example.staffrating.model.StaffRating;
import com.example.staffrating.service.StaffRatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class StaffRatingControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private StaffRatingService service;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testGetIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("ratings"));
    }

    @Test
    public void testPostCreateSuccess() throws Exception {
        mockMvc.perform(post("/ratings")
                .param("name", "John")
                .param("email", "john.unique.test" + System.currentTimeMillis() + "@example.com")
                .param("roleType", "TA")
                .param("clarity", "8")
                .param("niceness", "9")
                .param("knowledgeableScore", "10")
                .param("comment", "Great!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testPostCreateFailure() throws Exception {
        // Missing name, invalid email, etc.
        mockMvc.perform(post("/ratings")
                .param("email", "invalid")
                .param("roleType", "TA")
                .param("clarity", "15")) // clarity > 10
                .andExpect(status().isOk())
                .andExpect(view().name("create"))
                .andExpect(model().hasErrors());
    }
}
