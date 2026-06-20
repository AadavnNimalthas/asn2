package com.example.staffrating;

import com.example.staffrating.controller.StaffRatingController;
import com.example.staffrating.model.RoleType;
import com.example.staffrating.model.StaffRating;
import com.example.staffrating.service.StaffRatingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StaffRatingController.class)
public class StaffRatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StaffRatingService service;

    @Test
    public void testGetIndex() throws Exception {
        when(service.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("ratings"));
    }

    @Test
    public void testPostCreateSuccess() throws Exception {
        when(service.emailExists(any())).thenReturn(false);

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

    @Test
    public void testPostUpdateSuccess() throws Exception {
        StaffRating existing = new StaffRating();
        existing.setId(1L);
        existing.setEmail("old@example.com");
        when(service.findById(1L)).thenReturn(java.util.Optional.of(existing));
        when(service.emailExists(any())).thenReturn(false);

        mockMvc.perform(post("/ratings/1")
                .param("name", "John Updated")
                .param("email", "john.updated@example.com")
                .param("roleType", "TA")
                .param("clarity", "9")
                .param("niceness", "9")
                .param("knowledgeableScore", "10")
                .param("comment", "Updated!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/ratings/1"));
    }

    @Test
    public void testPostDeleteSuccess() throws Exception {
        mockMvc.perform(post("/ratings/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
