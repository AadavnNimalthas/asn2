package com.example.staffrating;

import com.example.staffrating.model.RoleType;
import com.example.staffrating.model.StaffRating;
import com.example.staffrating.repository.StaffRatingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StaffRatingRepositoryTest {

    @Autowired
    private StaffRatingRepository repository;

    @Test
    public void testSaveAndRetrieve() {
        StaffRating rating = new StaffRating();
        rating.setName("Alice Smith");
        rating.setEmail("alice@example.com");
        rating.setRoleType(RoleType.PROF);
        rating.setClarity(9);
        rating.setNiceness(9);
        rating.setKnowledgeableScore(10);

        StaffRating saved = repository.save(rating);
        assertNotNull(saved.getId());

        Optional<StaffRating> retrievedOpt = repository.findById(saved.getId());
        assertTrue(retrievedOpt.isPresent());
        assertEquals("Alice Smith", retrievedOpt.get().getName());
    }

    @Test
    public void testDelete() {
        StaffRating rating = new StaffRating();
        rating.setName("Bob Jones");
        rating.setEmail("bob@example.com");
        rating.setRoleType(RoleType.INSTRUCTOR);
        rating.setClarity(7);
        rating.setNiceness(8);
        rating.setKnowledgeableScore(7);

        StaffRating saved = repository.save(rating);
        Long id = saved.getId();

        repository.deleteById(id);

        Optional<StaffRating> deletedOpt = repository.findById(id);
        assertFalse(deletedOpt.isPresent());
    }
}
